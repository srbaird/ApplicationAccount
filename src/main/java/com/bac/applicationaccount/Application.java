/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bac.applicationaccount;

/**
 *
 * @author user0001
 */
public interface Application extends AccessByPrimaryKey {

    void setId(Integer id);

    String getName();

    void setName(String name);

    Character getActive();

    void setActive(Character active);

    Character getAcceptRegistration();

    void setAcceptRegistration(Character acceptRegistration);
}
