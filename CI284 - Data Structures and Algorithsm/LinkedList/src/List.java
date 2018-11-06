import java.util.Comparator;

public class List implements Comparator<Node> {

    private Node Head;
    private static int Size;

    public List() // empty list constructor
    {
        this.Head = new Node(null);
        this.Size = 0;
    }

    public void Add(Object data, int priority) {
        Node n = new Node(data, priority);
        Node current = this.Head;
        if (current != null) {
            while (current.getTail() != null && compare(n, current.getTail()) >= 0)
            {
                current = current.getTail();
            }
            n.setTail(current.getTail()); // set new node tail to current node's tail
            current.setTail(n); // set this node's tail to new node
            incrementSize(); // increment the size of the list
        }
    }

    public boolean remove(int index)
    {
        // Index Out Of Bounds Exception check
        if(index < 1 || index > Size())
            return false;

        Node current = this.Head;
        if(this.Head != null)
        {
            for(int i = 0; i < index; i++)
            {
                if(current.getTail()==null)
                    return false;
                current = current.getTail();
            }
            current.setTail(current.getTail().getTail());
            decrementSize();
            return true;
        } else
            return false;

    }

    public Object get(int index)
    {
        if(index < 0)
        {
            return null; // cannot have an index lower than 0
        }

        Node current = null;
        if(this.Head != null)
        {
            current = this.Head.getTail();
            for(int i = 0; i < index; i++)
            {
                if(current.getTail() == null) return null;

                current = current.getTail();
            }
            return current.getContent();
        }
        return current;
    }

    public int Size()
    {
        return this.Size;
    }

    public void incrementSize()
    {
        this.Size++;
    }

    public void decrementSize()
    {
        this.Size--;
    }

    public String toString()
    {
        String s = "";
        if(this.Head!=null)
        {
            Node current = this.Head.getTail();
            while(current != null)
            {
                s += "\n{" + current.getContent().toString() +"}"  + " -> "; // "Example" -> "Example2"
                current = current.getTail();
            }
        }
        return s;
    }

    @Override
    public int compare(Node n1, Node n2)
    {
        return n1.getPriority() - n2.getPriority();
    }
}