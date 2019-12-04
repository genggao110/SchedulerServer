package com.scheduler.sdk;

import com.scheduler.utils.MyHttpUtils;
import com.scheduler.utils.MyStringUtils;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @Author: wangming
 * @Date: 2019-11-28 10:40
 */
public class Service {

    private String ip;
    private int port;

    public Service(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public Service() {
        this.ip = "127.0.0.1";
        this.port = 8060;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    protected String getBaseUrl(){
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("http://").append(this.ip).append(":").append(this.port).append("/");
        return stringBuilder.toString();
    }

    public int connect() throws IOException, URISyntaxException{
        String url = this.getBaseUrl();
        url += "ping";
        String body = MyHttpUtils.GET(url, "UTF-8", null);
        String content = MyStringUtils.replaceBlank(body);
        //JSON Object
        if (content.equals("OK")){
            return 1;
        }
        return 0;
    }
}
