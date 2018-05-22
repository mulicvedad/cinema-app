package ba.etf.unsa.nwt.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

/*	@Bean
	public CommandLineRunner demo(ChargeRequestRepository repository) {
		return (args) -> {
			ChargeRequest chargeRequest = new ChargeRequest("Example Charge", 10,
					ChargeRequest.Currency.EUR, "example@gmail.com",
					"tok_1A9MPEEHp8nYHO");
			ChargeRequest chargeRequest2 = new ChargeRequest("Ticket purchase", 10,
					ChargeRequest.Currency.EUR, "example2@gmail.com",
					"tok_1CMDPEEHp8nTY7");

			repository.save(chargeRequest);
			repository.save(chargeRequest2);
		};
	}*/
}
