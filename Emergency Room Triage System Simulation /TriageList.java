public class TriageList {

    private PatientNode head;
    private PatientNode tail;
    private int size;

    public TriageList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addPatient(PatientNode node) {
        node.next = null; // clear old pointers before inserting
        node.prev = null;

        if (head == null) { // List empty
            head = node;
            tail = node;
        } else { // List with patients
            tail.next = node; // tail points to new node
            node.prev = tail; // new node points backward to current tail
            tail = node; // new node is assigend to tail
        }
        size++;
        mergeSort();
    }

    public void removePatient(String name) {

        if (head == null) {
            return;
        }
        PatientNode node = linearSearch(name);
        if (node == null) {
            return; // Patient does not exist
        }

        if (node == head) { // patient becomes new head
            head = head.next;
            if (head != null) {
                head.prev = null; // new head has no previous
            }
        } else if (node == tail) { // previous patient becomes new tail
            tail = tail.prev;
            if (tail != null) {
                tail.next = null; // new tail has no next
            }
        } else { // node is in the middle
            node.prev.next = node.next; // skip over node going forward
            node.next.prev = node.prev; // skip over node going backward
        }
        size--;
    }

    public PatientNode linearSearch(String name) {

        PatientNode current = head;

        while (current != null) {
            if (current.name.equals(name)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void mergeSort() {
        if (head == null || head.next == null) {
            return;
        }

        int length = size;

        for (int width = 1; width < length; width *= 2) {
            PatientNode current = head;
            PatientNode newHead = null;
            PatientNode newTail = null;

            while (current != null) {
                PatientNode left = current;
                PatientNode right = split(left, width);
                current = split(right, width);

                PatientNode merged = merge(left, right);

                if (newHead == null) {
                    newHead = merged;
                    newTail = merged;
                } else {
                    newTail.next = merged;
                    merged.prev = newTail; // Fixing prev attachment
                }
                while (newTail.next != null) {
                    newTail = newTail.next;
                }
            }
            head = newHead;
        }
        tail = head; // Resetting the tail
        while (tail.next != null) {
            tail = tail.next;
        }
    }

    private PatientNode split(PatientNode node, int steps) {
        if (node == null)
            return null;

        for (int i = 1; i < steps && node.next != null; i++) {
            node = node.next;
        }

        PatientNode remainder = node.next;
        node.next = null;
        if (remainder != null)
            remainder.prev = null;
        return remainder;
    }

    private PatientNode merge(PatientNode a, PatientNode b) {
        PatientNode tempHead = new PatientNode(null, -1);
        PatientNode current = tempHead;

        while (a != null && b != null) {
            current.next = null;
            if (a.priorityScore < b.priorityScore ||
                    (a.priorityScore == b.priorityScore && a.timeArrived <= b.timeArrived)) {
                current.next = a;
                a.prev = current;
                a = a.next;
            } else {
                current.next = b;
                b.prev = current;
                b = b.next;
            }
            current = current.next;
        }

        // Cleaning up remaining halfs
        if (a != null) {
            current.next = a;
        }
        if (b != null) {
            current.next = b;
        }
        tempHead.next.prev = null;
        return tempHead.next;
    }

    public PatientNode getHead() {
        return head;
    }

}

/*
 * fields: head, tail, size (all private)
 * constructor initializes all to null/0
 * addPatient() — inserts at tail, calls mergeSort()
 * removePatient() — handles empty, head, tail, middle cases
 * linearSearch() — walks list, uses .equals() for String comparison
 * mergeSort() — null check, calls helper, calls fixPrev()
 * split() — slow/fast pointers, cuts list, cleans prev
 * merge() — recursive, handles null cases, fixes prev pointers
 * getHead() — public getter for private head field
 */
