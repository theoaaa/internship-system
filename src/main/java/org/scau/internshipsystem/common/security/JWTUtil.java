package org.scau.internshipsystem.common.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JWTUtil {
    private static final long EXPIRE_TIME = 60*60*1000;
    private static final String TOKEN_SECRET = "156f1ewr5e6b1r6b1er651we56f1we56f1we651myj5u61yu6juy6dew";
    /**
     * 验证token是否正确
     * */
    public static boolean verify(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
        try {
            verifier.verify(token);
        }catch (Exception e){
            log.error("token验证失败..." + token);
            return false;
        }
        return true;
    }


    /**
     * 签发token
     * */
    public static String signToken(String username){
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        String token = JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .withClaim("username",username)
                .sign(algorithm);
        return token;
    }

    /**
     * 解析token
     * **/
    public static JWTToken parseToken(String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        JWTToken jwtToken = JWTToken.builder()
                .expireTime(decodedJWT.getExpiresAt())
                .username(decodedJWT.getClaim("username").asString())
                .build();
        return jwtToken;
    }
}
