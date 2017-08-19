package com.example.android.sunshine.app;


import android.util.Log;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by mike on 8/8/2017.
 */

public class TcpClient {

       // message to send to the server
        private String mServerMessage;
        // sends message received notifications
    //    private OnMessageReceived mMessageListener = null;
        // used to send messages
        private PrintWriter mBufferOut;
        // used to read messages from the server
        private BufferedReader mBufferIn;
        Socket mSocket;
        private String mIPString;
        private int mPort;


         // Constructor of the class. OnMessagedReceived listens for the messages received from server

        public TcpClient(String mIPString, int mPort) {
            this.mIPString = mIPString;
            this.mPort = mPort;
        }

        /**
         * Sends the message entered by client to the server
         *
         * @param message text entered by client
         */
        public void sendMessage(String message) throws IOException {

            if(mBufferOut.checkError()) {
                throw new IOException("Server unreachable - connection severed");
            }
            if (mBufferOut != null && !mBufferOut.checkError()) {
                mBufferOut.print(message);
                mBufferOut.flush();
            }


        }

        public boolean isConnected() {
            if (mSocket != null) return mSocket.isConnected();
            return false;
        }
        /**
         * Close the connection and release the members
         */
        public void disconnect() {
            // send mesage that we are closing the connection
//            sendMessage("Kazy");
           // String c  = new String("C");
          //  sendMessage(c);

            if (mBufferOut != null) {
                mBufferOut.flush();
                mBufferOut.close();
            }
            //mMessageListener = null;
            mBufferIn = null;
            mBufferOut = null;
            mServerMessage = null;
            if (mSocket!=null)
                try {
                    Log.e("TCP", "Closed Socket");

                    mSocket.close();
                } catch (Exception e) {
                    Log.e("TCP", "C: Error", e);

                }

        }


        public void connect() throws Exception {

                InetAddress serverAddress = InetAddress.getByName(mIPString);
                Log.e("TCP Client", "C: Connecting...");
                //create a socket to make the connection with the server
                mSocket = new Socket(serverAddress, mPort);
                Log.v(TcpClient.class.getSimpleName(),"Socket() returned");
                //sends the message to the server
                mBufferOut = new PrintWriter(new BufferedWriter(
                                                new OutputStreamWriter(mSocket.getOutputStream())), true);

                    //receives the message which the server sends back
                mBufferIn = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));

                Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + mServerMessage + "'");

           // } catch (Exception e) {
            //    Log.e("TCP", "C: Error", e);
             //   throw e;

        }

}

