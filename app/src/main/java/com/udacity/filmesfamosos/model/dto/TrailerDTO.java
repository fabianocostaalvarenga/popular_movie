package com.udacity.filmesfamosos.model.dto;

import com.udacity.filmesfamosos.model.TrailerModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fabiano.alvarenga on 13/02/18.
 */

public class TrailerDTO implements Serializable {

    private Long id;

    private List<TrailerModel> results;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TrailerModel> getResults() {
        return results;
    }

    public void setResults(List<TrailerModel> results) {
        this.results = results;
    }
}
