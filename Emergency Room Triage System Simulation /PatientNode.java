public class PatientNode {

    String name;
    int priorityScore;
    Long timeArrived;

    // Pointers
    public PatientNode next;
    public PatientNode prev;

    public PatientNode(String name, int priorityScore) {
        this.priorityScore = priorityScore;
        this.name = name;
        this.timeArrived = System.currentTimeMillis();
        this.next = null;
        this.prev = null;

    }
}
