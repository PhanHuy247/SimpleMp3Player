package vn.techkid.simplemp3player.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import vn.techkid.simplemp3player.Fragment.CheckConnection;
import vn.techkid.simplemp3player.Interface.OnTaskCompleted;
import vn.techkid.simplemp3player.Model.Album;
import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Singleton.countSplash;

public class SplashScreenActivity extends AppCompatActivity{
    public static String URLVIET = "http://chiasenhac.vn/mp3/vietnam/album.html";
    public static String URLUS = "http://chiasenhac.vn/mp3/us-uk/album.html";
    public static String URLKOR = "http://chiasenhac.vn/mp3/korea/album.html";

    public static String URLVIETNAM = "http://chiasenhac.vn/mp3/beat-playback/v-instrumental/";
    public static String URLUSUK = "http://chiasenhac.vn/mp3/beat-playback/us-instrumental/";
    public static String URLKOREA = "http://chiasenhac.vn/mp3/beat-playback/k-instrumental/";
    // Splash screen timer
    ImageView imageView;
    private static int SPLASH_TIME_OUT = 2000;
    public static DownloadTask task1 = new DownloadTask();
    public static DownloadTask task2 = new DownloadTask();
    public static DownloadTask task3 = new DownloadTask();
    public static DownloadTask taskAlbum1 = new DownloadTask();
    public static DownloadTask taskAlbum2 = new DownloadTask();
    public static DownloadTask taskAlbum3 = new DownloadTask();
    private CheckConnection checkConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView = (ImageView) findViewById(R.id.imgsplash);
        checkConnection = new CheckConnection(this);
        if(countSplash.getInstance().getCount() != 1) countSplash.getInstance().setCount(0);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("splash2","phanhuy95");
        if(checkConnection.checkMobileInternetConn() && countSplash.getInstance().getCount() == 0){
            Log.d("splash",countSplash.getInstance().getCount()+"");
            countSplash.getInstance().setCount(1);
            setupAsyntaskAlbum();
            setupAsyntask();

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);

    }

    public static void setupAsyntaskAlbum() {
        try {
            SplashScreenActivity.taskAlbum3.execute(URLUS).get();
            SplashScreenActivity.taskAlbum2.execute(URLKOR).get();
            SplashScreenActivity.taskAlbum1.execute(URLVIET).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public static void setupAsyntask() {
        Log.d("splash2","khongancut");
        try {
            SplashScreenActivity.task2.execute(URLKOREA).get();
            SplashScreenActivity.task3.execute(URLUSUK).get();
            SplashScreenActivity.task1.execute(URLVIETNAM).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static class DownloadTask extends AsyncTask<String, Void, Void> {
       public ArrayList<Song> listNews = null;
       public ArrayList<Album> listAlbum = null;

        public DownloadTask() {
        }

        @Override
       protected void onPreExecute() {
           super.onPreExecute();

       }

       @Override
        public Void doInBackground(String... strings) {
           listNews = new ArrayList<Song>();
           listAlbum = new ArrayList<>();
            Document document = null;
            int length = 20;
            try {
                document = (Document) Jsoup.connect(strings[0]).get();
                if(strings[0].equals(URLKOREA) || strings[0].equals(URLUSUK) || strings[0].equals(URLVIETNAM)){
                    Elements subjectElements = document.select("div.text2");
                    Elements categoryElements = document.select("div.texte2");
                    if (subjectElements != null && subjectElements.size() > 0) {
                        if(subjectElements.size() < 20) {
                            length = subjectElements.size();
                        }
                        for (int i = 0; i < length; i++) {
                            Element titleSubject = subjectElements.get(i).getElementsByTag("a").first();
                            Element artistSubject = subjectElements.get(i).getElementsByTag("p").first();
                            Element categorySubject = categoryElements.get(i).getElementsByTag("p").last();
                            if (titleSubject != null && artistSubject != null) {
                                String title = titleSubject.text();
                                String link = titleSubject.attr("href");
                                String artist = artistSubject.text();
                                String category = categorySubject.text();
                                listNews.add(new Song(i,link,title,category,artist));
                            }
                        }
                    }
                }
                if(strings[0].equals(URLVIET) || strings[0].equals(URLUS) || strings[0].equals(URLKOR)){
                    Elements firstElements = document.select("span.genmed");
                    Elements secondElements = document.select("span.gen");

                    if (firstElements != null && firstElements.size() > 0 ) {
                        Log.d("www",secondElements.size()+"");
                        for (int i = 0,j = 0; i < firstElements.size(); i++,j++) {
                            Element imageSubject = firstElements.get(i).getElementsByTag("img").first();
                            Element titleSubject = secondElements.get(i).getElementsByTag("a").first();
                            Element categorySubject = secondElements.get(i).getElementsByTag("span").last();
                            if (titleSubject != null && imageSubject != null) {
                                String title = titleSubject.text();
                                String link = titleSubject.attr("href");
                                String category = categorySubject.text();
                                String image = imageSubject.attr("src");
                                listAlbum.add(new Album(image,title,category,link));
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
}
