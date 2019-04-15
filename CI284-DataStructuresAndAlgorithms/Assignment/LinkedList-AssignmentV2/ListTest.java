package CI284;

import java.util.NoSuchElementException;

public class ListTest {
	@SuppressWarnings("unused")
    public static void main(String[] args)
    {
        List list = new List(); // Construct an empty list;

        // Construct Ticket data to input into the Queue;
        Ticket shane = new Ticket("Shane Butt",
                "Software Issue", "Michael Jordan", 1, 3);
        Ticket jack = new Ticket("Jack Whitchurch",
                "Network Issue","PB J Sarnies", 2, 3);
        Ticket marc = new Ticket("Marc Walker",
                "Data Issue","Fusili McPasta", 3, 2);
        Ticket mollie = new Ticket("Mollie Applebee",
                "Application Crash", "Jane Doe", 4, 1);

        // Construct Nodes out of the Ticket information;
        Node shaneNode = list.Add(shane, shane.getPriority());
        Node jackNode = list.Add(jack, jack.getPriority());
        Node marcNode = list.Add(marc, marc.getPriority());
        Node mollieNode = list.Add(mollie, mollie.getPriority());

        // Extra Ticket for method testing;
        Ticket elena = new Ticket("Elena Barry",
                "Software Issue", "Michael Jordan", 5, 1);


        // Output Tests

        // Tests the toString() override;
        System.out.println("ToString(): " + list);

        // Test Peek() method;
        try {
            System.out.println("Peek()ing: " + list.Peek());
        } catch(NullPointerException e)
        {
            System.out.println("List must be empty");
        }

        // Tests the Size() method;
        System.out.println("Size(): " + list.Size());

        // Tests the Find(int index:) method;
        System.out.println("Find(int 3): " + list.Find(3));

        // Tests the Find(Object Data) method;
        System.out.println("Find(Jack's Ticket): " + list.Find(jack));
        
        // Tests the Delete(Object Data) method;
        System.out.println("Delete(Jack's Ticket): " + list.Delete(jack));
        
        // Tests the Contains(Object Data) method;
        System.out.println("Contains(Jack's Ticket): " + list.Contains(jack));
        
        // Check the content of the list
        System.out.println("List: " + list);
        
        // Tests the DeleteIndex(int index:) method;
        System.out.println("DeleteIndex(int 2): " + list.DeleteIndex(2));
        
        // Check the content of the list
        System.out.println("List: " + list);

        // Constructs a Node out of the extra Ticket we made earlier;
        // Utilising the UpdateNode(Node oldNode, Object data, int Priority) method;
        Node elenaNode = null;
        try {
        	elenaNode = list.UpdateNode(marcNode, elena, elena.getPriority());
        	System.out.println("Successfully updated MarcNode with Elena");
        } catch(NoSuchElementException ex)
        {
        	System.out.println("Could not update node, previous node, or head of list, does not exist!");
        }

        // Tests the Contains(Node node) method;
        System.out.println("Contains(Node ElenaNode): " + list.Contains(elenaNode));

        // Tests the Contains(Object data) method;
        System.out.println("Contains(Object elena): " + list.Contains(elena));

        // Tests the Size() method;
        System.out.println("Size(): " + list.Size());

        // Tests the toString() override method;
        System.out.println("ToString(): " + list);

        // Tests the isEmpty() method;
        System.out.println("isEmpty(): " + list.isEmpty());

        // Tests and performs list-wide node deletion;
        System.out.println("DeleteAll()");
        list.DeleteAll(); // Deletes all Nodes;

        // Tests the Size() method;
        System.out.println("Size(): " + list.Size());

        // Tests the isEmpty() method;
        System.out.println("isEmpty(): " + list.isEmpty());

        // Tests the toString() override method;
        System.out.println("ToString(): " + list);
        
        try {
        	Node n = list.UpdateNode(elenaNode, marc, marc.getPriority());
        } catch (NoSuchElementException e)
        {
        	System.out.println("Testing for the sake of it");
        }
        
    }
}