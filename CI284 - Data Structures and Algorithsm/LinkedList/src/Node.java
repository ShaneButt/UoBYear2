public class Node {

    Object Content;
    Node Tail;
    int Priority;

    public Node(Object data)
    {
        this.Tail = null;
        this.Content = data;
        this.Priority = 1;
    }

    public Node(Object data, int priority)
    {
        this.Content = data;
        this.Priority = priority;
        this.Tail = null;
    }

    public Node(Object data, Node tail, int priority)
    {
        this.Tail = tail;
        this.Content = data;
        this.Priority = priority;
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

    public int getPriority(){ return this.Priority; }

    public void setPriority(int priority) { this.Priority=priority; }

    @Override
    public String toString()
    {
        return getContent().toString();
    }
}
