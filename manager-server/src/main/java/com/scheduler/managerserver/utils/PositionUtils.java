package com.scheduler.managerserver.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.scheduler.managerserver.pojo.support.GeoInfoMeta;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.net.InetAddress;

/**
 * @Author: wangming
 * @Date: 2020-05-18 11:07
 */
public class PositionUtils {
    public static int count = 0;

    public static class Method {
        public static String POST = "POST";
        public static String GET = "GET";
    }

    public static GeoInfoMeta getGeoInfoMeta(String host) {
        String filePath = "/static/GeoLite2-City.mmdb";
        DatabaseReader reader;
        GeoInfoMeta geoInfoMeat = new GeoInfoMeta();
        try {
            File file = new ClassPathResource(filePath).getFile();
            reader = new DatabaseReader.Builder(file).build();
            InetAddress address = InetAddress.getByName(host);
            CityResponse city = reader.city(address);
            JSONObject res = JSON.parseObject(city.toJson());

            geoInfoMeat.setCity(res.getJSONObject("city").getJSONObject("names").getString("en"));
            geoInfoMeat.setRegion(res.getJSONArray("subdivisions").getJSONObject(0).getJSONObject("names").getString("en"));
            geoInfoMeat.setCountryCode(res.getJSONObject("country").getString("iso_code"));
            geoInfoMeat.setCountryName(res.getJSONObject("country").getJSONObject("names").getString("en"));
            geoInfoMeat.setLatitude(res.getJSONObject("location").getFloat("latitude").toString());
            geoInfoMeat.setLongitude(res.getJSONObject("location").getFloat("longitude").toString());

        } catch (Exception e) {

            geoInfoMeat.setCity("Nanjing");
            geoInfoMeat.setRegion("Jiangsu");
            geoInfoMeat.setCountryCode("CN");
            geoInfoMeat.setCountryName("China");
            geoInfoMeat.setLatitude("32.0617");
            geoInfoMeat.setLongitude("118.7778");

        }

        return geoInfoMeat;
    }
}
