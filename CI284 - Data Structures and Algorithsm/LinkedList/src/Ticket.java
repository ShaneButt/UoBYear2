public class Ticket {

    private String Creator; // ticket submitter
    private String Error; // ticket message
    private String Owner; // ticket handler
    private int UID; // ticket id
    private int Priority; // ticket priority

    public Ticket()
    {

    }

    public Ticket(String creator, String error, String owner, int id, int priority)
    {
        Creator = creator;
        Error = error;
        Owner = owner;
        UID = id;
        Priority = priority;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int severity) {
        Priority = severity;
    }


    @Override
    public String toString()
    {
        return "\n\t\t{creator: " + Creator + "}" +
                "\n\t\t{id: " + UID + "}" +
                "\n\t\t{severity: " + Priority + "}\n";
    }

}
