package top.imwonder.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSAUtil {

    public static final String PEM_PUBLIC_FORMAT = "-----BEGIN PUBLIC KEY-----\n%s\n-----END PUBLIC KEY-----";
    public static final String PEM_PRIVATE_FORMAT = "-----BEGIN PRIVATE KEY-----\n%s\n-----END PRIVATE KEY-----";
    public static final Encoder ENCODER = Base64.getMimeEncoder(76, new byte[] { '\n' });
    public static final Decoder DECODER = Base64.getMimeDecoder();

    private static final KeyPairGenerator GENERATOR;
    private static final KeyFactory KEY_FACTORY;

    private static Logger log = LoggerFactory.getLogger(RSAUtil.class);

    static {
        KeyPairGenerator gen;
        KeyFactory kf;
        try {
            gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(4096, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            gen = null; // This should not happen!
            log.info("This should not happen");
            e.printStackTrace();
        }
        try {
            kf = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            kf = null; // This should not happen!
            log.info("This should not happen");
            e.printStackTrace();
        }
        GENERATOR = gen;
        KEY_FACTORY = kf;
    }

    private RSAUtil() {
    }

    public static KeyPair generateKey() {
        if (GENERATOR == null) {
            log.info("This should not happen");
            return null;
        }
        return GENERATOR.genKeyPair();
    }

    public static String toPemKey(Key key) {
        byte[] encoded = key.getEncoded();
        return ENCODER.encodeToString(encoded);
    }

    public static PublicKey readForPublicKey(String pem) throws InvalidKeySpecException {
        byte[] keybyte = DECODER.decode(pem);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keybyte);
        return KEY_FACTORY.generatePublic(keySpec);
    }

    public static PrivateKey readForPrivateKey(String pem) throws InvalidKeySpecException {
        byte[] keybyte = DECODER.decode(pem);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keybyte);
        return KEY_FACTORY.generatePrivate(keySpec);
    }
}
