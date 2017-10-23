package com.udacity.filmesfamosos.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fabiano.alvarenga on 10/15/17.
 */

public class ProductionCountriesModel implements Serializable {

    @SerializedName("iso_3166_1")
    private String isoId;

    private String name;

    public String getIsoId() {
        return isoId;
    }

    public void setIsoId(String isoId) {
        this.isoId = isoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
