package com.scheduler.sdk;

import com.alibaba.fastjson.JSONObject;
import com.scheduler.utils.MyHttpUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangming
 * @Date: 2019-11-28 14:12
 */
public class GeoDataServiceServer extends Service implements DataServer {

    private String userName;
    private int type;

    public GeoDataServiceServer(String ip, int port, String userName) {
        super(ip, port);
        this.userName = userName;
        this.type = 2;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getType() {
        return type;
    }

    @Override
    public Data upload(String dataPath, String tag) throws Exception {
        File file = new File(dataPath);
        if (!file.exists()) {
            return null;
        }
        String fileFullName = file.getName();
        String fileName = fileFullName.substring(0,fileFullName.lastIndexOf("."));
        String ext = fileFullName.substring(fileFullName.lastIndexOf(".")+1, fileFullName.length());

        Map<String, String> fileMap = new HashMap<>();
        fileMap.put("file", dataPath);

        String actionURL = this.getBaseUrl() + "file/upload/store_dataResource_files";
        String result = MyHttpUtils.POSTFile(actionURL,"UTF-8",null,fileMap);
        JSONObject jResult = JSONObject.parseObject(result);
        if(jResult.getIntValue("code") == 0){
            String dataId = jResult.getString("data");
            String dataUrl = this.getBaseUrl() + "dataResource";
            JSONObject formData = new JSONObject();
            formData.put("author", this.getUserName());
            formData.put("fileName", fileName);
            formData.put("sourceStoreId", dataId);
            formData.put("suffix", ext);
            formData.put("type", "OTHER");

            String result2 = MyHttpUtils.POSTWithJSON(dataUrl,"UTF-8",null,formData);
            JSONObject jsResult2 = JSONObject.parseObject(result2);
            if(jsResult2.getIntValue("code") == 0){
                return new DCData(this.getIp(), this.getPort(), dataId);
            }else {
                return null;
            }
        }
        return null;
    }

    @Override
    public int connect() throws IOException, URISyntaxException {
        String url = this.getBaseUrl();
        url = url + "ping";
        String body = MyHttpUtils.GET(url,"UTF-8",null);
        if(body == null){
            return 0;
        }else{
            JSONObject result = JSONObject.parseObject(body);
            // JSON Object
            if(result.getString("data").equals("OK")){
                return 1;
            }
            return 0;
        }
    }
}
