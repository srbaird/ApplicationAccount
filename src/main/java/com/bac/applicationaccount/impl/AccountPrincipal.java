
package com.bac.applicationaccount.impl;

import java.io.Serializable;
import java.security.Principal;
import java.util.Objects;

/**
 *
 * @author Simon Baird
 */
public class AccountPrincipal implements Principal, Serializable {

    private static final long serialVersionUID = 698788636930700L;

    private final String NULL_INPUT_MSG = "Attempt to instantiate with null parameter";
    private final String name;

    public AccountPrincipal(String name) {

        Objects.requireNonNull(name, NULL_INPUT_MSG);
        this.name = name;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public int hashCode() {

        int hash = 7;
        hash = 17 * hash + Objects.hashCode(name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccountPrincipal other = (AccountPrincipal) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {

        return "AccountPrincipal{" + "name=" + name + '}';
    }
}
