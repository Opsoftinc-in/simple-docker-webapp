package com.practice.webapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/api/info")
    public Map<String, Object> info() {
        long uptimeMillis = ManagementFactory.getRuntimeMXBean().getUptime();
        Duration uptime = Duration.ofMillis(uptimeMillis);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Hello from your Dockerized Java webapp!");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("javaVersion", System.getProperty("java.version"));
        response.put("javaVendor", System.getProperty("java.vendor"));
        response.put("osName", System.getProperty("os.name"));
        response.put("osArch", System.getProperty("os.arch"));
        response.put("availableProcessors", Runtime.getRuntime().availableProcessors());
        response.put("uptimeSeconds", uptime.toSeconds());
        response.put("uptimeFormatted", formatUptime(uptime));
        return response;
    }

    @GetMapping("/api/greet")
    public Map<String, String> greet(@RequestParam(defaultValue = "DevOps") String name) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("greeting", "Hello, " + name + "!");
        return response;
    }

    private String formatUptime(Duration d) {
        long h = d.toHours();
        long m = d.toMinutesPart();
        long s = d.toSecondsPart();
        return String.format("%02d:%02d:%02d", h, m, s);
    }

}
