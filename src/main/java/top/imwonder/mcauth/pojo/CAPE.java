package top.imwonder.mcauth.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CAPE{

	@JsonProperty("url")
	private String url;
	public CAPE(){

	}
	public CAPE(String url){
		this.url = url;
	}
	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url == null?"":url;
	}
}