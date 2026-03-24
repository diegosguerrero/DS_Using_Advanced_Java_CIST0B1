public class IntakeQueue {

    PatientNode start;
    PatientNode end;
    int size;

    public IntakeQueue() {
        this.start = null;
        this.end = null;
        this.size = 0;
    }

    public void enQueue (PatientNode node){
        if (end == null) {
            start = node;
            end = node;
        } else {
            end.next = node; //Adding to the end
            end = node; //Update End
        }
        size++; //Increments when not empty
    }

    public PatientNode deQueue(){
        if (start == null) {
            return null;
        }
        PatientNode temp = start;
        start = start.next;
        if (start == null) {
            end = null;
        }
        size--;
        return temp;
    }
    public boolean isEmpty(){
        return size == 0;
    }
}
