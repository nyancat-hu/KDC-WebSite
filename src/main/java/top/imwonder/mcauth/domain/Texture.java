package top.imwonder.mcauth.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import top.imwonder.mcauth.enumeration.TextureType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Texture {

    private String id;

    private String hash;

    private String name;

    private TextureType type;

    private Integer size;

    private String upid;

    private Date time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TextureType getType() {
        return type;
    }

    public void setType(TextureType type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getUpid() {
        return upid;
    }

    public void setUpid(String upid) {
        this.upid = upid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
