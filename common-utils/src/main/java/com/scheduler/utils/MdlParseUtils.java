package com.scheduler.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scheduler.mdl.MdlDocument;
import com.scheduler.mdl.behavior.*;
import com.scheduler.mdl.description.AttributeSet;
import com.scheduler.mdl.description.Category;
import com.scheduler.mdl.description.LocalAttribute;
import com.scheduler.mdl.environment.*;
import com.scheduler.mdl.environment.Runtime;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * MDL转换工具类
 * @Author: wangming
 * @Date: 2019-12-28 22:47
 */
public class MdlParseUtils {

    public static MdlDocument convertMdl(String mdl){
        MdlDocument mdlDocument = new MdlDocument();
        try {
            Document mdlDoc = DocumentHelper.parseText(mdl);
            Element rootElement = mdlDoc.getRootElement();
            //解析mdl的根节点信息
            mdlDocument.setName(rootElement.attributeValue("name"));
            mdlDocument.setUid(rootElement.attributeValue("uid"));
            //mdl此处节点内容可能是style或者type
            if(rootElement.attributeValue("type") == null){
                mdlDocument.setType(rootElement.attributeValue("style"));
            }else{
                mdlDocument.setType(rootElement.attributeValue("type"));
            }

            //处理模型描述接口信息
            Element attributeSetElement = rootElement.element("AttributeSet");
            mdlDocument.setAttributeSet(handleAttributeInfo(attributeSetElement));

            //处理模型执行行为接口信息
            Element behaviorElement = rootElement.element("Behavior");
            mdlDocument.setBehavior(handleBehaviorInfo(behaviorElement));

            //处理模型运行环境依赖接口信息
            Element runtimeElement = rootElement.element("Runtime");
            mdlDocument.setRuntime(handlerRuntimeInfo(runtimeElement));

        }catch (DocumentException e){
            e.printStackTrace();
            return null;
        }
        return mdlDocument;
    }

    /**
     * 处理模型描述接口
     * @param atttibuteSetElement
     * @return com.scheduler.mdl.description.AttributeSet
     * @author wangming
     * @date 2019/12/30 11:08
     */
    private static AttributeSet handleAttributeInfo(Element atttibuteSetElement){
        AttributeSet attributeSet = new AttributeSet();
        List<Category> categories = new ArrayList<>();
        List<LocalAttribute> localAttributes = new ArrayList<>();
        //处理类别信息
        List<Element> categoryElement = atttibuteSetElement.element("Categories").elements();
        if(categoryElement.size() > 0){
            for (Element element: categoryElement){
                Category category = new Category();
                category.setPrinciple(element.attributeValue("principle"));
                category.setPath(element.attributeValue("path"));
                categories.add(category);
            }
        }

        //处理描述信息
        List<Element> localAttributeElement = atttibuteSetElement.element("LocalAttributes").elements();
        if(localAttributeElement.size() > 0){
            for(Element element : localAttributeElement){
                LocalAttribute localAttribute = new LocalAttribute();
                localAttribute.setLocal(element.attributeValue("local"));
                localAttribute.setLocalName(element.attributeValue("localName"));
                localAttribute.setWiki(element.attributeValue("wiki"));
                localAttribute.setAbstracts(element.element("Keywords").getText());
                localAttribute.setKeywords(element.element("Abstract").getText());
                localAttributes.add(localAttribute);
            }
        }
        attributeSet.setCategories(categories);
        attributeSet.setLocalAttributes(localAttributes);
        return attributeSet;
    }

