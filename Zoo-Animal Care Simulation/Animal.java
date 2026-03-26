package Lab_8and9;

public class Animal {

    public String name;
    public int careLevel;
    public String species;

    public Animal(String name, String species, int careLevel) {
        this.name = name;
        this.species = species;
        this.careLevel = careLevel;
    }

    public String getFacility() {
        if (careLevel >= 1 && careLevel <= 3)
            return "Basic Care";
        if (careLevel >= 4 && careLevel <= 7)
            return "Advanced Care";
        if (careLevel >= 8 && careLevel <= 10)
            return "Intensive Care";
        return "Unknown";
    }

    @Override
    public String toString() {
        return String.format("Name: %-11s | Species: %-9s | Level: %-2d | Facility: %s", name, species, careLevel, getFacility());
    }

}
