package top.imwonder.mcauth.controller.init;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import top.imwonder.mcauth.config.ApplicationConfig;
import top.imwonder.mcauth.pojo.ServerInfo;
import top.imwonder.mcauth.pojo.responsebody.ApiMeta;
import top.imwonder.util.FileOperatingUtil;
import top.imwonder.util.RSAUtil;

@Component
@Order(value = 1)
public class InitApiMetaTask implements CommandLineRunner {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationConfig config;

    @Autowired
    private ApiMeta apiMeta;

    @Autowired
    private ServerInfo serverInfo;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        String resourceDir = config.getResourceDir();
        File apiJsonFile = new File(resourceDir, "apimeta.json");
        if (!apiJsonFile.exists()) {
            initApiMeta(apiJsonFile);
            return;
        }
        String apiJson = FileOperatingUtil.readForString(apiJsonFile);
        ApiMeta fromSave = objectMapper.readValue(apiJson, ApiMeta.class);
        apiMeta.copyFrom(fromSave);
    }

    public void initApiMeta(File apiJsonFile) {
        apiMeta.setSignaturePublickey(initKey(new File(apiJsonFile.getParent(), "keys")));
        apiMeta.addSkinDomains("localhost");
        serverInfo.setImplementationName("Wonder MC AuthServer");
        serverInfo.setImplementationVersion("1.0.0");
        serverInfo.setLegacySkinApi(false);
        serverInfo.setHomepage("localhost");
        serverInfo.setRegister("localhost/regist");
        serverInfo.setNoMojangNamespace(false);
        serverInfo.setNonEmailLogin(true);
        serverInfo.setServerName("Wonder MC AuthServer");
        try {
            objectMapper.writeValue(apiJsonFile, apiMeta);
        } catch (IOException e) {
            log.info("can not save apimeta.json");
            e.printStackTrace();
        }
    }

    private String initKey(File keyDir) {
        if (!keyDir.exists()) {
            keyDir.mkdirs();
        }
        File publicKey = new File(keyDir, "public.pem");
        if (publicKey.exists()) {
            return String.format(RSAUtil.PEM_PUBLIC_FORMAT, FileOperatingUtil.readForStringNoThrow(publicKey));
        }
        String keyPath = keyDir.getAbsolutePath();
        KeyPair keyPair = RSAUtil.generateKey();
        String pubString = RSAUtil.toPemKey(keyPair.getPublic());
        String priString = RSAUtil.toPemKey(keyPair.getPrivate());
        FileOperatingUtil.printToFile(String.format("%s/public.pem", keyPath), pubString);
        FileOperatingUtil.printToFile(String.format("%s/private.pem", keyPath), priString);
        return String.format(RSAUtil.PEM_PUBLIC_FORMAT, pubString);
    }

}