    /**
     * 处理模型执行接口
     * @param behaviorElement
     * @return com.scheduler.mdl.behavior.Behavior
     * @author wangming
     * @date 2019/12/30 11:11
     */
    private static Behavior handleBehaviorInfo(Element behaviorElement){
        Behavior behavior = new Behavior();
        //////////////////////////////////  UDX Dataset开始   ////////////////////////////////////////////////
        Element relatedDatasetsElement = behaviorElement.element("RelatedDatasets");
        if(relatedDatasetsElement == null){
            //兼容版本
            relatedDatasetsElement = behaviorElement.element("DatasetDeclarations");
        }
        List<Element> DatasetItems = relatedDatasetsElement.elements();
        List<DatasetItem> datasetItemList = new ArrayList<>();
        if(DatasetItems.size() > 0){
            for (Element item : DatasetItems){
                DatasetItem datasetItem = new DatasetItem();
                datasetItem.setName(item.attributeValue("name"));
                datasetItem.setType(item.attributeValue("type"));
                datasetItem.setDescription(item.attributeValue("des"));
                if (item.attributeValue("type").equals("external")){
                    //如果udx schema是external类型的话
                    String external = "";
                    if(item.attribute("externalId") != null){
                        external = item.attributeValue("externalId");
                        datasetItem.setId(external);
                    }else if(item.attribute("external") != null){
                        external = item.attributeValue("external");
                        datasetItem.setId(external);
                    }
                    datasetItem.setParentId("null");
                    datasetItemList.add(datasetItem);
                }else {
                    //如果udx schema是internal类型
                    Element UDXDeclaration;
                    if(item.element("UdxDeclaration") != null){
                        UDXDeclaration = item.element("UdxDeclaration");
                    }else {
                        UDXDeclaration = item.element("UDXDeclaration");
                    }
                    String rootId = "";
                    if(UDXDeclaration.attribute("id") != null){
                        rootId = "root" + UDXDeclaration.attributeValue("id");
                    }else {
                        rootId = "root" + UUID.randomUUID().toString();
                    }
                    datasetItem.setId(rootId);
                    datasetItem.setParentId("null");

                    //处理UDXNode数据
                    Element udxNode;
                    if(UDXDeclaration.element("UDXNode") != null){
                        udxNode = UDXDeclaration.element("UDXNode");
                    }else {
                        udxNode = UDXDeclaration.element("UdxNode");
                    }
                    List<Element> UdxNodes = udxNode.elements();
                    if(UdxNodes.size() > 0){
                        //临时添加一个JSONObject对象
                        JSONObject temp = new JSONObject();
                        temp.put("nodes", new JSONArray());
                        //处理udx data
                        handlerUDXData(UdxNodes,temp);
                        //转换JSONObject对象结果至具体对象
                        datasetItem.setDataset((JSONArray) temp.get("nodes"));
                    }
                    datasetItemList.add(datasetItem);
                }
            }
            behavior.setRelatedDatasets(datasetItemList);
        }
        ////////////////////////////////////////  UDX Dataset结束   ///////////////////////////////////////////////////////////////////

        ///////////////////////////////////////   State模块解析开始  /////////////////////////////////////////////////////////////////
        Element States = behaviorElement.element("StateGroup").element("States");
        List<Element> StateList = States.elements();
        List<State> states = new ArrayList<>();
        if(StateList.size() > 0){
            for(Element stateElement: StateList){
                State state = new State();
                state.setId(stateElement.attributeValue("id"));
                state.setName(stateElement.attributeValue("name"));
                state.setType(stateElement.attributeValue("type"));
                state.setDescription(stateElement.attributeValue("description"));
                //处理State下的Event
                List<Element> EventList = stateElement.elements();
                List<Event> eventList = new ArrayList<>();
                for(Element eventElement : EventList){
                    Event event = new Event();
                    event.setId(UUID.randomUUID().toString());
                    event.setName(eventElement.attributeValue("name"));
                    event.setType(eventElement.attributeValue("type"));
                    event.setDescription(eventElement.attributeValue("description"));
                    Element Parameter_element = null;
                    if (eventElement.attributeValue("type").equals("response")) {
                        Parameter_element = eventElement.element("ResponseParameter");
                    }else {
                        Parameter_element = eventElement.element("DispatchParameter");
                    }
                    if (eventElement.attribute("optional") != null){
                        if(eventElement.attributeValue("optional").equalsIgnoreCase("True")){
                            if (eventElement.element("ControlParameter") != null){
                                Parameter_element = eventElement.element("ControlParameter");
                            }
                            event.setOptional(true);
                        }else{
                            event.setOptional(false);
                        }
                    }
                    Parameter parameter = new Parameter();
                    parameter.setDesceiption(Parameter_element.attributeValue("description"));
                    parameter.setDatasetReferenceId(Parameter_element.attributeValue("datasetReference"));
                    event.setParameter(parameter);
                    eventList.add(event);
                }
                state.setEvents(eventList);
                states.add(state);
            }
            behavior.setStates(states);
        }
        ///////////////////////////////////////   State模块解析结束  /////////////////////////////////////////////////////////////////
        return behavior;
    }

    /**
     * TODO 节点类型以及节点数据正确解析存在问题
     * 递归处理udx data 节点
     * @param udxNodes
     * @param root
     * @return com.alibaba.fastjson.JSONArray
     * @author wangming
     * @date 2019/12/30 15:10
     */
    private static void handlerUDXData(List<Element> udxNodes, JSONObject root){
        if (udxNodes.size() > 0) {
            for (Element udxNode : udxNodes) {
                JSONObject node = new JSONObject();
                node.put("text", udxNode.attributeValue("name"));
                String dataType=udxNode.attributeValue("type");
                String dataType_result="";
                switch (dataType) {
                    case "DTKT_INT | DTKT_LIST":
                        dataType_result = "int_array";
                        break;
                    default:
                        String[] strings=dataType.split("_");
                        for(int i=0;i<strings.length;i++){
                            if(!strings[i].equals("DTKT")){
                                dataType_result+=strings[i];
                                if(i!=strings.length-1){
                                    dataType_result+="_";
                                }
                            }
                        }
                }
                node.put("dataType", dataType_result);
                node.put("desc", udxNode.attributeValue("description"));
                if (udxNode.attributeValue("type").equals("external")) {
                    node.put("externalId", udxNode.attributeValue("externalId"));
                }
                List<Element> nodeChildren = udxNode.elements();
                if (nodeChildren.size() > 0) {
                    node.put("nodes", new JSONArray());
                    handlerUDXData(nodeChildren, node);
                }
                JSONArray nodes = root.getJSONArray("nodes");
                nodes.add(node);
            }
        } else {
            return;
        }
    }

