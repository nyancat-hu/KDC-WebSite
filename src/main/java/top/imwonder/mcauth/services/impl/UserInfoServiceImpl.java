package top.imwonder.mcauth.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import top.imwonder.mcauth.dao.ProfileDAO;
import top.imwonder.mcauth.dao.user.UserInfoDAO;
import top.imwonder.mcauth.dao.user.UserPwdDAO;
import top.imwonder.mcauth.domain.Profile;
import top.imwonder.mcauth.domain.User;
import top.imwonder.mcauth.enumeration.LoginType;
import top.imwonder.mcauth.pojo.Property;
import top.imwonder.mcauth.pojo.UserInfo;
import top.imwonder.mcauth.services.ProfileInfoService;
import top.imwonder.mcauth.services.UserInfoService;

import java.util.Objects;

@Service
public class UserInfoServiceImpl implements UserInfoService {



    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserPwdDAO userPwdDAO;

    @Autowired
    private UserInfoDAO userInfoDAO;

    @Autowired
    private ProfileDAO profileDAO;

    @Override
    public LoginType verifyPwdForType(String username, String pwd) {
        LoginType type = LoginType.FAIL;
        User user = userPwdDAO.loadOneByClause("where w_username=? or w_email=?", username, username);
        if (user == null) {
            Profile profile = profileDAO.loadOneByClause(ProfileInfoService.LOAD_BY_NAME_CLAUSE, username);
            type = LoginType.PROFILE_NAME;
            user = userPwdDAO.loadOneByPk(profile.getUid());
        }
        if (user == null) {
            return LoginType.FAIL;
        }
        if (!passwordEncoder.matches(pwd, user.getPassword())) {
            return LoginType.FAIL;
        }
        if (type == LoginType.PROFILE_NAME) {
            return LoginType.PROFILE_NAME;
        }
        if (Objects.equals(user.getEmail(), username)) {
            return LoginType.EMAIL;
        }
        return LoginType.USERNAME;
    }


    @Override
    public User loadUser(String username, LoginType type) {
        switch (type) {
            case EMAIL:
                return loadUserByEmail(username);
            case USERNAME:
                return loadUserByUsername(username);
            case PROFILE_NAME:
                return loadUserByProfileName(username);
            default:
                return null;
        }
    }


    @Override
    public User loadUserByEmail(String email) {
        return userInfoDAO.loadOneByClause("where w_email=?", email);
    }

    @Override
    public User loadUserByUsername(String username) {
        return userInfoDAO.loadOneByClause("where w_username=?", username);
    }

    @Override
    public User loadUserByProfileName(String profileName) {
        Profile profile = profileDAO.loadOneByClause(ProfileInfoService.LOAD_BY_NAME_CLAUSE, profileName);
        return userInfoDAO.loadOneByPk(profile.getUid());
    }

    @Override
    public UserInfo createUserInfo(String uid) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(uid);
        userInfo.addProperty(Property.PREF_LANGUAGE, "zh_CN");
        return userInfo;
    }

    @Override
    public int insertUserInfo(User user) {
        return userInfoDAO.insert(user);
    }

    @Override
    public int changePassword(User user) {
        return userInfoDAO.update(user);
    }

    @Override
    public int deleteUser(String id) {
        return userInfoDAO.delete(id);
    }
}
