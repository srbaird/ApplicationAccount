
package com.bac.applicationaccount;

/**
 * A class to represent the system to which access is to be controlled.
 * 
 * @author Simon Baird
 */
public interface Application extends AccessByPrimaryKey {

	/**
	 * Set the primary key id of the Application. Typically this is an auto
	 * generated value
	 * 
	 * @param id
	 *            Integer value of the primary key
	 */
	void setId(Integer id);

	/**
	 * The name associated with the Application. This should be unique to
	 * provide a secondary key.
	 * 
	 * @return a String value uniquely identifying the Application
	 */
	String getName();

	/**
	 * Set the String value uniquely identifying the Application.
	 * 
	 * @param name
	 *            a String value to identify the Application
	 */
	void setName(String name);

	/**
	 * Get the value indicating whether the Application mapping is active or not
	 * 
	 * @return a character value representing the active status of the
	 *         Application
	 */
	Character getActive();

	/**
	 * Set the active status of the Application. The default value is 'N'.
	 * 
	 * @param active
	 *            a character value representing the active status of the
	 *            Application
	 */
	void setActive(Character active);

	/**
	 * Get the value indicating whether the Application is available to accept
	 * Account or User mappings.
	 * 
	 * @return a character value representing the availability status of the
	 *         Application
	 */
	Character getAcceptRegistration();

	/**
	 * Set the mapping availability status of the Application. The default value
	 * is 'N'.
	 * 
	 * @param active
	 *            a character value representing the availability status of the
	 *            Application
	 */
	void setAcceptRegistration(Character acceptRegistration);
}
