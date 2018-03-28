package ba.etf.unsa.nwt.paymentservice.controller;

import ba.etf.unsa.nwt.paymentservice.domain.ChargeRequest;
import ba.etf.unsa.nwt.paymentservice.service.ChargeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Adna Karkelja on 3/19/18.
 *
 * @author Adna Karkelja
 */

@RestController
public class CheckoutController {

	@Value("${STRIPE_PUBLIC_KEY}")
	private String stripePublicKey;

	@Autowired
	ChargeRequestService service;

	@RequestMapping("/checkout")
	public String checkout(Model model) {
		model.addAttribute("amount", 50 * 100); // in cents
		model.addAttribute("stripePublicKey", stripePublicKey);
		model.addAttribute("currency", ChargeRequest.Currency.EUR);
		return "checkout";
	}

	@RequestMapping(value = "/documentsPerUser")
	public ChargeRequest getChargeRequestByID(@RequestParam Long id) {
		return service.getById(id);
	}
}
