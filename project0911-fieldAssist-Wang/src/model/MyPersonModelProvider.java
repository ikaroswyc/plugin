package model;

import java.util.ArrayList;
import java.util.List;

public enum MyPersonModelProvider {
   INSTANCE;

   private List<MyPerson> persons;

   private MyPersonModelProvider() {
      persons = new ArrayList<MyPerson>();
      persons.add(new MyPerson("AFirstName1", "ALastName1", "female", true));
      persons.add(new MyPerson("CFirstName1", "CLastName1", "female", true));
      persons.add(new MyPerson("DFirstName1", "DLastName1", "male", true));
      persons.add(new MyPerson("BFirstName1", "BLastName1", "female", true));
      persons.add(new MyPerson("AFirstName3", "ALastName3", "female", true));
      persons.add(new MyPerson("CFirstName3", "CLastName3", "female", true));
      persons.add(new MyPerson("DFirstName3", "DLastName3", "male", true));
      persons.add(new MyPerson("BFirstName3", "BLastName3", "female", true));
      persons.add(new MyPerson("AFirstName2", "ALastName2", "male", false));
      persons.add(new MyPerson("CFirstName2", "CLastName2", "female", true));
      persons.add(new MyPerson("DFirstName2", "DLastName2", "female", true));
      persons.add(new MyPerson("BFirstName2", "BLastName2", "female", true));
   }

   public List<MyPerson> getPersons() {
      return persons;
   }
}
