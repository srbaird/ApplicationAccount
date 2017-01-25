/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bac.applicationaccount.data.hibernate;

import java.io.Serializable;

import com.bac.applicationaccount.data.ApplicationAccountEntity;

/**
 *
 * @author user0001
 */
public interface ApplicationAccountEntityProxy extends Serializable {

	ApplicationAccountEntity getDelegate();

	void setDelegate(ApplicationAccountEntity delegate);

	String getAllQueryName();
}
