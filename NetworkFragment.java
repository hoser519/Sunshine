package com.example.android.sunshine.app;

import android.app.Activity;
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
import java.net.ConnectException;
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
        private TcpClient  mTcpClient;
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
            NetworkFragment networkFragment = (NetworkFragment)
                                fragmentManager.findFragmentByTag(NetworkFragment.TAG);
            if (networkFragment == null) {
                networkFragment = new NetworkFragment();
                Bundle args = new Bundle();
                args.putString(HOSTIP_KEY, hostIP);
                args.putInt(HOSTPORT_KEY, hostPort);
                networkFragment.setArguments(args);
                fragmentManager.beginTransaction().add(networkFragment, NetworkFragment.TAG).commit();
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
            // Connect to server

        }

        @Override
        public void onAttach(Activity parent) {
           super.onAttach(parent);
            // Host Activity will handle callbacks from task.
        try {
             mCallback = (NetworkIOCallback)parent;
        } catch (ClassCastException e) {
        throw new ClassCastException(  " must implement NetworIOCallback");
    }
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
            if (mTcpClient != null)
                if (mTcpClient.isConnected())
                    mTcpClient.disconnect();

            super.onDestroy();
        }

        /**
         * Start non-blocking execution of NetworkIOTask.
         */
        public void startNetworkIO(String cmd) {
            stopNetworkIO();
            Log.v(NetworkFragment.class.getSimpleName(), "startNetworkIO - start");
            if (mNetworkIOTask == null) {
                Log.v(NetworkFragment.class.getSimpleName(), "startNetworkIO");
                mNetworkIOTask = new NetworkIOTask();
                mNetworkIOTask.execute(cmd);
            }

        }

        // If Network task exists and not cancelled, cancel it (if we are waiting for Soclet.connect
        // it won't kill the task until Socket.connect returns, and then will be killed calling onCancelled).

        public void stopNetworkIO() {
            if (mNetworkIOTask != null) {
                if (!mNetworkIOTask.isCancelled()) {
                    Log.v(NetworkFragment.class.getSimpleName(), "cancelling task");
                    mNetworkIOTask.cancel(true);
                } else {
                    Log.v(NetworkFragment.class.getSimpleName(), "Was already cancelled");
                }
                if (mNetworkIOTask.getStatus() == AsyncTask.Status.RUNNING) {
                    Log.v(NetworkFragment.class.getSimpleName(), "AsyncTask.Status.RUNNING");

                }
                if (mNetworkIOTask.getStatus() == AsyncTask.Status.PENDING) {
                    Log.v(NetworkFragment.class.getSimpleName(), "AsyncTask.Status.PENDING");

                }
                if (mNetworkIOTask.getStatus() == AsyncTask.Status.FINISHED) {
                    Log.v(NetworkFragment.class.getSimpleName(), "AsyncTask.Status.FINISHED");
                    mNetworkIOTask = null;
                }
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

            // Check for presence of active network connection and cancel this NetworkIOTask
            // if not present.
            @Override
            protected void onPreExecute() {
                if (mCallback != null) {
                  //  Log.v(NetworkFragment.class.getSimpleName(),"onPreExecute=");

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

            // doInBackground-. This runs in a seperate (from UI) thread.
            //  Establish a Socket conection if not already present. Send each parameter.

            @Override
            protected Result doInBackground(String... param) {
                Result result = null;
                if (!isCancelled() ) {
                    String cmd = param[0];
                    String resultString=null;
                    try {
                        // Connect if not we haven't done so already

                        if (mTcpClient == null){
                            mTcpClient = new TcpClient(mIPString, mPort);

                        }
                        if (mTcpClient.isConnected()){
                            // Send any pending message. If we couldn't send close socket and throw the error
                            try {
                                Log.v(NetworkFragment.class.getSimpleName(), "Tryingto send");

                                mTcpClient.sendMessage(cmd);
                                Log.v(NetworkFragment.class.getSimpleName(), "success");

                            } catch (IOException e) {
                                mTcpClient.disconnect();
                                mTcpClient = null;
                                throw e;
                            }
                            // TODO: Receive any data

                        } else {
                            mTcpClient.connect();
                        }

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
                  //  Log.v(NetworkFragment.class.getSimpleName(),"onPostExecute=");

                    if (result.mException != null) {
                        mCallback.updateFromDownload(result.mException.getMessage());
                    } else if (result.mResultValue != null) {
                        mCallback.updateFromDownload(result.mResultValue);
                   //     Log.v(NetworkFragment.class.getSimpleName(),"Received from Server="+result);

                    }
                    mCallback.cancelNetworkIO();
                }
            }

            /**
             * Override to add special behavior for cancelled AsyncTask.
             */
            @Override
            protected void onCancelled(Result result) {
                Log.v(NetworkFragment.class.getSimpleName(),"onCancelled() called");

            }
            }

}
