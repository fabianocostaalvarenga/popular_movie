package com.udacity.filmesfamosos.repository;

import android.provider.BaseColumns;

/**
 * Created by fabiano.alvarenga on 15/02/18.
 */

public class MoviesContract {

    private MoviesContract(){}

    public class MoviesEntry implements BaseColumns {

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        public static final String COLUMN_NAME_POSTER_PATH = "posterPath";
        public static final String COLUMN_NAME_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_NAME_ORIGINAL_TITLE = "originalTitle";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
    }

}
