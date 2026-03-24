import java.util.Scanner;

/*
    Diego Guerrero - CIST04B1 (Data Structures Using Advanced Java)
 */

public class TriageSystem {

    private TriageList triageList = new TriageList();
    private IntakeQueue intakeQueue = new IntakeQueue();
    private DischargeStack history = new DischargeStack();

    public void admitPatient(int priority, String name) {

        PatientNode admitted = new PatientNode(name, priority);
        intakeQueue.enQueue(admitted);
        admitted = intakeQueue.deQueue();
        triageList.addPatient(admitted);

    }

    public void updatePriority(String name, int score) {

        // Search by name
        PatientNode node = triageList.linearSearch(name);

        if (node == null) {
            System.out.println("Patient not found!");
        } else {
            node.priorityScore = score;
            triageList.mergeSort();
        }
    }

    public void disCharge() {

        if (triageList.getHead() == null) {
            System.out.println("No patients to discharge.");
        } else {
            history.push(triageList.getHead()); //
            triageList.removePatient(triageList.getHead().name); // Remove from triage list
        }

    }

    public void undoDischarge() {

        // Checking if empty without accessing stack
        if (history.peek() == null) {
            System.out.println("No discharge history.");
        } else {
            PatientNode node = history.pop();
            triageList.addPatient(node);
        }
    }

    public void displayQueue() {

        PatientNode current = triageList.getHead();

        while (current != null) {
            System.out.println(
                    "Name of Patient: " + current.name + " | " +
                            "Current Priority Score: " + current.priorityScore + " | " +
                            "Time Admitted: " + new java.util.Date(current.timeArrived));
            current = current.next;
        }
    }

    public PatientNode searchPatient(String name) {
        return triageList.linearSearch(name);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        TriageSystem system = new TriageSystem();

        while (true) {
            int number = 0;
            while (number < 1 || number > 7) {
                System.out.print("\n======= STAFF INTERACTIVE MENU =======\n" +
                        "Press (1) to admit a new patient\n" +
                        "Press (2) to update a patient's priority\n" +
                        "Press (3) to search for a patient by name\n" +
                        "Press (4) to discharge the top priority patient\n" +
                        "Press (5) to view the full queue\n" +
                        "Press (6) to undo the last discharge\n" +
                        "Press (7) to exit\n" +
                        "Enter here: ");

                if (!input.hasNextInt()) {
                    // input is not a number
                    System.err.println("\nERROR: Input has to be a number, try again.");
                    input.next(); // clears the bad input
                    continue; // go back to top of loop
                }

                number = input.nextInt();
                input.nextLine();

                if (number < 1 || number > 7) {
                    // checks if input is not a menu button
                    System.err.println("\nERROR: Invalid option, try again.\n");
                    continue; // go back to top of loop
                }
                break;
            }

            switch (number) {
                case 1: // Admitting a new patient
                    System.out.print("\n==== Admitting a New Patient ====");
                    System.out.print("\nName of the patient: ");
                    String name = input.nextLine();

                    if (name.isEmpty() || name == null) {
                        System.err.println("ERROR: Please enter a valid name, try again.");
                        break;
                    }

                    System.out.print(
                            "== Quick Priority Chart == \n(1) Highest Priority\n(2) Intermediate Priority\n(3) Lowest Priority\n");

                    System.out.print("Enter priority score of patient: ");

                    if (!input.hasNextInt()) {
                        // input is not a number
                        System.err.println("\nERROR: Input has to be a number, try again.");
                        input.next(); // clears the bad input
                        break; // Shows main menu again
                    }
                    int priority = input.nextInt();
                    input.nextLine();

                    // What if left blank and pressed again, throw an error.. 
                    if (priority < 1 || priority > 3) {
                        System.err.println("\nERROR: Invalid priority score, try again.");
                        break;
                    }

                    system.admitPatient(priority, name);
                    System.out.println("Patient Successfully Admitted!");

                    break;

                case 2: // Updating a patient's priority

                    System.out.print("\n==== Updating a Patient's Priority ====\n");
                    // Add another safety check? What if two patients have the same name...
                    System.out.print("Name of the patient: ");
                    String updatePatient = input.nextLine();

                    if (updatePatient.isEmpty() || updatePatient == null) {
                        System.err.println("ERROR: Please enter a valid name, try again.");
                        break;
                    }

                    System.out.print("Enter the new priority score: ");
                    int newPriority = input.nextInt();
                    input.nextLine();

                    system.updatePriority(updatePatient, newPriority);

                    System.out.println("\nPatient " + updatePatient + "'s Priority Score Updated.");

                    break;

                case 3: // Searching for a patient by name

                    System.out.println("\n==== Searching a Patient By Name ====");
                    // Add another safety check? What if two patients have the same name.. Ask tmr
                    System.out.print("Name of the Patient: ");
                    String searchPatient = input.nextLine();

                    if (searchPatient.isEmpty() || searchPatient == null) {
                        System.err.println("ERROR: Please enter a valid name, try again.");
                        break;
                    }

                    PatientNode found = system.searchPatient(searchPatient);
                    if (found == null) {
                        System.out.println("Patient NOT found.");
                    } else {
                        System.out.println(
                                "Patient Found: " + found.name + " | " + "Priority Score: " + found.priorityScore);
                    }

                    break;

                case 4: // Discharging the top priority patient
                    String dischargedName = system.triageList.getHead().name;
                    system.disCharge();
                    System.out.println("\nPatient " + dischargedName + " has been discharged.");

                    break;

                case 5: // Displaying/Viewing full queue
                    System.out.println("\n=== Current Patient Queue ===");
                    system.displayQueue();
                    System.out.println("");

                    break;

                case 6: // Undoing the last discharge
                    System.out.println("\nUndo last discharge made.\n");
                    system.undoDischarge();
                    break;

                case 7: // Close-Exit menu
                    System.out.println("\nACTION: Exiting menu...");
                    input.close();
                    return;
            }
        }
    }
}
