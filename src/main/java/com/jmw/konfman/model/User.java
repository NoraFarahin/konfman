/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.jmw.konfman.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

/**
 * Represents a user in the Konfman application
 * @author judahw
 *
 */
@Entity
@Table(name="Users")
public class User extends BaseObject implements UserDetails{
    private static final long serialVersionUID = 3257568390917667126L;

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private String verifyPassword;
    
    private String adminStatus;
    private Floor defaultFloor;
    private Date birthday;
    private String username;
    private boolean enabled = true;
    
    private Set<Authority> roles = new HashSet<Authority>();  
	private Set<Room> administeredRooms = new HashSet<Room>();
    private List<Reservation> reservations;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled value to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = true;
	}

	/**
	 * Gets the rooms that this user can administer
	 * @return the administeredRooms
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	@OrderBy("name")
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
    public boolean equals(Object o) {
    	if (o != null){
	    	if (o.getClass().equals(User.class)) {
	    		User user = (User)o;
	    		if (user.id != null && (user.id.longValue() == id.longValue())) {
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
    	return firstName + " " + lastName;
    }

    /**
	 * @return the reservations
	 */
	@OneToMany(mappedBy = "user")
	@OrderBy("startDateTime")
    public List<Reservation> getReservations() {
		return reservations;
	}

	/**
	 * @param reservations the reservations to set
	 */
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

    /**
	 * Gets the roles that this user is assigned to. 
	 * Must be eager fetched so that the user object can be used for security
	 * outside of the original transaction.   
	 * @return the authorities
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	public Set<Authority> getRoles() {
		return roles;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setRoles(Set<Authority> roles) {
		this.roles = roles;
	}
	
	public boolean addRoll(Authority authority){
		return roles.add(authority);
	}
	
	public boolean removeRoll(Authority authority){
		return roles.remove(authority);
	}

	@Transient
	public GrantedAuthority[] getAuthorities() {
		GrantedAuthority[] grantedAuthorities = new GrantedAuthority[0]; 
		return roles.toArray(grantedAuthorities);
	}

	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return enabled;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Sets variable to contain the confirmation password during a password change. 
	 * @param verifyPassword the verifyPassword to set
	 */
	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}

	/**
	 * Gets variable containing the confirmation password during a password change. 
	 * @return the verifyPassword
	 */
	@Transient
	public String getVerifyPassword() {
		if (verifyPassword == null){
			return password;
		}
		return verifyPassword;
	}
}
