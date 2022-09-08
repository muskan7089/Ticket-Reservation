package trs.models;

/**
 * Transaction entity, representing a payment
 */
public class Transaction {
	private int id, pin;
	String cardNumber;
	private double amount;
	private boolean cardValidated;

	public Transaction(int id, String cardNumber, int pin, double amount) {
		this.id = id;
		this.cardNumber = cardNumber;
		this.pin = pin;
		this.amount = amount;
		cardValidated = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isCardValidated() {
		return cardValidated;
	}

	public void setCardValidated(boolean cardValidated) {
		this.cardValidated = cardValidated;
	}
}
