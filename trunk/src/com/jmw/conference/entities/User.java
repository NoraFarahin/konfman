/**
 * 
 */
package com.jmw.conference.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * @author judahw
 *
 */
@Entity
@Table(name = "USERS")
@Proxy(lazy=false)
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ID")
	int id;
	String firstName;
	String lastName;
	String password;
	String phone;
	String email;
	int adminStatus;
	
	int defaultFloor;
	
	public User(){;}
	
	public User(int id){
		this.id = id;
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
	private void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the adminStatus
	 */
	public int getAdminStatus() {
		return adminStatus;
	}

	/**
	 * @param adminStatus the adminStatus to set
	 */
	public void setAdminStatus(int adminStatus) {
		this.adminStatus = adminStatus;
	}

	/**
	 * @return the defaultFloor
	 */
	public int getDefaultFloor() {
		return defaultFloor;
	}

	/**
	 * This is the default floor that the user normally schedules his meetings on
	 * @param defaultFloor the defaultFloor to set
	 */
	public void setDefaultFloor(int defaultFloor) {
		this.defaultFloor = defaultFloor;
	}
}
