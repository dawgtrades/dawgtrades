package edu.uga.dawgtrades.model.impl;

/**
 * Implementation of RegisteredUser
 * @author Vic Lawson
 */

public RegisteredUser
    extends Persistent
    implements Category
{
    private String name;
    private long parentId;

    public RegisteredUser(String name, long parentId) {
	this.userId = Id;
	this.lastName = lname;
	this.firstName = fname;
	this.phone = phone;
	this.email = email;
	this.status = status;
	this.password = password;
	this.isAdmin = admin;
	this.can_text = can_text;
	this.membershipFee = membershipFee;
	}
    public RegisteredUser() {}

    public String getName() {
	return name;
    }
    public void setName(String name) {
	this.name = name;
    }
    public long getParentId() {// this probably needs to be different
	return parentId;
    }
    public void setParentId(long parentId) {
	this.parentId = parentId;
    }
}