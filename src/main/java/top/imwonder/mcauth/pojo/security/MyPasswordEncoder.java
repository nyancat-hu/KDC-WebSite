package top.imwonder.mcauth.pojo.security;

import fr.xephi.authme.data.auth.PlayerAuth;
import fr.xephi.authme.libs.google.common.base.MoreObjects;
import fr.xephi.authme.security.HashUtils;
import fr.xephi.authme.security.PasswordSecurity;
import fr.xephi.authme.security.crypts.HashedPassword;
import fr.xephi.authme.util.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder implements PasswordEncoder {

    public MyPasswordEncoder() {
    }

    /**
     　　* 重写加密方法，采用MD5算法加密(可以自定义任意算法)
     　　*/
    @Override
    public String encode(CharSequence charSequence) {
        return computeHash(charSequence.toString(),generateSalt());
    }

    /*
     * charSequence:前端传过来的明文密码
     * s:数据库中存储的密文密码
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String password = charSequence.toString();
        String[] line = s.split("\\$");
        // line[2]是数据库密码中的盐
        // 比较数据库密码和明文加密后的密码是否一致
        return line.length == 4 && s.equals(this.computeHash(password, line[2]));
    }


    private String computeHash(String password, String salt) {
        return "$SHA$" + salt + "$" + HashUtils.sha256(HashUtils.sha256(password) + salt);
    }

    private String generateSalt() {
        return RandomStringUtils.generateHex(16);
    }
}
