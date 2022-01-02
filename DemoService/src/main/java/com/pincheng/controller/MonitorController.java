/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pincheng.controller;

import Model.DashBoardDto;
import Model.PerformaceDto;
import Model.Search;
import Model.Threshold;
import com.pincheng.facade.MonitorFacade;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
@CrossOrigin
@RequestMapping("/MonitorApi")
public class MonitorController {
    
    
    @Autowired
    MonitorFacade monitorFacade;
    
    //即時監控
    @PostMapping(value = "/getPerformance", produces = MediaType.APPLICATION_JSON_VALUE)
    public PerformaceDto getPerformance(@RequestBody Threshold requestBody) {
        PerformaceDto resultPerformaceModel = monitorFacade.startPerformaceMonitor(requestBody);

        return resultPerformaceModel;
    }
    
    //儀表查詢效能
    @PostMapping(value = "/getDashBoardData", produces = MediaType.APPLICATION_JSON_VALUE)
    public DashBoardDto getPerformance(@RequestBody Search filter) {
        DashBoardDto tmpDashBoardDto = null;
        System.err.println("getDashBoardData");
        try {
            Date startdate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(filter.getStartTime());
            Date enddate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(filter.getEndTime());
            tmpDashBoardDto = monitorFacade.getDashBoardInfo(startdate,enddate,filter.getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tmpDashBoardDto;
    }
    
    
//    @PostMapping(value = "/getMemoryUsage", produces = MediaType.APPLICATION_JSON_VALUE)
//    public String getMemoryUsage() {
//        System.err.println("getMemoryUsage2");
//        return monitorFacade.createMemoryPerformace();
//    }
//    
//    
//    @PostMapping(value = "/getCpuUsage", produces = MediaType.APPLICATION_JSON_VALUE)
//    public String getCpuUsage() {
//        System.err.println("getCpuUsage");
//        return monitorFacade.createCpuPerformace();
//    }
    
}
