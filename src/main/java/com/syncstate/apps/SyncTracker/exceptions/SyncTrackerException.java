package com.syncstate.apps.SyncTracker.exceptions;

public class SyncTrackerException extends Exception{

    public SyncTrackerException(String message)
    {
        super(message);
    }
    public SyncTrackerException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
