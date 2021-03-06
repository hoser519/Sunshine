package com.example.android.sunshine.app;

import android.net.NetworkInfo;

/**
 * Created by mike on 8/6/2017.
 */


public interface NetworkIOCallback {
    interface Progress {
        int ERROR = -1;
        int CONNECT_SUCCESS = 1;
        int GET_INPUT_STREAM_SUCCESS = 2;
        int PROCESS_INPUT_STREAM_IN_PROGRESS = 3;
        int PROCESS_INPUT_STREAM_SUCCESS = 4;
    }

    /**
     * Indicates that the callback handler needs to update its appearance or information based on
     * the result of the task. Expected to be called from the main thread.
     */
    void updateFromDownload(String result);

    /**
     * Get the device's active network status in the form of a NetworkInfo object.
     */
    NetworkInfo getActiveNetworkInfo();

    /**
     * Indicate to callback handler any progress update.
     * @param progressCode must be one of the constants defined in NetworkIOCallback.Progress.
     * @param percentComplete must be 0-100.
     */
    void onProgressUpdate(int progressCode, int percentComplete);

    /**
     * Indicates that the download operation has finished. This method is called even if the
     * download hasn't completed successfully.
     */
    void cancelNetworkIO();

}