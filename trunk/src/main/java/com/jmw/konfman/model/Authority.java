/**
 * 
 */
package com.jmw.konfman.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;

/**
 * @author judahw
 *
 */
@Entity
public class Authority implements GrantedAuthority{
	private Long id;
	private String role;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	public boolean equals(Object obj){
		if (!obj.getClass().equals(Authority.class)){
			return false;
		}
		Authority authority = (Authority)obj;
		if (this.id.equals(authority.id)){
			return true;
		}
		return false;
	}
	   /**
     * Needed for comparison. Returns the hashcode of the id object
     */
    public int hashCode(){
    	if (id != null){
    		return id.hashCode();
    	}
    	return 0;
    }

	@Transient
	public String getAuthority() {
		return role;
	}

	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
    
}
