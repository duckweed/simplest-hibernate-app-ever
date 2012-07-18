package org.adscale;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Copyright AdScale GmbH, Germany, 2007
 */
@Entity
public class Person {

    private String name;

    @Id
    long id;


    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
