package ba.etf.unsa.nwt.paymentservice.domain;

import lombok.Data;

/**
 * Created by Adna Karkelja on 4/1/18.
 *
 * @author Adna Karkelja
 */

@Data
public class ChargeResponse {
	String id;
	String object;
	String status;
	String balanceTransaction;

	public ChargeResponse(String id, String object, String status, String balanceTransaction) {
		this.id = id;
		this.object = object;
		this.status = status;
		this.balanceTransaction = balanceTransaction;
	}
}
