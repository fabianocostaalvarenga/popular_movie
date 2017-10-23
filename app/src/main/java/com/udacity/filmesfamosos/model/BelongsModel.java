package com.udacity.filmesfamosos.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fabiano.alvarenga on 10/15/17.
 */

public class BelongsModel implements Serializable {

    private Long id;

    private String name;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

}
