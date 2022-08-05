package top.imwonder.mcauth.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Property {

    public static final String PREF_LANGUAGE = "preferredLanguage";
    public static final String TEXTURES = "textures";
    public static final String UPLOADABLE_TEXTURES = "uploadableTextures";

    private String name;

    private String value;

    private String signature;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
