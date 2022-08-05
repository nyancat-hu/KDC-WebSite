package top.imwonder.mcauth.pojo;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("serverinfo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerInfo {

    private String serverName;

    private String implementationName;

    private String implementationVersion;

    @JsonProperty("feature.non_email_login")
    private boolean nonEmailLogin;

    @JsonProperty("feature.legacy_skin_api")
    private boolean legacySkinApi;

    @JsonProperty("feature.no_mojang_namespace")
    private boolean noMojangNamespace;

    private Map<String, String> links;

    public ServerInfo() {
        this.links = new HashMap<>();
    }

    public ServerInfo copyFrom(ServerInfo serverInfo) {
        this.serverName = serverInfo.serverName;
        this.implementationName = serverInfo.implementationName;
        this.implementationVersion = serverInfo.implementationVersion;
        this.nonEmailLogin = serverInfo.nonEmailLogin;
        this.legacySkinApi = serverInfo.legacySkinApi;
        this.noMojangNamespace = serverInfo.noMojangNamespace;
        this.links = serverInfo.links;
        return this;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getImplementationName() {
        return implementationName;
    }

    public void setImplementationName(String implementationName) {
        this.implementationName = implementationName;
    }

    public String getImplementationVersion() {
        return implementationVersion;
    }

    public void setImplementationVersion(String implementationVersion) {
        this.implementationVersion = implementationVersion;
    }

    public boolean isNonEmailLogin() {
        return nonEmailLogin;
    }

    public void setNonEmailLogin(boolean nonEmailLogin) {
        this.nonEmailLogin = nonEmailLogin;
    }

    public boolean isLegacySkinApi() {
        return legacySkinApi;
    }

    public void setLegacySkinApi(boolean legacySkinApi) {
        this.legacySkinApi = legacySkinApi;
    }

    public boolean isNoMojangNamespace() {
        return noMojangNamespace;
    }

    public void setNoMojangNamespace(boolean noMojangNamespace) {
        this.noMojangNamespace = noMojangNamespace;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setHomepage(String homepage) {
        links.put("homepage", homepage);
    }

    public void setRegister(String register) {
        links.put("register", register);
    }

}
