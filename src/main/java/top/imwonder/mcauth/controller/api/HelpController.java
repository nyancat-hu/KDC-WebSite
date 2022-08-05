package top.imwonder.mcauth.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import top.imwonder.mcauth.pojo.responsebody.ApiMeta;

@RestController
@RequestMapping("/help")
public class HelpController {


    @GetMapping("/status")
    @ResponseStatus(code = HttpStatus.OK, reason = "Successful!")
    public String getAlive() {
        return "{\"code\": 200, \"msg\": \"Successful!\"}";
    }
}
