package top.imwonder.mcauth.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import top.imwonder.mcauth.config.ApplicationConfig;
import top.imwonder.mcauth.dao.TextureDAO;
import top.imwonder.mcauth.domain.Profile;
import top.imwonder.mcauth.domain.Texture;
import top.imwonder.mcauth.enumeration.TextureType;
import top.imwonder.mcauth.pojo.TexturesInfo;
import top.imwonder.mcauth.pojo.WebTexture;
import top.imwonder.mcauth.services.TextureInfoService;
import top.imwonder.mcauth.util.SkinAPIUtil;
import top.imwonder.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class TextureInfoServiceImpl implements TextureInfoService {

    @Autowired
    private TextureDAO textureDAO;

    @Autowired
    private ApplicationConfig config;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Texture upload(MultipartFile texture, String pid, TextureType type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(String pid, TextureType type) {
        // TODO Auto-generated method stub

    }

    @Override
    public TexturesInfo loadTexturesInfoOfProfile(Profile profile) {
        TexturesInfo textures = new TexturesInfo();
        textures.setProfileId(profile.getId());
        textures.setProfileName(profile.getName());
        textures.setTimestamp(System.currentTimeMillis());
        try {
            setTextures(textures, SkinAPIUtil.skinAPI(profile.getName(), profile.getUid()));
        } catch (Exception e) {
            return null;
        }
        return textures;
    }

    @Override
    public String loadTexturesBase64OfProfile(Profile profile) {
        try {
            TexturesInfo texturesInfo = loadTexturesInfoOfProfile(profile);
            if(texturesInfo!=null) return Base64.getEncoder().encodeToString(objectMapper.writeValueAsString(texturesInfo).getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void setTextures(TexturesInfo textures, String base64) throws JsonProcessingException {
        TexturesInfo ti = objectMapper.readValue(SkinAPIUtil.decodeByJava8(base64), TexturesInfo.class);
        textures.addTexture(TexturesInfo.SKIN, ti.getTextureValue(TexturesInfo.SKIN));
        textures.addTexture(TexturesInfo.CAPE, ti.getTextureValue(TexturesInfo.CAPE));
    }

}
