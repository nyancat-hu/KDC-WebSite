package top.imwonder.mcauth.services;

import top.imwonder.mcauth.domain.User;
import top.imwonder.mcauth.enumeration.LoginType;
import top.imwonder.mcauth.pojo.UserInfo;

public interface UserInfoService {

    public default boolean verifyPwd(String username, String pwd) {
        LoginType type = verifyPwdForType(username, pwd);
        return type != null && type != LoginType.FAIL;
    };

    LoginType verifyPwdForType(String username, String pwd);

    User loadUser(String username, LoginType type);

    User loadUserByEmail(String email);

    User loadUserByUsername(String username);

    User loadUserByProfileName(String profileName);

    UserInfo createUserInfo(String uid);

   int insertUserInfo(User user);

   int changePassword(User user);

   int deleteUser(String id);
}
