/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bac.applicationaccount.data;

/**
 *
 * @author user0001
 */
public class SimpleApplication implements Application {

    private Integer id;
    private String name;
    private Character active;
    private Character acceptRegistration;
    private final Character DEFAULT_ACTIVE = 'N';
    private final Character DEFAULT_ACCEPT_REGISTRATION = 'N';

    public SimpleApplication() {
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Character getActive() {
    	return active == null ? DEFAULT_ACTIVE : active;
    }

    public void setActive(Character active) {
    	this.active =  active == null ? DEFAULT_ACTIVE : active;
    }

    public Character getAcceptRegistration() {

    	return acceptRegistration == null ? DEFAULT_ACCEPT_REGISTRATION : acceptRegistration;
    }

    public void setAcceptRegistration(Character acceptRegistration) {
    	
    	this.acceptRegistration =  acceptRegistration == null ? DEFAULT_ACCEPT_REGISTRATION : acceptRegistration;
    }
}
