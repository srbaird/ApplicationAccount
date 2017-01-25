
package com.bac.applicationaccount;

import java.util.Date;

/**
 * An account represents a point of access to an individual Application. Applications may
 * have many accounts with differing characteristics. Users share a many-to-many
 * relationship with Accounts.
 * 
 * @author Simon Baird
 */
public interface Account extends AccessByPrimaryKey {

	/**
	 * Set the primary key of the Account. Typically this is an auto generated
	 * value
	 * 
	 * @param id
	 *            Integer value of the primary key
	 */
	void setId(Integer id);

	/**
	 * Get the primary key id of the Application to which the Account controls
	 * access.
	 * 
	 * @return Integer value of the primary key of the Application
	 */
	Integer getApplicationId();

	/**
	 * Define the primary key id of the Application to which the Account
	 * controls
	 * 
	 * @param applicationId
	 *            Integer value of the primary key of the Application
	 */
	void setApplicationId(Integer applicationId);

	String getResourceName();

	void setResourceName(String resourceName);

	/**
	 * Get the value indicating whether the Account is active or not
	 * 
	 * @return a character value representing the active status of the Account
	 */
	Character getActive();

	/**
	 * Set the active status of the Account. The default value is 'N'.
	 * 
	 * @param active a character value representing the active status of the Account
	 */
	void setActive(Character active);

	/**
	 * Get the creation date of the Account
	 * 
	 * @return the date on which the Account was created.
	 */
	Date getCreateDate();

	/**
	 * Set the creation date for the Account
	 * 
	 * @param createDate a Date value representing when the Account was created
	 */
	void setCreateDate(Date createDate);

}
