/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pincheng.facade;

import Entity.Performance;
import Entity.User;
import Model.DashBoardDto;
import Model.PerformaceDto;
import Model.Search;
import Model.Threshold;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pincheng.repository.PerformanceRepository;
import com.pincheng.repository.UserRepository;
import com.pincheng.utils.SnmpWalk;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class MonitorFacade {
    
    @Autowired
    PerformanceRepository performanceRepository;
    
    @Autowired
    UserRepository userRepository;
    
    private Gson gson = new GsonBuilder().serializeNulls().create();
    
    
    public PerformaceDto startPerformaceMonitor(Threshold threshold) {
        System.err.print(gson.toJson(threshold));
        String cpuUsage = SnmpWalk.snmpWalk("127.0.0.1", ".1.3.6.1.2.1.25.3.3.1.2");
        String memoryUsage = SnmpWalk.snmpWalk("127.0.0.1", ".1.3.6.1.2.1.25.2.3.1.6");
        Performance savePerformance = new Performance();
        Boolean isAlarm = false;
        Date now = new Date();
        Optional<User> loginUser = userRepository.findById(threshold.getUid());
        try {
            isAlarm = Float.valueOf(cpuUsage) > (Float.valueOf(threshold.getCpu()));
        } catch (Exception e) {
        }
        savePerformance.setUid(loginUser.get());
        savePerformance.setType("CPU");
        savePerformance.setIsAlarm(isAlarm);
        savePerformance.setUsageValue(cpuUsage);
        savePerformance.setEventTime(now);
        performanceRepository.save(savePerformance);
        
        isAlarm = false;
        try {
            isAlarm = Float.valueOf(memoryUsage) > (Float.valueOf(threshold.getMemory()));
        } catch (Exception e) {
        }
        savePerformance = new Performance();
        savePerformance.setUid(loginUser.get());
        savePerformance.setType("Memory");
        savePerformance.setIsAlarm(isAlarm);
        savePerformance.setUsageValue(memoryUsage);
        savePerformance.setEventTime(now);
        performanceRepository.save(savePerformance);
        PerformaceDto resultPerformaceModel = new PerformaceDto(memoryUsage, cpuUsage);
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        resultPerformaceModel.setTimeLabel(df.format(now));
        
        return resultPerformaceModel;
    }

    
    
    public DashBoardDto getDashBoardInfo(Date startdate,Date enddate,Integer uid) {
        DashBoardDto tmpDashBoardDto = new DashBoardDto();
        Optional<User> loginUser = userRepository.findById(uid);
        List<Performance> resultList = performanceRepository.findByDashBoard(startdate, enddate, loginUser.get());
        int cpuNormal = 0;
        int cpuCritical = 0;
        int cpuHealthRate = 0;
        int memoryNormal = 0;
        int memoryCritical = 0;
        int memoryHealthRate = 0;
        for (int i = 0; i < resultList.size(); i++) {
            if (resultList.get(i).getType().equals("CPU")) {
                if (resultList.get(i).getIsAlarm()) {
                    cpuCritical++;
                } else {
                    cpuNormal++;
                }
            } else {
                if (resultList.get(i).getIsAlarm()) {
                    memoryCritical++;
                } else {
                    memoryNormal++;
                }
            }
        }
        System.err.println(resultList.size());
        if (cpuNormal != 0 && cpuCritical != 0) {
            cpuHealthRate = cpuNormal * 100 / (cpuNormal + cpuCritical);
        }
        if (memoryNormal != 0 && memoryCritical != 0) {
            memoryHealthRate = memoryNormal * 100 / (memoryNormal + memoryCritical);
        }
        tmpDashBoardDto.setCpuNormal(String.valueOf(cpuNormal));
        tmpDashBoardDto.setCpuCritical(String.valueOf(cpuCritical));
        tmpDashBoardDto.setCpuHealthRate(cpuHealthRate);
        
        tmpDashBoardDto.setMemoryNormal(String.valueOf(memoryNormal));
        tmpDashBoardDto.setMemoryCritical(String.valueOf(memoryCritical));
        tmpDashBoardDto.setMemoryHealthRate(memoryHealthRate);
        
        return tmpDashBoardDto;
    }
    
    
    
}
