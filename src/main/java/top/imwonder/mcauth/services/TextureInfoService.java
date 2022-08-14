package top.imwonder.mcauth.services;

import org.springframework.web.multipart.MultipartFile;

import top.imwonder.mcauth.domain.Profile;
import top.imwonder.mcauth.domain.Texture;
import top.imwonder.mcauth.enumeration.TextureType;
import top.imwonder.mcauth.pojo.TexturesInfo;

public interface TextureInfoService {

    Texture upload(MultipartFile texture, String pid, TextureType type);

    void delete(String pid, TextureType type);

    TexturesInfo loadTexturesInfoOfProfile(Profile profile);

    String loadTexturesBase64OfProfile(Profile profile);
}
