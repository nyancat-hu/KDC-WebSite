package top.imwonder.mcauth.util;

import net.sf.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/***
方法名：skinAPI
 传参：①玩家名字 ②系统分配（假）UUID
 返回：皮肤材质JSON 或者 NULL
 */

public class SkinAPIUtil {

    public static JSONObject skinAPI(String username,String pirateUUid){
        JSONObject UUidJSON = getAuthorizedUUid(username);
        String val = UUidJSON != null ? UUidJSON.getString("id") : null;

        if (val != null){//名字在正版 查出正版uuid 获取正版材质信息
            return getAuthorizedSkin(val);
        }else {//名字不在正版 查出盗版uuid对应的材质信息
            return getPirateSkin(pirateUUid);
        }
    }

    //获取正版UUID
    public static JSONObject getAuthorizedUUid(String username){
        return APIGetJSON("https://api.mojang.com/users/profiles/minecraft/"+username);
    }

    //通过正版UUID获取正版皮肤
    public static JSONObject getAuthorizedSkin(String UUID){
        return APIGetJSON("https://sessionserver.mojang.com/session/minecraft/profile/"+UUID);
    }

    //通过盗版UUID循环访问网站获取皮肤
    public static JSONObject getPirateSkin(String UUID){
        String[] HttpList = {"https://sessionserver.mojang.com/session/minecraft/profile/"+ UUID,
                "https://littleskin.cn/api/yggdrasil/sessionserver/session/minecraft/profile/"+UUID,
                "https://littleskin.cn/api/yggdrasil/sessionserver/session/minecraft/profile/2"+UUID,
                "https://littleskin.cn/api/yggdrasil/sessionserver/session/minecraft/profile/3"+UUID,
                "https://littleskin.cn/api/yggdrasil/sessionserver/session/minecraft/profile/4"+UUID};
        JSONObject getSkin = null;
        int HttpNum = 0;
        while(getSkin == null && HttpNum < 5){
            getSkin = APIGetJSON(HttpList[HttpNum]);
            System.out.println(getSkin);
            HttpNum++;
        }
        return getSkin;
    }

    //访问API获取JSON的通用方法
    public static JSONObject APIGetJSON(String myUrl) {
        try {
            HttpURLConnection connection;
            InputStream is;
            BufferedReader br;

            URL url = new URL(myUrl);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            connection.connect();

            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                StringBuilder sbf = new StringBuilder();
                String temp;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                }

                JSONObject json = JSONObject.fromObject(sbf.toString());

                br.close();
                is.close();
                connection.disconnect();

                return json;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

