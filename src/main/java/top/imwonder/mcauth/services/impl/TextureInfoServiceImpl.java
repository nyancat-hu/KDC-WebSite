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
import top.imwonder.mcauth.pojo.TexturesInfo.TextureMeta;
import top.imwonder.mcauth.services.TextureInfoService;
import top.imwonder.util.StringUtil;

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
        String skinId = profile.getSkinId();
        String capeId = profile.getCapeId();
        if (!StringUtil.isEmpty(skinId)) {
            Texture texture = textureDAO.loadOneByPk(skinId);
            TextureMeta tm = textures.addTexture(TexturesInfo.SKIN, textureUrl(texture.getHash()));
            tm.addMetadata("model", texture.getType().getModel());
        }
        if (!StringUtil.isEmpty(capeId)) {
            Texture texture = textureDAO.loadOneByPk(capeId);
            textures.addTexture(TexturesInfo.CAPE, textureUrl(texture.getHash()));
        }
        return textures;
    }

    @Override
    public String loadTexturesBase64OfProfile(Profile profile) {
        try {
            return objectMapper.writeValueAsString(loadTexturesInfoOfProfile(profile));
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    private String textureUrl(String hash) {
        return String.format("%s://%s/api/textures/%s", "https", config.getHost(), hash);
    }

}
