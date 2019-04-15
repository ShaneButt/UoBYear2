package Lab5;

public class AccountBetter1 extends Account implements Transfer
{
		
	public boolean transferFrom(Account Sender, double Amount)
	{
		if(!(Amount>=0.00)) return false;
		
		double Withdrawn = Sender.withdraw(Amount);
		if(Withdrawn!=0)
		{
			this.deposit(Withdrawn);
			return true;
		}
		else return false;
	}

	public boolean transferTo(Account Target, double Amount)
	{
		if(!(Amount>=0.00)) return false;
		
		double Withdrawn = this.withdraw(Amount);
		if(Withdrawn!=0)
		{
			Target.deposit(Withdrawn);
			return true;
		}
		else return false;
	}
}
