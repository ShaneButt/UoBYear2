public class Node {

    Object Content;
    Node Tail;

    public Node(Object data)
    {
        this.Tail = null;
        this.Content = data;
    }

    public Node(Object data, Node tail)
    {
        this.Tail = tail;
        this.Content = data;
    }

    public Node getTail()
    {
        return Tail;
    }

    public void setTail(Node n)
    {
        this.Tail = n;
    }

    public Object getContent()
    {
        return this.Content;
    }

    public void setContent(Object data)
    {
        this.Content = data;
    }
}
