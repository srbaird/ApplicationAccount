package com.bac.applicationaccount.data.hibernate;

import org.hibernate.Query;

public interface ProxyHasSecondaryKey {
	
    String getSecondaryKeyQueryName();

    void setSecondaryKeyQuery(Query query);

}
