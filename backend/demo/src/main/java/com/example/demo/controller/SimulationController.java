package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.demo.service.SimulationService;

@RestController
@RequestMapping("/api")
public class SimulationController {

    private final SimulationService service;

    public SimulationController(SimulationService service) {
        this.service = service;
    }

    @GetMapping("/simulate")
    public Map<String, Object> simulate(@RequestParam String stock) {
        return service.run(stock);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Backend working!";
    }
}