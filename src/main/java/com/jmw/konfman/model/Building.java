package com.jmw.konfman.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 * Represents a building in the Konfman application
 * @author judahw
 *
 */
@Entity
public class Building extends BaseObject {
    private static final long serialVersionUID = 3257568390917667126L;

    private Long id;
    private String name;
    private String title;
    private String comment;
    private boolean active;
    
    private List<Floor> floors;
	
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
	@OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
	@OrderBy("name")
	public List<Floor> getFloors() {
		return floors;
	}
	/**
	 * @param floors the floors to set
	 */
	public void setFloors(List<Floor> floors) {
		this.floors = floors;
	}

	public String toString(){
		return "Building: " + name;
	}

}
