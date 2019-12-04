package com.scheduler.sdk;

import com.alibaba.fastjson.JSONObject;
import com.scheduler.utils.MyFileUtils;
import com.scheduler.utils.MyHttpUtils;
import org.apache.commons.codec.DecoderException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangming
 * @Date: 2019-11-28 14:00
 */
public class GeoDataExServer extends Service implements DataServer {

    private String userName;
    private int type;

    public GeoDataExServer(String ip, int port, String userName) {
        super(ip, port);
        this.userName = userName;
        this.type = 1;
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
    public Data upload(String dataPath, String tag) throws IOException, URISyntaxException, DecoderException {
        String md5 = MyFileUtils.getMD5(dataPath);
        String path = "data?md5=" + md5;
        String url = this.getBaseUrl() + path;
        String response = MyHttpUtils.GET(url, "UTF-8", null);
        JSONObject jResponse = JSONObject.parseObject(response);
        if (jResponse.getString("result").equals("suc")){
            int code = jResponse.getIntValue("code");
            if(code == 1){
                JSONObject jData = jResponse.getJSONObject("data");
                String pwd = jData.getString("d_pwd");
                pwd = MyFileUtils.decryption(MyFileUtils.decryption(pwd));
                String id = jData.getString("id");
                return new ExData(this.getIp(), this.getPort(), id, pwd);
            }else {
                Map<String, String> params = new HashMap<String, String>();
                Map<String, String> fileMap = new HashMap<String, String>();

                params.put("datatag", tag);
                params.put("pwd", "true");
                fileMap.put("datafile", dataPath);

                String actionUrl = this.getBaseUrl() + "data";
                String result = MyHttpUtils.POSTFile(actionUrl,"UTF-8",params,fileMap);
                JSONObject jResult = JSONObject.parseObject(result);
                if(jResult.getString("result").equals("suc")){
                    JSONObject jData = jResult.getJSONObject("data");
                    String pwd = jData.getString("d_pwd");
                    pwd = MyFileUtils.decryption(MyFileUtils.decryption(pwd));
                    String id = jData.getString("id");
                    return new ExData(this.getIp(), this.getPort(), id, pwd);
                }
            }
        }
        return null;
    }
}
