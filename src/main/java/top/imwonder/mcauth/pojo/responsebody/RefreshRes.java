package top.imwonder.mcauth.pojo.responsebody;

import com.fasterxml.jackson.annotation.JsonInclude;

import top.imwonder.mcauth.pojo.ProfileInfo;
import top.imwonder.mcauth.pojo.UserInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefreshRes {

    private String accessToken;

    private String clientToken;

    private ProfileInfo selectedProfile;

    private UserInfo user;

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
}
