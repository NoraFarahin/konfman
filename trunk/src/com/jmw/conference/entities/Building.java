package com.jmw.conference.entities;

import java.util.*;
import javax.persistence.*;

import org.hibernate.annotations.Proxy;
@Entity
@Table(name = "BUILDINGS")
@Proxy(lazy=false)
public class Building {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="BUILDING_ID")
	int id;
	@Column(name="NAME")
	String name;
	@Column(name="TITLE")
	String title;
	@Column(name="COMMENT")
	String comment;
	@Column(name="ACTIVE")
	boolean active;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="BUILDING_ID")
	Set<Floor> floors = new HashSet<Floor>();
	
	public Building(){;}
	
	public Building(String name){
		this.name = name;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
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
	 * @return the floors
	 */
	public Set<Floor> getFloors() {
		return floors;
	}
	/**
	 * @param floors the floors to set
	 */
	public void setFloors(Set<Floor> floors) {
		this.floors = floors;
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
	
}
