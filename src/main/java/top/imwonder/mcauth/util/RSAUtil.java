package top.imwonder.mcauth.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * SHA1WithRSA-数字签名
 * Created by yanshao on 2018/12/12.
 */
// TODO: 2018/12/12 数字签名就发现这个能用 
// TODO: 2018/12/12 原文地址：https://blog.csdn.net/qq_23974323/article/details/77678491
public class RSAUtil {
    //加密算法
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    //私钥
    private static final String privateKey = "MIIJQQIBADANBgkqhkiG9w0BAQEFAASCCSswggknAgEAAoICAQCFNLhgDqjUu2sYtyv9FS2M2zh6\n" +
            "/5JSHU5JM35IKy+hWrgvanNGrVIcPCiAlMYDR8Ox/nVRkLF3JA6f6dTX5CwzQcB6Z+6SVpuaJ6c4\n" +
            "+83M55Raixb0zBKTQ9RH6i8vS56XLa1HxCz4oY30eFjuFzUn4AaoAFRDffrjwoRUt2G3ghzQvU+/\n" +
            "Bqv33q9xfG0I1ztyMg63hSiW/td/3ZxNu0L43/IFqNLxAY7X4Ey9lpMca2LHXh6BlcKo8ybsUilN\n" +
            "dx92++3KiZ0i3Lk5f1KdxcCKncsRhDzq3e10uarGRfRwDSg+D3D62PKz0CW+QNIR6Jl13/MMJUpI\n" +
            "awEtpI9OLJyGjSwNolljzemxn9Sli7guL7UE2vWFd07hYNlAVvht3u9tSej1Xd8WqLMQju1Kyh3f\n" +
            "9t6MinN+oZEJPDjyPcveyj5jv9wAA+1XkJmPZWABFS5NFGK7d2kNTAkkeSpFnonhkifuQN1TgWqr\n" +
            "mSiCkymf/0BSEcinRbM8bXZifxRer/uO+LcjXfBSiUkhJwhhnGgxrrn9gtVN0k8z3M2iZCEsxsx1\n" +
            "g7L4qWDttKh9vIsh8u6IH+O/JSO1cbw63YnTKfl35femy94tq1a81DJaXHg57DIxBuBM0EWwv36n\n" +
            "L9mVBM3rOfMAUcMfzV3Px9kNZhuduGO9hJIJ7/8F2NUawNeBpwIDAQABAoICADbdNQeTYiRkoULH\n" +
            "xx1xSJaYgFXgx/J8UY9C30N4TiDmTbBx5tL4djZc7M2w+THGsCqyxL14nC+XMVLaK6lUOIRQE6gX\n" +
            "AKNTerlulaiZxfUcePUmDql7GDeBm4CXSyHuwn4/+PvzKBar690CaJaABovK8NLnd81oqRqANqLx\n" +
            "+RRtSdML9jmW1OO924MhnYMjqz9osLkT5ljy0+29aDB37ai2DGgaVe+gFaaCovj/eM4eyMuWz3fO\n" +
            "KCQQKjrxyEM4ykPmldVzAr6mLMAq2etT1O8C+O3ZCoIuMCyAgH8ztu8j7CxLjdwz2RsIo1MOPbfU\n" +
            "lsh/GgspnMPhFtYmzkODimAkxmNFQqOBZDN6dQYyuJuKSbanycCnv7MCbe+oFhK6nlo6QaMrZLUS\n" +
            "P5kYyswSCqe9hMeHtxpRA1oeUJ6sjdTnXSTMGX5mCkaq8dT5zSHrYIZ09Zho9Zc+kSMm+lJZnfPs\n" +
            "TGCNOL5yilOgLg8ET7bWxXwIQjeiNjjoH9+6ibPq2WX2VTLe1JJ3DIBP3RznfnA5cKZb2ZCCTQAn\n" +
            "UoxK6u9oOQtoqmm3s6EFvZ6lOnr07S0Tv/EMMfGJptyyjA+Hzz/l/3089EO6SgHrdWldLxyAhj7T\n" +
            "95NPeCjLV9pWbXgqQOsmajz09q+YoycYg6HaFjAm2pATGcr939YX8NWYVaDxAoIBAQC8DM3vJCHj\n" +
            "XyOEjb8dNTMOWtE3DUliC/ojbBH5S5rJYT1YiEtRKeFk30I8m7ZAqU2ecyoIi9we//jzb70r89DR\n" +
            "4HKfoCg/dAvT1zAD7aXsXSykSa3Qs30wzzN6La7QzhgE9BGMpYho3jgXdnTEweCkofhcav/t3A8f\n" +
            "K/uS+M7Q7x4PCGxTdbySchdvm9ko/011aV6fvbZS0I6n48+4mBCnY3SuvdV6E6RxagYbyAvjsabq\n" +
            "EW+Os2QegYQ053ixjFm3Ge1zFkvEFivsn5cHJMzS6uKxfzkeUFGpm2g0BIblDtS84RB51xfZIVra\n" +
            "3/hz/lmKNS+KO02zPwqIOriCxsPvAoIBAQC1Vqwr013RURVqy4bdS57o0PDxxqNsv1De8EIBxz0z\n" +
            "8192DLozU963Bb6+xyxDNd2st6AvsSij6lNA6rH4t1l/mRd6BmL6J++W21Drzo/FgZ/ZmzA+TO8k\n" +
            "t1TLYUvB/8DrNstJKRFjXp0eCpDQXIunBKL3AHcrjUlWSaUMNOrwuUJb1GSModsiWkX26jdlMxUR\n" +
            "PKsY/+XCwmw5X6expB32CXS/USDGVwAXL1193pQPLf1qiuYT2E/GCcN6JW4hwS1FCS/Jm0EID0ao\n" +
            "7cDtn7ynSx/qxqDai5gz12AKxXLO4rSolpNkX4CRVwryIBX2lLRuki8faayBsLjrftZaTAXJAoIB\n" +
            "AHOufNDsLVyuHi9g83yISYw/Ggz/w3DpZUt9I+F0PRg9FXvs/EhFtz5SiXC3aXqFrBYZSnZPc24u\n" +
            "kXtP9dOf/YnexSSlZZ0DSnNbIVKJU1UATIIUQTTt9cmc7VHv4GbbIbOKYXFgM3YvndnxKQwRKJye\n" +
            "dyaDPEWa5kKK0AwtuyyUrK0axWjiOMruHLSv7vtlcsUTuP4TuqFfYyKs5g/sm/IdIJ6OcVDT3IlY\n" +
            "a8GTYp0DoGbFqxbiDGkny6iv7HNsn5QgAw6VXK6X5RV/Z20hmk7159bBKOYZCuOHkqbNUh5+7Sf0\n" +
            "XCpXw3fzKO0UcscIZRS8ey5SJQ5rbZM4bGEm3UkCggEAPhL3SOFHwjlBXZdiCSqzsPqlH/5hPv6W\n" +
            "jlosHjO3rarnyJ8+tgdFu90JmIDRlAhCY7dP7EN2p/W0ngo49ey9hEJBmRHKm9tD1p4oqy/AY1mm\n" +
            "AHVxJEJtsmHXcu9RYY1M2redCuQ2AZAHtJrlS44CeB/MMUVFhfENY64+A1mx+slP/+NKsCFPxAim\n" +
            "3oj6gXflBEuCPkKKwgqPxWwLGXIWl3xRLJR25a4uZ8Fc/ZNNl4ykrO3zKGPpmt8IvS1G7+MSvgkR\n" +
            "BLBSqJGiHH2Wh0Eg8Rt0R4fK1EToywY06DsCu/M66GLy/W77scTcCFoXapso0JjV7Kjd1l9KTcLT\n" +
            "WYGgkQKCAQBUWwaskHqQ2An0Btgo6Ym+r1B0czGCA2yuG2nmJ3bhvA6WrukKx6ybaOnedY3KwThK\n" +
            "MT2g+VKt8Jc0vRFvvrvgzyHx9LqbVKca3LmvkRLuePBIRO/PHuoubigdDdB4ja3VI4BYcqOg3yzP\n" +
            "8iPAYyWYUpVKSHXXgK14FWccMemYoSh3zV8WqdyiMeuFRSTgQvMTH1iPxYPM4nsHcXdRvYzjD8im\n" +
            "UNSl/GAZPUdeZUPDNjsgiFhYUjp4YPf9rKCnVNZxBmbLET+rlnpGPOJU/xm9SUj1T/VDLZygj0J/\n" +
            "ZQQyT2ETk/Dv3zpj3Y6//vWiGaTIkqHCc7gaORKT73XAV4pN";
    //公钥
    private static final String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAhTS4YA6o1LtrGLcr/RUtjNs4ev+SUh1O\n" +
            "STN+SCsvoVq4L2pzRq1SHDwogJTGA0fDsf51UZCxdyQOn+nU1+QsM0HAemfuklabmienOPvNzOeU\n" +
            "WosW9MwSk0PUR+ovL0uely2tR8Qs+KGN9HhY7hc1J+AGqABUQ33648KEVLdht4Ic0L1Pvwar996v\n" +
            "cXxtCNc7cjIOt4Uolv7Xf92cTbtC+N/yBajS8QGO1+BMvZaTHGtix14egZXCqPMm7FIpTXcfdvvt\n" +
            "yomdIty5OX9SncXAip3LEYQ86t3tdLmqxkX0cA0oPg9w+tjys9AlvkDSEeiZdd/zDCVKSGsBLaSP\n" +
            "Tiycho0sDaJZY83psZ/UpYu4Li+1BNr1hXdO4WDZQFb4bd7vbUno9V3fFqizEI7tSsod3/bejIpz\n" +
            "fqGRCTw48j3L3so+Y7/cAAPtV5CZj2VgARUuTRRiu3dpDUwJJHkqRZ6J4ZIn7kDdU4Fqq5kogpMp\n" +
            "n/9AUhHIp0WzPG12Yn8UXq/7jvi3I13wUolJIScIYZxoMa65/YLVTdJPM9zNomQhLMbMdYOy+Klg\n" +
            "7bSofbyLIfLuiB/jvyUjtXG8Ot2J0yn5d+X3psveLatWvNQyWlx4OewyMQbgTNBFsL9+py/ZlQTN\n" +
            "6znzAFHDH81dz8fZDWYbnbhjvYSSCe//BdjVGsDXgacCAwEAAQ==";


