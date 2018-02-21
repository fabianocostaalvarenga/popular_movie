package com.udacity.filmesfamosos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.filmesfamosos.repository.MoviesContract;
import com.udacity.filmesfamosos.repository.MoviesDBHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

/**
 * Created by fabiano.alvarenga on 20/02/18.
 */

@RunWith(AndroidJUnit4.class)
public class MovieContentProviderTest {

    private final Context mContext = InstrumentationRegistry.getTargetContext();

    @Before
    public void setUp() {
        deleteAllRecordsFromWeatherTable();
    }

    @Test
    public void testBasicMovieQuery() {

        MoviesDBHelper dbHelper = new MoviesDBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues testMovieValues = TestUtils.createTestMovieContentValues();

        long movieRowId = database.insert(
                MoviesContract.MoviesEntry.TABLE_NAME,
                null,
                testMovieValues);

        String insertFailed = "Insert Error In Database...";
        assertTrue(insertFailed, movieRowId != -1);

        database.close();

        Cursor movieCursor = mContext.getContentResolver().query(
                MoviesContract.MoviesEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        TestUtils.validateThenCloseCursor("basicMovieQueryTest", movieCursor, testMovieValues);
    }

    private void deleteAllRecordsFromWeatherTable() {

        MoviesDBHelper helper = new MoviesDBHelper(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase database = helper.getWritableDatabase();

        database.delete(MoviesContract.MoviesEntry.TABLE_NAME, null, null);

        database.close();
    }
}


