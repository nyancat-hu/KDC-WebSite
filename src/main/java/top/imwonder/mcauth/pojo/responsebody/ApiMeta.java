package top.imwonder.mcauth.pojo.responsebody;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import top.imwonder.mcauth.pojo.ServerInfo;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiMeta {

    @Autowired
    private ServerInfo meta;

    private List<String> skinDomains;

    private String signaturePublickey;

    public ApiMeta() {
        this.skinDomains = new ArrayList<>();
    }

    public ApiMeta copyFrom(ApiMeta apiMeta) {
        this.skinDomains = apiMeta.skinDomains;
        this.signaturePublickey = apiMeta.signaturePublickey;
        meta.copyFrom(apiMeta.meta);
        return this;
    }

    public ServerInfo getMeta() {
        return meta;
    }

    public List<String> getSkinDomains() {
        return skinDomains;
    }

    public ApiMeta addSkinDomains(String skinDomains) {
        this.skinDomains.add(skinDomains);
        return this;
    }

    public String getSignaturePublickey() {
        return signaturePublickey;
    }

    public void setSignaturePublickey(String signaturePublickey) {
        this.signaturePublickey = signaturePublickey;
    }

}
