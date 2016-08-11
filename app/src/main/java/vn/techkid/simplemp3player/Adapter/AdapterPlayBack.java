package vn.techkid.simplemp3player.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.techkid.simplemp3player.Model.Album;
import vn.techkid.simplemp3player.Model.PlayBack;
import vn.techkid.simplemp3player.R;

/**
 * Created by HP on 8/5/2016.
 */
public class AdapterPlayBack extends  RecyclerView.Adapter<AdapterPlayBack.PlayBackViewHolder> {

    ArrayList<PlayBack> listPlayBack;
    Context mContext;
    View itemView;

    OnItemClickListener onItemClickListener;
    public AdapterPlayBack(ArrayList<PlayBack> listPlayBack, Context mContext) {
        this.listPlayBack = listPlayBack;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public PlayBackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        PlayBackViewHolder viewHolder = null;
        itemView
                = layoutInflater.inflate(R.layout.item_play_back, parent, false);
        viewHolder = new PlayBackViewHolder(itemView);
        viewHolder.onItemClickListener = onItemClickListener;
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(PlayBackViewHolder holder, int position) {
        Log.d("phanhuy", String.valueOf(listPlayBack.size()));
        holder.txtNamePlayBack.setText(listPlayBack.get(position).getNameSongPlayBack());
        holder.txtNameArtist.setText(listPlayBack.get(position).getNameArtistPlayBack());
        holder.category.setText(listPlayBack.get(position).getNameCategoryPlayBack());
        holder.ranking.setText(listPlayBack.get(position).getRanking());

    }

    @Override
    public int getItemCount() {
        return listPlayBack.size();
    }
    public static class PlayBackViewHolder extends RecyclerView.ViewHolder {
        OnItemClickListener onItemClickListener;
        public TextView txtNamePlayBack;
        public TextView txtNameArtist;
        public TextView category;
        public TextView ranking;
        public PlayBackViewHolder(final View itemView) {
            super(itemView);
            txtNamePlayBack = (TextView)itemView.findViewById(R.id.txtNameSongPlayBack);
            txtNameArtist = (TextView)itemView.findViewById(R.id.txtNameArtistPlayBack);
            category = (TextView) itemView.findViewById(R.id.categoryPlayBack);
            ranking = (TextView) itemView.findViewById(R.id.txtchartPlayBackNumber);

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