    public static String sign( String param) {
        Signature signature = null;
        try {
            byte[] privateKeyByte = Base64.decode(privateKey);
            KeyFactory keyfactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec encoderule = new PKCS8EncodedKeySpec(privateKeyByte);
            PrivateKey key = keyfactory.generatePrivate(encoderule);
            signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(key, new SecureRandom());
            signature.update(param.getBytes(StandardCharsets.UTF_8));
            return Base64.encode(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 用公钥验证签名
     *
     * @param param     入参
     * @param signature 使用私钥签名的入参字符串
     * @return 返回验证结果
     */
    public static boolean verifyRes(String param, String signature) {
        try {
//获取公钥
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] publicKeyByte = Base64.decode(publicKey);
            X509EncodedKeySpec encodeRule = new X509EncodedKeySpec(publicKeyByte);
            PublicKey key = keyFactory.generatePublic(encodeRule);
//用获取到的公钥对   入参中未加签参数param 与  入参中的加签之后的参数signature 进行验签
            Signature sign = Signature.getInstance(SIGN_ALGORITHMS);
            sign.initVerify(key);
            sign.update(param.getBytes(StandardCharsets.UTF_8));

//验证签名
            return sign.verify(Base64.decode(signature));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * byte数组转换成十六进制字符串
     *
     * @param bytes byte数组
     * @return 返回十六进制字符串
     */
    private static String bytesToHexStr(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer("");
        for (int i = 0; i < bytes.length; ++i) {
            stringBuffer.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF)).substring(1).toUpperCase());
        }
        return stringBuffer.toString();
    }

    /**
     * 十六进制字符串转成byte数组
     *
     * @param hexStr 十六进制字符串
     * @return 返回byte数组
     */
    private static byte[] hexStrToBytes(String hexStr) {
        byte[] bytes = new byte[hexStr.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hexStr.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

//    public static void main(String[] args) {
//        String texHash = "ew0KICAicHJvZmlsZUlkIiA6ICJmMTM5YTM1NDE4NzUzYzQyOTU2M2YzZmRkODljMTY2YSIsDQogICJwcm9maWxlTmFtZSIgOiAiaWNlX2xpZ2h0IiwNCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IGZhbHNlLA0KICAidGV4dHVyZXMiIDogew0KICAgICJDQVBFIiA6IHsNCiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjM0MGMwZTAzZGQyNGExMWIxNWE4YjMzYzJhN2U5ZTMyYWJiMjA1MWIyNDgxZDBiYTdkZWZkNjM1Y2E3YTkzMyINCiAgICB9LA0KICAgICJTS0lOIiA6IHsNCiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWM4NjA1NmVmNmZjZmQ3MzhjYzFjOWVmMzViN2NjYTk1ZTMxODc2Y2IzMGI2YTE0YWYzMTBhNjVmNGU4MDUzOCINCiAgICB9DQogIH0sDQogICJ0aW1lc3RhbXAiIDogIjE2NjA0ODc4NDk3MDQiDQp9";
//        String signBase64 = "QsuNcpdU37qsbF691xigJ+7SlC8R4G/jlNlYdnd9DT95eBXVHPAGYNbKWDtBVyl24JMWA4ADkdHVclmsoyvT8oGjUECCfrUhioRhkxB9i7GepWs1KQhTZUM+ko/EnnXBHFTmuQFg7zyKecQfJYxUKSzblUbUCKRrpWqpct38WYQZ5lN6/8Ig94V2U/oJkePVeg11Oc2mz2Hq2mcy0qy8IGHLX3KlP/z/7sBR10j5/M269WGVatqSKBCAquwCNKTUXoOYuMlOco70gkGwI3ppmldRZ/5ln7gUbbCgFKM7xE/YcJr3DXPQSv1NSqLLPLcqtbC+DrkxAajw7XLpRB9zdT6ZTqjYmr5/JpaM7W1yXVFgfoatBE/EZlXNCa42gDc6F+HBn6dfKUhbZn/keh+bLoSUyZ/nJ/Dl4EnbkSUVviVWXpJNybUwsTfShu8y3DsMO30PzYQzbk4QJiTMFT+mS2lS1K9eNvqCXcPRpfXE+ItV9gtBfa3EN98lpunPf76qEgiGY+c8j0DealsXBcxlTtnI3lR3ULj29XCXnT1a5VqxeaOqlqBWwdwHSbNxaPGeM+zKGlWSOI37SR/14COptJguGTjhWZ0UIzAH+4RFu+kIlSj9KHOTtKiYLED3QIGHBXPl4ybdHEuhLDBOTxVLZ8CLJ+fkvsOnqFL/mrfUI9A=";
//        String sign = sign(texHash);
//        System.out.println("签名后的参数>>>" + sign);
//        System.out.println("验证结果>>>" + verifyRes(texHash, sign));
//    }
}