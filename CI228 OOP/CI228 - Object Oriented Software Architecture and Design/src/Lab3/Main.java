package Lab3;

import java.lang.reflect.Method;

// Test AccountBetter1 

class Main
{
  public static void main( String args[] )
  {
    Main m = new Main();
    m.execute();
  }

  private AccountBetter1 ab = new AccountBetter1();
  private Account        a  = new Account();

  private void execute()
  {
    System.out.println("Using the declarations: ");
    System.out.println( "AccountBetter1 ab = new AccountBetter1();" );
    System.out.println( "Account        a  = new Account();" );
    System.out.println();

    methodExists( ab, "getBalance" );
    methodExists( ab, "deposit" );
    methodExists( ab, "withdraw" );
    methodExists( ab, "setOverdraftLimit" );
    methodExists( ab, "getOverdraftLimit" );

    System.out.println( "Then sending messages to these objects - gives:" );
    System.out.println();

    check( "Dab", "100.00",
           "Da",  "50.00",

           "TT",  "50",
           "TT",  "40",
           "TT",  "-1",
           "TT",  "10",
           "TT",  "1",
           "TT",  "-0.01",

           "TF",  "50",
           "TF",  "50",
           "TF",  "40",
           "TF",  "-1",
           "TF",  "10",
           "TF",  "1",
           "TF",  "-0.01"
    );
  }


  private void check( String... params )
  {
    ab = new AccountBetter1();
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
      case "Dab" :
        System.out.printf("ab.deposit( %6.2f )", value );
        ab.deposit( value );
        state();
        break;

      case "Da" :
        System.out.printf("a.deposit( %6.2f )", value );
        a.deposit( value );
        state();
        break;

      case "TT" :
        System.out.printf("ab.transferTo( a, %6.2f )", value );
        res = ab.transferTo( a, value );
        state( res );
        break;

      case "TF" :
        System.out.printf("ab.transferFrom( a, %6.2f )", value );
        res = ab.transferFrom( a, value );
        state( res );
        break;
    }
    System.out.println();
  }

  private void state()
  {
    System.out.print( "\n    " );
    System.out.printf( "ab.getbalance() -> %6.2f ", ab.getBalance() );
    System.out.printf( "a.getbalance() -> %6.2f ", a.getBalance() );
    System.out.println();
  }


  private void state( boolean res )
  {
    System.out.printf( "  -> returns %s",
                       res ? "true" : "false" );
    state();
  }

  private boolean methodExists( AccountBetter1 ab, String aName )
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