/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:59:51 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-02 17:59:51 
 */
package top.imwonder.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class IdUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String roleId(String roleName) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + roleName).getBytes(StandardCharsets.UTF_8)).toString()
                .replaceAll("-", "");
    }
}
