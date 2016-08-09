package vn.techkid.simplemp3player.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.techkid.simplemp3player.Adapter.AlbumAdapter;
import vn.techkid.simplemp3player.Adapter.ListCardArtistAdapter;
import vn.techkid.simplemp3player.Model.Album;
import vn.techkid.simplemp3player.Model.Artist;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAlbum extends Fragment {

    ArrayList<Album>[] listAlbum;
    RecyclerView[] recyclerView;
    AlbumAdapter[] adapter;

    public FragmentAlbum() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        listAlbum = new ArrayList[6];
        adapter = new AlbumAdapter[6];
        recyclerView = new RecyclerView[6];
        setupNewObject();

        createDataForListAlbum();
        setupView(view);
        getActivity().setTitle("Album");
        setUpAdapter();

        return  view;
    }

    private void createDataForListAlbum() {
        for(int i = 0; i < 6 ; i++){
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
            listAlbum[i].add(new Album("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
        }
    }

    private void setUpAdapter() {
        for(int i = 0; i < 6 ; i++){
            adapter[i] = new AlbumAdapter(listAlbum[i],getActivity());
            recyclerView[i].setAdapter(adapter[i]);
            adapter[i].setOnItemClickListener(new AlbumAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int postion) {
                    Log.d("POSITION", String.valueOf(postion));
                }
            });
        }
    }

    private void setupNewObject() {
        for(int i = 0; i < 6; i++){
            listAlbum[i] = new ArrayList<>();
        }
    }

    private void setupView(View view) {
        recyclerView[0] = (RecyclerView) view.findViewById(R.id.listAlbum);
        recyclerView[1] = (RecyclerView) view.findViewById(R.id.listAlbumUSUK);
        recyclerView[2] = (RecyclerView) view.findViewById(R.id.listAlbumChiNa);
        recyclerView[3] = (RecyclerView) view.findViewById(R.id.listAlbumKorea);
        recyclerView[4] = (RecyclerView) view.findViewById(R.id.listAlbumJapan);
        recyclerView[5] = (RecyclerView) view.findViewById(R.id.listAlbumBeatBlack);
    }

}
