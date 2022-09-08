package trs.models;

/**
 * Confirmation entity, representing a confirmation sent out after either a
 * ticket purchase or ticket cancellation
 */
public class Confirmation {
	private String email;
	private String message;
	private Transaction transaction;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
