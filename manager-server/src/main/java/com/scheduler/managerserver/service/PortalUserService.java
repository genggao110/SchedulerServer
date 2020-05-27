package com.scheduler.managerserver.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scheduler.managerserver.dto.UserAddDTO;
import com.scheduler.utils.MyHttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Service
public class PortalUserService {

    @Value("${prop.portalAddress}")
    private String PORTAL_ADDRESS;

    public JSONObject getUserInfoFromPortal(String userName, String password) {
        String url = PORTAL_ADDRESS + "/user/getLoginInfo";
        Map<String, String> params = new HashMap<>();
        params.put("account", userName);
        params.put("password_md5", password);
        JSONObject result_json = new JSONObject();
        try {
            String result = MyHttpUtils.POST(url, "UTF-8", null, params, null);
            JSONObject jResponse = JSONObject.parseObject(result);
            if(jResponse.getIntValue("code") == 0){
                result_json = jResponse.getJSONObject("data");
            }else{
                result_json = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            result_json = null;
        }
        return result_json;
    }

    public int addUserToPortal(UserAddDTO userAddDTO){
        String url = PORTAL_ADDRESS + "/user/add";
        Map<String,String> userAdd = new HashMap<>();
        userAdd.put("email",userAddDTO.getEmail());
        userAdd.put("userName",userAddDTO.getName());
        userAdd.put("name",userAddDTO.getName());
        userAdd.put("password",userAddDTO.getPassword());
        userAdd.put("org",userAddDTO.getOrg());
        int res;
        try {
            String result = MyHttpUtils.POST(url, "UTF-8", null, userAdd, null);
            String response = JSONObject.parseObject(result).getString("data");
            res = Integer.parseInt(response.toString()) ;

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return res;

    }

    public JSONArray getUsersFromPortal(){
        JSONArray array = new JSONArray();
        Map<String,String> para = new HashMap<>();
        String url = PORTAL_ADDRESS + "/user/getUsers";
        try {
            String result = MyHttpUtils.GET(url, "UTF-8", null);
            JSONObject jResponse = JSONObject.parseObject(result);
            if(jResponse.getIntValue("code") == 0){
                array = jResponse.getJSONArray("data");
            }else{
                array = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            array = null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            array = null;
        }

        return array;
    }

    public JSONArray getUsersFromPortalByType(String searchText, String type){
        JSONArray array = new JSONArray();
        Map<String,String> para = new HashMap<>();
        String url = PORTAL_ADDRESS + "/user/searchUsersByType?searchText="+searchText+"&type="+type;
        try {
            String result = MyHttpUtils.GET(url, "UTF-8", null);
            JSONObject jResponse = JSONObject.parseObject(result);
            if(jResponse.getIntValue("code") == 0){
                array = jResponse.getJSONArray("data");
            }else{
                array = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            array = null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            array = null;
        }

        return array;
    }

    public JSONArray getUserFromPortalByName(String Name){
        JSONArray array = new JSONArray();
        Map<String,String> para = new HashMap<>();
        String url = PORTAL_ADDRESS + "/user/getUsers";
        try {
            String result = MyHttpUtils.GET(url, "UTF-8", null);
            JSONObject jResponse = JSONObject.parseObject(result);
            if(jResponse.getIntValue("code") == 0){
                array = jResponse.getJSONArray("data");
            }else{
                array = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            array = null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            array = null;
        }

        return array;
    }
}