package hoopoe.monitor.logfilter;

import hoopoe.core.HoopoeConsts;
import hoopoe.jwt.JWTToken;
import hoopoe.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@ServerEndpoint("/api/v1/logs/sys/{username}")
@Component
public class LogWebSocketServer{

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static AtomicLong onlineCount = new AtomicLong(0);
    /**concurrent包的线程安全Set，用来存放每个客户端对应的CumWebSocket对象。*/
    private static CopyOnWriteArraySet<Session> webSocketSet = new CopyOnWriteArraySet<Session>();
    /**
     * 连接建立成功调用的方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws Exception{
        log.debug("用户[" + username +"]连接websocket");
//        if(StringUtils.isBlank(token))throw new Exception("参数不正确");
//        JWTToken jwtToken = JWTUtil.getFromToken(token);
//        String redisToken = redisTemplate.opsForValue().get(jwtToken.toRedisKey(token));
//        if(StringUtils.isBlank(redisToken) || !redisToken.equals(token))throw new Exception("参数不正确");
        //加入set中
        webSocketSet.add(session);
        //添加在线人数
        addOnlineCount();
        try {
            sendMessage(session, "连接成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        log.info("有连接关闭。当前在线人数为："+getOnlineCount());
    }

    /**
     * 收到客户端消息后调用
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("客户端发送的消息："+message);
        sendAll(message);
    }


    private void sendAll(String message) {
        Arrays.asList(webSocketSet.toArray()).forEach(item -> {
            Session session = (Session) item;
            //群发
            try {
                sendMessage(session, message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("有异常啦");
        error.printStackTrace();
    }

    /**
     * 减少在线人数
     */
    private void subOnlineCount() {
        LogWebSocketServer.onlineCount.decrementAndGet();
    }

    /**
     * 添加在线人数
     */
    private void addOnlineCount() {
        LogWebSocketServer.onlineCount.incrementAndGet();
    }

    /**
     * 当前在线人数
     * @return
     */
    public static synchronized long getOnlineCount() {
        return LogWebSocketServer.onlineCount.get();
    }

    /**
     * 发送信息
     * @param message
     * @throws IOException
     */
    public void sendMessage(Session session,String message) throws IOException {
        //获取session远程基本连接发送文本消息
        session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static void sendToAll(String message)throws IOException {
        Arrays.asList(webSocketSet.toArray()).forEach(item -> {
            Session session = (Session) item;
            //群发
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
