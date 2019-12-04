package com.scheduler.sdk;

/**
 * @Author: wangming
 * @Date: 2019-11-28 10:51
 */
public class Server extends Service {

    public Server(String ip, int port) {
        super(ip, port);
    }

    public int setIPAndPort(String ip, int port){
        this.setIp(ip);

        if(port > 65535 || port < 0){
            return -1;
        }
        this.setPort(port);
        return 1;
    }
}
