package top.imwonder.mcauth.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import top.imwonder.mcauth.pojo.responsebody.ApiMeta;
import top.imwonder.mcauth.pojo.responsebody.DownloadBean;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/help")
public class HelpController {


    @GetMapping("/status")
    @ResponseStatus(code = HttpStatus.OK, reason = "Successful!")
    public String getAlive() {
        return "{\"code\": 200, \"msg\": \"Successful!\"}";
    }

    @GetMapping("/update")
    public DownloadBean getFileUrl(String FileName) {
        final DownloadBean downloadBean = new DownloadBean();
        downloadBean.setUrl(String.format("http://117.78.1.88/source/home:jhu/KDC-resource/%s", FileName));
        // 返回登录信息的转码
        downloadBean.setAuth("Basic " + Base64.getEncoder().encodeToString("jhu:430525@HJf".getBytes(StandardCharsets.UTF_8)));
        return downloadBean;
    }


}
