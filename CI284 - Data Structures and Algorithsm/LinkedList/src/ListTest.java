public class ListTest {
    public static void main(String[] args)
    {
        List list = new List();
        Ticket shane = new Ticket(
                "Shane Butt", "Software Glitch",
                "Michael Jordan", 1, 3
        );
        Ticket jack = new Ticket(
                "Jack Whitchurch", "Music Stuttering",
                "PB J Sarnies", 2, 1
        );
        Ticket marc = new Ticket(
                "Marc Walker", "Lighting Glitch",
                "Fusili McPasta", 3, 2
        );
        Ticket mollie = new Ticket(
                "Mollie Applebee", "Game-crashing bug",
                "Jane Doe", 4, 5
        );
        list.Add(shane, shane.getSeverity());
        list.Add(jack, jack.getSeverity());
        list.Add(marc, marc.getSeverity());
        list.Add(mollie, mollie.getSeverity());



        System.out.println("ToString(): " + list);
        System.out.println("Size(): " + list.Size());
        System.out.println("Get(3): " + list.get(3));
        System.out.println("Remove(2): " + list.remove(2));
        System.out.println("Get(3): " + list.get(3));
        System.out.println("Size(): " + list.Size());
        System.out.println("ToString(): " + list);
    }
}
