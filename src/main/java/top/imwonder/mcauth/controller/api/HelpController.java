package top.imwonder.mcauth.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import top.imwonder.mcauth.config.ApplicationConfig;
import top.imwonder.mcauth.pojo.requestbody.ForgetPasswordReq;
import top.imwonder.mcauth.pojo.requestbody.RegisterReq;
import top.imwonder.mcauth.pojo.responsebody.DownloadBean;
import top.imwonder.mcauth.services.UserInfoService;
import top.imwonder.mcauth.services.impl.ChangePasswordImpl;
import top.imwonder.mcauth.services.impl.EmailImpl;
import top.imwonder.util.FileOperatingUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@CrossOrigin
@Controller
@RequestMapping("/help")
public class HelpController {

    @Autowired
    private ApplicationConfig config;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailImpl email;
    @Autowired
    private ChangePasswordImpl changePassword;
    private LinkedHashMap<String,String> codeMap = new LinkedHashMap<>();
    @Autowired
    private UserInfoService userInfoService;


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

//    @GetMapping("/log")
//    public String getLog() {
//        return "updateLog";
//    }
//
//
//    @GetMapping("/about")
//    public String getAbout() {
//        return "about";
//    }

    @ResponseBody
    @GetMapping(value = "/modlist" , produces = "text/html;charset=UTF-8")
    public String getModList() throws IOException {
        String resourceDir = config.getResourceDir();
        File modlistFile = new File(resourceDir, "modlist.txt");
        String modlist = FileOperatingUtil.readForString(modlistFile);
        return modlist;
    }

    //重新设置密码
    @ResponseBody
    @PostMapping("/resetPassword")
    public int resetPassword(@RequestBody ForgetPasswordReq req)  {
        req.setPassword(passwordEncoder.encode(req.getPassword()));
        return changePassword.changePassword(req);
    }

    //发送验证码并验证邮箱
    @ResponseBody
    @PostMapping("/email")
    public String  email(@RequestBody RegisterReq req){
        if(userInfoService.loadUserByEmail(req.getEmail())!=null)
        {
            String code= email.sentEmail(req.getEmail());
            String uuid = String.valueOf(UUID.randomUUID());
            codeMap.put(uuid,code);
            codeMap.put(code,req.getEmail());
            if(codeMap.size()>1000){
                int num = 0;
                Iterator<Map.Entry<String, String>> iterator = codeMap.entrySet().iterator();
                while(num<500){
                    iterator.remove();
                    num+=1;
                }
            }
            return uuid;
        }
        else
        {

            return "no";
        }
    }
    @ResponseBody
    @PostMapping("/checkCode")
    public String checkCode(@RequestBody RegisterReq req) {
        //防止前后邮箱不一样
        if (codeMap.get(req.getUuid()).equals(req.getCode())&&codeMap.get(req.getCode()).equals(req.getEmail())) {
            codeMap.remove(req.getUuid());
            codeMap.remove(req.getCode());
            return "yes"; //重定向
        }
        return "no";
    }


    @GetMapping("/second")
    public String returnSecond( ) {
        return "second";
    }

    //入口
    @GetMapping("/forgetPassword")
    public String returnFirst( ) {
        return "first";
    }

}
