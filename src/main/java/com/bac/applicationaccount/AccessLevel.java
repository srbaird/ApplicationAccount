
package com.bac.applicationaccount;

/**
 *
 * Class to define a value representing a nominal level of access to another entity
 * 
 * @author Simon Baird
 */
public interface AccessLevel extends AccessByPrimaryKey {
    
	/**
	 * Set the primary key for an entity represented by this class
	 * 
	 * @param id the Integer representing the primary key of this entity
	 */
    void setId(Integer id);
    
    /**
     * Get the description of this access level entitiy
     * 
     * @return void
     */
    String getDescription();
    
    /**
     * Set the desciption of this entity
     * 
     * @param description String representation of the entity
     */
    void setDescription(String description);   

}
