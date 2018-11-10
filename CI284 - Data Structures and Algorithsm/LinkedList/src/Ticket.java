public class Ticket {

    private String Submitter; // ticket submitter
    private String ErrorMessage; // ticket message
    private String EmployeeHandler; // ticket handler
    private int UniqueIdentifier; // ticket id
    private int Priority; // ticket priority

    public Ticket()
    {

    }

    public Ticket(String submitter, String errorMessage, String employeeHandler, int uniqueIdentifier, int priority)
    {
        Submitter = submitter;
        ErrorMessage = errorMessage;
        EmployeeHandler = employeeHandler;
        UniqueIdentifier = uniqueIdentifier;
        Priority = priority;
    }

    public String getCreator() {
        return Submitter;
    }

    public void setCreator(String submitter) {
        Submitter = submitter;
    }

    public String getError() {
        return ErrorMessage;
    }

    public void setError(String error) {
        ErrorMessage = error;
    }

    public String getOwner() {
        return EmployeeHandler;
    }

    public void setOwner(String employeeHandler) {
        EmployeeHandler = employeeHandler;
    }

    public int getUID() {
        return UniqueIdentifier;
    }

    public void setUID(int UID) {
        this.UniqueIdentifier = UID;
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
        return "\n\t\t{creator: " + Submitter + "}" +
                "\n\t\t{id: " + UniqueIdentifier + "}" +
                "\n\t\t{handler: " + EmployeeHandler + "}" +
                "\n\t\t{severity: " + Priority + "}\n";
    }
}
