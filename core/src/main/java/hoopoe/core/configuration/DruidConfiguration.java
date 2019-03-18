package hoopoe.core.configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DruidConfiguration implements WebMvcConfigurer {

    @Bean
    @Order
    public ServletRegistrationBean statViewServlet() {
        StatViewServlet servlet = new StatViewServlet();
        ServletRegistrationBean bean = new ServletRegistrationBean(servlet, "/druid/*");
        return bean;
    }

    @Bean
    public FilterRegistrationBean webStatFilter(){
        WebStatFilter filter = new WebStatFilter();
        FilterRegistrationBean bean = new FilterRegistrationBean(filter);
        bean.addUrlPatterns("/*");
        bean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,*.font,/druid/*");
        return bean;
    }

}
