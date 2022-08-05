package top.imwonder.mcauth.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Profile {

    private String id;

    private String name;

    private String uid;

    private String skinId;

    private String capeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSkinId() {
        return skinId;
    }

    public void setSkinId(String tid) {
        this.skinId = tid;
    }

    public String getCapeId() {
        return capeId;
    }

    public void setCapeId(String capeId) {
        this.capeId = capeId;
    }

    public boolean isBlongTo(String uid) {
        return uid != null && uid.equals(this.uid);
    }
}
