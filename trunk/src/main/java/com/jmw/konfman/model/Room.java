package com.jmw.konfman.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Represents a room in the Konfman application
 * @author judahw
 *
 */
@Entity
public class Room extends BaseObject {
	
	private static final long serialVersionUID = 7574865458906566122L;
	
	private Long id;
    private String name;
    private String title;
    private String comment;
    private String attributes;
    private boolean active;
    private Floor floor = null;
    private List<Reservation> reservations; 
    private Set<User> administrators;
    
    public void setFloor(Floor floor){
    	this.floor = floor;
    }
    
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Floor getFloor(){
    	return floor;
    }
    
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the attributes
	 */
	public String getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}


	/**
	 * @param reservations the reservations to set
	 */
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}


	/**
	 * @return the reservations
	 */
	@OneToMany(mappedBy="room", cascade=CascadeType.ALL)
	public List<Reservation> getReservations() {
		return reservations;
	}

	/**
	 * Gets the administrators for this room
	 * @return the administrators
	 */
	@ManyToMany(mappedBy="administeredRooms", cascade=CascadeType.ALL)
	public Set<User> getAdministrators() {
		return administrators;
	}

	/**
	 * @param administrators the administrators to set
	 */
	public void setAdministrators(Set<User> administrators) {
		this.administrators = administrators;
	}

	public void addAdministrator(User user){
		administrators.add(user);
	}

	public void removeAdministrator(User user){
		administrators.remove(user);
	}

	public boolean equals(Object o){
    	if (o.getClass().equals(Room.class)){
    		Room room = (Room)o;
    		if (room.id.longValue() == id.longValue()){
    			return true;
    		}
    	}
    	return false;
    }
   
    public int hashCode(){
    	return id.hashCode();
    }
    
    public String toString(){
    	return "Room: " + name;
    }
}
