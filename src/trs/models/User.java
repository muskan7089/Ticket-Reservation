package trs.models;

/**
 * User entity, representing a non-registered user
 */
public class User {
	private Transaction transaction;

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
}
