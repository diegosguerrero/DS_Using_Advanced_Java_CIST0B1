package Lab_8and9;

public class BasicHashTable {

    private Object[] table;
    private int size;

    public BasicHashTable(int capacity) {
        table = new Object[capacity];
        size = 0;
    }

    // HashFunction
    private int hash(String key) {
        int hashValue = 0;
        for (int i = 0; i < key.length(); i++) {
            hashValue += key.charAt(i);
        }
        return Math.abs(hashValue % table.length);
    }

    // Add
    public void put(String key, Object value) {
        // check if there's room available
        if (size == table.length) {
            System.err.println("Hash table full");
            return;
        }
        if (value.equals("DELETED")) {
            System.err.println("Don't enter a DELETED value!");
            return;
        }
        // get the hash index based on key
        int index = hash(key);
        int startIndex = index;
        // check if it's occupied, insert if not.
        // if so, probe to next valid location and repeat
        // also stop if we return to the start index
        while (table[index] != null && !table[index].equals("DELETED")) {

            HashTableEntry existing = (HashTableEntry) table[index];
            if (existing.key.equals(key)) {
                existing.value = value; // overwrite instead of duplicating
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
        table[index] = new HashTableEntry(key, value);
        size++;
    }

    // Lookup
    public Object get(String key) {
        int index = hash(key);
        int startIndex = index;

        while (table[index] != null) {
            if (!table[index].equals("DELETED")) {
                HashTableEntry entry = (HashTableEntry) table[index];
                if (entry.key.equals(key)) {
                    return entry.value;
                }
            }
            index = (index + 1) % table.length;
            if (index == startIndex)
                break;
        }
        return null;
    }

    // Remove
    public boolean remove(String key) {
        int index = hash(key);
        int startIndex = index;

        while (table[index] != null) {
            if (!table[index].equals("DELETED")) {
                HashTableEntry entry = (HashTableEntry) table[index];
                if (entry.key.equals(key)) {
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
