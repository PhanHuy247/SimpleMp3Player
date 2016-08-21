package vn.techkid.simplemp3player.Getter;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import vn.techkid.simplemp3player.R;

/**
 * Created by Laptop88 on 8/21/2016.
 */
public class DownloadTask extends AsyncTask<String, String, String> {
    final String secStore = System.getenv("SECONDARY_STORAGE");

    private Context mContext;
    private String songDetail;
    private String name;
    public DownloadTask(Context mContext, String songDetail, String name){
        this.mContext = mContext;
        this.songDetail = songDetail;
        this.name = name;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Shows Notification
        Notification.Builder mBuilder = new Notification.Builder(mContext);
        mBuilder.setSmallIcon(R.drawable.image_music)
                .setTicker("Downloading: "+ songDetail)
                .setContentTitle("Downloading... 0%")
                .setContentText(songDetail);
        Notification downloadNoti = mBuilder.build();
        NotificationManager manager =  (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(110, downloadNoti);

    }

    // Download Music File from Internet
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            // Get Music file length
            int lenghtOfFile = conection.getContentLength();
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(),10*1024);
            // Output stream to write file in SD card
            OutputStream output = new FileOutputStream (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/"+name);
//            Log.d("location", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/"+name);
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                // Publish the progress which triggers onProgressUpdate method
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                // Write data to file
                output.write(data, 0, count);
            }
            // Flush output
            output.flush();
            // Close streams
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return null;
    }



    // Once Music File is downloaded
    @Override
    protected void onPostExecute(String file_url) {
        // Show notification
        Notification.Builder mBuilder = new Notification.Builder(mContext);
        mBuilder.setTicker("Finished: Downloading: "+ songDetail)
                .setContentTitle("Finished")
                .setContentText(songDetail)
                .setSmallIcon(R.drawable.image_music)
                .setAutoCancel(true);
        Notification downloadNoti = mBuilder.build();
        NotificationManager manager =  (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(110, downloadNoti);
    }
}


