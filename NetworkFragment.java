package com.example.android.sunshine.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
     * Created by mike on 8/6/2017.
     */

    public class NetworkFragment extends Fragment {
        public static final String TAG = "NetworkFragment";

        private static final String HOSTIP_KEY = "HostIP";
        private static final String HOSTPORT_KEY = "HostPort";

        private NetworkIOCallback mCallback;
        private NetworkIOTask mNetworkIOTask;
        private String mIPString;
        private int mPort;

        /**
         * Static initializer for NetworkFragment that sets the IP and Port of the host it will be interfacing with
         */
        public static NetworkFragment getInstance(FragmentManager fragmentManager, String hostIP, int hostPort) {
            // Recover NetworkFragment in case we are re-creating the Activity due to a config change.
            // This is necessary because NetworkFragment might have a task that began running before
            // the config change and has not finished yet.
            // The NetworkFragment is recoverable via this method because it calls
            // setRetainInstance(true) upon creation.
            NetworkFragment networkFragment = (NetworkFragment) fragmentManager.findFragmentByTag(NetworkFragment.TAG);
            if (networkFragment == null) {
                networkFragment = new NetworkFragment();
                Bundle args = new Bundle();
                args.putString(HOSTIP_KEY, hostIP);
                args.putInt(HOSTPORT_KEY, hostPort);
                networkFragment.setArguments(args);
                fragmentManager.beginTransaction().add(networkFragment, TAG).commit();
            }
            return networkFragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Retain this Fragment across configuration changes in the host Activity.
            setRetainInstance(true);
            mIPString = getArguments().getString(HOSTIP_KEY);
            mPort = getArguments().getInt(HOSTPORT_KEY);
        }

        //@Override
        public void onAttach(Context context) {
          //  super.onAttach(context);
            // Host Activity will handle callbacks from task.
            mCallback = (NetworkIOCallback)context;
        }

        @Override
        public void onDetach() {
            super.onDetach();
            // Clear reference to host Activity.
            mCallback = null;
        }

        @Override
        public void onDestroy() {
            // Cancel task when Fragment is destroyed.
            stopNetworkIO();
            super.onDestroy();
        }

        /**
         * Start non-blocking execution of NetworkIOTask.
         */
        public void startNetworkIO() {
            stopNetworkIO();
            mNetworkIOTask = new NetworkIOTask();
            mNetworkIOTask.execute();
        }

        /**
         * Cancel (and interrupt if necessary) any ongoing DownloadTask execution.
         */
        public void stopNetworkIO() {
            if (mNetworkIOTask != null) {
                mNetworkIOTask.cancel(true);
                mNetworkIOTask = null;
            }
        }

        /**
         * Implementation of AsyncTask that runs a network operation on a background thread.
         */
        private class NetworkIOTask extends AsyncTask<String, Integer, NetworkIOTask.Result> {

            /**
             * Wrapper class that serves as a union of a result value and an exception. When the
             * download task has completed, either the result value or exception can be a non-null
             * value. This allows you to pass exceptions to the UI thread that were thrown during
             * doInBackground().
             */
            class Result {
                public String mResultValue;
                public Exception mException;
                public Result(String resultValue) {
                    mResultValue = resultValue;
                }
                public Result(Exception exception) {
                    mException = exception;
                }
            }

            /**
             * Cancel background network operation if we do not have network connectivity.
             */
            @Override
            protected void onPreExecute() {
                if (mCallback != null) {
                    NetworkInfo networkInfo = mCallback.getActiveNetworkInfo();
                    if (networkInfo == null || !networkInfo.isConnected() ||
                            (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                                    && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                        // If no connectivity, cancel task and update Callback with null data.
                        mCallback.updateFromDownload(null);
                        cancel(true);
                    }
                }
            }

            /**
             * Defines work to perform on the background thread.
             */
            @Override
            protected Result doInBackground(String... urls) {
                Result result = null;
                if (!isCancelled() && urls != null && urls.length > 0) {
                    String urlString = urls[0];
                    try {

                      /*  InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                        Log.e("TCP Client", "C: Connecting...");

                        //create a socket to make the connection with the server
                        Socket socket = new Socket(serverAddr, SERVER_PORT);

                        //URL url = new URL(urlString); */
                       // String resultString = downloadUrl(url);
                        String resultString = new String("aas");
                        if (resultString != null) {
                            result = new Result(resultString);
                        } else {
                            throw new IOException("No response received.");
                        }
                    } catch(Exception e) {
                        result = new Result(e);
                    }
                }
                return result;
            }

            /**
             * Send NetworkIOCallback a progress update.
             */
            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                if (values.length >= 2) {
                    mCallback.onProgressUpdate(values[0], values[1]);
                }
            }

            /**
             * Updates the NetworkIOCallback with the result.
             */
            @Override
            protected void onPostExecute(Result result) {
                if (result != null && mCallback != null) {
                    if (result.mException != null) {
                        mCallback.updateFromDownload(result.mException.getMessage());
                    } else if (result.mResultValue != null) {
                        mCallback.updateFromDownload(result.mResultValue);
                    }
                    mCallback.finishDownloading();
                }
            }

            /**
             * Override to add special behavior for cancelled AsyncTask.
             */
            @Override
            protected void onCancelled(Result result) {
            }
            }

}
