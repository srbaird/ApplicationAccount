/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bac.applicationaccount.data;

import java.util.Date;

/**
 *
 * @author user0001
 */
public interface AccountUser extends ApplicationAccountEntity {

    void setAccountId(Integer id);

    Integer getAccountId();

    void setUserId(Integer id);

    Integer getUserId();

    Character getActive();

    void setActive(Character active);

    Date getCreateDate();

    void setCreateDate(Date createDate);

    Date getLastAccessDate();

    void setLastAccessDate(Date createDate);

    void setAccessLevelId(Integer id);

    Integer getAccessLevelId();

    String getAccountMessage();

    void setAccountMessage(String msg);
}
