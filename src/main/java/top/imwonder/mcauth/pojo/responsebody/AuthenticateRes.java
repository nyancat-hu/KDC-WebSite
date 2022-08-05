package top.imwonder.mcauth.pojo.responsebody;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import top.imwonder.mcauth.pojo.ProfileInfo;
import top.imwonder.mcauth.pojo.UserInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticateRes {

    private String accessToken;

    private String clientToken;

    private List<ProfileInfo> availableProfiles;

    private ProfileInfo selectedProfile;

    private UserInfo user;

    public AuthenticateRes() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public ProfileInfo getSelectedProfile() {
        return selectedProfile;
    }

    public void setSelectedProfile(ProfileInfo selectedProfile) {
        this.selectedProfile = selectedProfile;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public void setAvailableProfiles(List<ProfileInfo> availableProfiles) {
        this.availableProfiles = availableProfiles;
    }

    public List<ProfileInfo> getAvailableProfiles() {
        return availableProfiles;
    }
}
