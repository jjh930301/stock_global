package stock.global.ws.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthController {
    @GetMapping(path="/healthcheck",headers="Connection!=Upgrade")
    public String status() {
        return "OK";
    }
    
}