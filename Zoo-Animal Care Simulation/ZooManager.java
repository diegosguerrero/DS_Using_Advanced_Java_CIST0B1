package Lab_8and9;

import java.util.ArrayList;
import java.util.Scanner;

public class ZooManager {

    AnimalHashTable animalHash = new AnimalHashTable(100);
    CareBinarySearchTree careBinary = new CareBinarySearchTree();

    Animal animal1 = new Animal("Pedro", "Zebra", 5);
    Animal animal2 = new Animal("Juanse", "Cocodrile", 3);
    Animal animal3 = new Animal("Manolo", "Lion", 7);
    Animal animal4 = new Animal("Dumbo", "Jiraffee", 9);
    Animal animal5 = new Animal("Carlitos", "Hippo", 8);
    Animal animal6 = new Animal("Simba", "Hiena", 4);
    Animal animal7 = new Animal("Manuel", "Kangaroo", 3);
    Animal animal8 = new Animal("Leonidas", "Capibara", 10);
    Animal animal9 = new Animal("Pejelagarto", "Gorilla", 1);
    Animal animal10 = new Animal("Alejandra", "Tiger", 2);

    public void addAnimal(Animal animal) {
        animalHash.put(animal);
        careBinary.insert(animal);
    }

    public void simulatingCareNeeds() {
        System.out.println("Animals loading....");
        System.out.println("10 Animals were successfully loaded!!\n");
        System.out.println("=== Care Level Increased ===");
        System.out.println("Increasing Care Level For Some Animals...");

        String[] names = { "Simba", "Manuel", "Manolo" };

        for (int i = 0; i < names.length; i++) {
            Animal animal = animalHash.get(names[i]);

            if (animal != null && animal.careLevel < 10) {
                System.out.printf("(Before) %-6s | %-8s | Old Level: %-2d | %s%n", animal.name, animal.species,
                        animal.careLevel, animal.getFacility());

                careBinary.remove(animal.name, animal.careLevel);

                animal.careLevel += 1;

                careBinary.insert(animal);

                animalHash.put(animal);

                System.out.printf("(After)  %-6s | %-8s | New Level: %-2d | %s%n", animal.name, animal.species,
                        animal.careLevel, animal.getFacility());
                System.out.println("");
            }
        }

        System.out.println("=== Basic Care (1-3) ===");
        ArrayList<Animal> basic = careBinary.getRange(1, 3);
        for (int i = 0; i < basic.size(); i++) {
            System.out.println(basic.get(i));
        }

        System.out.println("\n=== Advanced Care (4-7) ===");
        ArrayList<Animal> advanced = careBinary.getRange(4, 7);
        for (int i = 0; i < advanced.size(); i++) {
            System.out.println(advanced.get(i));
        }

        System.out.println("\n=== Intensive Care (8-10) ===");
        ArrayList<Animal> intensive = careBinary.getRange(8, 10);
        for (int i = 0; i < intensive.size(); i++) {
            System.out.println(intensive.get(i));
        }
    }

    public static void main(String[] args) {
        ZooManager zoo = new ZooManager();
        java.util.Scanner input = new java.util.Scanner(System.in);

        zoo.addAnimal(zoo.animal1);
        zoo.addAnimal(zoo.animal2);
        zoo.addAnimal(zoo.animal3);
        zoo.addAnimal(zoo.animal4);
        zoo.addAnimal(zoo.animal5);
        zoo.addAnimal(zoo.animal6);
        zoo.addAnimal(zoo.animal7);
        zoo.addAnimal(zoo.animal8);
        zoo.addAnimal(zoo.animal9);
        zoo.addAnimal(zoo.animal10);
        zoo.simulatingCareNeeds();

        // Searching for an animal
        System.out.println("");
        System.out.print("Which animal do you want to search for? (name) ");
        String searchName = input.nextLine();
        Animal found = zoo.animalHash.get(searchName);
        System.out.println("");
        System.out.println("SEARCH RESULT FOR:");
        System.out.println(found);

        // Deleting an animal
        System.out.println("");
        System.out.print("Which animal do you want to remove? (name) ");
        String removeName = input.nextLine();
        Animal toRemove = zoo.animalHash.get(removeName);

        if (toRemove != null) {
            zoo.animalHash.remove(toRemove.name);
            zoo.careBinary.remove(toRemove.name, toRemove.careLevel);
            System.out.println("");
            System.out.println("ANIMAL REMOVED:");
            System.out.println(toRemove);
        }

        // Increment care level
        System.out.println("");
        System.out.print("Which animal's care level do you want to increase? ");
        String incrementName = input.nextLine();
        Animal toIncrement = zoo.animalHash.get(incrementName);

        if (toIncrement != null) {

            zoo.careBinary.remove(toIncrement.name, toIncrement.careLevel);

            toIncrement.careLevel += 1;

            zoo.careBinary.insert(toIncrement);

            zoo.animalHash.put(toIncrement);
            System.out.println("");
            System.out.println("CARE LEVEL UPDATED FOR:");
            System.out.println(toIncrement);
        }

        // Facility queries
        System.out.println("");
        System.out.print("[CURRENT FACILITY AND ANIMALS]");
        System.out.println("\n=== Basic Care (1-3) ===");

        for (Animal a : zoo.careBinary.getRange(1, 3)) {
            System.out.println(a);
        }

        System.out.println("\n=== Advanced Care (4-7) ===");

        for (Animal a : zoo.careBinary.getRange(4, 7)) {
            System.out.println(a);
        }

        System.out.println("\n=== Intensive Care (8-10) ===");

        for (Animal a : zoo.careBinary.getRange(8, 10)) {
            System.out.println(a);
        }
        input.close();
    }
}
