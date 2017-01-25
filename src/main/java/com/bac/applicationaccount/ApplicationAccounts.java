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
public class ApplicationAccounts {
    
    private static ApplicationAccount instance;

    public static ApplicationAccount getInstance() {
        return instance;
    }

    public static void setInstance(ApplicationAccount instance) {
        ApplicationAccounts.instance = instance;
    }
        
}
