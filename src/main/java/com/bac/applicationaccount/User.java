
package com.bac.applicationaccount;

import java.util.Date;
import java.util.Set;

/**
 * A class representing a user which may be authorised to access an Application
 *
 * @author Simon Baird
 */
public interface User extends AccessByPrimaryKey {

	/**
	 * Set the primary key id of the User. Typically this is an auto generated
	 * value
	 * 
	 * @param id
	 *            Integer value of the primary key
	 */
	void setId(Integer id);

	/**
	 * Get the name associated with the User.
	 * 
	 * @return the User name
	 */
	String getUserName();

	/**
	 * Set the name associated with the User.
	 * 
	 * @param userName
	 *            the User name
	 */
	void setUserName(String userName);

	/**
	 * Get the unique character value that uniquely represents this user. This
	 * is likely to be something like an email address or auto generated value.
	 * 
	 * @return the unique User identifier
	 */
	String getUserKey();

	/**
	 * Set the unique character value that uniquely represents this user.
	 * 
	 * @param userKey
	 *            the unique User identifier
	 */
	void setUserKey(String userKey);

	/**
	 * Get the password associate with the User. This should necessarily be in
	 * an encrypted form
	 * 
	 * @return the User password array
	 */
	byte[] getUserPassword();

	/**
	 * Associate a a password with the User which will be used to authenticate
	 * the User.
	 * 
	 * @param userPassword
	 *            the User password array
	 */
	void setUserPassword(byte[] userPassword);

	/**
	 * Get the unique random password value to aid in the encryption of the User
	 * password. This should be unique but is not enforced within the
	 * application.
	 * 
	 * @return the unique password salt value associated with the User
	 */
	byte[] getPasswordSalt();

	/**
	 * Set a password salt value used in encrypting a plaintext password. The
	 * value is random and therefore unique by definition
	 * 
	 * @param pSalt
	 *            the unique password salt value to be associated with the User
	 */
	void setPasswordSalt(byte[] pSalt);

	/**
	 * Get the value indicating whether the User is active or not.
	 * 
	 * @return a character value representing the active status of the User
	 */
	Character getActive();

	/**
	 * Set the active status of the USer. The default value is 'N'.
	 * 
	 * @param active
	 *            a character value representing the active status of the User
	 */
	void setActive(Character active);

	/**
	 * Get the creation date of the User
	 * 
	 * @return the date on which the User was created.
	 */
	Date getCreateDate();

	/**
	 * Set the creation date for the User.
	 * 
	 * @param createDate
	 *            a Date value representing when the User was created
	 */
	void setCreateDate(Date createDate);

	/**
	 * A convenience method to return the User access Accounts.
	 * 
	 * @return the Accounts mapped to the User the the AccountUser mapping.
	 */
	Set<Account> getAccounts();

	/**
	 * Set the Set of Accounts associated with the User. This is not intended to
	 * be used for data manipulation purposes.
	 * 
	 * @return the Accounts mapped to the User the the AccountUser mapping.
	 */
	void setAccounts(Set<Account> accounts);
}
