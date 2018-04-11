package ba.etf.unsa.nwt.cinemaservice.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ChargeRequest {

	@NotNull(message = "Id cannot be null")
	@Positive(message = "Id must be positive integer")
	private Long id;

	private String description;

	@Positive(message = "Amount should be greater than zero")
	private int amount;

	@NotNull(message = "Currency cannot be null")
	private Currency currency;

	@Email(message = "Email should be valid")
	private String stripeEmail;

	@NotNull(message = "Stripe Token cannot be null")
	private String stripeToken;

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

	public ChargeRequest(int amount, String stripeEmail, String stripeToken) {
		this.amount = amount;
		this.stripeEmail = stripeEmail;
		this.stripeToken = stripeToken;
	}

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
