package com.template.netty.controller;

import com.template.netty.service.IpTableServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final IpTableServiceImpl ipTableService;

    @GetMapping
    public String findAllIp() {
        ipTableService.findAll();
        return new String("");
    }
}
