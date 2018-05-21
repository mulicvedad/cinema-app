package ba.etf.unsa.nwt.paymentservice.service;

import ba.etf.unsa.nwt.paymentservice.domain.ChargeRequest;
import ba.etf.unsa.nwt.paymentservice.repository.ChargeRequestRepository;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adna Karkelja on 3/19/18.
 *
 * @author Adna Karkelja
 */

@Service
public class ChargeRequestService {
	@Autowired
	private ChargeRequestRepository repository;

	public ChargeRequest getById(Long id) {
		return repository.getOne(id);
	}

	public void saveChargeRequest(ChargeRequest request) {
		repository.save(request);
	}

	//@Value("")
	private String secretKey;

	@PostConstruct
	public void init() {
		Stripe.apiKey = secretKey;
	}

	public Charge charge(ChargeRequest chargeRequest)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		Map<String, Object> chargeParams = new HashMap<>();
		chargeParams.put("amount", chargeRequest.getAmount());
		chargeParams.put("currency", chargeRequest.getCurrency());
		chargeParams.put("description", chargeRequest.getDescription());
		chargeParams.put("source",chargeRequest.getStripeToken());
		return Charge.create(chargeParams);
	}
}