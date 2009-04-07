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
import javax.persistence.OrderBy;

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
    private Set<Facility> facilities;
    
    public void setFloor(Floor floor) {
    	this.floor = floor;
    }
    
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Floor getFloor() {
    	return floor;
    }
    
    /**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
	@OrderBy("startDateTime")
	public List<Reservation> getReservations() {
		return reservations;
	}

	/**
	 * Gets the administrators for this room
	 * @return the administrators
	 */
	@ManyToMany(mappedBy = "administeredRooms", cascade = CascadeType.ALL)
	@OrderBy("lastName")
	public Set<User> getAdministrators() {
		return administrators;
	}

	/**
	 * @param administrators the administrators to set
	 */
	public void setAdministrators(Set<User> administrators) {
		this.administrators = administrators;
	}

	/**
	 * Adds an administrator
	 * @param user the user to administer this room
	 * @return true if the admin was added, false if this admin already exists
	 */
	public boolean addAdministrator(User user){
		return administrators.add(user);
	}

	/**
	 * Removes an administrator
	 * @param user the admin to remove
	 * @return true if the admin was removed, false if the admin does not exist
	 */
	public boolean removeAdministrator(User user){
		return administrators.remove(user);
	}

	/**
	 * Gets the facilities for this room
	 * @return the facilities
	 */
	@ManyToMany
	@OrderBy("name")
	public Set<Facility> getFacilities() {
		return facilities;
	}

	/**
	 * @param facilitys the facilities to set
	 */
	public void setFacilities(Set<Facility> facilities) {
		this.facilities = facilities;
	}

	/**
	 * Adds an facility
	 * @param user the user to administer this room
	 * @return true if the admin was added, false if this admin already exists
	 */
	public boolean addFacility(Facility facility){
		return facilities.add(facility);
	}

	/**
	 * Removes an facility
	 * @param user the facilities to remove
	 * @return true if the facilities was removed, false if the facilities does not exist
	 */
	public boolean removeFacility(Facility facility){
		return facilities.remove(facility);
	}

	/**
     * Needed for comparison. Compares the value of the id.
     */
    public boolean equals(Object o){
    	if (o != null){
	    	if (o.getClass().equals(Room.class)) {
	    		Room room = (Room)o;
	    		if (room.id != null && (room.id.longValue() == id.longValue())) {
	    			return true;
	    		}
	    	}
    	}
    	return false;
    }
   
    /**
     * Needed for comparison. Returns the hashcode of the id object
     */
    public int hashCode() {
    	if (id != null){
    		return id.hashCode();
    	}
    	return 0;
    }
    
    public String toString() {
    	return "Room: " + name;
    }
}
