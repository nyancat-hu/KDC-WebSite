package top.imwonder.mcauth.pojo.requestbody;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientJoinReq {

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSelectedProfile() {
        return selectedProfile;
    }

    public void setSelectedProfile(String selectedProfile) {
        this.selectedProfile = selectedProfile;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    private String selectedProfile;

    private String serverId;
}
