package hoopoe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("hoopoe.")
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 30)//session过期时间(秒)
public class AllApplication {

    public static void main(String[] args){
        SpringApplication.run(AllApplication.class, args);
    }

}
