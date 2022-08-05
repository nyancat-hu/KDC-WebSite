package top.imwonder.mcauth.pojo.requestbody;

import com.fasterxml.jackson.annotation.JsonInclude;

import top.imwonder.mcauth.pojo.ProfileInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefreshReq {

    private String accessToken;

    private String clientToken;

    private Boolean requestUser;

    private ProfileInfo selectedProfile;

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

    public Boolean getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(Boolean requestUser) {
        this.requestUser = requestUser;
    }
}
