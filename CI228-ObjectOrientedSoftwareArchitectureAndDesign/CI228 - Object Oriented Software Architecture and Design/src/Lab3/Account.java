package Lab3;

public class Account {
	
	private double theBalance = 0.00;
	private double theOverdraft = 0.00; //Overdraft allowed
	
	public double getBalance()
	{
		return theBalance;
	}
	
	public double withdraw( final double money )
	{
		assert money >= 0.00; //Cause error if money -ve
		if ( theBalance - money >= theOverdraft )
		{
			theBalance = theBalance - money;
			return money;
		}
		else 
	 	{
	 		return 0.00;
	 	}
	 }
	 
	 public void deposit( final double money )
	 {
		 assert money >= 0.00; //Cause error if money -ve
		 theBalance = theBalance + money;
	 }
	 
	 public void setOverdraftLimit( final double money )
	 {
		 theOverdraft = money;
	 }
	 
	 public double getOverdraftLimit()
	 {
		 return theOverdraft;
	 }
}
