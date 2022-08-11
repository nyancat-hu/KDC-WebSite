package top.imwonder.mcauth.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Textures{

	@JsonProperty("SKIN")
	private SKIN sKIN;

	@JsonProperty("CAPE")
	private CAPE cAPE;
	public Textures(){}
	public Textures(SKIN skin, CAPE cape){
		this.sKIN = skin;
		this.cAPE = cape;
	}

	@JsonIgnore
	public void setSKIN(SKIN sKIN){
		this.sKIN = sKIN;
	}
	@JsonIgnore
	public SKIN getSKIN(){
		return sKIN;
	}
	@JsonIgnore
	public void setCAPE(CAPE cAPE){
		this.cAPE = cAPE;
	}
	@JsonIgnore
	public CAPE getCAPE(){
		return cAPE;
	}
}