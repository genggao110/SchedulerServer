package com.scheduler.managerserver.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scheduler.managerserver.dao.ComputerInfoDao;
import com.scheduler.managerserver.dao.DeployTaskDao;
import com.scheduler.managerserver.dto.DeployedResourceFindDTO;
import com.scheduler.managerserver.dto.ModelResourceFindDTO;
import com.scheduler.managerserver.dto.computer.RecommendComputer;
import com.scheduler.managerserver.dto.modelresource.ModelDeployDTO;
import com.scheduler.managerserver.dto.modelresource.ModelEnvironment;
import com.scheduler.managerserver.po.ComputerInfo;
import com.scheduler.managerserver.po.DeployTask;
import com.scheduler.managerserver.po.Resource;
import com.scheduler.managerserver.pojo.JudgeHardware;
import com.scheduler.managerserver.pojo.computer.Hardware;
import com.scheduler.managerserver.pojo.computer.Optional;
import com.scheduler.managerserver.pojo.computer.Software;
import com.scheduler.managerserver.pojo.resource.HardwareInfo;
import com.scheduler.managerserver.pojo.resource.SoftwareInfo;
import com.scheduler.managerserver.vo.ComputerServerInfo;
import com.scheduler.managerserver.vo.ModelInfoVO;
import com.scheduler.managerserver.vo.ModelResourceVO;
import com.scheduler.mdl.MdlDocument;
import com.scheduler.utils.MdlParseUtils;
import com.scheduler.utils.MyHttpUtils;
import com.scheduler.utils.MyStringUtils;
import com.scheduler.webCommons.enums.ResultEnum;
import com.scheduler.webCommons.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 从门户获取可用的计算资源
 * @Author: wangming
 * @Date: 2019-12-28 22:02
 */
@Service
public class ModelResourceService {

    @Value("${prop.portalAddress}")
    private String PORTAL_ADDRESS;

    @Value("${prop.managerAddress}")
    private String MANAGER_ADDRESS;

    @Autowired
    ComputerInfoService computerInfoService;

    @Autowired
    ProjectService projectService;

    @Autowired
    ComputerInfoDao computerInfoDao;

    @Autowired
    DeployTaskDao deployTaskDao;

    public List<ModelInfoVO> getModelResourcePackage(int page, String sortType, int sortAsc ){
        List<ModelInfoVO> modelInfoVO = getModelResource(page,sortType,sortAsc);
        return modelInfoVO;
    }

    public JSONObject listModelResourceFromPortal(ModelResourceFindDTO modelResourceFindDTO){
        JSONObject result_json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        params.put("asc", modelResourceFindDTO.getAsc().toString());
        params.put("page", modelResourceFindDTO.getPage().toString());
        params.put("pageSize", modelResourceFindDTO.getPageSize().toString());
        params.put("searchText", modelResourceFindDTO.getSearchText());
        params.put("type", modelResourceFindDTO.getType());

        String url = PORTAL_ADDRESS + "/computableModel/listWithType";
        String result = "";
        try {
            result = MyHttpUtils.POST(url,"UTF-8",null, params,null);
            JSONObject jResponse = JSON.parseObject(result);
            if(jResponse.getIntValue("code") == 0){
                //表明是成功获取
                JSONObject data = jResponse.getJSONObject("data");
                result_json.put("total", data.getIntValue("total"));
                result_json.put("pages", data.getIntValue("pages"));
                JSONArray list_Array = data.getJSONArray("list");
                List<ModelResourceVO> modelResourceVOList = new ArrayList<>();
                for(int i =0; i< list_Array.size(); i++){
                    ModelResourceVO modelResourceVO = handleModelPackageData(list_Array.getJSONObject(i));
                    modelResourceVOList.add(modelResourceVO);
                }
                result_json.put("resource",modelResourceVOList);
                result_json.put("users", data.getJSONArray("users"));
            }

        }catch (IOException e) {
            e.printStackTrace();
            result_json = null;
        }
        return result_json;
    }

