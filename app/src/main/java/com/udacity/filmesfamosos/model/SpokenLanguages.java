package com.udacity.filmesfamosos.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fabiano.alvarenga on 10/15/17.
 */

public class SpokenLanguages implements Serializable {

    @SerializedName("iso_639_1")
    private Long isoId;

    private String name;

    public Long getIsoId() {
        return isoId;
    }

    public void setIsoId(Long isoId) {
        this.isoId = isoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
