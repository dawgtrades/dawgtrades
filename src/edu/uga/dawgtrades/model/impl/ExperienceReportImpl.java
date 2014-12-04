package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Persistable;
import edu.uga.dawgtrades.model.RegisteredUser;

/**
 * Implementation of ExperienceReport
 * @author Vic Lawson
 *
 */

//NOT WORKING UNTIL USER IS WORKING

public class ExperienceReportImpl extends Persistent implements ExperienceReport {
	private int rating;
	private String report;
	private Date date;
    private RegisteredUser reviewer;
    private RegisteredUser reviewed;
	
	// Report object is new
	public ExperienceReportImpl (int rating, String report, Date date, RegisteredUser reviewer, RegisteredUser reviewed) throws DTException {
		super(-1);
		if (reviewer == null)
			throw new DTException("Reviewer is null");
		if(reviewed == null)
		    throw new DTException("Reviewed is null");
		if (!reviewer.isPersistent())
			throw new DTException("Reviewer is not persistent");
		if(!reviewed.isPersistent())
		    throw new DTException("Reviewed is not persistent");
		this.rating = rating;
		this.report = report;
		this.date = date;
		this.reviewer = reviewer;
		this.reviewed = reviewed;
	}
	
	/** Report object exists, DON"T THINK WE NEED THIS
	public ExperienceReportImpl (int rating,
	String report,Date date,long reviewer,long reviewed) {
	    super(-1);
		this.rating = rating;
		this.report = report;
		this.date = date;
		this.reviewer = reviewer;
		this.reviewed = reviewed;
	}
	**/
	public int getRating() {
		return rating;
	}
	public void setRating( int rating ) throws DTException {
		this.rating = rating;
	}
	public String getReport() {
		return report;
	}
	public void setReport( String report ) {
		this.report = report;
	}
	public Date getDate() {
		return date;
	}
	public void setDate( Date date ) {
		this.date = date;
	}
    public RegisteredUser getReviewer() {//NEEDS TO RETURN USER OBJ
		return reviewer;
	}
	public void setReviewer( RegisteredUser reviewer ) {
	    this.reviewer = reviewer;
	}
    public RegisteredUser getReviewed() {//ALSO NEEDS TO RETURN USER OBJ
		return reviewed;
	}
	public void setReviewed( RegisteredUser reviewed ) {
	    this.reviewed = reviewed;
	}
	public String toString() {
		String result = "ExperienceReport[" + getId() + "]: date[" + getDate() + "] Report[" + getReport() + "]";
		result += "\n     Reviewer: " + getReviewer().toString();
		result += "\n     Reviewed: " + getReviewed().toString();
		return result;
	}

}
