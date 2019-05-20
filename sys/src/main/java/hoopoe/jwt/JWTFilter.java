package hoopoe.jwt;

import com.alibaba.fastjson.JSON;
import hoopoe.core.HoopoeConsts;
import hoopoe.core.configuration.HoopoeConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JWTFilter extends GenericFilterBean {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private HoopoeConfig config;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String path = ((HttpServletRequest)request).getRequestURI();
//        if(!path.startsWith("/api/")||config.getAnonUrls().contains(path)){
//            chain.doFilter(request, response);
//            return;
//        }
        try{
            Object headers = ((HttpServletRequest) request).getHeaderNames();
            String path = ((HttpServletRequest) request).getRequestURI();
            String token = JWTUtil.getToken((HttpServletRequest) request);
            if (StringUtils.isNotBlank(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                if (auth != null) {
                    UserDetails userDetails = (UserDetails) auth.getPrincipal();
                    boolean valid = JWTUtil.validateToken(token, userDetails);
                    if(valid){
                        String key = JWTUtil.getFromToken(token).toRedisKey(token);
                        String tokenCopy = this.redisTemplate.opsForValue().get(key);
                        if(StringUtils.isBlank(tokenCopy) || !token.equals(tokenCopy)){
                            throw new RuntimeException("未授权或授权已过期");
                        }
                    }
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }catch (Exception e){
//            throw new IOException(e.getMessage(),e);
            log.error(e.getMessage(),e);
        }

        chain.doFilter(request, response);
    }
}
