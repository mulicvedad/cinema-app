package ba.etf.unsa.nwt.paymentservice.controller;

import ba.etf.unsa.nwt.paymentservice.domain.ChargeRequest;
import ba.etf.unsa.nwt.paymentservice.domain.ChargeResponse;
import ba.etf.unsa.nwt.paymentservice.service.ChargeRequestService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ChargeController {

	@Autowired
	private ChargeRequestService paymentsService;

	@PostMapping("/charge")
	public ResponseEntity charge(@RequestBody ChargeRequest chargeRequest)
			throws StripeException {
		chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
		Charge charge = paymentsService.charge(chargeRequest);
		ChargeResponse chargeResponse = new ChargeResponse(charge.getId(),charge.getObject(),
				charge.getStatus(),charge.getBalanceTransaction());
		return ResponseEntity.ok(chargeResponse);
	}

	@ExceptionHandler(StripeException.class)
	public String handleError(Model model, StripeException ex) {
		model.addAttribute("error", ex.getMessage());
		return "result";
	}
}