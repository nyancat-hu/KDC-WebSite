package top.imwonder.mcauth.services;

import java.util.List;

import top.imwonder.mcauth.domain.Profile;
import top.imwonder.mcauth.domain.User;
import top.imwonder.mcauth.pojo.ProfileInfo;

public interface ProfileInfoService {

    static final String LOAD_BY_NAME_CLAUSE = "where w_name=?";

    boolean isBlongTo(String pid, String username);

    // boolean isBlongTo(String profileName, String username);

    List<ProfileInfo> loadAllProfileOfUser(String uid, boolean hasProps, boolean isSign);

    ProfileInfo loadProfileByName(String profileName, boolean hasProps, boolean isSign);

    ProfileInfo loadProfileByName(Profile profile, boolean hasProps, boolean isSign);

    List<ProfileInfo> loadProfilesByName(boolean hasProps, boolean isSign, String... pns);

    int insertProfile(Profile profile);
}
