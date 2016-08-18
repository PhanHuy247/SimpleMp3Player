package vn.techkid.simplemp3player.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Model.Song;

/**
 * Created by HP on 7/27/2016.
 */
public class AdapterChartSong extends BaseAdapter {
    ArrayList<Song> arrayList = new ArrayList<>();
    public AdapterChartSong(ArrayList<Song> arrayList){
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Song getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_song_chart, parent, false);
            CommentViewHolder holder = new CommentViewHolder();
            holder.txtNameSong = (TextView)convertView.findViewById(R.id.tv_title);
            holder.txtNameArtist = (TextView)convertView.findViewById(R.id.tv_artist);
            holder.txtRank = (TextView)convertView.findViewById(R.id.txtRank);
            convertView.setTag(holder);
        }

        CommentViewHolder holder = (CommentViewHolder) convertView.getTag();
        holder.txtNameSong.setText(getItem(position).getTitle());
        holder.txtNameArtist.setText(getItem(position).getArtist());
        holder.txtRank.setText(getItem(position).getPosition()+1+"");

        return convertView;
    }

    public static class CommentViewHolder{
        public TextView txtNameSong;
        public TextView txtNameArtist;
        public TextView txtRank;
    }
}
