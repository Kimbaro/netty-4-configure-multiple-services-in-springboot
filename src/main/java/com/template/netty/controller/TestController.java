package com.template.netty.controller;

import com.template.netty.db.redis00.entity.IpTableCache;
import com.template.netty.service.IpTableServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final IpTableServiceImpl ipTableService;

    @GetMapping("/find-all-ip")
    public String findAllIp() {
        return ipTableService.findAllIptables().toString();
    }

    @GetMapping("/find-ip/{companyId}")
    public String findIp(@PathVariable("companyId") String companyId) {
        return ipTableService.findByCompanyId(companyId).toString();
    }

    @PostMapping("/save-ip-tables")
    public String saveIpTables(@RequestBody Map<String, String> body) {
        ipTableService.save(IpTableCache.builder().companyId(body.get("companyId")).ipTables(body.get("ip")).build());
        return ipTableService.findByCompanyId(body.get("companyId")).toString();
    }
}
