package model;

public class Person {
   private String  firstName;
   private String  lastName;
   private String  Address;

   public Person() {
   }

   public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getAddress() {
	return Address;
}

public void setAddress(String address) {
	Address = address;
}

public Person(String firstName, String lastName, String Address) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.Address = Address;
      
   }



   @Override
   public String toString() {
      return firstName + " " + lastName;
   }
}
