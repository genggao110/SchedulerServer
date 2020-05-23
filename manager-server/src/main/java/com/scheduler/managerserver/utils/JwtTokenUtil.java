package com.scheduler.managerserver.utils;

import com.scheduler.webCommons.enums.ResultEnum;
import com.scheduler.webCommons.exception.MyException;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

/**
 * 用于生成jwTokens类
 * @Author: wangming
 * @Date: 2019-12-19 10:46
 */
public class JwtTokenUtil {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private static final String SECRET = "jwtsecretmanager";
    private static final String ISS = "OpenGMS";
    private static final Key KEY = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    //过期时间是3600秒，即为1个小时
    private static final long EXPIRATION = 3600L;

    //选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER  = 604800L;

    public static String createToken(String id, String name,String password, boolean isRememberMe){
        long expiration = isRememberMe ? EXPIRATION_REMEMBER  : EXPIRATION;
        HashMap<String, Object> map = new HashMap<>(30);
        map.put("id", id);
        map.put("name", name);
        map.put("password", password);
        map.put("issuer",ISS);
        return Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS256,KEY)
                .compact();
    }

    /**
     * request的token信息可以放在Header的Authorization字段 或者 Parameter的token字段 Cookie的token字段
     * @param request
     * @return java.lang.String
     * @author wangming
     * @date 2019/12/19 22:16
     */
    public static String getTokenFromRequest(HttpServletRequest request){
        String token;
        if((token = request.getHeader(TOKEN_HEADER)) != null){
            return token;
        }else if((token = request.getParameter("token")) != null){
            return token;
        }else if((token = CookieUtils.getValue(request, "token")) != null){
            return token;
        }else{
            return null;
        }
    }

    public static Object getSpecificClaimByKey(String token, String key){
        return getTokenBody(token).get(key);
    }

    public static Claims getTokenBody(String token){
        try{
            Claims claims =  Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            return claims;
        }catch (ExpiredJwtException e){
            //过期
            throw new MyException(ResultEnum.TOKEN_EXPIRED);
        }catch (SignatureException e){
            //签名无效
            throw new MyException(ResultEnum.TOKEN_WRONG);
        }catch (MalformedJwtException e){
            throw new MyException(ResultEnum.TOKEN_WRONG);
        }

    }

}
