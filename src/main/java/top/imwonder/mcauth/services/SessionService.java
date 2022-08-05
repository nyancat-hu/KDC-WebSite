package top.imwonder.mcauth.services;

import top.imwonder.mcauth.pojo.ProfileInfo;
import top.imwonder.mcauth.pojo.YggdrasilSession;

public interface SessionService {

    YggdrasilSession clientJoin(String accessToken, String pid, String serverId);

    ProfileInfo hasJoined(String username, String serverId, String ip);
}
