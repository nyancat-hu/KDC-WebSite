package top.imwonder.mcauth.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import top.imwonder.mcauth.exception.WonderMcException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class YggdrasilToken {

    private String clientToken;

    private String accessToken;

    private UserInfo user;

    private ProfileInfo role;

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public ProfileInfo getRole() {
        return role;
    }

    public void setRole(ProfileInfo role) {
        if (this.role != null) {
            throw WonderMcException.illegalArgumentException("试图向一个已经绑定了角色的令牌指定其它角色");
        }
        this.role = role;
    }

    public boolean isBind(String pid) {
        return role != null && role.getId().equals(pid);
    }

}
