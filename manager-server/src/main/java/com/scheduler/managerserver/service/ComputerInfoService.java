package com.scheduler.managerserver.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scheduler.managerserver.dao.ComputerInfoDao;
import com.scheduler.managerserver.dto.computer.ComputerInfoDTO;
import com.scheduler.managerserver.po.ComputerInfo;
import com.scheduler.managerserver.pojo.environment.HardwareEnvironment;
import com.scheduler.managerserver.pojo.environment.SoftwareEnvironment;
import com.scheduler.managerserver.utils.PositionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: wangming
 * @Date: 2020-04-22 19:50
 */
@Service
public class ComputerInfoService {

    @Autowired
    ComputerInfoDao computerInfoDao;

    @Autowired
    PortalUserService portalUserService;

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean addComputer(ComputerInfoDTO computerInfoDTO) {
        ComputerInfo computerInfo = new ComputerInfo();
        //进行字段填充
        BeanUtils.copyProperties(computerInfoDTO, computerInfo);
        computerInfo.setStatus(true);
        Date now = new Date();
        computerInfo.setDate(now);
        computerInfo.setUpdateDate(now);
        //填充软件环境信息
        SoftwareEnvironment softwareEnvironment = new SoftwareEnvironment();
        softwareEnvironment.setOs(computerInfoDTO.getPlatform());
        softwareEnvironment.setOsVersion(computerInfoDTO.getVersion());
        softwareEnvironment.setSoftwares(computerInfoDTO.getSoftware());
        computerInfo.setSoftware_info(softwareEnvironment);

        //填充硬件环境信息
        HardwareEnvironment hardwareEnvironment = new HardwareEnvironment();
        hardwareEnvironment.setStaticInfo(computerInfoDTO.getHardware());
        hardwareEnvironment.setDynamicInfo(computerInfoDTO.getDynamicInfo());
        computerInfo.setHardware_info(hardwareEnvironment);
        //填充位置信息
        try {
            computerInfo.setGeoInfo(PositionUtils.getGeoInfoMeta(computerInfoDTO.getIp()));
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        //插入记录
        computerInfoDao.insert(computerInfo);
        return true;
    }

    //更新计算节点环境信息
    public boolean updateComputer(ComputerInfo computerInfo, ComputerInfoDTO computerInfoDTO) throws Exception {
        Date now = new Date();
        computerInfo.setUpdateDate(now);
        //如果ip未变化的话不用进行位置的更新
        if(!computerInfo.getIp().equals(computerInfoDTO.getIp())) {
            computerInfo.setIp(computerInfoDTO.getIp());
            try {
                computerInfo.setGeoInfo(PositionUtils.getGeoInfoMeta(computerInfoDTO.getIp()));
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
        }
        //更新软件、硬件环境信息
        SoftwareEnvironment softwareEnvironment =  new SoftwareEnvironment();;
        softwareEnvironment.setOs(computerInfoDTO.getPlatform());
        softwareEnvironment.setOsVersion(computerInfoDTO.getVersion());
        softwareEnvironment.setSoftwares(computerInfoDTO.getSoftware());
        computerInfo.setSoftware_info(softwareEnvironment);

        HardwareEnvironment hardwareEnvironment = new HardwareEnvironment();
        hardwareEnvironment.setStaticInfo(computerInfoDTO.getHardware());
        hardwareEnvironment.setDynamicInfo(computerInfoDTO.getDynamicInfo());
        computerInfo.setHardware_info(hardwareEnvironment);

        //更新记录
        computerInfoDao.save(computerInfo);
        return true;
    }

    public JSONArray findAll(){
        List<ComputerInfo> computerInfoList = computerInfoDao.findAll();
        JSONArray result = new JSONArray();

        for (int i=0; i<computerInfoList.size();i++){
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(computerInfoList.get(i));
            JSONArray array = portalUserService.getUsersFromPortalByType(computerInfoList.get(i).getUserId(),"userName");
            jsonObject.put("user",((JSONObject)array.get(0)).getString("name"));
            result.add(jsonObject);
        }

        return result;

    }

    public JSONArray findAllByUserId(String id){
        List<ComputerInfo> computerInfoList = computerInfoDao.findAllByUserId(id);
        JSONArray result = new JSONArray();

        for (int i=0; i<computerInfoList.size();i++){
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(computerInfoList.get(i));
            JSONArray array = portalUserService.getUsersFromPortalByType(computerInfoList.get(i).getUserId(),"userName");
            jsonObject.put("user",((JSONObject)array.get(0)).getString("name"));
            result.add(jsonObject);
        }

        return result;

    }


}
