package top.imwonder.mcauth.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
					return textures.getSKIN().getUrl();
				case TexturesInfo.CAPE:
					return textures.getCAPE().getUrl();
				default:
					return "";
			}
		}catch (NullPointerException e){
			return "";
		}
	}
}