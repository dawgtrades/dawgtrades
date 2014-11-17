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
	private long userId;
	private float rating;
	private String report;
	private Date date;
    private long reviewer;
    private long reviewed;
	
	// Report object is new
	public ExperienceReportImpl (RegisteredUser user,float rating,
	String report,Date date,long reviewer,long reviewed) throws DTException {
		super(-1);
		if (user == null)
			throw new DTException("The user related to the ExperienceReport is null");
		if (!user.isPersistent())
			throw new DTException("The user related to the ExperienceReport is not persistent");
		this.userId = user.getId();
		this.rating = rating;
		this.report = report;
		this.date = date;
		this.reviewer = reviewer;
		this.reviewed = reviewed;
	}
	
	// Report object exists
	public ExperienceReportImpl (long userId, float rating,
	String report,Date date,long reviewer,long reviewed) {
		this.userId = userId;
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
	public RegisteredUser getReviewer() {
		return reviewer;
	}
	public void setReviewer( RegisteredUser reviewer ) {
		this.reviewer = reviewer;
	}
	public RegisteredUser getReviewed() {
		return reviewed;
	}
	public void setReviewed( RegisteredUser reviewed ) {
		this.reviewed = reviewed;
	}
	public String toString() {
		return "ExperienceReport[" + getId() + "]: Reviewer[" + getReviewer() + "] Reviewed[$" + getReviewed() + "] date[$" + getDate() + "] Report[" + getReport();
	}

}
