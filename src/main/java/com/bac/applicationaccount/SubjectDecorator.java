package com.bac.applicationaccount;

import javax.security.auth.Subject;

public interface SubjectDecorator {

	/*
	 * Add further detail to the authentication Subject
	 */
	Subject addSubjectDetail(Subject subject);
}
