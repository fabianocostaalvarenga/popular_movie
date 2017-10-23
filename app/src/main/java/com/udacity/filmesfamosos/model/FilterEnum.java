package com.udacity.filmesfamosos.model;

/**
 * Created by fabiano.alvarenga on 10/22/17.
 */

public enum FilterEnum {

    POPULAR_MOVIES("popular"),
    TOP_RATED("top_rated");

    private String filter;

    FilterEnum(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }

}
