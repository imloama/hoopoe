package hoopoe.jwt;


import hoopoe.core.HoopoeConsts;
import hoopoe.core.configuration.HoopoeConfig;
import hoopoe.sys.model.User;
import hoopoe.utils.SpringContextUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
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

    public static Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
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

    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRE_TIME * 1000);
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private static Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
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
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
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
        return request.getHeader(HoopoeConsts.TOKEN_HEADER_KEY);
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
