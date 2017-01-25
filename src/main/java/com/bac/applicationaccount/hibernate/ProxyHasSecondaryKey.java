package com.bac.applicationaccount.hibernate;

import org.hibernate.Query;

public interface ProxyHasSecondaryKey {
	
    String getSecondaryKeyQueryName();

    void setSecondaryKeyQuery(Query query);

}
