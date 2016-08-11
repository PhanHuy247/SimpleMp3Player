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

import vn.techkid.simplemp3player.Model.Category;
import vn.techkid.simplemp3player.Model.PlayBack;
import vn.techkid.simplemp3player.R;

/**
 * Created by HP on 8/8/2016.
 */
public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.CategoryViewHolder>{
    ArrayList<Category> listCategory;
    Context mContext;
    View itemView;

    OnItemClickListener onItemClickListener;
    public AdapterCategory(ArrayList<Category> listCategory, Context mContext) {
        this.listCategory = listCategory;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CategoryViewHolder viewHolder = null;
        itemView
                = layoutInflater.inflate(R.layout.item_category, parent, false);
        viewHolder = new CategoryViewHolder(itemView);
        viewHolder.onItemClickListener = onItemClickListener;
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Log.d("phanhuy", String.valueOf(listCategory.size()));
        holder.txtNameCategory.setText(listCategory.get(position).getNameSongCategory());
        holder.txtNameArtist.setText(listCategory.get(position).getNameArtistCategory());
        holder.category.setText(listCategory.get(position).getNameCategoryQuality());
        holder.ranking.setText(listCategory.get(position).getRanking());
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }
    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        OnItemClickListener onItemClickListener;
        public TextView txtNameCategory;
        public TextView txtNameArtist;
        public TextView category;
        public TextView ranking;
        public CategoryViewHolder(final View itemView) {
            super(itemView);
            txtNameCategory = (TextView)itemView.findViewById(R.id.txtNameSongCategory);
            txtNameArtist = (TextView)itemView.findViewById(R.id.txtNameArtistCategory);
            category = (TextView) itemView.findViewById(R.id.categoryQuality);
            ranking = (TextView) itemView.findViewById(R.id.txtchartCategoryNumber);

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
