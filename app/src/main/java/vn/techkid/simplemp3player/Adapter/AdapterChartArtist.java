package vn.techkid.simplemp3player.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import vn.techkid.simplemp3player.Model.Artist;
import vn.techkid.simplemp3player.R;

/**
 * Created by HP on 8/4/2016.
 */
public class AdapterChartArtist extends RecyclerView.Adapter<AdapterChartArtist.CardViewHolder> {

    ArrayList<Artist> listArtist;
    Context mContext;

    OnItemClickListener onItemClickListener;

    public AdapterChartArtist(ArrayList<Artist> listArtist, Context mContext) {
        this.listArtist = listArtist;
        this.mContext = mContext;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardViewHolder viewHolder = null;
                View itemView
                        = layoutInflater.inflate(R.layout.item_artist, parent, false);
                viewHolder = new CardViewHolder(itemView);
                viewHolder.onItemClickListener = onItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.txtNameArtist.setText(listArtist.get(position).getNameArtist());
        holder.txtChartNumber.setText(listArtist.get(position).getCharNumber());
    }

    @Override
    public int getItemCount() {
        return listArtist.size();
    }
    static class CardViewHolder extends RecyclerView.ViewHolder {
        OnItemClickListener onItemClickListener;
        public TextView txtNameArtist;
        public TextView txtChartNumber;
        public CardViewHolder(final View itemView) {
            super(itemView);
            txtChartNumber = (TextView)itemView.findViewById(R.id.txtchartArtistNumber);
            txtNameArtist = (TextView)itemView.findViewById(R.id.txtNameArtistRating);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("HASD", "DKDKDJSDSD1");
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(itemView, getPosition());
                        Log.d("phanhuy", String.valueOf(getPosition()));
                    }else{
                        Log.d("ppp","111");
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int postion);
    }
}
