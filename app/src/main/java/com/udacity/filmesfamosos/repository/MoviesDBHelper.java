package com.udacity.filmesfamosos.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fabiano.alvarenga on 17/02/18.
 */

public class MoviesDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + MoviesContract.MoviesEntry.TABLE_NAME + " (" +
            MoviesContract.MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MoviesContract.MoviesEntry.COLUMN_NAME_MOVIE_ID + " INTEGER NOT NULL, " +
            MoviesContract.MoviesEntry.COLUMN_NAME_ORIGINAL_TITLE + " STRING, " +
            MoviesContract.MoviesEntry.COLUMN_NAME_RELEASE_DATE + " INTEGER, " +
            MoviesContract.MoviesEntry.COLUMN_NAME_VOTE_AVERAGE + " NUMBER, " +
            MoviesContract.MoviesEntry.COLUMN_NAME_OVERVIEW + " STRING, " +
            MoviesContract.MoviesEntry.COLUMN_NAME_POSTER_PATH + " STRING NOT NULL" +
            "); ";

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}