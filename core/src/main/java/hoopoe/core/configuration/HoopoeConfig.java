package hoopoe.core.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Data
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ConfigurationProperties(prefix = "hoopoe")
public class HoopoeConfig {

    /**
     * 不校验权限的地址
     */
    private List<String> anonUrls;
    /**
     * token默认有效时间 1天
     */
    private Long jwtTimeOut = 86400L;

}
