package hoopoe.jwt;

import hoopoe.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
@Slf4j
public class JWTTokenProvider {

    @Autowired
    private UserService userDetailsService;



    public Authentication getAuthentication(String token) throws Exception {
        return getAuthenticationByUsername(getUsername(token));
    }

    public Authentication getAuthenticationByUsername(String username) throws Exception {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    public String getUsername(String token)throws Exception{
        return JWTUtil.getUsernameFromToken(token);
    }




}