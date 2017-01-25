
package com.bac.applicationaccount;

import java.util.Date;

/**
 * A Class to represent the many-to-many User to Account mapping as well as
 * controlling the AccessLevel of the association.
 *
 * @author Simon Baird
 */
public interface AccountUser extends ApplicationAccountEntity {

	/**
	 * Set the primary key id of the Account side of the mapping. When combined
	 * with the User id the combination must be unique
	 * 
	 * @param id
	 *            an Integer value representing the Account primary key id
	 */
	void setAccountId(Integer id);

	/**
	 * Get the primary key id of the Account side of the mapping.
	 * 
	 * @return an Integer value representing the Account primary key id
	 */
	Integer getAccountId();

	/**
	 * Set the primary key id of the User side of the mapping. This should be
	 * unique in combination with the Account id.
	 * 
	 * @param id
	 *            an Integer value representing the User primary key id
	 */
	void setUserId(Integer id);

	/**
	 * Get the primary key id of the User side of the mapping.
	 * 
	 * @return an Integer value representing the User primary key id
	 */
	Integer getUserId();

	/**
	 * Get the value indicating whether the AccountUser mapping is active or not
	 * 
	 * @return a character value representing the active status of the
	 *         AccountUser
	 */
	Character getActive();

	/**
	 * Set the active status of the AccountUser mapping. The default value is
	 * 'N'.
	 * 
	 * @param active
	 *            a character value representing the active status of the
	 *            AccountUser
	 */
	void setActive(Character active);

	/**
	 * Get the creation date of the AccountUser mapping.
	 * 
	 * @return the date on which the AccountUser was created.
	 */
	Date getCreateDate();

	/**
	 * Set the creation date for the AccountUser mapping.
	 * 
	 * @param createDate
	 *            a Date value representing when the AccountUser mapping was
	 *            created
	 */
	void setCreateDate(Date createDate);

	/**
	 * Get the date of the last AccountUser mapping access.
	 * 
	 * @return the date on which the AccountUser was last accessed.
	 */
	Date getLastAccessDate();

	/**
	 * Set the last access date for the AccountUser mapping.
	 * 
	 * @param createDate
	 *            a Date value representing when the AccountUser mapping was
	 *            last accessed
	 * 
	 */
	void setLastAccessDate(Date createDate);

	/**
	 * Set the primary key id of the AccessLevel associated with this mapping.
	 * 
	 * @return an Integer value representing the AccessLevel primary key id
	 */
	void setAccessLevelId(Integer id);

	/**
	 * Get the primary key id of the AccessLevel associated with this mapping.
	 * 
	 * @return an Integer value representing the AccessLevel primary key id
	 */
	Integer getAccessLevelId();

	/**
	 * Get an optional message that may be associated with the mapping.
	 * 
	 * @return the message
	 */
	String getAccountMessage();

	/**
	 * Set an optional message that may be associated with the mapping.
	 * 
	 * @param msg
	 *            the message
	 */
	void setAccountMessage(String msg);
}