    /**
     * TODO
     * 处理模型运行环境依赖接口
     * @param runtimeElement
     * @return com.scheduler.mdl.environment.Runtime
     * @author wangming
     * @date 2019/12/30 11:15
     */
    private static Runtime handlerRuntimeInfo(Element runtimeElement){
        Runtime runtime = new Runtime();
        runtime.setName(runtimeElement.attributeValue("name"));
        runtime.setVersion(runtimeElement.attributeValue("version"));
        runtime.setBaseDir(runtimeElement.attributeValue("baseDir"));
        runtime.setEntry(runtimeElement.attributeValue("entry"));
        //处理硬件环境信息，需要兼容老旧版本MDL(老版本MDL是INSERT标签，新版本是ADD标签)
        List<Element> hardwareConfigure_element = runtimeElement.element("HardwareConfigures").elements();
        List<HardwareConfigure> hardwareConfigures = new ArrayList<>();
        if(hardwareConfigure_element.size() > 0){
            if(hardwareConfigure_element.get(0).getName().equals("INSERT")){
                // 是INSERT标签模式
                for (Element element: hardwareConfigure_element){
                    HardwareConfigure hardwareConfigure = new HardwareConfigure();
                    hardwareConfigure.setName(element.attributeValue("name"));
                    hardwareConfigure.setValue(element.getText());
                    hardwareConfigures.add(hardwareConfigure);
                }
            }else {
                //是ADD标签形式
                for(Element element: hardwareConfigure_element){
                    HardwareConfigure hardwareConfigure = new HardwareConfigure();
                    hardwareConfigure.setName(element.attributeValue("key"));
                    hardwareConfigure.setValue(element.attributeValue("value"));
                    hardwareConfigures.add(hardwareConfigure);
                }
            }
        }
        runtime.setHardwareConfigures(hardwareConfigures);

        //处理软件环境信息，需要兼容老旧版本
        List<Element> softwareConfigure_element = runtimeElement.element("SoftwareConfigures").elements();
        List<SoftwareConfigure> softwareConfigures = new ArrayList<>();
        if(softwareConfigure_element.size() > 0){
            if(softwareConfigure_element.get(0).getName().equals("INSERT")){
                //INSERT标签模式
                for(Element element: softwareConfigure_element){
                    SoftwareConfigure softwareConfigure = new SoftwareConfigure();
                    softwareConfigure.setName(element.attributeValue("name"));
                    softwareConfigure.setPlatform(element.attributeValue("platform"));
                    softwareConfigure.setVersion(element.getText());
                    softwareConfigures.add(softwareConfigure);
                }
            }
        }else {
            //ADD标签模式
            for (Element element: softwareConfigure_element){
                SoftwareConfigure softwareConfigure = new SoftwareConfigure();
                softwareConfigure.setName(element.attributeValue("key"));
                softwareConfigure.setVersion(element.attributeValue("value"));
                //兼容师兄封装模型的mdl文档
                if(element.attributeValue("platform") == null){
                    //如果不存在，就设置为全平台，环境匹配的时候可以直接忽略该字段
                    softwareConfigure.setPlatform("all");
                }else{
                    softwareConfigure.setPlatform(element.attributeValue("platform"));
                }
                softwareConfigures.add(softwareConfigure);
            }
        }
        runtime.setSoftwareConfigures(softwareConfigures);

        //处理Assemblies
        List<Element> assemblies_element = runtimeElement.element("Assemblies").elements();
        List<Assembly> assemblies = new ArrayList<>();
        if (assemblies_element.size() > 0){
            for (Element element : assemblies_element){
                Assembly assembly = new Assembly();
                assembly.setName(element.attributeValue("name"));
                assembly.setPath(element.attributeValue("path"));
                assemblies.add(assembly);
            }
        }
        runtime.setAssemblies(assemblies);

        //处理SupportiveResources
        List<Element> supportive_element = runtimeElement.element("SupportiveResources").elements();
        List<SupportiveResource> supportiveResources = new ArrayList<>();
        if(supportive_element.size() > 0){
            for (Element element: supportive_element){
                SupportiveResource supportiveResource = new SupportiveResource();
                supportiveResource.setName(element.attributeValue("name"));
                supportiveResource.setType(element.attributeValue("type"));
                supportiveResources.add(supportiveResource);
            }
        }
        runtime.setSupportiveResources(supportiveResources);
        return runtime;
    }
}
