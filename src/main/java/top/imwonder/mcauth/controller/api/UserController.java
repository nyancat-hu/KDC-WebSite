package top.imwonder.mcauth.controller.api;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import top.imwonder.mcauth.domain.Profile;
import top.imwonder.mcauth.domain.Token;
import top.imwonder.mcauth.domain.User;
import top.imwonder.mcauth.enumeration.LoginType;
import top.imwonder.mcauth.exception.WonderMcException;
import top.imwonder.mcauth.pojo.ProfileInfo;
import top.imwonder.mcauth.pojo.requestbody.*;
import top.imwonder.mcauth.pojo.responsebody.AuthenticateRes;
import top.imwonder.mcauth.pojo.responsebody.RefreshRes;
import top.imwonder.mcauth.services.ProfileInfoService;
import top.imwonder.mcauth.services.TokenService;
import top.imwonder.mcauth.services.UserInfoService;
import top.imwonder.mcauth.services.UserRegisterService;
import top.imwonder.mcauth.services.impl.EmailImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/authserver")
public class UserController {

    @Autowired
    private TokenService tokens;
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ProfileInfoService profileInfoService;

    @Autowired
    private EmailImpl email;

    private LinkedHashMap<String,String> codeMap = new LinkedHashMap<>();

    @PostMapping("/authenticate")
    public AuthenticateRes authenticate(@RequestBody AuthenticateReq req) {
        AuthenticateRes res = new AuthenticateRes();
        LoginType type = userInfoService.verifyPwdForType(req.getUsername(), req.getPassword());
        if (type == LoginType.FAIL) {
            throw WonderMcException.forbiddenOperationException("账号或密码错误");
        }
        User user = userInfoService.loadUser(req.getUsername(), type);
        Token token = tokens.createToken(user.getId(), req.getClientToken());
        List<ProfileInfo> profiles = profileInfoService.loadAllProfileOfUser(user.getId(), true, true);
        res.setAccessToken(token.getAccessToken());
        res.setClientToken(token.getClientToken());
        res.setAvailableProfiles(profiles);

        if (profiles.size() == 1) {
            res.setSelectedProfile(profiles.get(0));
            tokens.bindProfile(token, profiles.get(0).getId());
        }

        if (type == LoginType.PROFILE_NAME) {
            res.setSelectedProfile(profileInfoService.loadProfileByName(req.getUsername(), true, true));
            tokens.bindProfile(token, res.getSelectedProfile().getId());
        }
        if (req.getRequestUser()) {
            res.setUser(userInfoService.createUserInfo(token.getUid()));
        }
        return res;
    }

    @PostMapping("/refresh")
    public RefreshRes refresh(@RequestBody RefreshReq ref) {
        String accessToken = ref.getAccessToken();
        String clientToken = ref.getClientToken();
        Token token = tokens.querySessionByAccessToken(accessToken);
        if (token == null || (clientToken != null && !token.getClientToken().equals(clientToken))) {
            throw WonderMcException.forbiddenOperationException("令牌无效");
        }
        RefreshRes res = new RefreshRes();
        ProfileInfo profileInfo = ref.getSelectedProfile();
        if (profileInfo != null) {
            tokens.bindProfile(token, profileInfo.getId());
        }
        tokens.updateToken(token);
        res.setAccessToken(token.getAccessToken());
        res.setClientToken(token.getClientToken());
        if (ref.getRequestUser()) {
            res.setUser(userInfoService.createUserInfo(token.getUid()));
        }
        return res;
    }

    @PostMapping("/validate")
    public ResponseEntity<Void> validate(@RequestBody Validate val) {
        String accessToken = val.getAccessToken();
        String clientToken = val.getClientToken();
        Token token = tokens.querySessionByAccessToken(accessToken);
        if (token == null || (clientToken != null && !token.getClientToken().equals(clientToken))) {
            throw WonderMcException.forbiddenOperationException("令牌无效");
        }
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/invalidate")
    public ResponseEntity<Void> invalidate(@RequestBody Validate val) {
        tokens.removeToken(val.getAccessToken());
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> signout(@RequestBody(required=false) String username, @RequestBody(required=false) String password) {
        if (userInfoService.verifyPwd(username, password)) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        throw WonderMcException.forbiddenOperationException("账号或密码错误");
    }
    // post
    @PostMapping("/register")
    public String register(HttpServletRequest request,@RequestBody RegisterReq req){
       if(codeMap.get(req.getUuid()).equals(req.getCode()))
       {
           User user= new User();
           Profile profile= new Profile();
           UUID uuidn = UUID.nameUUIDFromBytes(("OfflinePlayer:" + req.getUsername()).getBytes(StandardCharsets.UTF_8));
           String expenseUUID = uuidn.toString().replaceAll("-", "");
           user.setId(expenseUUID);
           profile.setId(expenseUUID);
           profile.setName(req.getUsername());
           profile.setUid(expenseUUID);
           user.setEmail(req.getEmail());
           user.setUsername(req.getUsername());
           user.setNickname(req.getUsername());
           user.setPassword(passwordEncoder.encode(req.getPassword()));
           userRegisterService.insertUser(user);
           profileInfoService.insertProfile(profile);
           codeMap.remove(req.getUuid());
           return "yes";
       }
       else
       {
           return "no";
       }

    }

    //验证用户名  get
    @GetMapping("/username/{username}")
    public String userName(@PathVariable("username") String userName){
        if(userInfoService.loadUserByUsername(userName)==null)
        {
            return "can use";
        }
        else
        {
            return "not can use";
        }
    }
    //发送验证码并验证邮箱
    @PostMapping("/email")
    public String  email(@RequestBody String ema,HttpServletRequest request){
        if(userInfoService.loadUserByEmail(ema)==null)
        {
//            HttpSession session=request.getSession();
            String code= email.sentEmail(ema);
//            session.setAttribute("code",code);
            String uuid = String.valueOf(UUID.randomUUID());
            codeMap.put(uuid,code);
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
            email.sentEmail(ema);
            return userInfoService.loadUserByEmail(ema).getUsername();
        }
    }
    //修改密码  put
    @PutMapping("/changePassword/{username}/{oldPassword}/{newPassword}")
    public String changePassword(@PathVariable("username")String username,@PathVariable("oldPassword")String oldPassword,@PathVariable("newPassword")String newPassword) {
        User user =userInfoService.loadUserByUsername(username);
        if(newPassword.equals(oldPassword))
        {
            return "password is repeated";
        }
        if(user !=null){
            if (passwordEncoder.matches(oldPassword,user.getPassword()))
            {
                user.setPassword(passwordEncoder.encode(newPassword));
                userInfoService.changePassword(user);
                return "yes";
            }
            else{
                return "password is wrong";
            }
        }
        else{
            return "no username";
        }
    }
//    //删除用户  delete
//    @DeleteMapping("/deleteUser/{username}")
//    public String deleteUser(@PathVariable("username")String username) {
//        User user =userInfoService.loadUserByUsername(username);
//        if(user!=null){
//            userInfoService.deleteUser(user.getId());
//            return "have delete";
//        }else{
//            return "no username";
//        }
//    }
}
