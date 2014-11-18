package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.RegisteredUser;

/**
 * Implementation of Item
 * @author Vic Lawson
 *
 */
public class RegisteredUserImpl extends Persistent implements RegisteredUser {
	private String name;
	private String firstName;
	private String lastName;
	private String password;
	private boolean isAdmin;
	private String email;
	private String phone;
	private boolean canText;
	
	// constructor for new objects
	// ??? may want to pass membershipID
	public RegisteredUserImpl (String name, String firstName, String lastName, String passwd, boolean isAdmin, String email, String phone, boolean canText) throws DTException {
 	    this.name = name;
	    this.firstname = firstName;
	    this.lastname = lastName;
		this.password = password;
		this.isAdmin = isAdmin;
		this.email = email;
		this.phone = phone;
		this.canText = canText;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstName() {
		return firstName;
	}
	void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String boolean getCanText() {
		return canText;
	}
	public void setCanText(boolean canText) {
		this.canText = canText;
	}	
	public String toString() {
		return "RegisteredUser[" + getId() + "]: name[" + getName() + "]: Lastname[" + getLastName() + "] Firstname[" + getFirstName() + "] ";
	}
}
