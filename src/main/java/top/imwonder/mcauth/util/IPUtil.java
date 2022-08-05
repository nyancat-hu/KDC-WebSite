package top.imwonder.mcauth.util;

import javax.servlet.http.HttpServletRequest;

import top.imwonder.util.StringUtil;

public class IPUtil {
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtil.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index).trim();
            } else {
                return ip.trim();
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!StringUtil.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip.trim();
        }
        return request.getRemoteAddr();
    }
}
