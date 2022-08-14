package top.imwonder.mcauth.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class TexturesInfo{
	public static final String SKIN = "SKIN";
	public static final String CAPE = "CAPE";

	@JsonProperty("timestamp")
	private Long timestamp;

	@JsonProperty("profileId")
	private String profileId;

	@JsonProperty("profileName")
	private String profileName;

	@JsonProperty("textures")
	private Textures textures;

	public void setProfileName(String profileName){
		this.profileName = profileName;
	}

	public String getProfileName(){
		return profileName;
	}

	public void setTextures(Textures textures){ this.textures = textures;
	}

	public Textures getTextures(){
		return textures;
	}

	public void setProfileId(String profileId){
		this.profileId = profileId;
	}

	public String getProfileId(){
		return profileId;
	}

	public void setTimestamp(Long timestamp){
		this.timestamp = timestamp;
	}

	public Long getTimestamp(){
		return timestamp;
	}

	public void addTexture(String type, String url) {
		if(textures == null) textures = new Textures(new SKIN(), new CAPE());
		switch(type){
			case TexturesInfo.SKIN:
				textures.setSKIN(new SKIN(url));
				break;
			case TexturesInfo.CAPE:
				textures.setCAPE(new CAPE(url));
				break;
			default:
				break;
		}
	}
	public String getTextureValue(String key){
		try{
			switch(key){
				case TexturesInfo.SKIN:
					String url = textures.getSKIN().getUrl();
					return StringUtils.isEmpty(url) ?
							"http://textures.minecraft.net/texture/7868f0fa8378776ac1575e6acbdd278110b0d91cbb9ff950b8ca010474652103" : url;
				case TexturesInfo.CAPE:
					String url2 = textures.getCAPE().getUrl();
					return StringUtils.isEmpty(url2) ?
							"http://textures.minecraft.net/texture/2340c0e03dd24a11b15a8b33c2a7e9e32abb2051b2481d0ba7defd635ca7a933" : url2;
				default:
					return "http://textures.minecraft.net/texture/2340c0e03dd24a11b15a8b33c2a7e9e32abb2051b2481d0ba7defd635ca7a933";
			}
		}catch (NullPointerException e){
			if(key.equals(TexturesInfo.SKIN))return "http://textures.minecraft.net/texture/7868f0fa8378776ac1575e6acbdd278110b0d91cbb9ff950b8ca010474652103";
			else return "http://textures.minecraft.net/texture/2340c0e03dd24a11b15a8b33c2a7e9e32abb2051b2481d0ba7defd635ca7a933";
		}
	}
}