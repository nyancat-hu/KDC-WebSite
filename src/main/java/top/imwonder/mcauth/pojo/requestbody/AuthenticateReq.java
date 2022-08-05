package top.imwonder.mcauth.pojo.requestbody;

import com.fasterxml.jackson.annotation.JsonInclude;

import top.imwonder.mcauth.pojo.Agent;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticateReq {

    private String username;

    private String password;

    private String clientToken;

    private Boolean requestUser;

    private Agent agent;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public Boolean getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(Boolean requestUser) {
        this.requestUser = requestUser;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
