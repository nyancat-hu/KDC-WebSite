package top.imwonder.mcauth.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import top.imwonder.mcauth.dao.ProfileDAO;
import top.imwonder.mcauth.domain.Profile;
import top.imwonder.mcauth.domain.Token;
import top.imwonder.mcauth.exception.WonderMcException;
import top.imwonder.mcauth.pojo.ProfileInfo;
import top.imwonder.mcauth.pojo.requestbody.ClientJoinReq;
import top.imwonder.mcauth.pojo.requestbody.ServerCheckJoinReq;
import top.imwonder.mcauth.services.ProfileInfoService;
import top.imwonder.mcauth.services.SessionService;
import top.imwonder.mcauth.services.TokenService;
import top.imwonder.util.StringUtil;

@RestController
@RequestMapping("/api/sessionserver/session/minecraft")
public class SessionController {

    @Autowired
    private ProfileDAO profileDAO;

    @Autowired
    private ProfileInfoService profileInfoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody ClientJoinReq req) {
        String accessToken = req.getAccessToken();
        Token token = tokenService.querySessionByAccessToken(accessToken);
        if (token == null) {
            throw WonderMcException.forbiddenOperationException("令牌无效");
        }
        String pid = token.getPid();

        if (StringUtil.isEmpty(pid) || !pid.equals(req.getSelectedProfile())) {
            throw WonderMcException.forbiddenOperationException("令牌无效");
        }
        sessionService.clientJoin(accessToken, pid, req.getServerId());
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hasJoined")
    public Object hasjoin(ServerCheckJoinReq req) {
        ProfileInfo profile = sessionService.hasJoined(req.getUsername(), req.getServerId(), req.getIp());
        if (profile == null) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        return profile;
    }

    @GetMapping("/profile/{uuid}")
    public ProfileInfo profile(@PathVariable("uuid") String uuid, Boolean unsigned) {
        Profile profile = profileDAO.loadOneByPk(uuid);
        return profileInfoService.loadProfileByName(profile, true, unsigned == null || !unsigned);
    }
}
