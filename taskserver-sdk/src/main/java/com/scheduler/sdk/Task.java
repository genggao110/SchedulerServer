package com.scheduler.sdk;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scheduler.utils.MyHttpUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @Author: wangming
 * @Date: 2019-11-28 14:22
 */
public class Task extends Service {

    private String pid;
    private DataServer dxServer;
    private ExDataConfiguration inputData;
    private ExDataConfiguration outputData;
    private String userName;
    private String tid;
    private TaskStatus status;

    public Task(String ip, int port, String pid, DataServer dxServer, String userName) {
        super(ip, port);
        this.pid = pid;
        this.dxServer = dxServer;
        this.inputData = new ExDataConfiguration();
        this.outputData = new ExDataConfiguration();
        this.userName = userName;
        this.tid = "";
        this.status = null;
    }

    public Task(String ip, int port, String pid, DataServer dxServer, ExDataConfiguration inputData, ExDataConfiguration outputData, String userName, String tid, TaskStatus status) {
        super(ip, port);
        this.pid = pid;
        this.dxServer = dxServer;
        this.inputData = inputData;
        this.outputData = outputData;
        this.userName = userName;
        this.tid = tid;
        this.status = status;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public DataServer getDxServer() {
        return dxServer;
    }

    public void setDxServer(DataServer dxServer) {
        this.dxServer = dxServer;
    }

    public ExDataConfiguration getInputData() {
        return inputData;
    }

    public void setInputData(ExDataConfiguration inputData) {
        this.inputData = inputData;
    }

    public ExDataConfiguration getOutputData() {
        return outputData;
    }

    public void setOutputData(ExDataConfiguration outputData) {
        this.outputData = outputData;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int configInputData(String stateName, String event, String dataPath, String tag){
        try{
            Data exData = this.dxServer.upload(dataPath, tag);
            String url = exData.getURL();
            this.inputData.insertData(stateName,event,url,tag);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public int refresh() throws IOException, URISyntaxException {
        if (this.tid.equals("")){
            return -1;
        }
        String url_origin = this.getBaseUrl() + "task/" + this.tid;
        String response = MyHttpUtils.GET(url_origin, "UTF-8", null);
        JSONObject jResponse = JSONObject.parseObject(response);
        if (jResponse.getString("result").equals("suc")) {
            JSONObject jData = jResponse.getJSONObject("data");
            TaskStatus taskStatus;
            String status = jData.getString("t_status");
            if(status.equals("Inited")){
                taskStatus = TaskStatus.TASK_INITED;
            }else if (status.equals("Started")){
                taskStatus = TaskStatus.TASK_STARTED;
            }else if(status.equals("Finished")){
                taskStatus = TaskStatus.TASK_FINISHED;
            }else{
                taskStatus = TaskStatus.TASK_ERROR;
            }
            this.setStatus(taskStatus);
            JSONArray jOutputs = jData.getJSONArray("t_outputs");
            for(int i = 0; i < jOutputs.size(); i++){
                JSONObject jOutput = jOutputs.getJSONObject(i);
                String stateName = jOutput.getString("StateName");
                String event = jOutput.getString("Event");
                String url = jOutput.getString("Url");
                String tag = jOutput.getString("Tag");
                this.outputData.insertData(stateName, event, url, tag);
            }
        }else{
            return -1;
        }
        return 1;
    }

    public int wait4Finished(){
        int status = this.getStatus().getCode();
        while (status == 1 || status == 0){
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                this.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            status = this.getStatus().getCode();
        }
        if(status == 2){
            return 1; //task finished
        }else{
            return -1; //task error
        }
    }

    //status: "Inited", "Started", "Finished", "Error"
    public int wait4Status(String status_w, int timeout){
        long startTime = System.currentTimeMillis();
        try {
            this.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String status = this.getStatus().getName();
        long endTime = System.currentTimeMillis();
        while (!status.equals(status_w) && (endTime - startTime) < timeout){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                this.refresh();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            status = this.getStatus().getName();
            endTime = System.currentTimeMillis();
        }

        if((endTime - startTime) > timeout){
            //TODO update more detail
            return -1;
        }
        return 1;
    }

    public int downloadResultByStateEvent(String stateName, String event, String path) throws IOException {
        ExDataConfiguration temp_output = this.getOutputData();
        String url = temp_output.getDataUrl(stateName, event);
        if(url != null){
            File file = MyHttpUtils.downloadFile(url,path);
            if(file.exists()){
                return 1;
            }else {
                return 0;
            }
        }
        return -1;
    }

    public int bind(String tid, String status){
        this.tid = tid;
        TaskStatus taskStatus;
        if(status.equals("Inited")){
            taskStatus = TaskStatus.TASK_INITED;
        }else if(status.equals("Started")){
            taskStatus = TaskStatus.TASK_STARTED;
        }else if(status.equals("Finished")){
            taskStatus = TaskStatus.TASK_FINISHED;
        }else {
            taskStatus = TaskStatus.TASK_ERROR;
        }

        this.status = taskStatus;
        return 1;
    }
}
