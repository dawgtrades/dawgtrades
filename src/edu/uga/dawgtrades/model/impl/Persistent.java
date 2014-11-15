package edu.uga.dawgtrades.model.impl;

import edu.uga.

public abstract class Persistent {
    private long id;

    public Persistent(long id) {
	this.id = id;
    }
    public Persistent() {
	this.id = -1;
    }

    public long getId() {
	return id;
    }
    public void setId(long id) {
	if(id > 0)
	    this.id = id;
    }

}