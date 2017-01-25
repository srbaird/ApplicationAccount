/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bac.applicationaccount.hibernate;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bac.applicationaccount.AccessByPrimaryKey;
import com.bac.applicationaccount.AccessLevel;
import com.bac.applicationaccount.Account;
import com.bac.applicationaccount.AccountUser;
import com.bac.applicationaccount.Application;
import com.bac.applicationaccount.ApplicationAccount;
import com.bac.applicationaccount.User;

/**
 *
 * @author user0001
 */
public class HibernateApplicationAccount implements ApplicationAccount {

	private EntityProxyObjectFactory objectFactory;

	private SessionFactory sessionFactory;

	private Session session;
	// logger
	private static final Logger logger = LoggerFactory.getLogger(HibernateApplicationAccount.class);
	//
	private final String nullParameter = "Supplied parameter was null";
	//

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;

	}

	public EntityProxyObjectFactory getObjectFactory() {
		return objectFactory;
	}

	@Resource(name = "objectFactory")
	public void setObjectFactory(EntityProxyObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	//
	// Account
	//
	@Override
	public Account getAccount(Integer id) {

		ApplicationAccountEntityProxy proxy = getEntity(objectFactory.getAccountClass(), id);
		return (Account) (proxy == null ? null : proxy.getDelegate());
	}

	@Override
	public List<? extends Account> getAccounts() {

		List<ApplicationAccountEntityProxy> proxies = getAllEntites(objectFactory.getAccountObject());
		return proxies.stream().map(p -> (Account) p.getDelegate()).collect(Collectors.toList());
	}

	@Override
	public Account createAccount(Account account) {

		ApplicationAccountEntityProxy proxy = createEntity(objectFactory.getObject(account));
		return (Account) (proxy == null ? null : proxy.getDelegate());
	}

	@Override
	public Account updateAccount(Account account) {

		ApplicationAccountEntityProxy proxy = updateEntity(objectFactory.getAccountClass(), account);
		return (Account) (proxy == null ? null : proxy.getDelegate());
	}

	@Override
	public void deleteAccount(Account account) {

		deleteEntity(objectFactory.getAccountClass(), account);
	}

	//
	// Account User
	//
	public AccountUser getAccountUser(AccountUser accountUser) {

		ApplicationAccountEntityProxy proxy = getEntity(objectFactory.getObject(accountUser));
		return (AccountUser) (proxy == null ? null : proxy.getDelegate());
	}

	@Override
	public List<? extends AccountUser> getAccountUsers() {

		List<ApplicationAccountEntityProxy> proxies = getAllEntites(objectFactory.getAccountUserObject());
		return proxies.stream().map(p -> (AccountUser) p.getDelegate()).collect(Collectors.toList());
	}

	public AccountUser createAccountUser(AccountUser accountUser) {

		ApplicationAccountEntityProxy proxy = createEntity(objectFactory.getObject(accountUser));
		return (AccountUser) (proxy == null ? null : proxy.getDelegate());
	}

	public AccountUser updateAccountUser(AccountUser accountUser) {

		ApplicationAccountEntityProxy proxy = updateEntity(objectFactory.getObject(accountUser));
		return (AccountUser) (proxy == null ? null : proxy.getDelegate());
	}

	public void deleteAccountUser(AccountUser accountUser) {

		deleteEntity(objectFactory.getObject(accountUser));
	}
	@Override
	public AccountUser getAccountUserBySecondaryKey(AccountUser accountUser) {

		ApplicationAccountEntityProxy proxy = getEntityBySecondaryKey(objectFactory.getSecondaryKeyObject(accountUser));
		return (AccountUser) (proxy == null ? null : proxy.getDelegate());
	}

	//
	// User
	//
	@Override
	public User getUser(Integer id) {

		ApplicationAccountEntityProxy proxy = getEntity(objectFactory.getUserClass(), id);
		return (User) (proxy == null ? null : proxy.getDelegate());
	}
	
	@Override
	public User reloadUser(Integer id) {

		final Class<?> clazz = objectFactory.getUserClass();
		evictEntity(clazz, id);
		ApplicationAccountEntityProxy proxy = getEntity(clazz, id);
		return (User) (proxy == null ? null : proxy.getDelegate());
	}

	@Override
	public List<? extends User> getUsers() {

		List<ApplicationAccountEntityProxy> proxies = getAllEntites(objectFactory.getUserObject());
		return proxies.stream().map(p -> (User) p.getDelegate()).collect(Collectors.toList());
	}

	@Override
	public User createUser(User user) {

		ApplicationAccountEntityProxy proxy = createEntity(objectFactory.getObject(user));
		return (User) (proxy == null ? null : proxy.getDelegate());
	}

	@Override
	public User updateUser(User user) {

		ApplicationAccountEntityProxy proxy = updateEntity(objectFactory.getUserClass(), user);
		return (User) (proxy == null ? null : proxy.getDelegate());
	}

	@Override
	public void deleteUser(User user) {

		deleteEntity(objectFactory.getUserClass(), user);
	}

	@Override
	public User getUserBySecondaryKey(User user) {

		ApplicationAccountEntityProxy proxy = getEntityBySecondaryKey(objectFactory.getSecondaryKeyObject(user));
		return (User) (proxy == null ? null : proxy.getDelegate());
	}

	//
	// Access level
	//
	@Override
	public AccessLevel getAccessLevel(Integer id) {

		ApplicationAccountEntityProxy proxy = getEntity(objectFactory.getAccessLevelClass(), id);
		return (AccessLevel) (proxy == null ? null : proxy.getDelegate());
	}

	@Override
	public List<? extends AccessLevel> getAccessLevels() {

		List<ApplicationAccountEntityProxy> proxies = getAllEntites(objectFactory.getAccessLevelObject());
		return proxies.stream().map(p -> (AccessLevel) p.getDelegate()).collect(Collectors.toList());
	}

	@Override
	public AccessLevel createAccessLevel(AccessLevel accessLevel) {

		ApplicationAccountEntityProxy proxy = createEntity(objectFactory.getObject(accessLevel));
		return (AccessLevel) (proxy == null ? null : proxy.getDelegate());
	}

	@Override
	public AccessLevel updateAccessLevel(AccessLevel accessLevel) {

		ApplicationAccountEntityProxy proxy = updateEntity(objectFactory.getAccessLevelClass(), accessLevel);
		return (AccessLevel) (proxy == null ? null : proxy.getDelegate());
	}

	@Override
	public void deleteAccessLevel(AccessLevel accessLevel) {

		deleteEntity(objectFactory.getAccessLevelClass(), accessLevel);
	}

	//
	// Application
	//
	@Override
	public Application getApplication(Integer id) {

		ApplicationAccountEntityProxy proxy = getEntity(objectFactory.getApplicationClass(), id);
		return (Application) (proxy == null ? null : proxy.getDelegate());
	}

	@Override
	public List<? extends Application> getApplications() {

		List<ApplicationAccountEntityProxy> proxies = getAllEntites(objectFactory.getApplicationObject());
		return proxies.stream().map(p -> (Application) p.getDelegate()).collect(Collectors.toList());
	}

	@Override
	public Application createApplication(Application application) {

		ApplicationAccountEntityProxy proxy = createEntity(objectFactory.getObject(application));
		return (Application) (proxy == null ? null : proxy.getDelegate());
	}

	@Override
	public Application updateApplication(Application application) {

		ApplicationAccountEntityProxy proxy = updateEntity(objectFactory.getApplicationClass(), application);
		return (Application) (proxy == null ? null : proxy.getDelegate());
	}

	@Override
	public void deleteApplication(Application application) {

		deleteEntity(objectFactory.getApplicationClass(), application);
	}

	@Override
	public Application getApplicationBySecondaryKey(Application application) {

		ApplicationAccountEntityProxy proxy = getEntityBySecondaryKey(objectFactory.getSecondaryKeyObject(application));
		return (Application) (proxy == null ? null : proxy.getDelegate());
	}

	//
	// Persistence methods
	//
	private ApplicationAccountEntityProxy createEntity(ApplicationAccountEntityProxy proxy) throws HibernateException {

		Objects.requireNonNull(proxy, nullParameter);
		session = sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(proxy);
			session.flush(); // required to apply the insert
		} catch (HibernateException e) {
			logger.error("Create entity", e.getMessage());
			throw (e);
		}
		return proxy;
	}

	private void evictEntity(Class<?> clazz, Serializable id) {
		
		session = sessionFactory.getCurrentSession();
		try {
			Object target =  session.get(clazz, id);
			session.evict(target);	
		} catch (HibernateException e) {
			logger.error("Evicting an entity", e.getMessage());
		}
	}
	private ApplicationAccountEntityProxy getEntity(Class<?> clazz, Integer id) throws HibernateException {

		Objects.requireNonNull(id, nullParameter);
		ApplicationAccountEntityProxy proxy;
		session = sessionFactory.getCurrentSession();
		try {
			proxy = (ApplicationAccountEntityProxy) session.get(clazz, id);
		} catch (HibernateException e) {
			logger.error("Read entity using id", e.getMessage());
			throw (e);
		}
		return proxy;
	}

	private ApplicationAccountEntityProxy getEntity(ApplicationAccountEntityProxy entity) throws HibernateException {

		Objects.requireNonNull(entity, nullParameter);
		ApplicationAccountEntityProxy proxy;
		session = sessionFactory.getCurrentSession();
		try {
			session.evict(entity);	
			proxy = (ApplicationAccountEntityProxy) session.get(entity.getClass(), entity);
		} catch (HibernateException e) {
			logger.error("Read entity by proxy", e.getMessage());
			throw (e);
		}
		return proxy;
	}

	private ApplicationAccountEntityProxy updateEntity(Class<?> clazz, AccessByPrimaryKey entity)
			throws HibernateException {

		Objects.requireNonNull(entity, nullParameter);
		ApplicationAccountEntityProxy proxy;
		session = sessionFactory.getCurrentSession();
		try {
			proxy = (ApplicationAccountEntityProxy) session.get(clazz, entity.getId());
			proxy.setDelegate(entity);
			proxy = (ApplicationAccountEntityProxy) session.merge(proxy);
			session.flush(); // required to apply the update
		} catch (HibernateException e) {
			logger.error("Update entity", e.getMessage());
			throw (e);
		}
		return proxy;
	}

	private ApplicationAccountEntityProxy updateEntity(ApplicationAccountEntityProxy entity) throws HibernateException {

		Objects.requireNonNull(entity, nullParameter);
		ApplicationAccountEntityProxy proxy;
		session = sessionFactory.getCurrentSession();
		try {
			proxy = (ApplicationAccountEntityProxy) session.load(entity.getClass(), entity);
			proxy = (ApplicationAccountEntityProxy) session.merge(proxy);
			session.flush(); // required to apply the update
		} catch (HibernateException e) {
			logger.error("Update entity by proxy", e.getMessage());
			throw (e);
		}
		return proxy;
	}

	private void deleteEntity(Class<?> clazz, AccessByPrimaryKey entity) throws HibernateException {

		Objects.requireNonNull(entity, nullParameter);
		session = sessionFactory.getCurrentSession();
		try {
			ApplicationAccountEntityProxy proxy = (ApplicationAccountEntityProxy) session.get(clazz, entity.getId());
			session.delete(proxy);
			session.flush();
		} catch (HibernateException e) {
			logger.error("Delete entity", e.getMessage());
			throw (e);
		}
	}

	private void deleteEntity(ApplicationAccountEntityProxy entity) throws HibernateException {

		Objects.requireNonNull(entity, nullParameter);

		session = sessionFactory.getCurrentSession();
		try {
			ApplicationAccountEntityProxy proxy = (ApplicationAccountEntityProxy) session.get(entity.getClass(),
					entity);
			session.delete(proxy);
			session.flush();
		} catch (HibernateException e) {
			logger.error("Delete entity by proxy", e.getMessage());
			throw (e);
		}
	}

	private ApplicationAccountEntityProxy getEntityBySecondaryKey(ProxyHasSecondaryKey entity)
			throws HibernateException {

		Objects.requireNonNull(entity, nullParameter);
		String queryName = entity.getSecondaryKeyQueryName();

		if (queryName == null || queryName.isEmpty()) {
			return null;
		}
		ApplicationAccountEntityProxy proxy = null;
		session = sessionFactory.getCurrentSession();
		try {
			Query query = session.getNamedQuery(queryName);
			entity.setSecondaryKeyQuery(query);
			proxy = (ApplicationAccountEntityProxy) query.uniqueResult();
		} catch (HibernateException e) {
			logger.error("Retrieve entity by secondary key", e.getMessage());
			throw (e);
		}
		return proxy;
	}

	@SuppressWarnings("unchecked")
	private List<ApplicationAccountEntityProxy> getAllEntites(ApplicationAccountEntityProxy entity)
			throws HibernateException {

		Objects.requireNonNull(entity, nullParameter);
		String queryName = entity.getAllQueryName();
		if (queryName == null || queryName.isEmpty()) {
			return Collections.emptyList();
		}
		List<ApplicationAccountEntityProxy> proxy = null;
		session = sessionFactory.getCurrentSession();
		try {
			Query query = session.getNamedQuery(queryName);
			proxy = (List<ApplicationAccountEntityProxy>) query.list();
		} catch (HibernateException e) {
			logger.error("Retrieve entity by secondary key", e.getMessage());
			throw (e);
		}
		return proxy;
	}
}