    //从门户获取所有可以调用的计算模型(门户数据库deploy字段为true的情况)
    public JSONObject listDeployedComputerModelFromPortal(DeployedResourceFindDTO deployedResourceFindDTO){
        JSONObject result = new JSONObject();
        //拼凑参数
        Map<String,String> params = new HashMap<>();
        params.put("asc", deployedResourceFindDTO.getAsc().toString());
        params.put("page", deployedResourceFindDTO.getPage().toString());
        params.put("pageSize", deployedResourceFindDTO.getPageSize().toString());
        params.put("searchText", deployedResourceFindDTO.getSearchText());

        String url = PORTAL_ADDRESS + "/computableModel/listDeployed";
        String response = "";
        try{
            response = MyHttpUtils.POST(url, "UTF-8",null,params,null);
            JSONObject jResponse = JSON.parseObject(response);
            if(jResponse.getIntValue("code") == 0){
                JSONObject data = jResponse.getJSONObject("data");
                result.put("total", data.getIntValue("total"));
                result.put("pages", data.getIntValue("pages"));
                //处理计算模型列表
                JSONArray array_list = data.getJSONArray("list");
                List<ComputerServerInfo> computerServerInfos = new ArrayList<>();
                for(int i = 0; i < array_list.size(); i++){
                    ComputerServerInfo computerServerInfo = handleComputerModelData(array_list.getJSONObject(i));
                    computerServerInfos.add(computerServerInfo);
                }
                result.put("list",computerServerInfos);
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    public JSONObject getModelResourceByOid(String oid){
        JSONObject result_json = new JSONObject();
        String url = PORTAL_ADDRESS + "/computableModel/getInfo/" + oid;
        String result = "";
        try{
            result = MyHttpUtils.GET(url, "UTF-8", null);
            JSONObject resJson = JSON.parseObject(result);
            if(resJson.getIntValue("code") == 0){
                //表明是成功获取
                JSONObject data = resJson.getJSONObject("data");
                ModelResourceVO modelResourceVO = handleModelPackageData(data);
                //单独对runtime节点信息进行处理
                if(data.getString("runtime") == null){
                    //对mdl信息进行额外处理
                    String mdlString = data.getString("mdlJson");
                    JSONObject mdlJson = JSON.parseObject(mdlString);
                    JSONObject modelClass=mdlJson.getJSONArray("ModelClass").getJSONObject(0);
                    JSONObject runtime = convertMDLRuntimeToJSON(modelClass.getJSONArray("Runtime").getJSONObject(0));
                    modelResourceVO.setRuntime(runtime.toJSONString());
                }
                result_json.put("info", modelResourceVO);
            }
        }catch (IOException e) {
            e.printStackTrace();
            result_json = null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            result_json = null;
        }
        return result_json;
    }

    public JSONObject getModelResource(){
        JSONObject result_json = new JSONObject();
        String url = PORTAL_ADDRESS + "/computableModel/getAllInfo" ;
        String result = "";
        try{
            result = MyHttpUtils.GET(url, "UTF-8", null);
            JSONObject resJson = JSON.parseObject(result);
            if(resJson.getIntValue("code") == 0){
                //表明是成功获取
                JSONObject data = resJson.getJSONObject("data");
                ModelResourceVO modelResourceVO = handleModelPackageData(data);
                //单独对runtime节点信息进行处理
                if(data.getString("runtime") == null){
                    //对mdl信息进行额外处理
                    String mdlString = data.getString("mdlJson");
                    JSONObject mdlJson = JSON.parseObject(mdlString);
                    JSONObject modelClass=mdlJson.getJSONArray("ModelClass").getJSONObject(0);
                    JSONObject runtime = convertMDLRuntimeToJSON(modelClass.getJSONArray("Runtime").getJSONObject(0));
                    modelResourceVO.setRuntime(runtime.toJSONString());
                }
                result_json.put("info", modelResourceVO);
            }
        }catch (IOException e) {
            e.printStackTrace();
            result_json = null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            result_json = null;
        }
        return result_json;
    }

    // 返回符合条件的计算资源id (代码就先这样写吧，后面有需要可以进行优化)
    public List<RecommendComputer> getSuitableComputerForDeploy(ModelEnvironment modelEnvironment){
        List<RecommendComputer> result = new ArrayList<>();
        //首先获取到所有可用的计算资源
        List<ComputerInfo> computerInfos = computerInfoDao.findAllByStatus(true);
        //1. 首先根据platform字段进行计算资源的筛选(匹配操作系统和版本)
        List<ComputerInfo> suiter1 = computerInfos.stream().filter(computerInfo -> {
            //windows需要单独进行判断
            if(modelEnvironment.getPlatform().toLowerCase().indexOf("windows") != -1){
                //获取到需要的位数
                int position = modelEnvironment.getPlatform().lastIndexOf("_");
                if(position == -1){
                    //说明是全平台
                    return computerInfo.getSystem().equals("Windows_NT");
                }else {
                    String bit = modelEnvironment.getPlatform().substring(position+1).toLowerCase();
                    return computerInfo.getSystem().equals("Windows_NT") && bit.equals(computerInfo.getOs_bit());
                }
            }else {
                //直接进行匹配
                if(modelEnvironment.getPlatform().toLowerCase().equals(computerInfo.getSystem())){
                    return true;
                }else {
                    return false;
                }
            }
        }).collect(Collectors.toList());

        //2. 对于软件环境，由于环境匹配的复杂性，这里就只匹配操作系统和版本号(windows直接匹配版本号，linux的操作系统还需要匹配版本号),其他软件环境默认匹配，后期希望完善
        List<SoftwareInfo> softwareInfos = modelEnvironment.getSoftware();
        SoftwareInfo softwareInfo = null;
        for(int i = 0; i < softwareInfos.size(); i++){
            SoftwareInfo temp_software = softwareInfos.get(i);
            if(temp_software.getName().equals("Operation System")){
                softwareInfo = temp_software;
                break;
            }
        }
        List<ComputerInfo> suiter2 = suiter1;
        if(softwareInfo != null){
            //说明存在运行环境需求的信息，需要进行结果的筛选,符合条件的留下
            String name = softwareInfo.getValue();
            //默认没有填写版本的都是null
            String minVersion = softwareInfo.getMinVersion();
            String maxVersion = softwareInfo.getMaxVersion();
            suiter2 = suiter1.stream().filter(computerInfo -> {
                if(name.toLowerCase().equals(computerInfo.getSoftware_info().getOs().toLowerCase())){
                    //首先进行小版本匹配
                    if(minVersion.equals("null")){
                        if (maxVersion.equals("null")){
                            //说明任意版本都适合
                            return true;
                        }else if(MyStringUtils.compareAppVersion(maxVersion, computerInfo.getSoftware_info().getOsVersion()) >= 0){
                            return true;
                        }else {
                            return false;
                        }
                    }else {
                        //说明存在小版本
                        if(MyStringUtils.compareAppVersion(minVersion, computerInfo.getSoftware_info().getOsVersion()) > 0){
                            //要求的最小版本都超过操作系统的版本，说明不适合
                            return false;
                        }else {
                            if (maxVersion.equals("null")){
                                return true;
                            }else if(MyStringUtils.compareAppVersion(maxVersion, computerInfo.getSoftware_info().getOsVersion()) >= 0) {
                                return true;
                            }else {
                                return false;
                            }
                        }
                    }
                }else {
                    return false;
                }
            }).collect(Collectors.toList());
        }
        //3. 匹配硬件环境信息
        final List<HardwareInfo> hardwareInfos = modelEnvironment.getHardware();
        List<ComputerInfo> suiter3 = suiter2.stream().filter(computerInfo -> {
            for(int i = 0; i <hardwareInfos.size(); i++){
                String name = hardwareInfos.get(i).getName();
                String value = hardwareInfos.get(i).getValue();
                if(!matchHardware(name,value,computerInfo)){
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());

        //TODO 4. 软件环境的匹配(这里默认软件环境都是可以的，参与式部署，因为发现都不符合。。。)

        //5.调用择优推荐算法的推荐
        if(suiter3.size() > 1){
            //当有两个以上的时候才需要调用择优推荐算法
            result = preferred_Recommend(suiter3);
        }else if(suiter3.size() == 1) {
            RecommendComputer recommendComputer = new RecommendComputer();
            recommendComputer.setOid(suiter3.get(0).getId());
            recommendComputer.setMac(suiter3.get(0).getMac());
            //随意填写一个字段即可
            recommendComputer.setScore(3.96);
            result.add(recommendComputer);
            return result;
        }
        return result;

    }

    // 模型资源部署包在计算资源上的部署
    public DeployTask deployModel(ModelDeployDTO modelDeployDTO){

        String agentId = modelDeployDTO.getAgentId();
        DeployTask deployTask = new DeployTask();
        //1. 根据agentId 从Manager Server服务器获取到相关Task Server的ip和port
        String url = MANAGER_ADDRESS + "/GeoModeling/taskNode/" + agentId;
        try{
            String taskServer = MyHttpUtils.GET(url, "UTF-8", null);
            JSONObject taskServer_Json = JSONObject.parseObject(taskServer);
            if(taskServer_Json.getIntValue("code") == 1){
                JSONObject task_data = taskServer_Json.getJSONObject("data");
                String task_ip = task_data.getString("host");
                int port = task_data.getIntValue("port");
                // 将部署任务post到具体的task server
                String deployUrl = "http://" + task_ip + ":" + port + "/deploy";
                //数据体为json
                JSONObject params = new JSONObject();
                params.put("modelPackagePath", modelDeployDTO.getPackagePath());
                params.put("mac", modelDeployDTO.getMac());
                params.put("name", modelDeployDTO.getModelName());
                params.put("user", modelDeployDTO.getUserName());
                params.put("pid", modelDeployDTO.getMd5());
                String deploy_res = MyHttpUtils.POSTWithJSON(deployUrl,"UTF-8",null,params);
                JSONObject deploy_res_Json = JSONObject.parseObject(deploy_res);
                if(deploy_res_Json.getString("result").equals("suc")){
                    //基本属性赋值
                    deployTask.setIp(task_ip);
                    deployTask.setPort(port);
                    deployTask.setMac(modelDeployDTO.getMac());
                    deployTask.setOid(modelDeployDTO.getOid());
                    deployTask.setModelName(modelDeployDTO.getModelName());
                    deployTask.setType(modelDeployDTO.getType());

                    JSONObject res_data = deploy_res_Json.getJSONObject("data");
                    String task_Id = res_data.getString("task_id");
                    int status = res_data.getIntValue("status");
                    if(status == 1){
                        //更新状态到门户
                        boolean update_status = updateDeployStatusToPortal(modelDeployDTO.getOid());
                        if(!update_status){
                            deployTask.setStatus(0);
                        }else {
                            deployTask.setStatus(1);
                        }
                    }else {
                        deployTask.setStatus(status);
                    }
                    deployTask.setTaskId(task_Id);
                    //插入数据库记录
                    deployTaskDao.insert(deployTask);

                    String projectId = modelDeployDTO.getProjectId();
                    projectService.changeStatus(projectId);

                    return deployTask;
                }
            }else {
                throw new MyException(ResultEnum.NO_OBJECT);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    //更新模型部署的数据库记录，同时还要更新到门户
    public boolean updateDeployRecord(DeployTask deployTask,int status){
        if (status == 1){
            boolean update_status = updateDeployStatusToPortal(deployTask.getOid());
            if(update_status){
                deployTask.setStatus(1);
            }else {
                deployTask.setStatus(0);
            }
        }else {
            deployTask.setStatus(status);
        }
        deployTaskDao.save(deployTask);
        return true;
    }

    public boolean updateDeployStatusToPortal(String oid){
        String url = PORTAL_ADDRESS + "/computableModel/updateDeployStatus/" + oid;
        String result = "";
        try{
            result = MyHttpUtils.GET(url, "UTF-8", null);
            JSONObject resJson = JSON.parseObject(result);
            if(resJson.getIntValue("code") == 0){
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return true;
    }

    //刷新部署任务记录
    public DeployTask refreshDeployRecord(DeployTask deployTask){
        int status = deployTask.getStatus();
        if(status == 0){
            //等于0的时候表明处于等待状态，因为可能存在task server未能成功同步状态的情况，需要发起请求
            String url = "http://" + deployTask.getIp() + ":" + deployTask.getPort() + "/deploy/" + deployTask.getTaskId();
            try{
                String resJson = MyHttpUtils.GET(url, "UTF-8", null);
                JSONObject jResponse = JSON.parseObject(resJson);
                if(jResponse.getString("result").equals("suc")){
                    JSONObject jData = jResponse.getJSONObject("data");
                    if(jData == null){
                        return deployTask;
                    }
                    String taskStatus = jData.getString("d_status");
                    if(!taskStatus.equals("Inited")){
                        if (taskStatus.equals("Finished")){
                            //更新状态到门户,如果门户那边状态未改变，这个模型也没办法用
                            boolean update_status = updateDeployStatusToPortal(deployTask.getOid());
                            if(update_status){
                                deployTask.setStatus(1);
                            }else {
                                deployTask.setStatus(0);
                            }
                        }if(taskStatus.equals("Error")){
                            deployTask.setStatus(-1);
                        }
                        deployTaskDao.save(deployTask);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return deployTask;
    }

    //硬件环境的匹配(因为存在单位的转换，所以需要根据环境字典从而区分不同的类型)
    private boolean matchHardware(String name, String value, ComputerInfo computerInfo){
        Hardware staticInfo = computerInfo.getHardware_info().getStaticInfo();
        if(name.equals("CPU_Type")){
            //CPU类型就是个噱头，直接返回
            return true;
        }else if(name.equals("CPU_Core")){
            int core_count = Integer.parseInt(value);
            if(staticInfo.getCpu_core() >= core_count){
                return true;
            }else{
                return false;
            }
        }else if(name.equals("CPU_Frequency")){
            //单位都是GHz，所以不用进行单位转换
            double cpu_frequency_need = Double.parseDouble(value.substring(0,value.length() - 3));
            double cpu_offer = Double.parseDouble(staticInfo.getCpu_frequency().substring(0,staticInfo.getCpu_frequency().length() - 3));
            return (cpu_offer >= cpu_frequency_need);
        }else if(name.equals("Disk_Size")){
            int index = getIndex(value);
            double size_value = Double.parseDouble(value.substring(0, index+1));
            String unit = value.substring(index+1);
            int computer_index = getIndex(staticInfo.getDisk_size());
            double computer_size = Double.parseDouble(staticInfo.getDisk_size().substring(0, computer_index +1));
            if(unit.equals("KB")|| unit.equals("K")){
                size_value = size_value / 1024 / 1024;
                return computer_size > size_value;
            }else if(unit.equals("MB") || unit.equals("M")){
                size_value = size_value / 1024;
                return computer_size > size_value;
            }else {
                return computer_size > size_value;
            }
        }else if(name.equals("Memory_Size")){
            int index = getIndex(value);
            double size_value = Double.parseDouble(value.substring(0, index+1));
            String unit = value.substring(index+1);
            int computer_index = getIndex(staticInfo.getMemory_size());
            double computer_size = Double.parseDouble(staticInfo.getMemory_size().substring(0, computer_index +1));
            if(unit.equals("KB")|| unit.equals("K")){
                size_value = size_value / 1024 / 1024;
                return computer_size > size_value;
            }else if(unit.equals("MB") || unit.equals("M")){
                size_value = size_value / 1024;
                return computer_size > size_value;
            }else {
                return computer_size > size_value;
            }
        }else if(name.equals("Band_Width")){
            int band_need = Integer.parseInt(value.substring(0, value.length() - 1));
            int band_offer = Integer.parseInt(staticInfo.getBand_width().substring(0, staticInfo.getBand_width().length() - 1));
            return band_offer >= band_need;
        }else {
            return true;
        }
    }

    //择优推荐算法的实现(计算性能值)
    private List<RecommendComputer> preferred_Recommend(List<ComputerInfo> computerInfos){
        List<RecommendComputer> recommendComputerList = new ArrayList<>();
        //获取到所参与择优推荐字段的最小值
        JudgeHardware judgeHardware = getBaseHardwareForJudge(computerInfos);
        for(int i = 0; i< computerInfos.size(); i++){
            Hardware hardware = computerInfos.get(i).getHardware_info().getStaticInfo();
            //处理各个字段的性能规约函数
            //1. 处理CPU的信息，这里只包含两个字段，CPU_Core和CPU_Frequency
            int cpu_core = hardware.getCpu_core();
            double cpu_frequency = Double.parseDouble(hardware.getCpu_frequency().substring(0,hardware.getCpu_frequency().length() - 3));
            double func_cpu_core = 1 + (cpu_core / judgeHardware.getCpu_core() -1)* 0.1;
            double func_cpu_frequency = 1 + (cpu_frequency / judgeHardware.getCpu_frequency() - 1)*0.1;
            double func_cpu = (func_cpu_core +func_cpu_frequency) / 2;

            //2. 处理内存
            int memory_size = Integer.parseInt(hardware.getMemory_size().substring(0,hardware.getMemory_size().length() - 2));
            double func_memory = 1 + (memory_size / judgeHardware.getMemory_size() - 1)* 0.1;

            //3. 处理磁盘容量
            int disk_size = Integer.parseInt(hardware.getDisk_size().substring(0, hardware.getDisk_size().length() - 1));
            double func_disk = 1 + (disk_size / judgeHardware.getDisk_size() - 1) * 0.1;

            //4. 处理网络带宽
            int band_width = Integer.parseInt(hardware.getBand_width().substring(0,hardware.getBand_width().length() - 1));
            double func_net = 1 + (band_width / judgeHardware.getBand_width() - 1) * 0.1;

            //5. 处理cpu实时利用率参数


            //TODO 计算性能规约值(这里4个因素所占有的权重一样，即都为25%,后期可以增添用户决策需求，也不难)
            double performance = (func_cpu+ func_memory +func_disk + func_net) * 0.25;
            RecommendComputer recommendComputer = new RecommendComputer();
            recommendComputer.setOid(computerInfos.get(i).getId());
            recommendComputer.setMac(computerInfos.get(i).getMac());
            recommendComputer.setScore(performance);
            recommendComputerList.add(recommendComputer);
        }
        return recommendComputerList;
    }

    // 看到这个垃圾代码我真的都不想写了，哎，为了论文
    private JudgeHardware getBaseHardwareForJudge(List<ComputerInfo> computerInfos){
        //先取出第一个作为基本模板
        Hardware staticInfo = computerInfos.get(0).getHardware_info().getStaticInfo();
        // 1.处理cpu_core
        int min_cpu_core = staticInfo.getCpu_core();
        // 2.处理cpu_frequency
        double min_cpu_frequncency = Double.parseDouble(staticInfo.getCpu_frequency().substring(0,staticInfo.getCpu_frequency().length() - 3));
        // 3.处理memory_size
        int min_memory_size = Integer.parseInt(staticInfo.getMemory_size().substring(0,staticInfo.getMemory_size().length() - 2));
        // 4.处理band_width
        int min_band_width = Integer.parseInt(staticInfo.getBand_width().substring(0,staticInfo.getBand_width().length() - 1));
        // 5.处理disk_size
        int min_disk_size = Integer.parseInt(staticInfo.getDisk_size().substring(0, staticInfo.getDisk_size().length() - 1));

        //对剩余的进行处理
        JudgeHardware judgeHardware = new JudgeHardware();
        for(int i = 1; i< computerInfos.size(); i++){
            Hardware hardware = computerInfos.get(i).getHardware_info().getStaticInfo();
            int temp_cpu_core = hardware.getCpu_core();
            min_cpu_core = temp_cpu_core < min_cpu_core ? temp_cpu_core : min_cpu_core;
            double temp_cpu_frequency = Double.parseDouble(hardware.getCpu_frequency().substring(0,hardware.getCpu_frequency().length() - 3));
            min_cpu_frequncency = temp_cpu_frequency < min_cpu_frequncency ? temp_cpu_frequency : min_cpu_frequncency;
            int temp_memory_size = Integer.parseInt(hardware.getMemory_size().substring(0,hardware.getMemory_size().length() - 2));
            min_memory_size = temp_memory_size < min_memory_size ? temp_memory_size : min_memory_size;
            int temp_band_width = Integer.parseInt(hardware.getBand_width().substring(0,hardware.getBand_width().length() - 1));
            min_band_width = temp_band_width <min_band_width ? temp_band_width :min_band_width;
            int temp_disk_size = Integer.parseInt(hardware.getDisk_size().substring(0, hardware.getDisk_size().length() - 1));
            min_disk_size = temp_disk_size < min_disk_size ? temp_disk_size : min_disk_size;
        }
        judgeHardware.setCpu_core(min_cpu_core);
        judgeHardware.setCpu_frequency(min_cpu_frequncency);
        judgeHardware.setMemory_size(min_memory_size);
        judgeHardware.setBand_width(min_band_width);
        judgeHardware.setDisk_size(min_disk_size);
        return judgeHardware;
    }

    //工具方法，分离单位和数字
    private int getIndex(String s){
        for(int i = s.length() - 1; i>=0;i--){
            if((s.charAt(i) - 48) <= 9){
                return i;
            }
        }
        return -1;
    }

    /**
     * 从门户获取计算资源
     * @param page
     * @param sortType
     * @param sortAsc
     * @return com.alibaba.fastjson.JSONObject
     * @author wangming
     * @date 2020/1/2 9:37
     */
    private List<ModelInfoVO> getModelResource(int page, String sortType, int sortAsc){
        String url = PORTAL_ADDRESS + "/computableModel/integratingList?page=" + page + "&sortType=" + sortType + "&sortAsc=" + sortAsc;
        String result = "";
        try {
            result = MyHttpUtils.GET(url, "UTF-8",null);
        } catch (IOException e) {
            result = null;
            e.printStackTrace();
        } catch (URISyntaxException e) {
            result = null;
            e.printStackTrace();
        }
        if(result == null) {
            //错误交给上层去处理
            return null;
        }else {
            JSONObject temp = JSONObject.parseObject(result);
            //默认是一次性取得10条记录
            JSONArray content = temp.getJSONArray("content");
            List<ModelInfoVO> modelInfoVOList = new ArrayList<>();
            for (int i = 0; i < content.size(); i++) {
                ModelInfoVO modelInfoVO = handleModelResourceData(content.getJSONObject(i));
                modelInfoVOList.add(modelInfoVO);
            }
            return modelInfoVOList;
        }
    }

    private ModelInfoVO handleModelResourceData(JSONObject input){

        ModelInfoVO modelInfoVO = new ModelInfoVO();
        modelInfoVO.setId(input.getString("id"));
        modelInfoVO.setName(input.getString("name"));
        modelInfoVO.setDescription(input.getString("description"));
        modelInfoVO.setPid(input.getString("md5"));
        //默认只会存在一条关于部署包路径的数据
        JSONArray resources = input.getJSONArray("resources");
        modelInfoVO.setPackagePath(PORTAL_ADDRESS + "/static/computableModel/Package" + resources.get(0));
        String mdl = input.getString("mdl");
        MdlDocument mdlDocument = MdlParseUtils.convertMdl(mdl);
        modelInfoVO.setMdlDocument(mdlDocument);
        if(input.getBooleanValue("deploy")){
            //deploy为true,表明已经存在相应的服务
            modelInfoVO.setType(1);
        }else {
            // deploy为false，还未存在相对应的服务
            modelInfoVO.setType(0);
        }
        return modelInfoVO;
    }

    private ModelResourceVO handleModelPackageData(JSONObject list){
        ModelResourceVO modelResourceVO = new ModelResourceVO();
        modelResourceVO.setOid(list.getString("oid"));
        modelResourceVO.setName(list.getString("name"));
        modelResourceVO.setDescription(list.getString("description"));
        modelResourceVO.setDetail(list.getString("detail"));
        modelResourceVO.setAuthor_name(list.getString("author_name"));
        modelResourceVO.setAuthor_oid(list.getString("author_oid"));
        modelResourceVO.setCreateTime(list.getString("createTime"));
        modelResourceVO.setViewCount(list.getIntValue("viewCount"));
        modelResourceVO.setContentType(list.getString("contentType"));
        modelResourceVO.setMd5(list.getString("md5"));
        //头像默认都设置为空字符串
        modelResourceVO.setImage("");
        //默认只会存在一条关于部署包路径的数据
        JSONArray resources = list.getJSONArray("resources");
        modelResourceVO.setPackagePath(PORTAL_ADDRESS + "/static/computableModel/Package" + resources.get(0));
        //获取模型是否存在可用服务信息(因为数据库可能存在这个字段为空，所以需要这样判断)
        if(list.getBooleanValue("deploy")){
            //说明存在服务
            modelResourceVO.setType(1);
        }else {
            modelResourceVO.setType(0);
        }
        modelResourceVO.setRuntime(list.getString("runtime"));
        modelResourceVO.setMdlJson(list.getString("mdlJson"));

        //处理资源JSON
        JSONArray resourceJson = list.getJSONArray("resourceJson");
        //默认部署包只会存在一条资源
        if(resourceJson != null){
            Resource resource = new Resource();
            resource.setId(resourceJson.getJSONObject(0).getString("id"));
            resource.setName(resourceJson.getJSONObject(0).getString("name"));
            resource.setPath(resourceJson.getJSONObject(0).getString("path"));
            resource.setSuffix(resourceJson.getJSONObject(0).getString("suffix"));
            modelResourceVO.setResources(resource);
        }
        return modelResourceVO;
    }

    private ComputerServerInfo handleComputerModelData(JSONObject list){
        ComputerServerInfo computerServerInfo = new ComputerServerInfo();
        computerServerInfo.setOid(list.getString("oid"));
        computerServerInfo.setName(list.getString("name"));
        computerServerInfo.setDescription(list.getString("description"));
        computerServerInfo.setAuthor_name(list.getString("author_name"));
        computerServerInfo.setAuthor_oid(list.getString("author_oid"));
        computerServerInfo.setCreateTime(list.getString("createTime"));
        computerServerInfo.setContentType(list.getString("contentType"));
        computerServerInfo.setPid(list.getString("md5"));
        computerServerInfo.setMdl(list.getString("mdl"));
        computerServerInfo.setMdlJson(list.getString("mdlJson"));
        return computerServerInfo;
    }

    //add by wangming at 2020.05.19 从mdl中解析环境配置信息为json
    private JSONObject convertMDLRuntimeToJSON(JSONObject runtime){
        JSONObject result = new JSONObject();
        //填充基本信息
        result.put("name",runtime.getString("name"));
        result.put("version", runtime.getString("version"));
        result.put("baseDir",  runtime.getString("baseDir"));
        result.put("entry", runtime.getString("entry"));

        //处理HardwareConfigures节点（不兼容老版本了）
        if(runtime.getJSONArray("HardwareConfigures").getJSONObject(0) != null){
            JSONArray hardware = new JSONArray();
            JSONArray hardware_info = runtime.getJSONArray("HardwareConfigures").getJSONObject(0).getJSONArray("Add");
            if(hardware_info != null) {
                for (int j = 0; j < hardware_info.size(); j++) {
                    JSONObject obj = hardware_info.getJSONObject(j);
                    JSONObject temp = new JSONObject();
                    temp.put("name", obj.getString("key"));
                    temp.put("value", obj.getString("value"));
                    hardware.add(temp);
                }
            }
            result.put("hardware",hardware);
        }
        else{
            result.put("hardware",new JSONArray());
        }

        //处理SoftwareConfigures节点
        if(runtime.getJSONArray("SoftwareConfigures").getJSONObject(0) != null){
            JSONArray software = new JSONArray();
            JSONArray software_info = runtime.getJSONArray("SoftwareConfigures").getJSONObject(0).getJSONArray("Add");
            if(software_info != null){
                for(int i = 0; i< software_info.size(); i++){
                    JSONObject obj = software_info.getJSONObject(i);
                    JSONObject temp = new JSONObject();
                    temp.put("name", obj.getString("key"));
                    temp.put("platform",obj.getString("platform") != null ? obj.getString("platform"):"null");
                    temp.put("value", obj.getString("value"));
                    software.add(temp);
                }
            }
            result.put("software", software);
        }else{
            result.put("software", new JSONArray());
        }

        //处理Assemblies节点
        if(runtime.getJSONArray("Assemblies").getJSONObject(0) != null){
            JSONArray assembly = new JSONArray();
            JSONArray assembly_info = runtime.getJSONArray("Assemblies").getJSONObject(0).getJSONArray("Assembly");
            if(assembly_info != null) {
                for (int i = 0; i < assembly_info.size(); i++) {
                    JSONObject obj = assembly_info.getJSONObject(i);
                    JSONObject temp = new JSONObject();
                    temp.put("name", obj.getString("name"));
                    temp.put("path", obj.getString("path"));
                    assembly.add(temp);
                }
                result.put("assemblies", assembly);
            }
        }else {
            result.put("assemblies", new JSONArray());
        }

        //处理supportive
        if(runtime.getJSONArray("SupportiveResources").getJSONObject(0) != null){
            JSONArray supportive = new JSONArray();
            JSONArray supportive_info = runtime.getJSONArray("SupportiveResources").getJSONObject(0).getJSONArray("Add");
            if(supportive_info != null) {
                for (int i = 0; i < supportive_info.size(); i++) {
                    JSONObject obj = supportive_info.getJSONObject(i);
                    JSONObject temp = new JSONObject();
                    temp.put("name", obj.getString("name"));
                    temp.put("type", obj.getString("type"));
                    supportive.add(temp);
                }
                result.put("support", supportive);
            }
        }else {
            result.put("support", new JSONArray());
        }
        return result;
    }

}
