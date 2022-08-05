package top.imwonder.mcauth.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.imwonder.mcauth.dao.ProfileDAO;
import top.imwonder.mcauth.dao.user.UserInfoDAO;
import top.imwonder.mcauth.domain.Profile;
import top.imwonder.mcauth.domain.User;
import top.imwonder.mcauth.pojo.ProfileInfo;
import top.imwonder.mcauth.pojo.Property;
import top.imwonder.mcauth.services.ProfileInfoService;
import top.imwonder.mcauth.services.TextureInfoService;

@Service
public class ProfileInfoServiceImpl implements ProfileInfoService {

    private static final String IS_BLONG_TO_CLAUSE = "where w_id=(select w_uid from w_profile where w_id=?) and (w_username=? or w_email=?)";

    @Autowired
    private UserInfoDAO userDAO;

    @Autowired
    private ProfileDAO profileDAO;

    @Autowired
    private TextureInfoService textureInfoService;

    @Override
    public boolean isBlongTo(String pid, String username) {
        User user = userDAO.loadOneByClause(IS_BLONG_TO_CLAUSE, pid, username, username);
        return user != null;
    }

    @Override
    public List<ProfileInfo> loadAllProfileOfUser(String uid, boolean hasProps, boolean isSign) {
        List<ProfileInfo> list = new ArrayList<>();
        List<Profile> profiles = profileDAO.loadMore("where w_uid=?", uid);
        for (Profile profile : profiles) {
            ProfileInfo pi = loadProfileByName(profile, hasProps, isSign);
            if (pi != null) {
                list.add(pi);
            }
        }
        return list;
    }

    @Override
    public ProfileInfo loadProfileByName(String profileName, boolean hasProps, boolean isSign) {
        Profile profile = profileDAO.loadOneByClause(LOAD_BY_NAME_CLAUSE, profileName);
        if (profile == null) {
            return null;
        }
        return loadProfileByName(profile, hasProps, isSign);
    }

    @Override
    public ProfileInfo loadProfileByName(Profile profile, boolean hasProps, boolean isSign) {
        if (profile == null) {
            return null;
        }
        ProfileInfo rst = new ProfileInfo();
        rst.setId(profile.getId());
        rst.setName(profile.getName());
        if (hasProps) {
            rst.addProperty(Property.UPLOADABLE_TEXTURES, "skin,cape", isSign);
            rst.addProperty(Property.TEXTURES, textureInfoService.loadTexturesBase64OfProfile(profile), isSign);
        }
        return rst;
    }

    @Override
    public List<ProfileInfo> loadProfilesByName(boolean hasProps, boolean isSign, String... pns) {
        List<ProfileInfo> list = new ArrayList<>();
        for (String pn : pns) {
            ProfileInfo pi = loadProfileByName(pn, hasProps, isSign);
            if (pi != null) {
                list.add(pi);
            }
        }
        return list;
    }

    @Override
    public int insertProfile(Profile profile) {
        return profileDAO.insert(profile);
    }

}
