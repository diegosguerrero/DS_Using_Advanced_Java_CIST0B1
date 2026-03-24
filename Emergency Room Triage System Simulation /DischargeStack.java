public class DischargeStack {

    public Stack<PatientNode> stack = new Stack<>(10);

    public DischargeStack() {
    }

    public void push(PatientNode node) {
        stack.push(node);
    }

    public PatientNode pop() {

        if (stack.isEmtpy()) {
            return null;
        }
        return stack.pop();
    }

    public PatientNode peek() {
        if (stack.isEmtpy()) {
            return null;

        }
        return stack.peek();
    }
}
