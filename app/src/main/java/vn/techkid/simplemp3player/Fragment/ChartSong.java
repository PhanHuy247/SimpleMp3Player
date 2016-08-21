package vn.techkid.simplemp3player.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.concurrent.ExecutionException;

import vn.techkid.simplemp3player.Adapter.AdapterChartSong;
import vn.techkid.simplemp3player.Getter.ChartSongListGetter;
import vn.techkid.simplemp3player.Interface.OnTaskCompleted;
import vn.techkid.simplemp3player.Service.FloatingControlWindow;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartSong extends ListSongsFragment implements OnTaskCompleted {
    ChartSongListGetter getter;
    public ChartSong(){
        intentKey = "chartSong";
        title = "Bảng xếp hạng";
        isHot = true;
    }
    @Override
    public void setupAsynTask() {
        checkConnection = new CheckConnection(getContext());
        if(checkConnection.checkMobileInternetConn()){
            getter = new ChartSongListGetter(URL,this);
            getter.execute();
            Log.d("chartsong12345","12345");
        }
    }

    @Override
    public void setupAdapter() {
    }

    @Override
    public void onTaskCompleted() {
        songs = getter.songs;
        AdapterChartSong adaper = new AdapterChartSong(songs,getActivity(), isHot);
        lv_songs.setAdapter(adaper);
        lv_songs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FloatingControlWindow.class);
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                bundle.putString("key", intentKey);
                bundle.putSerializable("list",songs);
                intent.putExtra("bundle",bundle);
                getActivity().startService(intent);

            }
        });

    }
}
