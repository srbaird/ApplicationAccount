/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bac.applicationaccount;

import java.util.Date;
import java.util.Set;

/**
 *
 * @author user0001
 */
public interface User extends AccessByPrimaryKey {

    void setId(Integer id);

    String getUserName();

    void setUserName(String userName);

    String getUserEmail();

    void setUserEmail(String userEmail);

    byte[] getUserPassword();

    void setUserPassword(byte[] userPassword);

    byte[] getPasswordSalt();

    void setPasswordSalt(byte[] pSalt);

    Character getActive();

    void setActive(Character active);

    Date getCreateDate();

    void setCreateDate(Date createDate);

    Set<Account> getAccounts();

    void setAccounts(Set<Account> accounts);
}