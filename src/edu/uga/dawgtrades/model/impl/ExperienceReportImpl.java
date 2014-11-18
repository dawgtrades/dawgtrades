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
    private long reviewer;
    private long reviewed;
	
	// Report object is new
	public ExperienceReportImpl (float rating, String report, Date date, RegisteredUser reviewer, RegisteredUser reviewed) throws DTException {
		super(-1);
		if (rreviewer == null)
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
		this.reviewer = reviewer.getId();
		this.reviewed = reviewed.getId();
	}
	
	// Report object exists
	public ExperienceReportImpl (float rating,
	String report,Date date,long reviewer,long reviewed) {
	    super(-1);
		this.rating = rating;
		this.report = report;
		this.date = date;
		this.reviewer = reviewer;
		this.reviewed = reviewed;
	}
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
	    this.reviewer = reviewer.getId();
	}
    public RegisteredUser getReviewed() {//ALSO NEEDS TO RETURN USER OBJ
		return reviewed;
	}
	public void setReviewed( RegisteredUser reviewed ) {
	    this.reviewed = reviewed.getId();
	}
	public String toString() {
		return "ExperienceReport[" + getId() + "]: Reviewer[" + getReviewer() + "] Reviewed[$" + getReviewed() + "] date[$" + getDate() + "] Report[" + getReport();
	}

}
