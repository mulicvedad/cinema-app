package ba.unsa.etf.nwt.gateway;

import ba.unsa.etf.nwt.gateway.filters.CustomCorsFilter;
import ba.unsa.etf.nwt.gateway.filters.SimpleFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    @Value("${jwt.secret-key}")
    private String jwtKey;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public SimpleFilter simpleFilter()
    {
        return new SimpleFilter();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public FilterRegistrationBean corsFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CustomCorsFilter corsFilter = new CustomCorsFilter();
        registrationBean.setFilter(corsFilter);
        registrationBean.addUrlPatterns("*");
        registrationBean.setOrder(Integer.MAX_VALUE - 1);
        return registrationBean;
    }
}

