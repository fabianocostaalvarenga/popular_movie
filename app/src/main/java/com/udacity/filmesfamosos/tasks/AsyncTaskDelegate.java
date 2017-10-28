package com.udacity.filmesfamosos.tasks;

/**
 * Created by fabiano.alvarenga on 10/28/17.
 */

public interface AsyncTaskDelegate<T> {

    public void processStart();
    public void processFinish(T object);
}
