package Lab5;

import java.lang.reflect.Method;

// Test AccountStudent 
class Main
{
  public static void main( String args[] )
  {
    Main m = new Main();
    m.execute();
  }


  private void execute()
  {
    System.out.println("Using the declaration: ");
    System.out.println( "AccountStudent as = new AccountStudent();" );
    System.out.println();
    System.out.println( "Then sending messages to the object as - gives:" );
    System.out.println();

    methodExists( as, "getBalance" );
    methodExists( as, "deposit" );
    methodExists( as, "withdraw" );
    methodExists( as, "setOverdraftLimit" );
    methodExists( as, "getOverdraftLimit" );
    methodExists( as, "transferFrom" );
    methodExists( as, "transferTo" );
    methodExists( as, "inCredit" );

    check( "D AS",    "100.00",
           "AS CC",   "0",
           "AS IC",   "0",

           "W AS",    "100.00",
           "AS CC",   "0",
           "AS IC",   "0",

           "AS SOL",  "-100.00",
           "AS CC",   "0",
           "AS IC",   "0",

           "W AS",    "100.00",
           "AS CC",   "0",
           "AS IC",   "0",

           "AS SOL",  "-5010.00",
           "AS CC",   "0",
           "W AS",    "4899.99",
           "AS IC",   "0",

           "AS CC",   "0",
           "W AS",    "0.01",
           "AS CC",   "0",
           "W AS",    "0.01",
           "AS CC",   "0",
           "W AS",    "0.01",
           "AS IC",   "0",
           "AS CC",   "0",

           "D AS",    "2.64",
           "AS IC",   "0",
           "AS CC",   "0",
           "AS CC",   "0"
    );
  }


  private AccountStudent as = new AccountStudent();

  private void check( String... params )
  {
    as = new AccountStudent();

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
      case "D AS" :
        System.out.printf("as.deposit( %8.2f )", value );
        as.deposit( value );
        state();
        break;
        
      case "W AS" :
        System.out.printf("as.withdraw( %8.2f )", value );
        double wd = as.withdraw( value );
        state( wd );
        break;

      case "AS SOL" :
        System.out.printf("as.setOverdraftLimit( %8.2f )", value );
        as.setOverdraftLimit( value );
        state();
        break;


      case "AS IC" :
        System.out.printf("as.inCredit()" );
        res = as.inCredit();
        state( res );
        break;

      case "AS CC" :
        System.out.printf("as.creditCharge()" );
        as.creditCharge();
        state();
        break;
    }
    System.out.println();
  }

  private void state()
  {
    System.out.print( "\n    " );
    System.out.printf( "as.getbalance() -> %8.2f ", as.getBalance() );
    System.out.printf( "as.getOverdraftLimit() -> %8.2f ", 
                        as.getOverdraftLimit() );
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

  private boolean methodExists( AccountStudent ab, String aName )
  {
    Method[] methods = ab.getClass().getDeclaredMethods();
    boolean cheat = false;;
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