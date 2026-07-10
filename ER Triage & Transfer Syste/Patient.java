public class Patient implements Comparable<Patient> {

    String name;
    int priorityScore;
    long timeArrived;
    String department;

    public Patient(String name, int priorityScore, String department) {
        this.priorityScore = priorityScore;
        this.name = name;
        this.timeArrived = System.currentTimeMillis();
        this.department = department;
    }

    @Override
    public int compareTo(Patient other) {
        
        return this.priorityScore - other.priorityScore;
    }

    @Override
    public String toString() {

        return "Name: " + name + " | Priority: " + priorityScore + " | Department: " + department;
    }
}
