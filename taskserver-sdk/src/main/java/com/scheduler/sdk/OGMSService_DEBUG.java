package com.scheduler.sdk;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @Author: wangming
 * @Date: 2019-11-28 15:11
 */
public class OGMSService_DEBUG {

    public static Server CreateServer(String ip, int port){
        return new Server(ip, port);
    }

    public static GeoDataExServer CreateDataExchangeServer(String ip, int port, String userName){
        GeoDataExServer dataExServer = new GeoDataExServer(ip,port,userName);
        try{
            int status = dataExServer.connect();
            if(status == 1){
                return dataExServer;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static GeoTaskServer CreateTaskServer(String ip, int port){
        GeoTaskServer taskServer = new GeoTaskServer(ip, port);
        try{
            int status = taskServer.connect();
            if(status == 1){
                return taskServer;
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GeoDataServiceServer CreateDataServiceServer(String ip, int port, String userName){
        GeoDataServiceServer dataServiceServer = new GeoDataServiceServer(ip, port, userName);
        try{
            int status = dataServiceServer.connect();
            if(status == 1){
                return dataServiceServer;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
