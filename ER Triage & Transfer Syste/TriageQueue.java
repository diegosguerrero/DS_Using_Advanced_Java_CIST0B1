import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;

public class TriageQueue {

    PriorityQueue<Patient> queue = new PriorityQueue<>();

    public TriageQueue() {
    }

    public void admit(Patient patient) {
        queue.add(patient);
    }

    public Patient poll() {

        if (queue.isEmpty()) {
            return null;
        }
        return queue.poll();
    }

    public Patient peek() {

        if (queue.isEmpty()) {
            return null;
        }
        return queue.peek();
    }

    public void updatePriority(String name, int newScore) {

        for (Patient patient : queue) {
            if (patient.name.equals(name)) {

                queue.remove(patient);
                patient.priorityScore = newScore;
                queue.add(patient);
                return;
            }
        }
        System.out.println("Patient not found");
    }

    public void displayQueue() {
        System.out.println("\n== LIST OF CURRENT PATIENTS ==");

        if (queue.isEmpty()) {
            System.out.println("No patients in queue.");
            return;
        }
        
        ArrayList<Patient> list = new ArrayList<>(queue);
        Collections.sort(list);

        for (Patient patient : list) {
            System.out.println(patient);
        }
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

/*
 * fields: queue (PriorityQueue<Patient>)
 * constructor initializes empty PriorityQueue
 * admit() — adds patient to queue, heap auto-orders by priorityScore
 * poll() — removes and returns highest-priority patient, null if empty
 * peek() — returns highest-priority patient without removing, null if empty
 * updatePriority() — removes patient, updates score, re-adds to reorder heap
 * displayQueue() — copies queue to ArrayList, sorts it, prints each patient
 * size() — returns number of patients in queue
 * isEmpty() — returns true if queue has no patients
 */