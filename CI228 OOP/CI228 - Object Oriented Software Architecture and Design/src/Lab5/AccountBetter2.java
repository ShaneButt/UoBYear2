package Lab5;

public class AccountBetter2 extends AccountBetter1 implements Interest {

	final double Rate = 0.00026116;
	
	@Override
	public boolean inCredit() {
		return getBalance() >= 0;
	}

	@Override
	public void creditCharge() {
		if(!inCredit())
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