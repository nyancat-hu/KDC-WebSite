package top.imwonder.mcauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("env")
public class ApplicationConfig {

    private String host;

    private Long sessionLimit;

    private Long accessTokenLimit;

    private Long accessTokenKeep;

    private Long clientTokenLimit;

    private String resourceDir;

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public Long getSessionLimit() {
        return sessionLimit;
    }

    public void setSessionLimit(Long sessionLimit) {
        this.sessionLimit = sessionLimit;
    }

    public Long getAccessTokenLimit() {
        return accessTokenLimit;
    }

    public void setAccessTokenLimit(Long tokenLimit) {
        this.accessTokenLimit = tokenLimit;
    }

    public Long getAccessTokenKeep() {
        return accessTokenKeep;
    }

    public void setAccessTokenKeep(Long tokenKeep) {
        this.accessTokenKeep = tokenKeep;
    }

    public Long getClientTokenLimit() {
        return clientTokenLimit;
    }

    public void setClientTokenLimit(Long clientTokenLimit) {
        this.clientTokenLimit = clientTokenLimit;
    }

    public String getResourceDir() {
        return resourceDir;
    }

    public void setResourceDir(String resourceDir) {
        this.resourceDir = resourceDir;
    }

}