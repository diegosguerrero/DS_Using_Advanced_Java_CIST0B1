import java.util.Stack;

public class DischargeStackFinal {

    public Stack<Patient> stack = new Stack<>();

    public DischargeStackFinal() {
    }

    public void push(Patient patient) {
        stack.push(patient);
    }

    public Patient pop() {

        if (stack.isEmpty()) {
            return null;
        }
        return stack.pop();
    }

    public Patient peek() {
        if (stack.isEmpty()) {
            return null;

        }
        return stack.peek();
    }

    public boolean isEmpty(){

        return stack.isEmpty();
    }

    public void displayHistory() {
            System.out.println("\n== DISCHARGE HISTORY OF PATIENTS ==");
        if (stack.isEmpty()){
            System.out.println("There is no discharges to make...");
            return;
        }

        for (Patient patient : stack){
            System.out.println(patient);
        }
    }
}
