package stock.global.api.domain.data;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import stock.global.core.annotations.SwaggerInfo;


@Tag(name = "data")
@Controller
@RequestMapping("data")
public class DataController {
    
    @SwaggerInfo(
        summary="path"
    )
    @GetMapping("path")
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.ok().body("body");
    }
    
}