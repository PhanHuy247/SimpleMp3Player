package vn.techkid.simplemp3player.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import vn.techkid.simplemp3player.Adapter.AdapterChartArtist;
import vn.techkid.simplemp3player.Model.Artist;
import vn.techkid.simplemp3player.Interface.OnTaskCompleted;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment implements OnTaskCompleted {
    private static final String URL = "http://chiasenhac.vn/";
    ArrayList<Artist> listNews;
    RecyclerView recyclerView;
    AdapterChartArtist listCardAdapter;
    private int posFirst,posLast;
    ArtistSongCountryFragment artistSongVietNamFragment;
    TextView txtcheck;
    CheckConnection checkConnection;
    DownloadTask task;

    public ArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Artist");
        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        listNews  = new ArrayList<>();
        setupView(view);
        setupPosForChart(posFirst,posLast);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkConnection = new CheckConnection(getContext());
        if(checkConnection.checkMobileInternetConn()){
            setupAsyntask();
            txtcheck.setVisibility(View.GONE);
        }else{
            if(listNews.size() == 0)
                txtcheck.setVisibility(View.VISIBLE);
        }
    }

    public void setupPosForChart(int posFirst, int posLast) {
        this.posFirst = posFirst;
        this.posLast = posLast;
    }

    private void setupAsyntask() {
        task = new DownloadTask(posFirst,posLast,this);
        task.execute(URL);
    }

    private void setupView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.listCard);
        txtcheck = (TextView) view.findViewById(R.id.txtCheckArtist);
    }

    @Override
    public void onTaskCompleted() {
        listNews = task.listNews ;
        listCardAdapter = new AdapterChartArtist(listNews,getActivity());
        LinearLayoutManager linearManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.setAdapter(listCardAdapter);
        listCardAdapter.setOnItemClickListener(new AdapterChartArtist.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Log.d("POSITION", String.valueOf(postion));
                artistSongVietNamFragment = new ArtistSongCountryFragment();
                artistSongVietNamFragment.setupUrl(listNews.get(postion).getLink());
                getParentFragment().getFragmentManager().beginTransaction().replace(R.id.frame_container,artistSongVietNamFragment )
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    static class DownloadTask extends AsyncTask<String, Void, Void> {
        private OnTaskCompleted listener;
        ArrayList<Artist> listNews;
        int posFirst,posLast;
        public DownloadTask(int posFirst,int posLast,OnTaskCompleted listener){
            this.posFirst = posFirst;
            this.posLast = posLast;
            this.listener = listener;
        }
        @Override
        protected Void doInBackground(String... strings) {
            listNews = new ArrayList<>();
            Document document = null;
            int j;
            try {
                document = (Document) Jsoup.connect(strings[0]).get();
                Elements subjectElements = document.select("div.artist-txt");
                if (subjectElements != null && subjectElements.size() > 0) {
                    for (int i = posFirst; i < posLast; i++) {
                        j = i;
                        Element titleSubject = subjectElements.get(i).getElementsByTag("a").first();
                        if (titleSubject != null) {
                            String title = titleSubject.attr("title");
                            String link = titleSubject.attr("href");
                            if(i > 21 && i < 32) j -= 22;
                            if(i > 31 && i < 54) j -= 32;
                            listNews.add(new Artist(i + 1 + "", link, j+1+"",title,link));
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
            listener.onTaskCompleted();
        }
    }
}
