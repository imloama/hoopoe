package hoopoe.interceptor;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import hoopoe.jwt.JWTToken;
import hoopoe.jwt.JWTUtil;
import hoopoe.monitor.logfilter.LoggerMessage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
@Aspect
@Component
public class LogInterceptor {


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Pointcut("execution(* hoopoe.*.controller..*(..))")
    private void controllerAspect() {
    }

    @Before(value = "controllerAspect()")
    public void beforeService(JoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
//        log.info("---------进入请求处理[{}]---------", request.getRequestURI());
//        Map<String, String[]> args = request.getParameterMap();
//        log.info("请求信息：{}", JSONUtil.parseFromMap(args).toString());
        LoggerMessage msg= new LoggerMessage();
        msg.setBody("用户请求处理：" + request.getRequestURI());
        msg.setTimestamp(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        messagingTemplate.convertAndSend("/sys/logs", msg);
    }

    @AfterReturning(returning = "o", pointcut = "controllerAspect()")
    public void afterService(JoinPoint joinPoint, Object o) {
//        if (o != null && o instanceof ResponseBean)
//            log.info("返回信息：{}", JSONUtil.toJsonStr(o));
//        log.info("---------请求处理完成---------");
        log.info("请求处理返回");
        LoggerMessage msg= new LoggerMessage();
        msg.setBody("用户请求完成，返回结果：" + JSON.toJSONString(o));
        msg.setTimestamp(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        messagingTemplate.convertAndSend("/sys/logs", msg);
    }


}
