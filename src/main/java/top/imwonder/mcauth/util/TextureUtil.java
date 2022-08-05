package top.imwonder.mcauth.util;

import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.security.MessageDigest;

public class TextureUtil {
    public static String textureHash(BufferedImage img) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        int width = img.getWidth();
        int height = img.getHeight();
        byte[] buf = new byte[4096];

        putInt(buf, 0, width); // 0~3: width(big-endian)
        putInt(buf, 4, height); // 4~7: height(big-endian)
        int pos = 8;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // pos+0: alpha
                // pos+1: red
                // pos+2: green
                // pos+3: blue
                putInt(buf, pos, img.getRGB(x, y));
                if (buf[pos + 0] == 0) {
                    // the pixel is transparent
                    buf[pos + 1] = buf[pos + 2] = buf[pos + 3] = 0;
                }
                pos += 4;
                if (pos == buf.length) {
                    // buffer is full
                    pos = 0;
                    digest.update(buf, 0, buf.length);
                }
            }
        }
        if (pos > 0) {
            // flush
            digest.update(buf, 0, pos);
        }

        byte[] sha256 = digest.digest();
        return String.format("%0" + (sha256.length << 1) + "x", new BigInteger(1, sha256)); // to hex
    }

    // put an int into the array in big-endian
    private static void putInt(byte[] array, int offset, int x) {
        array[offset + 0] = (byte) (x >> 24 & 0xff);
        array[offset + 1] = (byte) (x >> 16 & 0xff);
        array[offset + 2] = (byte) (x >> 8 & 0xff);
        array[offset + 3] = (byte) (x >> 0 & 0xff);
    }
}
