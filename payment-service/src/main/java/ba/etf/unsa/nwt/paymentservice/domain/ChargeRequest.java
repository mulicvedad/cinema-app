package ba.etf.unsa.nwt.paymentservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Adna Karkelja on 3/18/18.
 *
 * @author Adna Karkelja
 */

@Entity
public class ChargeRequest {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	public enum Currency {
		EUR, USD;
	}

	protected ChargeRequest() {};

	public ChargeRequest(String description, int amount, Currency currency, String stripeEmail, String stripeToken) {
		this.description = description;
		this.amount = amount;
		this.currency = currency;
		this.stripeEmail = stripeEmail;
		this.stripeToken = stripeToken;
	}

	private String description;
	private int amount;
	private Currency currency;
	private String stripeEmail;
	private String stripeToken;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getStripeEmail() {
		return stripeEmail;
	}

	public void setStripeEmail(String stripeEmail) {
		this.stripeEmail = stripeEmail;
	}

	public String getStripeToken() {
		return stripeToken;
	}

	public void setStripeToken(String stripeToken) {
		this.stripeToken = stripeToken;
	}
}
