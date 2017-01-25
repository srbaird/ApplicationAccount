package com.bac.applicationaccount;

import javax.security.auth.Subject;

/**
 * A class representing an object that can add specific value to a supplied
 * Subject.
 * 
 * @author Simon Baird
 *
 */
public interface SubjectDecorator {

	/**
	 * Enhance the content of a supplied Subject.
	 * 
	 * @param subject
	 *            the Subject which requires enhancement
	 * @return the Subject parameter with additional information.
	 */
	Subject addSubjectDetail(Subject subject);
}
