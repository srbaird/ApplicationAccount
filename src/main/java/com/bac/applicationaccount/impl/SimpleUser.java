/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bac.applicationaccount.impl;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import com.bac.applicationaccount.Account;
import com.bac.applicationaccount.User;

/**
 *
 * @author user0001
 */
public class SimpleUser implements User {

    private Integer id;
    private String userName;
    private String userEmail;
    private byte[] userPassword;
    private byte[] passwordSalt;
    private Character active;
    private final Character DEFAULT_ACTIVE = 'N';
    private Date createDate;
    private Set<Account> accounts;

    public SimpleUser() {

    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public byte[] getUserPassword() {
        return userPassword;
    }

    @Override
    public void setUserPassword(byte[] userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public byte[] getPasswordSalt() {
        return passwordSalt;
    }

    @Override
    public void setPasswordSalt(byte[] pSalt) {
        this.passwordSalt = pSalt;
    }

    @Override
    public Character getActive() {
        return active == null ? DEFAULT_ACTIVE : active;
    }

    @Override
    public void setActive(Character active) {
        this.active =  active == null ? DEFAULT_ACTIVE : active;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public Set<Account> getAccounts() {
        return accounts;
    }

    /*
     * Restrict any set of Accounts to SimpleAccounts
     */
    @Override
    public void setAccounts(Set<Account> accounts) {

        this.accounts = accounts == null ? null : accounts.stream().map(a -> SimpleComponentFactory.getAccount(a)).collect(Collectors.toSet());
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleUser other = (SimpleUser) obj;
		if (userEmail == null) {
			if (other.userEmail != null)
				return false;
		} else if (!userEmail.equals(other.userEmail))
			return false;
		return true;
	}
    
}
