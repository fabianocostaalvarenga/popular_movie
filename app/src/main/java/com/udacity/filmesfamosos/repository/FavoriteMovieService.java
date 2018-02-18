package com.udacity.filmesfamosos.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.udacity.filmesfamosos.model.dto.MovieDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fabiano.alvarenga on 17/02/18.
 */

public class FavoriteMovieService {

    private final MoviesDBHelper dbHelper;
    private final SQLiteDatabase database;

    public FavoriteMovieService(@NonNull Context context) {
        this.dbHelper = new MoviesDBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public List<MovieDTO> getAll() {

        Cursor cursor = database.query(
                MoviesContract.MoviesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        return popularMovieFactory(cursor);
    }

    @NonNull
    private List<MovieDTO> popularMovieFactory(Cursor cursor) {
        List<MovieDTO> movieDTOs = new ArrayList<MovieDTO>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                movieDTOs.add(
                        new MovieDTO(
                                cursor.getLong(cursor.getColumnIndexOrThrow(MoviesContract.MoviesEntry.COLUMN_NAME_MOVIE_ID)),
                                cursor.getString(cursor.getColumnIndexOrThrow(MoviesContract.MoviesEntry.COLUMN_NAME_POSTER_PATH)),
                                new Date(cursor.getLong(cursor.getColumnIndexOrThrow(MoviesContract.MoviesEntry.COLUMN_NAME_RELEASE_DATE))),
                                cursor.getString(cursor.getColumnIndexOrThrow(MoviesContract.MoviesEntry.COLUMN_NAME_ORIGINAL_TITLE)),
                                new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(MoviesContract.MoviesEntry.COLUMN_NAME_VOTE_AVERAGE))),
                                cursor.getString(cursor.getColumnIndexOrThrow(MoviesContract.MoviesEntry.COLUMN_NAME_OVERVIEW))
                        )
                );

                cursor.moveToNext();
            }
        }
        cursor.close();
        return movieDTOs;
    }

    public boolean add(MovieDTO movieDTO) {
        ContentValues cv = new ContentValues();
        cv.put(MoviesContract.MoviesEntry.COLUMN_NAME_MOVIE_ID, movieDTO.getId());
        cv.put(MoviesContract.MoviesEntry.COLUMN_NAME_POSTER_PATH, movieDTO.getPosterPath());
        cv.put(MoviesContract.MoviesEntry.COLUMN_NAME_RELEASE_DATE, movieDTO.getReleaseDate().getTime());
        cv.put(MoviesContract.MoviesEntry.COLUMN_NAME_ORIGINAL_TITLE, String.valueOf(movieDTO.getOriginalTitle()));
        cv.put(MoviesContract.MoviesEntry.COLUMN_NAME_VOTE_AVERAGE, String.valueOf(movieDTO.getVoteAverage()));
        cv.put(MoviesContract.MoviesEntry.COLUMN_NAME_OVERVIEW, String.valueOf(movieDTO.getOverview()));
        return database.insert(MoviesContract.MoviesEntry.TABLE_NAME, null, cv) == 1;
    }

    public boolean remove(long id) {
        return database.delete(MoviesContract.MoviesEntry.TABLE_NAME, MoviesContract.MoviesEntry.COLUMN_NAME_MOVIE_ID + "=" + id, null) > 0;
    }

    public List<MovieDTO> listByMovieID(Long movieId) {
        String selection = MoviesContract.MoviesEntry.COLUMN_NAME_MOVIE_ID + " = ?";
        String[] selectionArgs = { String.valueOf(movieId) };

        Cursor cursor = database.query(
                MoviesContract.MoviesEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        return popularMovieFactory(cursor);
    }

    public boolean isFavorite(MovieDTO movieDTO) {
        boolean result = false;

        List<MovieDTO> movieDTOs = listByMovieID(movieDTO.getId());
        MovieDTO movieDTOResult = null;

        if(null != movieDTOs && !movieDTOs.isEmpty()) {
            movieDTOResult = movieDTOs.get(0);
        }

        if(null != movieDTOResult) { result = true; }

        return result;
    }
}
