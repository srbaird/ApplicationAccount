/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bac.applicationaccount.hibernate;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bac.applicationaccount.AccessLevel;
import com.bac.applicationaccount.AccountUser;
import com.bac.applicationaccount.ApplicationAccountEntity;
import com.bac.applicationaccount.impl.SimpleComponentFactory;

/**
 *
 * @author user0001
 */
@Entity
@Table(name = "access_level")
@NamedQueries({ @NamedQuery(name = "AccessLevel.all", query = "SELECT l FROM HibernateAccessLevel l") })
public class HibernateAccessLevel implements AccessLevel, ApplicationAccountEntityProxy {

	private static final long serialVersionUID = 698788636930694L;
	private final String allQueryName = "AccessLevel.all";
	private AccessLevel delegate;
	// logger
	// private static final Logger logger =
	// LoggerFactory.getLogger(HibernateAccessLevel.class);

	public HibernateAccessLevel() {
	}

	public HibernateAccessLevel(AccessLevel delegate) {

		this.delegate = delegate;
	}

	@Transient
	@Override
	public AccessLevel getDelegate() {

		return delegate;
	}

	@Override
	public void setDelegate(ApplicationAccountEntity delegate) {
		this.delegate = (AccessLevel) delegate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Integer getId() {
		return getProxyDelegate().getId();
	}

	@Override
	public void setId(Integer id) {

		checkId(id);
		getProxyDelegate().setId(id);
	}

	private void checkId(Integer id) {

		final Integer localId = getProxyDelegate().getId();
		if (localId != null && localId != id) {
			throw new IllegalArgumentException("Id is already set");
		}
	}

	@Override
	public String getDescription() {

		return getProxyDelegate().getDescription();
	}

	@Override
	public void setDescription(String description) {

		getProxyDelegate().setDescription(description);
	}

	@Transient
	@Override
	public String getAllQueryName() {

		return allQueryName;
	}

	@Transient
	private AccessLevel getProxyDelegate() {

		if (delegate == null) {
			delegate = SimpleComponentFactory.getAccessLevel();
		}
		return delegate;
	}
	//
	// Mapping for referential integrity purposes only
	//
	@OneToMany(mappedBy = "accessLevelId", targetEntity = HibernateAccountUser.class)
	public Set<AccountUser> getAccountUsers() {
		
		return Collections.emptySet();
	}

	public void setAccountUsers(Set<AccountUser> accountUsers) {

		// Do nothing
	}
}
