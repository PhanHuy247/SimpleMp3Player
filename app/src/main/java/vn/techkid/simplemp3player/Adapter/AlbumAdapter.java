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
import vn.techkid.simplemp3player.R;

/**
 * Created by HP on 8/4/2016.
 */
public class AlbumAdapter extends  RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>{

    ArrayList<Album> listAlbum;
    Context mContext;
    View itemView;

    OnItemClickListener onItemClickListener;

    public AlbumAdapter(ArrayList<Album> listAlbum, Context mContext) {
        this.listAlbum = listAlbum;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AlbumViewHolder viewHolder = null;
        itemView
                = layoutInflater.inflate(R.layout.item_album, parent, false);
        viewHolder = new AlbumViewHolder(itemView);
        viewHolder.onItemClickListener = onItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        holder.txtNameAlbum.setText(listAlbum.get(position).getNameAlbum());
        holder.txtNameAlbumArtist.setText(listAlbum.get(position).getNameArtist());
        holder.category.setText(listAlbum.get(position).getCategory());
        Picasso.with(itemView.getContext())
                .load(listAlbum.get(position).getImageAlbum())
                .resize(150,150)
                .into(holder.imgAlbum);

    }


    @Override
    public int getItemCount() {
        return listAlbum.size();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        OnItemClickListener onItemClickListener;
        public TextView txtNameAlbum;
        public TextView txtNameAlbumArtist;
        public TextView category;
        public ImageView imgAlbum;
        public AlbumViewHolder(final View itemView) {
            super(itemView);
            txtNameAlbum = (TextView)itemView.findViewById(R.id.txtNameAlbum);
            txtNameAlbumArtist = (TextView)itemView.findViewById(R.id.txtNameAlbumArtist);
            imgAlbum = (ImageView)itemView.findViewById(R.id.imgAlbum);
            category = (TextView) itemView.findViewById(R.id.categotyAlbum);

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
