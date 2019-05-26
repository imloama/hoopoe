package hoopoe.jwt;


import hoopoe.core.HoopoeConsts;
import hoopoe.core.configuration.HoopoeConfig;
import hoopoe.sys.model.User;
import hoopoe.utils.SpringContextUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTUtil {
    private static final long EXPIRE_TIME = SpringContextUtil.getBean(HoopoeConfig.class).getJwtTimeOut() * 1000;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    public static String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public static JWTToken getFromToken(String token){
        return getJWTTokenFromToken(token);
    }



    public static Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey( HoopoeConsts.SECRET )
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private static JWTToken getJWTTokenFromToken(String token){
        Claims claims = getClaimsFromToken(token);
        if(claims == null)return null;
        return JWTToken.fromClaim(claims);
    }

    public  static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRE_TIME * 1000);
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private static Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public static String generateToken(User user) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
//        claims.put(CLAIM_KEY_CREATED, new Date());
        JWTToken token = JWTToken.fromUser(user);
        return generateToken(token);
    }

    static String generateToken(JWTToken token) {
        return Jwts.builder()
                .setClaims(token.toMap())
                .setSubject(token.getUsername())
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, HoopoeConsts.SECRET )
                .compact();
    }

    public static Boolean canTokenBeRefreshed(String token) {
        return !isTokenExpired(token);
    }

    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
//            claims.put(CLAIM_KEY_CREATED, new Date());
            final JWTToken t = JWTToken.fromClaim(claims);
            t.setCreated(new Date().getTime());
            refreshedToken = generateToken(t);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public static Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String username = getUsernameFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
        );
    }

    public static String getToken(HttpServletRequest request){
        String token =  request.getHeader(HoopoeConsts.TOKEN_HEADER_KEY);
        if(StringUtils.isBlank(token)){
            return request.getParameter(HoopoeConsts.TOKEN_HEADER_KEY);
        }
        return token;
    }

//
//    /**
//     * 校验 token是否正确
//     *
//     * @param token  密钥
//     * @param secret 用户的密码
//     * @return 是否正确
//     */
//    public static boolean verify(String token, String userId, String secret) {
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(secret);
//            JWTVerifier verifier = JWT.require(algorithm)
//                    .withClaim("userId", userId)
//                    .build();
//            verifier.verify(token);
//            log.info("token is valid");
//            return true;
//        } catch (Exception e) {
//            log.info("token is invalid{}", e.getMessage());
//            return false;
//        }
//    }
//
//
//    public static String getUserId(String token) {
//        try {
//            DecodedJWT jwt = JWT.decode(token);
//            return jwt.getClaim("userId").asString();
//        } catch (JWTDecodeException e) {
//            log.error("error：{}", e.getMessage());
//            return null;
//        }
//    }
//
//
//    /**
//     * 生成 token
//     *
//     * @param userId 用户ID
//     * @param secret   用户的密码
//     * @return token
//     */
//    public static String sign(String userId, String secret) {
//        try {
//            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
//            Algorithm algorithm = Algorithm.HMAC256(secret);
//            return JWT.create()
//                    .withClaim("userId", userId)
//                    .withExpiresAt(date)
//                    .sign(algorithm);
//        } catch (Exception e) {
//            log.error("error：{}", e);
//            return null;
//        }
//    }
}
