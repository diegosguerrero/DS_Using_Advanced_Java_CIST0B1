/*
    Diego Guerrero (2026) - CIST04B1 (Data Structures Using Advanced Java) - West Valley College
 */

import java.util.Scanner;

public class ERSystem {

    private TriageQueue triageQueue = new TriageQueue();
    private DischargeStackFinal dischargeHistory = new DischargeStackFinal();
    private PatientLookUp patientLookup = new PatientLookUp();
    private Graph hospitalMap = new Graph();
    Scanner input = new Scanner(System.in);

    public ERSystem() {
        initializeHospitalMap();
    }

    public void initializeHospitalMap() {
        hospitalMap.addEdge("ER", "Triage", 3);
        hospitalMap.addEdge("ER", "Radiology", 4);
        hospitalMap.addEdge("ER", "ICU", 1);
        hospitalMap.addEdge("ER", "Pharmacy", 4);
        hospitalMap.addEdge("Triage", "ICU", 5);
        hospitalMap.addEdge("Surgery", "ICU", 6);
        hospitalMap.addEdge("Radiology", "Surgery", 5);
        hospitalMap.addEdge("ICU", "Pharmacy", 2);
        hospitalMap.addEdge("Surgery", "Pharmacy", 4);
    }

    public void admitPatient() {
        System.out.println("\n== ADMITTING A NEW PATIENT ==");
        System.out.print("What is the name of the patient? ");
        String name = input.nextLine();

        while (name.isBlank()) {
            System.out.print("\nERROR: Name not entered... Try again.");
            System.out.print("\nWhat is the name of the patient? ");
            name = input.nextLine();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        if (patientLookup.contains(name)) {

            System.out.print("\nERROR: Patient already exists... Try again.\n");
            return;
        }

        System.out.print("What is the priority of the patient? ");
        int priority = input.nextInt();
        input.nextLine();

        while (priority < 1 || priority > 3) {
            System.out.print("\nERROR: Invalid priority... Try again.");
            System.out.print("\nWhat is the priority of the patient? ");
            priority = input.nextInt();
            input.nextLine();
        }

        System.out.print("What is the department? ");
        String department = input.nextLine();

        while (department.isBlank()) {
            System.out.print("\nERROR: Department not entered... Try again.");
            System.out.print("\nWhat is the department? ");
            department = input.nextLine();
            department = department.substring(0, 1).toUpperCase() + department.substring(1);
        }
        department = department.substring(0, 1).toUpperCase() + department.substring(1);

        Patient newPatient = new Patient(name, priority, department);
        triageQueue.admit(newPatient);
        patientLookup.addPatient(newPatient);

        System.out.println(name + " has been admitted successfully.");
    }

    public void updatePriority() {
        System.out.print("\n== UPDATING THE PRIORITY ==");
        System.out.print("\nWhat is the name of the patient? ");
        String name = input.nextLine();

        while (name.isBlank()) {
            System.out.print("\nERROR: Name not entered... Try again.");
            System.out.print("\nWhat is the name of the patient? ");
            name = input.nextLine();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        if (!patientLookup.contains(name)) {
            System.out.print("\nPatient not found...");
            return;
        }

        System.out.print("What is the new priority score? ");
        int newPriority = input.nextInt();
        input.nextLine();

        while (newPriority < 1 || newPriority > 3) {
            System.out.print("\nERROR: Invalid priority... Try again.");
            System.out.print("\nWhat is the new priority score? ");
            newPriority = input.nextInt();
            input.nextLine();
        }

        triageQueue.updatePriority(name, newPriority);
        patientLookup.findPatient(name).priorityScore = newPriority;
    }

    public void searchPatient() {
        System.out.println("\n== SEARCHING FOR A PATIENT ==");
        System.out.print("What is the name of the patient? ");
        String name = input.nextLine();

        while (name.isBlank()) {
            System.out.print("\nERROR: Name not entered... Try again.");
            System.out.print("\nWhat is the name of the patient? ");
            name = input.nextLine();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        Patient patient = patientLookup.findPatient(name);

        if (patient == null) {

            System.out.println("The patient was not found...");
        } else
            System.out.println(patient);
    }

    public void discharge() {
        System.out.println("\n== DISCHARGING A PATIENT ==");
        if (triageQueue.isEmpty()) {
            System.out.print("No patients were found...");
            return;
        }
        Patient patient = triageQueue.poll();
        System.out.println(patient + " | Has been discharged.");
        patientLookup.removePatient(patient.name);
        dischargeHistory.push(patient);
    }

    public void undoDischarge() {
        System.out.println("\n== UNDOING THE LAST DISCHARGE ==");
        if (dischargeHistory.isEmpty()) {
            System.out.print("There is nothing to undo...");

            return;
        }

        Patient patient = dischargeHistory.pop();
        triageQueue.admit(patient);
        patientLookup.addPatient(patient);

        System.out.println(patient.name + " has been re-admitted.");
    }

    public void findTransferRoute() {
        System.out.println("\n== FINDING AN OPTIMAL TRANSFER ROUTE ==");
        // ER, Surgery, Radiology, ICU, Pharmacy, Triage
        System.out.print("What is the source department? ");
        String source = input.nextLine();

        while (source.isBlank()) {
            System.out.print("\nERROR: Source not entered... Try again.");
            System.out.print("\nWhat is the source department? ");
            source = input.nextLine();
            source = source.substring(0, 1).toUpperCase() + source.substring(1);
        }
        source = source.substring(0, 1).toUpperCase() + source.substring(1);

        // ER, Surgery, Radiology, ICU, Pharmacy, Triage
        System.out.print("What is the destination department? ");
        String destination = input.nextLine();

        while (destination.isBlank()) {
            System.out.print("\nERROR: Destination not entered... Try again.");
            System.out.print("\nWhat is the destination department? ");
            destination = input.nextLine();
            destination = destination.substring(0, 1).toUpperCase() + destination.substring(1);
        }
        destination = destination.substring(0, 1).toUpperCase() + destination.substring(1);

        hospitalMap.dijkstra(source, destination);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ERSystem system = new ERSystem();

        while (true) {
            System.out.print("\n======= STAFF INTERACTIVE MENU =======\n" +
                    "Press (1) to admit a new patient\n" +
                    "Press (2) to update a patient's priority\n" +
                    "Press (3) to search for a patient by name\n" +
                    "Press (4) to discharge the top priority patient\n" +
                    "Press (5) to view the full queue\n" +
                    "Press (6) to undo the last discharge\n" +
                    "Press (7) to find a transfer route to take\n" +
                    "Press (8) to display the hospital map and graph\n" +
                    "Press (9) to display the discharge history\n" +
                    "Press (10) to exit the menu\n" +
                    "Enter here: ");

            if (!input.hasNextInt()) {
                // input is not a number
                System.err.println("\nERROR: Input has to be a number, try again.");
                input.next(); // clears the bad input
                continue;
            }

            int number = input.nextInt();
            input.nextLine();

            switch (number) {
                case 1:
                    system.admitPatient();
                    break;
                case 2:
                    system.updatePriority();
                    break;
                case 3:
                    system.searchPatient();
                    break;
                case 4:
                    system.discharge();
                    break;
                case 5:
                    system.triageQueue.displayQueue();
                    break;
                case 6:
                    system.undoDischarge();
                    break;
                case 7:
                    system.findTransferRoute();
                    break;
                case 8:
                    system.hospitalMap.displayGraph();
                    break;
                case 9:
                    system.dischargeHistory.displayHistory();
                    break;
                case 10:
                    System.out.println("\n=== EXITING MAIN MENU ===");
                    System.out.print("Exiting Successfully...");
                    input.close();
                    return;
                default:
                    System.err.println("\nERROR: Invalid option.");
            }
        }
    }
}
