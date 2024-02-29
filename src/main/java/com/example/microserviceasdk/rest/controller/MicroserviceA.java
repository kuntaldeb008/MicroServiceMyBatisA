package com.example.microserviceasdk.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microserviceasdk.service.MicroserviceAService;
import com.example.microserviceasdk.pojo.AppServerStatus;

@RestController
@RequestMapping(path = "/get")
public class MicroserviceA {
	
	@Autowired
	MicroserviceAService microserviceAService;
	
	
    @GetMapping("/data")
    public List<AppServerStatus> getDetails() {
    	
    	List<AppServerStatus> appServerList = microserviceAService.selectAllAppServerName();
        return appServerList;
    }

}
