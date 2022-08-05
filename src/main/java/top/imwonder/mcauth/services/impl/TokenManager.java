package top.imwonder.mcauth.services.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import top.imwonder.mcauth.config.ApplicationConfig;
import top.imwonder.mcauth.dao.ProfileDAO;
import top.imwonder.mcauth.dao.TokenDAO;
import top.imwonder.mcauth.domain.Profile;
import top.imwonder.mcauth.domain.Token;
import top.imwonder.mcauth.enumeration.TokenStatus;
import top.imwonder.mcauth.exception.WonderMcException;
import top.imwonder.mcauth.services.TokenService;
import top.imwonder.util.IdUtil;

@Service
public class TokenManager implements TokenService {

    private Logger log = LoggerFactory.getLogger(getClass());

    private long accessTokenLimit;

    private long accessTokenKeep;

    private long clientTokenLimit;

    @Autowired
    private JdbcTemplate jt;

    @Autowired
    private TokenDAO tokenDAO;

    @Autowired
    private ProfileDAO profileDAO;

    @Autowired
    public TokenManager(ApplicationConfig config) {
        accessTokenLimit = config.getAccessTokenLimit() * 1000;
        accessTokenKeep = config.getAccessTokenKeep() * 1000;
        clientTokenLimit = config.getClientTokenLimit() * 1000;
        if (clientTokenLimit < accessTokenKeep) {
            log.warn("clientTokenLimit should be longer than accessTokenKeep");
            config.setAccessTokenKeep(config.getClientTokenLimit());
            accessTokenKeep = clientTokenLimit;
        }
        if (accessTokenKeep < accessTokenLimit) {
            log.warn("accessTokenKeep should be longer than accessTokenLimit");
            config.setAccessTokenLimit(config.getAccessTokenKeep());
            accessTokenLimit = accessTokenKeep;
        }
    }

    @Override
    public Token createToken(String uid) {
        return createToken(uid, null, IdUtil.uuid());
    }

    @Override
    public Token createToken(String uid, String clientToken) {
        return createToken(uid, null, clientToken);
    }

    @Override
    public Token createToken(String uid, String pid, String clientToken) {
        Token token = new Token();
        token.setCreateTime(new Date());
        token.setAccessTime(new Date());
        token.setAccessToken(IdUtil.uuid());
        token.setClientToken(clientToken == null ? IdUtil.uuid() : clientToken);
        token.setUid(uid);
        token.setPid(pid);
        tokenDAO.insert(token);
        return token;
    }

    @Override
    public Token querySessionByAccessToken(String accessToken) {
        Token token = tokenDAO.loadOneByClause(LOAD_BY_ACCESS_TOKEN, accessToken);
        if (checkToken(token) == TokenStatus.INVALID) {
            return null;
        }
        return token;
    }

    @Override
    public Token updateToken(Token token) {
        String accessToken = token.getAccessToken();
        String clientToken = token.getClientToken();
        Token tokenFromDB = tokenDAO.loadOneByPk(clientToken, accessToken);
        if (checkToken(token) == TokenStatus.INVALID) {
            return null;
        }
        tokenFromDB.setAccessTime(new Date());
        token.setAccessToken(IdUtil.uuid());
        tokenDAO.updateWithPk(token, accessToken, clientToken);
        return token;
    }

    @Override
    public TokenStatus checkToken(String accessToken) {
        Token token = tokenDAO.loadOneByClause(LOAD_BY_ACCESS_TOKEN, accessToken);
        return checkToken(token);
    }

    private TokenStatus checkToken(Token token) {
        long now = System.currentTimeMillis();
        if (token.getCreateTime().getTime() + clientTokenLimit < now) {
            return TokenStatus.INVALID;
        }
        if (token.getAccessTime().getTime() + accessTokenLimit > now) {
            return TokenStatus.VALID;
        }
        if (token.getAccessTime().getTime() + accessTokenKeep > now) {
            return TokenStatus.HALF_VALID;
        }
        return TokenStatus.INVALID;
    }

    @Override
    public void removeToken(String accessToken) {
        jt.update("delete from w_token where w_access_token=?", accessToken);
    }

    @Override
    public void bindProfile(Token token, String pid) {
        Profile profile = profileDAO.loadOneByPk(pid);
        bindProfile(token, profile);
    }

    @Override
    public void bindProfile(Token token, Profile profile) {
        if (checkToken(token) == TokenStatus.INVALID) {
            throw WonderMcException.forbiddenOperationException("令牌无效");
        }
        if (!token.getUid().equals(profile.getUid())) {
            throw WonderMcException.forbiddenOperationException("非法操作");
        }
        token.setPid(profile.getId());
        token.setAccessTime(new Date());
        tokenDAO.update(token);
    }
}
