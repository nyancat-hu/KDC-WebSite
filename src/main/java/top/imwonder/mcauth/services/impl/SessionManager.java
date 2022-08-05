package top.imwonder.mcauth.services.impl;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import top.imwonder.mcauth.config.ApplicationConfig;
import top.imwonder.mcauth.domain.Token;
import top.imwonder.mcauth.pojo.ProfileInfo;
import top.imwonder.mcauth.pojo.YggdrasilSession;
import top.imwonder.mcauth.services.ProfileInfoService;
import top.imwonder.mcauth.services.SessionService;
import top.imwonder.mcauth.services.TokenService;
import top.imwonder.mcauth.util.IPUtil;
import top.imwonder.util.StringUtil;

@Service
public class SessionManager implements SessionService {

    private ExpiringMap<String, YggdrasilSession> sessions;

    @Autowired
    private HttpServletRequest req;

    @Autowired
    private ProfileInfoService profileInfoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    public SessionManager(ApplicationConfig config) {
        this.sessions = ExpiringMap.builder().expiration(config.getSessionLimit(), TimeUnit.SECONDS)
                .expirationPolicy(ExpirationPolicy.ACCESSED).build();
    }

    @Override
    public YggdrasilSession clientJoin(String accessToken, String pid, String serverId) {
        YggdrasilSession session = new YggdrasilSession();
        session.setAccessToken(accessToken);
        session.setPid(pid);
        session.setSid(serverId);
        session.setIp(IPUtil.getRemoteAddr(req));
        this.sessions.put(serverId, session);
        return session;
    }

    @Override
    public ProfileInfo hasJoined(String profileName, String serverId, String ip) {
        YggdrasilSession session = this.sessions.get(serverId);
        if (session == null) {
            return null;
        }
        if (!StringUtil.isEmpty(ip) && !ip.trim().equals(session.getIp())) {
            return null;
        }
        Token token = tokenService.querySessionByAccessToken(session.getAccessToken());
        ProfileInfo pi = profileInfoService.loadProfileByName(profileName, true, true);
        if (pi.getId().equals(token.getPid())) {
            return pi;
        }
        return null;
    }

}
