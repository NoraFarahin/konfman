package com.jmw.conference.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;


/**
 * Room definition
 * @author judahw
 *
 */
@Entity
@Table(name = "ROOMS")
@Proxy(lazy=false)
public class Room {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ROOM_ID")
	int id;

	@Column(name="BUILDING_ID")
	int buildingId;
	@Column(name="FLOOR_ID")
	int floorId;
	String name;
	String title;
	String comment;
    String attributes;
    boolean active;
    
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	public Room(){;}
	
	public Room(int building, int floor, String name){
		this.buildingId = building;
		this.floorId = floor;
		this.name = name;
	}
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * The database Id for this room
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * The building in which this room is located
	 * @return the building
	 */
	public int getBuildingId() {
		return buildingId;
	}
	
	/**
	 * The building in which this room is located
	 * @param building the building to set
	 */
	public void setBuildingId(int building) {
		this.buildingId = building;
	}
	/**
	 * The floor on which this room is located
	 * @return the floor
	 */
	public int getFloorId() {
		return floorId;
	}
	
	/**
	 * The floor on which this room is located
	 * @param floor the floor to set
	 */
	public void setFloorId(int floor) {
		this.floorId = floor;
	}
	
	/**
	 * The name of this room
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * The name by which this room is known
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * The attributes of this room, as a comma delimited list.
	 * Some examples are, phone, projector, speaker phone etc.
	 * @return the attributes
	 */
	public String getAttributes() {
		return attributes;
	}

	/**
	 * The attributes of this room, as a comma delimited list.
	 * Some examples are, phone, projector, speaker phone etc.
	 * @param attributes the attributes to set
	 */
	public void setAttributes(String attributes) {
		this.attributes = attributes;
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
	
}
