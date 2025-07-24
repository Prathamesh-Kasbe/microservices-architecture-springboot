package dev.pk.ms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import dev.pk.ms.client.AccountClient;

@Configuration
public class WebClientConfig {
	
	private LoadBalancedExchangeFilterFunction loadBalancedExchangeFilterFunction;
	
	@Autowired
	public void setLoadBalancedExchangeFilterFunction(
			LoadBalancedExchangeFilterFunction loadBalancedExchangeFilterFunction) {
		this.loadBalancedExchangeFilterFunction = loadBalancedExchangeFilterFunction;
	}


	@Bean
	public WebClient getWebClient() {
		return WebClient.builder().
				baseUrl("http://account-service")
				.filter(loadBalancedExchangeFilterFunction)
				.build();
	}
	
	@Bean
	public AccountClient getAccountClient() {
		HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
				.builderFor(WebClientAdapter.create(getWebClient()))
				.build();
		return httpServiceProxyFactory.createClient(AccountClient.class);
	}
}
