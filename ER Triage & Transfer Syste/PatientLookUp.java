import java.util.HashMap;

public class PatientLookUp {

    HashMap<String, Patient> lookup = new HashMap<>();

    public PatientLookUp(){

    }

    public void addPatient(Patient patient){

        lookup.put(patient.name, patient);
    }

    public Patient findPatient(String name) {

        return lookup.get(name);
    }

    public Patient removePatient(String name){

        return lookup.remove(name);
    }

    public boolean contains(String name){
        
        return lookup.containsKey(name);
    }
}
