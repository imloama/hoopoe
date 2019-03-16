package hoopoe.jwt;


import com.google.common.collect.Maps;
import hoopoe.sys.model.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class JWTToken implements Serializable {

    private Long id;
    private String username;
//    private Long expired;
    private Long created;



    public Map<String,Object> toMap(){
        Map<String,Object> map = Maps.newHashMap();
        map.put("id", id);
        map.put("username", username);
//        map.put("expired", expired);
        map.put("created", created);
        return map;

    }


    public static JWTToken fromClaim(Map<String,Object> claims){
        JWTToken token = new JWTToken();
        token.setId((Long) claims.get("id"));
        token.setUsername((String) claims.get("username"));
//        token.setExpired((Long) claims.get("expired"));
        token.setCreated((Long) claims.get("created"));
        return token;
    }

    public static JWTToken fromUser(User user) {
        JWTToken token = new JWTToken();
        token.setId(user.getId());
        token.setUsername(user.getUsername());
        token.setCreated(new Date().getTime());
        return token;
    }


}

//import org.apache.shiro.authc.AuthenticationToken;
//
//@Data
//public class JWTToken implements AuthenticationToken {
//
//    private static final long serialVersionUID = 1282057025599826155L;
//
//    private String token;
//
//    private String exipreAt;
//
//    public JWTToken(String token) {
//        this.token = token;
//    }
//
//    public JWTToken(String token, String exipreAt) {
//        this.token = token;
//        this.exipreAt = exipreAt;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return token;
//    }
//
//    @Override
//    public Object getCredentials() {
//        return token;
//    }
//
//}