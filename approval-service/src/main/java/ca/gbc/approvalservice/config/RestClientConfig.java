package ca.gbc.approvalservice.config;

import ca.gbc.approvalservice.client.EventClient;
import ca.gbc.approvalservice.client.UserClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {
    @Value("${user.service.url}")
    private String userServiceUrl;

    @Value("${event.service.url}")
    private String eventServiceUrl;

    @Bean
    public UserClient userClient(){
        RestClient restClient = RestClient.builder()
                .baseUrl(userServiceUrl)
                .build();

        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();


        return httpServiceProxyFactory.createClient(UserClient.class);

    }

    @Bean
    public EventClient eventClient(){
        RestClient restClient = RestClient.builder()
                .baseUrl(eventServiceUrl)
                .build();

        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();


        return httpServiceProxyFactory.createClient(EventClient.class);

    }



}
