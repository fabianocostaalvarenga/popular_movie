package com.udacity.filmesfamosos.model;

import java.io.Serializable;

/**
 * Created by fabiano.alvarenga on 10/15/17.
 */

public class GenresModel implements Serializable {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
