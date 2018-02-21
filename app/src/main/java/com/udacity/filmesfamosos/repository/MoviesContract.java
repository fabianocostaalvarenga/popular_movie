package com.udacity.filmesfamosos.repository;

import android.net.Uri;
import android.provider.BaseColumns;

import com.udacity.filmesfamosos.utils.ApplicationUtils;

/**
 * Created by fabiano.alvarenga on 15/02/18.
 */

public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "com.udacity.filmesfamosos";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    private MoviesContract(){
    }

    public static class MoviesEntry implements BaseColumns {

        public static final String TABLE_NAME = "movies";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static Uri buildMovieUriByMovieId(Long movieId) {
            return CONTENT_URI.buildUpon().appendPath(Long.toString(movieId)).build();
        }

        public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        public static final String COLUMN_NAME_POSTER_PATH = "posterPath";
        public static final String COLUMN_NAME_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_NAME_ORIGINAL_TITLE = "originalTitle";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_NAME_OVERVIEW = "overview";

    }

}
