package com.jmw.konfman.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * Represents a user in the Konfman application
 * @author judahw
 *
 */
@Entity
public class User extends BaseObject {
    private static final long serialVersionUID = 3257568390917667126L;

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private String adminStatus;
    private Floor defaultFloor;
    private Date birthday;
    
    private Set<Room> administeredRooms;

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}

	@ManyToOne
	public Floor getDefaultFloor() {
		return defaultFloor;
	}

	public void setDefaultFloor(Floor defaultFloor) {
		this.defaultFloor = defaultFloor;
	}

	/**
	 * TODO remove birthday useful for copying date 
	 * @return
	 */
	public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
	 * Gets the rooms that this user can administer
	 * @return the administeredRooms
	 */
	@ManyToMany(cascade=CascadeType.ALL)
    public Set<Room> getAdministeredRooms() {
		return administeredRooms;
	}

	/**
	 * Sets the rooms that this user can administer
	 * @param administeredRooms the administeredRooms to set
	 */
	public void setAdministeredRooms(Set<Room> administeredRooms) {
		this.administeredRooms = administeredRooms;
	}

	/**
	 * Adds a room to the user's set of administered rooms	
	 * @param room the room to add
	 * @return true if it was added false if it already exists
	 */
	public boolean addAdministeredRoom(Room room){
		return this.administeredRooms.add(room);
	}
	
	/**
	 * Removes a room from the user's set of administered rooms
	 * @param room the room to remove
	 * @return true if the room was found and removed
	 */
	public boolean removeAdminsteredRoom(Room room){
		return this.administeredRooms.remove(room);
	}
	
	
	
	/**
     * @return Returns firstName and lastName
     */
    @Transient
	public String getFullName() {
        return lastName + ", " + firstName;
    }
    
    /**
     * Needed for comparison. Compares the value of the id.
     */
    public boolean equals(Object o){
    	if (o.getClass().equals(User.class)){
    		User user = (User)o;
    		if (user.id.longValue() == id.longValue()){
    			return true;
    		}
    	}
    	return false;
    }
   
    /**
     * Needed for comparison. Returns the hashcode of the id object
     */
    public int hashCode(){
    	return id.hashCode();
    }
    
    public String toString(){
    	return firstName + " " + lastName;
    }
}
