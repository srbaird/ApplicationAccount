/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bac.applicationaccount.data.hibernate;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Query;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.bac.applicationaccount.data.Account;
import com.bac.applicationaccount.data.Application;
import com.bac.applicationaccount.data.ApplicationAccountEntity;
import com.bac.applicationaccount.data.SimpleComponentFactory;

/**
 *
 * @author user0001
 */
@Entity
@Table(name = "application")
@NamedQueries({ @NamedQuery(name = "Application.all", query = "SELECT a FROM HibernateApplication a"),
		@NamedQuery(name = "Application.byName", query = "SELECT a FROM HibernateApplication a WHERE a.name=:name") })
public class HibernateApplication implements Application, ApplicationAccountEntityProxy, ProxyHasSecondaryKey {

	private static final long serialVersionUID = 698788636930697L;
	//
	private final String allQueryName = "Application.all";
	private final String secondaryKeyQueryName = "Application.byName";
	private final String applicationNameKeyParamName = "name";

	private Application delegate;

	public HibernateApplication() {
	}

	public HibernateApplication(Application delegate) {

		this.delegate = delegate;
	}

	@Transient
	@Override
	public Application getDelegate() {

		return delegate;
	}

	@Override
	public void setDelegate(ApplicationAccountEntity delegate) {
		this.delegate = (Application) delegate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Integer getId() {
		return delegate == null ? null : delegate.getId();
	}

	@Override
	public void setId(Integer id) {

		getProxyDelegate().setId(id);
	}

	@Override
	@Column(unique=true)
	public String getName() {

		return delegate == null ? null : delegate.getName();
	}

	@Override
	public void setName(String name) {

		getProxyDelegate().setName(name);

	}

	@Transient
	private Application getProxyDelegate() {

		if (delegate == null) {
			delegate = SimpleComponentFactory.getApplication();
		}
		return delegate;
	}

	@Override
	public Character getActive() {

		return delegate == null ? null : delegate.getActive();
	}

	@Override
	public void setActive(Character active) {

		getProxyDelegate().setActive(active);
	}

	@Override
	@Column(name = "accept_registration")
	public Character getAcceptRegistration() {

		return delegate == null ? null : delegate.getAcceptRegistration();
	}

	@Override
	public void setAcceptRegistration(Character acceptRegistration) {

		getProxyDelegate().setAcceptRegistration(acceptRegistration);
	}

	@Transient
	@Override
	public String getAllQueryName() {

		return allQueryName;
	}

	@Transient
	@Override
	public String getSecondaryKeyQueryName() {

		return secondaryKeyQueryName;
	}

	@Override
	public void setSecondaryKeyQuery(Query query) {

		query.setParameter(applicationNameKeyParamName, getName());
	}

	/*
	 * List of accounts. Not required by Application interface but may be useful
	 * in the future. Included here to enforce foreign key delete
	 * 
	 */
	@OneToMany(mappedBy = "applicationId", targetEntity = HibernateAccount.class)
	@OnDelete(action = OnDeleteAction.CASCADE)
	public Set<Account> getAccounts() {
		
		return Collections.emptySet();
	}

	public void setAccounts(Set<Account> accounts) {

		// Do nothing
	}

}
