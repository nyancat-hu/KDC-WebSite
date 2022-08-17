package top.imwonder.mcauth.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import top.imwonder.mcauth.config.ApplicationConfig;
import top.imwonder.mcauth.pojo.responsebody.DownloadBean;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
@RequestMapping("/help")
public class HelpController {

    @Autowired
    private ApplicationConfig config;

    @GetMapping("/status")
    @ResponseStatus(code = HttpStatus.OK, reason = "Successful!")
    public String getAlive() {
        return "{\"code\": 200, \"msg\": \"Successful!\"}";
    }

    @ResponseBody
    @GetMapping("/update/{FileName}/{osEnv}")
    public DownloadBean getFileUrl(@PathVariable("FileName")String FileName,@PathVariable("osEnv")String osEnv) {
        final DownloadBean downloadBean = new DownloadBean();
        downloadBean.setLink(String.format("http://117.78.1.88/source/home:jhu/KDC-resource/%s", FileName));
        // 返回登录信息的转码
        downloadBean.setAuth("Basic " + Base64.getEncoder().encodeToString("jhu:430525@HJf".getBytes(StandardCharsets.UTF_8)));
        return downloadBean;
    }

    @GetMapping("/download")
    public ResponseEntity<StreamingResponseBody> download() {
        String resourceDir = config.getResourceDir();
        File file = new File(resourceDir, "Game_Start.exe");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(outputStream -> {
                    try (InputStream inputStream = new FileInputStream(file)) {
                        StreamUtils.copy(inputStream, outputStream);
                    } catch (IOException ignored) {
                    }
                });
    }

    @GetMapping("/log")
    public String getLog() {
        return "updateLog";
    }


    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }
}
