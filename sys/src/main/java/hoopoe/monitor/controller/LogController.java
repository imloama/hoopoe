package hoopoe.monitor.controller;

import hoopoe.monitor.logfilter.LoggerMessage;
import hoopoe.monitor.logfilter.LoggerQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/api/v1/logs")
    public void log(String msg){
        messagingTemplate.convertAndSend("got message:" + msg);
    }

    @Scheduled(fixedDelay = 100)
    public void showLogs() {
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
}
