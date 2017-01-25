
package com.bac.applicationaccount;

/**
 * 
 * Interface to represent entities which are represented by a unique Integer id
 *
 * @author Simon Baird
 */
public interface AccessByPrimaryKey extends ApplicationAccountEntity{

	/**
	 * Primary key is assumed to be an auto generated Integer value
	 * 
	 * @return integer representing the primary key of the enitity
	 */
    Integer getId();
}
