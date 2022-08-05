package top.imwonder.mcauth.pojo;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TexturesInfo {

    public static final String SKIN = "SKIN";
    public static final String CAPE = "CAPE";

    private long timestamp;

    private String profileId;

    private String profileName;

    private Map<String, TextureMeta> textures;

    public TexturesInfo() {
        this.textures = new HashMap<>();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public TextureMeta addTexture(String type, String url) {
        TextureMeta texture = new TextureMeta();
        texture.url = url;

        this.textures.put(type, texture);
        return texture;

    }

    public class TextureMeta {

        private String url;

        private Map<String, String> metadata;

        public String getUrl() {
            return url;
        }

        public Map<String, String> getMetadata() {
            return metadata;
        }

        public TextureMeta addMetadata(String key, String value) {
            if (this.metadata == null) {
                this.metadata = new HashMap<>();
            }
            this.metadata.put(key, value);
            return this;
        }

    }

}
