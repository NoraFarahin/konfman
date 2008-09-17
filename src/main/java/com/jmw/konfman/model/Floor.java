package com.jmw.konfman.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

/**
 * Represents a building in the Konfman application
 * @author judahw
 *
 */
@Entity
public class Floor extends BaseObject {
	private static final long serialVersionUID = 1874567676794791339L;

    private Long id;
    private String name;
    private String title;
    private String comment;
    private boolean active;
    //private Long buildingId = new Long(0);
	
    private Building building = null;
    
    private List<Room> rooms; 
    
    public void setBuilding(Building b){
    	building = b;
    }
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Building getBuilding(){
    	return building;
    }
    
    /**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	 * @return the floors
	 */
	@OneToMany(mappedBy = "floor", cascade = CascadeType.ALL)
	@OrderBy("name")
	public List<Room> getRooms() {
		return rooms;
	}
	/**
	 * @param floors the floors to set
	 */
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public String toString(){
		return "Floor: " + name;
	}
	
	/**
	 * Supplies the floor name and the name of the building that it is in
	 * @return the full name of the floor
	 */
	@Transient
	public String getFullName() {
		if (building != null){
			return building.getName() + " : " + name;
		}
		return ("No Building : " + name);
	}
}
