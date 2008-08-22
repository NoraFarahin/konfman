package com.jmw.konfman.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * Represents a room in the Konfman application
 * @author judahw
 *
 */
@Entity
public class Reservation extends BaseObject {
	
	private static final long serialVersionUID = -6541523687944328507L;
	private Long id;
    private String comment;
    private Date startDateTime;
    private Date endDateTime;
    private Room room;
    private User user;
    @Transient 
    private String date;
    @Transient
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
    @Transient
    DateFormat tf = new SimpleDateFormat("h:mm aa");
    @Transient
    DateFormat shortdf = new SimpleDateFormat("MM/dd/yyyy");
   
    /**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param startDateTime the startDateTime to set
	 */
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	/**
	 * @return the startDateTime
	 */
	public Date getStartDateTime() {
		return startDateTime;
	}

	/**
	 * @param endDateTime the endDateTime to set
	 */
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	/**
	 * @return the endDateTime
	 */
	public Date getEndDateTime() {
		return endDateTime;
	}

    public void setRoom(Room room){
    	this.room = room;
    }
    
    @ManyToOne
    public Room getRoom(){
    	return room;
    }

    /**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the user
	 */
    @ManyToOne
	public User getUser() {
		return user;
	}
    
    @Transient
    public void setDate(Date date){
    	startDateTime = date;
    	endDateTime = date;
    }
    
    @Transient
    public void setDate(String date){
    	this.date = date;
    }
    
    @Transient
    public String getDate(){
    	if (startDateTime == null){
    		return "";
    	}
    	return shortdf.format(startDateTime);
    }
    
    @Transient
    public void setStartTime(String time) throws Exception{
    	startDateTime = df.parse(date + " " + time);
    }
    
    @Transient    
    public String getStartTime(){
    	if (startDateTime == null){
    		return "";
    	}
    	return tf.format(startDateTime);
    }
    
    @Transient
    public String getEndTime(){
    	if (endDateTime == null){
    		return "";
    	}
    	return tf.format(endDateTime);
    }
        
    @Transient
    public void setEndTime(String time) throws Exception{
    	endDateTime = df.parse(date + " " + time);
    }
    
    public String toString(){
    	return "Reservation: " + comment;
    }
}
