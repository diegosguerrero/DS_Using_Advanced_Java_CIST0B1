package Lab_8and9;

public class AnimalHashTable {

    private Object[] table;
    private int size;

    public AnimalHashTable(int capacity) {
        table = new Object[capacity];
        size = 0;
    }

    // HashFunction
    private int hash(String animal) {
        int hashValue = 0;
        for (int i = 0; i < animal.length(); i++) {
            hashValue += animal.charAt(i);
        }
        return Math.abs(hashValue % table.length);
    }

    // Add
    public void put(Animal animal) {
        // check if there's room available
        if (size == table.length) {
            System.err.println("Hash table full");
            return;
        }
        if (animal.name.equals("DELETED")) {
            System.err.println("Don't enter a DELETED value!");
            return;
        }
        // get the hash index based on key
        int index = hash(animal.name);
        int startIndex = index;
        // check if it's occupied, insert if not.
        // if so, probe to next valid location and repeat
        // also stop if we return to the start index
        while (table[index] != null && !table[index].equals("DELETED")) {

            Animal existing = (Animal) table[index];
            if (existing.name.equals(animal.name)) {
                table[index] = animal; // overwrite instead of duplicating
                return;
            }
            // if we find the key again, overwrite value at that position
            index = (index + 1) % table.length;// linear probe

            if (index == startIndex) {
                System.err.println("No empty slot");
                return;
            }
        }
        // insert into table, my new item
        table[index] = animal;
        size++;
    }

    // Lookup
    public Animal get(String name) {
        int index = hash(name);
        int startIndex = index;

        while (table[index] != null) {
            if (!table[index].equals("DELETED")) {
                Animal entry = (Animal) table[index];
                if (entry.name.equals(name)) {
                    return entry;
                }
            }
            index = (index + 1) % table.length;
            if (index == startIndex)
                break;
        }
        return null;
    }

    // Remove
    public boolean remove(String name) {
        int index = hash(name);
        int startIndex = index;

        while (table[index] != null) {
            if (!table[index].equals("DELETED")) {
                Animal entry = (Animal) table[index];
                if (entry.name.equals(name)) {
                    table[index] = "DELETED";
                    size--;
                    return true;
                }
            }
            index = (index + 1) % table.length;
            if (index == startIndex)
                break;
        }
        return false;
    }

    public int size() {
        return size;
    }
}
    
