package Lab5;

public class AccountStudent extends AccountBetter2
{
	final double OverdraftLimit = -5000.00;
	@Override
	public void creditCharge()
	{
		if (!inCredit() && getBalance() < OverdraftLimit)
		{
			double overdrawn = getBalance(); // this.theBalance can be negative if this.theOverdraft limit allows
			double amount = (Math.abs(overdrawn)) * Rate;
			double newOverdraft = getBalance() - amount;

			if (newOverdraft < getOverdraftLimit())
			{
				setOverdraftLimit(newOverdraft);
			} 
			
			withdraw(amount);
			return;
		}
	}
}
