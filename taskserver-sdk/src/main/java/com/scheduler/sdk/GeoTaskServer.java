package com.scheduler.sdk;

import com.alibaba.fastjson.JSONObject;
import com.scheduler.utils.MyHttpUtils;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @Author: wangming
 * @Date: 2019-11-28 14:21
 */
public class GeoTaskServer extends Service {

    public GeoTaskServer(String ip, int port) {
        super(ip, port);
    }

    public Task createTask(String pid, DataServer dataExServer, String userName){
        String url = this.getBaseUrl() + "server?pid=" + pid;
        try{
            String resJson = MyHttpUtils.GET(url, "UTF-8", null);
            JSONObject jResponse = JSONObject.parseObject(resJson);
            if (!jResponse.getString("result").equals("suc") || jResponse.getIntValue("code") != 1){
                return null;
            }else {
                if (dataExServer == null) {
                    String result = MyHttpUtils.GET(this.getBaseUrl() + "dxserver?ac=recommend", "UTF-8", null);
                    JSONObject jResult = JSONObject.parseObject(result);
                    if(jResult.getString("result").equals("suc") && jResult.getIntValue("code") == 1){
                        JSONObject data = jResult.getJSONObject("data");
                        String ds_ip = data.getString("ds_ip");
                        int ds_port = data.getIntValue("ds_port");
                        int type = data.getIntValue("type");
                        if(type == 1){
                            dataExServer = new GeoDataExServer(ds_ip, ds_port, userName);
                        }else{
                            dataExServer = new GeoDataServiceServer(ds_ip, ds_port, userName);
                        }
                    }else {
                        return null;
                    }
                }
            }
            return new Task(this.getIp(), this.getPort(), pid, dataExServer, userName);
        }catch (IOException e){
            e.printStackTrace();
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
        return null;
    }

    public int subscribeTask(Task task){
        JSONObject params = new JSONObject();
        String inputsArray = task.getInputData().convertItems2JSON();
        params.put("inputs", inputsArray);
        params.put("username",task.getUserName());
        params.put("pid", task.getPid());

        String actionURL = this.getBaseUrl() + "task";

        try {
            String resJson = MyHttpUtils.POSTWithJSON(actionURL,"UTF-8",null,params);
            JSONObject jResponse = JSONObject.parseObject(resJson);
            if(jResponse.getString("result").equals("suc")){
                String tid = jResponse.getString("data");
                task.bind(tid, "Inited");
                return 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
