package Lab5;

public interface Transfer {
	public boolean transferFrom(Account Sender, double Amount);
	public boolean transferTo(Account Target, double Amount);
}
