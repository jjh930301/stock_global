package stock.global.api.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RouteController {
    @GetMapping("/healthcheck")
    public String healthcheck() {
        return "OK";
    }
    
}
