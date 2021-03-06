/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bac.applicationaccount.hibernate;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.bac.applicationaccount.Account;
import com.bac.applicationaccount.AccountUser;
import com.bac.applicationaccount.Application;
import com.bac.applicationaccount.ApplicationAccountEntity;
import com.bac.applicationaccount.impl.SimpleComponentFactory;

/**
 *
 * @author user0001
 */
@Entity
@Table(name = "account")
@NamedQueries({ @NamedQuery(name = "Account.all", query = "SELECT a FROM HibernateAccount a") })
public class HibernateAccount implements Account, ApplicationAccountEntityProxy {

	private static final long serialVersionUID = 698788636930695L;
	private final String allQueryName = "Account.all";
	private Account delegate;
	private Application application;

	public HibernateAccount() {
	}

	public HibernateAccount(Account delegate) {

		this.delegate = delegate;
	}

	@Transient
	@Override
	public Account getDelegate() {

		return delegate;
	}

	@Override
	public void setDelegate(ApplicationAccountEntity delegate) {

		checkId(((Account) delegate).getId());
		this.delegate = (Account) delegate;
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

	@Column(name = "application_id", nullable = false)
	@Override
	public Integer getApplicationId() {

		return getProxyDelegate().getApplicationId();
	}

	@Override
	public void setApplicationId(Integer applicationId) {

		getProxyDelegate().setApplicationId(applicationId);
	}

	@Column(name = "resource_name")
	@Override
	public String getResourceName() {

		return getProxyDelegate().getResourceName();
	}

	@Override
	public void setResourceName(String resourceName) {

		getProxyDelegate().setResourceName(resourceName);

	}

	@Override
	@Column
	public Character getActive() {

		return getProxyDelegate().getActive();
	}

	@Override
	public void setActive(Character active) {

		getProxyDelegate().setActive(active);
	}

	@Override
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	public Date getCreateDate() {

		return getProxyDelegate().getCreateDate();
	}

	@Override
	public void setCreateDate(Date createDate) {

		getProxyDelegate().setCreateDate(createDate);

	}

	@Transient
	@Override
	public String getAllQueryName() {

		return allQueryName;
	}

	@ManyToOne(targetEntity = HibernateApplication.class, optional = false)
	@JoinColumn(name = "application_id", insertable = false, updatable = false)
	public Application getApplication() {
		return this.application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	//
	// Mapping for referential integrity purposes only
	//
	@OneToMany(mappedBy = "accountId", targetEntity = HibernateAccountUser.class)
	@OnDelete(action = OnDeleteAction.CASCADE)
	public Set<AccountUser> getAccountUsers() {

		return Collections.emptySet();
	}

	public void setAccountUsers(Set<AccountUser> accountUsers) {

		// Do nothing
	}

	@Transient
	private Account getProxyDelegate() {

		if (delegate == null) {
			delegate = SimpleComponentFactory.getAccount();
		}
		return delegate;
	}
}
