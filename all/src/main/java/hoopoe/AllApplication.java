package hoopoe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("hoopoe.")
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class AllApplication {

    public static void main(String[] args){
        SpringApplication.run(AllApplication.class, args);
    }

}
