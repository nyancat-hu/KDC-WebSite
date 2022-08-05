package top.imwonder.mcauth.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import top.imwonder.mcauth.enumeration.TextureType;
import top.imwonder.mcauth.exception.WonderMcException;
import top.imwonder.mcauth.pojo.ProfileInfo;
import top.imwonder.mcauth.pojo.responsebody.ApiMeta;
import top.imwonder.mcauth.services.ProfileInfoService;

@RestController
@RequestMapping("/api")
public class CommonController {

    @Autowired
    private ProfileInfoService profileInfoService;

    @Autowired
    private ApiMeta apiMeta;

    @GetMapping("/")
    public ApiMeta getApiMeta() {
        return apiMeta;
    }

    @PostMapping("/profiles/minecraft")
    public List<ProfileInfo> queryProfiles(@RequestBody List<String> profileNames) {
        if (profileNames.size() > 50) {
            throw WonderMcException.illegalArgumentException("errorsize");
        }
        return profileInfoService.loadProfilesByName(false, false, profileNames.toArray(new String[0]));
    }

    @PutMapping("/user/profile/{uuid}/{textureType}")
    public ResponseEntity<Void> uploadTexture(@PathVariable("uuid") String uuid,
            @PathVariable("textureType") TextureType textureType, String model, MultipartFile file) {

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/user/profile/{uuid}/{textureType}")
    public ResponseEntity<Void> deleteTexture(@PathVariable("uuid") String uuid,
            @PathVariable("textureType") TextureType textureType) {

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
