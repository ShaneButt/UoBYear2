package Lab4;

import java.lang.reflect.Method;

// Test AccountBetter2 
class Main
{
  public static void main( String args[] )
  {
    Main m = new Main();
    m.execute();
  }

  private AccountBetter2 ab = new AccountBetter2();
  private Account        a  = new Account();

  private void execute()
  {
    System.out.println("Using the declaration: ");
    System.out.println( "AccountBetter2 ab = new AccountBetter2();" );
    System.out.println();
    System.out.println( "Then sending messages to ab - gives:" );
    System.out.println();

    methodExists( ab, "getBalance" );
    methodExists( ab, "deposit" );
    methodExists( ab, "withdraw" );
    methodExists( ab, "setOverdraftLimit" );
    methodExists( ab, "getOverdraftLimit" );
    methodExists( ab, "transferFrom" );
    methodExists( ab, "transferTo" );

    check( "D AB",  "100.00",      // Deposit into ab 100.00 
           "AB CC", "0",           // Credit charge on ab 
           "AB IC", "0",           // ab in credit ? 

           "W AB",     "100.00",   // Withdraw from ab 100.00 
           "AB CC", "0",           // Credit charge on ab 
           "AB IC", "0",           // ab in credit 

           "AB SOL","-100.00",     // ab setOverdraftLimit -100.00 
           "AB CC", "0",           // ab cc Credit Charge 
           "AB IC", "0",

           "W AB",     "100.00",
           "AB CC", "0",
           "AB IC", "0",

           "AB SOL","-1000.00",
           "AB IC", "0",           // ab in credit 
           "AB CC", "0",
           "W AB",     "899.00",
           "AB IC", "0",

           "AB CC", "0",
           "AB CC", "0",
           "AB CC", "0",
           "AB CC", "0",
           "AB CC", "0",
           "AB CC", "0"
    );
  }

  private void check( String... params )
  {
    ab = new AccountBetter2();
    a  = new Account();

    int size = params.length;

    for ( int i = 0; i<size; i += 2 )
    {
      String action = params[i];
      double value  = 0.00;
      try
      {
        value  = Double.parseDouble( params[i+1] );
      } catch ( Exception e ) {}
      perform( action, value );
    }
  }

  private void perform( String action, double value )
  {
    boolean res = true;
    switch ( action )
    {
      case "D AB" :
        System.out.printf("ab.deposit( %8.2f )", value );
        ab.deposit( value );
        state();
        break;

      case "D A" :
        System.out.printf("a.deposit( %8.2f )", value );
        a.deposit( value );
        state();
        break;

      case "W AB" :
        System.out.printf("ab.withdraw( %8.2f )", value );
        double wd = ab.withdraw( value );
        state( wd );
        break;

      case "AB SOL" :
        System.out.printf("ab.setOverdraftLimit( %8.2f )", value );
        ab.setOverdraftLimit( value );
        state();
        break;


      case "AB IC" :
        System.out.printf("ab.inCredit()" );
        res = ab.inCredit();
        state( res );
        break;

      case "AB CC" :
        System.out.printf("ab.creditCharge()" );
        ab.creditCharge();
        state();
        break;
    }
    System.out.println();
  }

  private void state()
  {
    System.out.print( "\n    " );
    System.out.printf( "ab.getbalance() -> %8.2f ", ab.getBalance() );
    System.out.printf( "ab.getOverdraftLimit() -> %8.2f ",
                        ab.getOverdraftLimit() );
    System.out.println();
  }

  private void state( boolean res )
  {
    System.out.printf( "  -> returns %s",
                       res ? "true" : "false" );
    state();
  }

  private void state( double res )
  {
    System.out.printf( "  -> returns %8.2f", res );
    state();
  }

  private boolean methodExists( AccountBetter2 ab, String aName )
  {
    Method[] methods = ab.getClass().getDeclaredMethods();
    boolean cheat = false;
    for (Method m : methods)
    {
      if (m.getName().equals(aName))
      {
        System.out.printf("Cheat: method %s is in your submitted class\n",
                          aName );
        cheat = true;
      }
    }
    return cheat;
  }
}