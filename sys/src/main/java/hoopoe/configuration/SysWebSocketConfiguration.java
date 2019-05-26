package hoopoe.configuration;

import hoopoe.monitor.logfilter.LoggerMessage;
import hoopoe.monitor.logfilter.LoggerQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class SysWebSocketConfiguration implements /*WebSocketConfigurer,*/ WebSocketMessageBrokerConfigurer {

    //使用boot内置tomcat时需要注入此bean
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }


//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(marcoHandler(), "/marco");
//    }
//
//    @Bean
//    public MarcoHandler marcoHandler() {
//
//        return new MarcoHandler();
//
//    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/v1/logs")
//                .setAllowedOrigins("http://localhost:8976")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostConstruct
    public void pushLogger(){
        ExecutorService executorService= Executors.newFixedThreadPool(2);
        Runnable runnable= () -> {
            while (true) {
                try {
                    LoggerMessage log = LoggerQueue.getInstance().poll();
                    if(log!=null){
                        if(messagingTemplate!=null)
                            messagingTemplate.convertAndSend("/sys",log);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        executorService.submit(runnable);
    }
}
