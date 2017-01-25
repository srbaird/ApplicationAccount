package com.bac.applicationaccount;

import javax.security.auth.Subject;

/**
 * The class allows for the construction of a chain of SubjectDecorators to
 * modify the content of a Subject.
 * 
 */
public abstract class AbstractSubjectDecorator implements SubjectDecorator {

	private final SubjectDecorator decorator;

	/**
	 * Constructor for a single modification only.
	 */
	public AbstractSubjectDecorator() {

		super();
		decorator = null;
	}

	/**
	 * Instantiate with a further SubjectDecorator in the chain.
	 * 
	 * @param decorator
	 *            the next SubjectDecorator to implement further modification
	 */
	public AbstractSubjectDecorator(SubjectDecorator decorator) {
		super();
		this.decorator = decorator;
	}

	/**
	 * Execute the decoration method and invoke the equivalent method on any
	 * subsequent Decorators supplied in the constructor. The output from the
	 * decorate method of this object is passed as the input parameter to the
	 * next SubjectDecorator
	 * 
	 * @param subject
	 *            the Subject to decorate
	 * @return the supplied Subject with additional content
	 */
	@Override
	public Subject addSubjectDetail(Subject subject) {

		return decorator == null ? decorate(subject) : decorator.addSubjectDetail(subject);
	}

	/**
	 * Perform the additional population of the supplied Subject
	 * 
	 * @param subject
	 *            the Subject to decorate
	 * @return the supplied Subject with additional content
	 */
	public abstract Subject decorate(Subject subject);

}
