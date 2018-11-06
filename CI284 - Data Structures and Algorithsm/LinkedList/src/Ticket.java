public class Ticket {

    private String Creator;
    private String Error;
    private String Owner;
    private int UID;
    private int Severity;

    public Ticket()
    {

    }

    public Ticket(String creator, String error, String owner, int id, int severity)
    {
        Creator = creator;
        Error = error;
        Owner = owner;
        UID = id;
        Severity = severity;
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

    public int getSeverity() {
        return Severity;
    }

    public void setSeverity(int severity) {
        Severity = severity;
    }


    @Override
    public String toString()
    {
        return "\n\tz{creator: " + Creator + "}" +
                "\n\t{id: " + UID + "}" +
                "\n\t{severity: " + Severity + "}\n";
    }

}
