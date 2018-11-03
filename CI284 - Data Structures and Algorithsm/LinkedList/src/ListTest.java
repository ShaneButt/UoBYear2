public class ListTest {
    public static void main(String[] args)
    {
        List list = new List();
        list.Add("1"); // Index 0
        list.Add("2"); // Index 1
        list.Add("3"); // Index 2
        list.Add("4"); // Index 3
        list.Add("5"); // Index 4

        System.out.println("ToString(): " + list);
        System.out.println("Size(): " + list.Size());
        System.out.println("Get(3): " + list.get(3));
        System.out.println("Remove(2): " + list.remove(2));
        System.out.println("Get(3): " + list.get(3));
        System.out.println("Size(): " + list.Size());
        System.out.println("ToString(): " + list);
    }
}
