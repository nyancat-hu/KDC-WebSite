package top.imwonder.mcauth.services;

import top.imwonder.mcauth.domain.Profile;
import top.imwonder.mcauth.domain.Token;
import top.imwonder.mcauth.enumeration.TokenStatus;

public interface TokenService {

    static final String LOAD_BY_ACCESS_TOKEN = "where w_access_token=?";

    Token createToken(String uid);

    Token createToken(String uid, String clientToken);

    Token createToken(String uid, String pid, String clientToken);

    Token querySessionByAccessToken(String accessToken);

    Token updateToken(Token token);

    TokenStatus checkToken(String accessToken);

    void removeToken(String accessToken);

    void bindProfile(Token token, String pid);

    void bindProfile(Token token, Profile profile);
}
