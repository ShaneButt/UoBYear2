public class ListTest {
    public static void main(String[] args)
    {
        List list = new List();
        Ticket shane = new Ticket(
                "Shane Butt", "Software Issue",
                "Michael Jordan", 1, 3
        );
        Ticket jack = new Ticket(
                "Jack Whitchurch", "Network Issue",
                "PB J Sarnies", 2, 3
        );
        Ticket marc = new Ticket(
                "Marc Walker", "Data Issue",
                "Fusili McPasta", 3, 2
        );
        Ticket mollie = new Ticket(
                "Mollie Applebee", "Application Crash",
                "Jane Doe", 4, 1
        );

        Node shaneNode = list.Add(shane, shane.getPriority());
        Node jackNode = list.Add(jack, jack.getPriority());
        Node marcNode = list.Add(marc, marc.getPriority());
        Node mollieNode = list.Add(mollie, mollie.getPriority());

        Ticket elena = new Ticket(
                "Elena Barry","Software Issue",
                "Michael Jordan", 5, 3
        );


        System.out.println("ToString(): " + list);
        //System.out.println("Size(): " + list.Size());
        //System.out.println("Find(3): " + list.Find(3));
        //System.out.println("Delete(2): " + list.Delete(2));
        Node elenaNode = list.UpdateNode(jackNode, elena, elena.getPriority());
        //System.out.println("Get(3): " + list.Find(3));
        //System.out.println("Size(): " + list.Size());
        System.out.println("ToString(): " + list);
    }
}
