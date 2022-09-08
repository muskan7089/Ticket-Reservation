package trs.models;
import java.util.Date;

/**
 * Coupon entity, representing a coupon returned after cancelling a ticket
 */
public class Coupon {
	private int code, amount;
	private Date expiration;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public Coupon(int code, int amount, Date expiration) {
		this.code = code;
		this.amount = amount;
		this.expiration = expiration;
	}

}
