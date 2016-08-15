package vn.techkid.simplemp3player.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;

/**
 * Created by HP on 8/10/2016.
 */
public class AdapterSongArtist extends RecyclerView.Adapter<AdapterSongArtist.ChartSongViewHolder> {
    ArrayList<Song> listSong;
    Context mContext;
    View itemView;

    OnItemClickListener onItemClickListener;
    public AdapterSongArtist(ArrayList<Song> listSong, Context mContext) {
        this.listSong = listSong;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ChartSongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ChartSongViewHolder viewHolder = null;
        itemView
                = layoutInflater.inflate(R.layout.item_artist_chart_song, parent, false);
        viewHolder = new ChartSongViewHolder(itemView);
        viewHolder.onItemClickListener = onItemClickListener;
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ChartSongViewHolder holder, int position) {
        Log.d("phanhuy", String.valueOf(listSong.size()));
        holder.txtNameSong.setText(listSong.get(position).getTitle());
        holder.txtNameArtist.setText(listSong.get(position).getArtist());
        holder.category.setText(listSong.get(position).getAlbum());
        holder.ranking.setText(listSong.get(position).getPosition()+"");
    }

    @Override
    public int getItemCount() {
        return listSong.size();
    }
    public static class ChartSongViewHolder extends RecyclerView.ViewHolder {
        OnItemClickListener onItemClickListener;
        public TextView txtNameSong;
        public TextView txtNameArtist;
        public TextView category;
        public TextView ranking;
        public ChartSongViewHolder(final View itemView) {
            super(itemView);
            txtNameSong = (TextView)itemView.findViewById(R.id.txtNameSongChartSong);
            txtNameArtist = (TextView)itemView.findViewById(R.id.txtNameArtistChartSong);
            category = (TextView) itemView.findViewById(R.id.categoryChartSong);
            ranking = (TextView) itemView.findViewById(R.id.txtchartNumberChartSong);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(itemView, getPosition());
                        Log.d("phanhuy", String.valueOf(getPosition()));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int postion);
    }
}
