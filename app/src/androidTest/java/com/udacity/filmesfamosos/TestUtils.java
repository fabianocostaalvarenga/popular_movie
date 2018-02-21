package com.udacity.filmesfamosos;

import android.content.ContentValues;
import android.database.Cursor;

import com.udacity.filmesfamosos.repository.MoviesContract;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by fabiano.alvarenga on 20/02/18.
 */

public class TestUtils {

    public static ContentValues createTestMovieContentValues() {

        ContentValues testMovieValues = new ContentValues();

        testMovieValues.put(MoviesContract.MoviesEntry.COLUMN_NAME_MOVIE_ID, 123456L);
        testMovieValues.put(MoviesContract.MoviesEntry.COLUMN_NAME_OVERVIEW,
                "Life there looks hopeless for its exiles until they get a visit from Kobayashi’s intrepid 12-year-old ward Atari (Koyu Rankin), " +
                "in search of his beloved, long-lost Spots. A band of mutts led by battle-scarred stray Chief (Bryan Cranston) help Atari on " +
                "his mission, which involves exploring the scarier parts of the island, a mix of industrial wasteland and abandoned funfair, with fully " +
                "functioning mechanised parts. Meanwhile, a pro-dog student group – including moppet-like American visitor Tracy (Greta Gerwig) " +
                "– are rising up against Kobayashi, with the help of research scientist Yoko Ono (voiced by Yoko Ono).");
        testMovieValues.put(MoviesContract.MoviesEntry.COLUMN_NAME_POSTER_PATH, "/jkkjhsui899871298.jpg");
        testMovieValues.put(MoviesContract.MoviesEntry.COLUMN_NAME_VOTE_AVERAGE, new Double("6.8"));
        testMovieValues.put(MoviesContract.MoviesEntry.COLUMN_NAME_ORIGINAL_TITLE, "Movie Test");
        testMovieValues.put(MoviesContract.MoviesEntry.COLUMN_NAME_RELEASE_DATE, new Date().getTime());

        return testMovieValues;
    }

    public static void validateThenCloseCursor(String error, Cursor valueCursor, ContentValues expectedValues) {

        assertNotNull("ERROR: This Cursor is null.", valueCursor);

        assertTrue("ERROR: Empty cursor. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    public static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int index = valueCursor.getColumnIndex(columnName);

            String columnNotFoundError = "Column '" + columnName + "' not found. " + error;
            assertFalse(columnNotFoundError, index == -1);

            String expectedValue = entry.getValue().toString();
            String actualValue = valueCursor.getString(index);

            String valuesDontMatchError = "Value '" + actualValue + "' not match expected value '" + expectedValue + "'. " + error;

            assertEquals(valuesDontMatchError, expectedValue, actualValue);
        }
    }

}