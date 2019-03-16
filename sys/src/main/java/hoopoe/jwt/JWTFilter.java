package hoopoe.jwt;

import hoopoe.core.HoopoeConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            String token = JWTUtil.getToken((HttpServletRequest) request);
            if (StringUtils.isNotBlank(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                if (auth != null) {
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

//
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import hoopoe.core.configuration.HoopoeConfig;
//import hoopoe.utils.EncryptUtil;
//import hoopoe.utils.SpringContextUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.authz.UnauthorizedException;
//import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
//import org.springframework.http.HttpStatus;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Slf4j
//public class JWTFilter extends BasicHttpAuthenticationFilter {
//
//    private static final String TOKEN = "Authentication";
//
//    private AntPathMatcher pathMatcher = new AntPathMatcher();
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HoopoeConfig config = SpringContextUtil.getBean(HoopoeConfig.class);
//        String[] anonUrl = StringUtils.splitByWholeSeparatorPreserveAllTokens(config.getAnonUrl(), StringPool.COMMA);
//
//        boolean match = false;
//        for (String u : anonUrl) {
//            if (pathMatcher.match(u, httpServletRequest.getRequestURI()))
//                match = true;
//        }
//        if (match) return true;
//        if (isLoginAttempt(request, response)) {
//            return executeLogin(request, response);
//        }
//        return false;
//    }
//
//    @Override
//    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
//        HttpServletRequest req = (HttpServletRequest) request;
//        String token = req.getHeader(TOKEN);
//        return token != null;
//    }
//
//    @Override
//    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        String token = httpServletRequest.getHeader(TOKEN);
//        JWTToken jwtToken = new JWTToken(EncryptUtil.doDecrypt(token));
//        try {
//            getSubject(request, response).login(jwtToken);
//            return true;
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return false;
//        }
//    }
//
//    /**
//     * 对跨域提供支持
//     */
//    @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
//        // 跨域时会首先发送一个 option请求，这里我们给 option请求直接返回正常状态
//        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            httpServletResponse.setStatus(HttpStatus.OK.value());
//            return false;
//        }
//        return super.preHandle(request, response);
//    }
//}