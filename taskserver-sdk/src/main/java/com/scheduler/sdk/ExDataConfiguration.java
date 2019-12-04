package com.scheduler.sdk;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangming
 * @Date: 2019-11-28 11:10
 */
public class ExDataConfiguration {

    private List<ExDataConfigItem> items;

    public ExDataConfiguration() {
        this.items = new ArrayList<>();
    }

    public int getCount(){
        return this.items.size();
    }

    public int insertData(String stateName, String event, String url, String tag){
        for(ExDataConfigItem item : this.items){
            if(item.getStatename().equals(stateName) && item.getEvent().equals("event")){
                item.setUrl(url);
                item.setTag(tag);
                return 2;
            }
        }

        ExDataConfigItem item_example = new ExDataConfigItem(stateName,event,url,tag);
        this.items.add(item_example);
        return 1;
    }

    public List<ExDataConfigItem> getItems(){
        return items;
    }

    public String getDataUrl(String stateName, String event){
        for (ExDataConfigItem item : this.items){
            if(item.getStatename().equals(stateName) && item.getEvent().equals(event)){
                return item.getUrl();
            }
        }
        return null;
    }

    public String convertItems2JSON(){
        JSONArray resultJson = new JSONArray();
        List<ExDataConfigItem> tempItems = this.getItems();
        for (int i = 0; i < tempItems.size(); i++) {
            JSONObject temp = new JSONObject();
            ExDataConfigItem item = tempItems.get(i);
            temp.put("StateName", item.getStatename());
            temp.put("Event", item.getEvent());
            temp.put("Url", item.getUrl());
            temp.put("Tag", item.getTag());
            resultJson.add(temp);
        }
        return resultJson.toJSONString();
    }
}
