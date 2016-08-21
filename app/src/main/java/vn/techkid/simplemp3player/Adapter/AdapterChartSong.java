package vn.techkid.simplemp3player.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import vn.techkid.simplemp3player.Activity.MainActivity;
import vn.techkid.simplemp3player.Fragment.ChartSong;
import vn.techkid.simplemp3player.Getter.DownloadTask;
import vn.techkid.simplemp3player.Getter.SongGetter;
import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Model.Song;

/**
 * Created by HP on 7/27/2016.
 */
public class AdapterChartSong extends BaseAdapter {
    ArrayList<Song> arrayList = new ArrayList<>();
    private  boolean isHot;
    private Context mContext;
    private int i = R.layout.item_song_chart;
    public AdapterChartSong(ArrayList<Song> arrayList, Context mContext, int i){
        this.arrayList = arrayList;
        this.mContext = mContext;
        this.i = i;
    }
    public AdapterChartSong(ArrayList<Song> arrayList, Context mContext, boolean isHot){
        this.arrayList = arrayList;
        this.isHot = isHot;
        this.mContext = mContext;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(i, parent, false);
            CommentViewHolder holder = new CommentViewHolder();
            holder.txtNameSong = (TextView)convertView.findViewById(R.id.tv_title);
            holder.txtNameArtist = (TextView)convertView.findViewById(R.id.tv_artist);
            holder.txtRank = (TextView)convertView.findViewById(R.id.txtRank);
            if ( i == R.layout.item_song_chart){
                holder.imgButton = (ImageButton) convertView.findViewById(R.id.imgbuttonsongchart);
            }
            convertView.setTag(holder);
        }

        CommentViewHolder holder = (CommentViewHolder) convertView.getTag();
        holder.txtNameSong.setText(getItem(position).getTitle());
        holder.txtNameArtist.setText(getItem(position).getArtist());
        holder.txtRank.setText(position+1+"");
        if (i == R.layout.item_song_chart){
            holder.imgButton.setFocusable(false);
            holder.imgButton.setFocusableInTouchMode(false);
            holder.imgButton.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    final SongGetter getter = new SongGetter(getItem(position).getAccessLink(),position,isHot);
                    try {
                        getter.execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                    builder.setTitle("Choose Quality Link Dowload :");
                    final ArrayList<String> arrayList = new ArrayList<String>();
                    Log.d("adapter",getter.listUrl.get(0).length()+"");
                    for(int i = 0; i < getter.listUrl.size(); i++){
                        int j = getter.listUrl.get(i).indexOf("[");
                        arrayList.add(getter.listUrl.get(i).substring(j));
                    }
                    final CharSequence[] LinkUrl = arrayList.toArray(new String[getter.listUrl.size()]);
                    builder.setItems(LinkUrl, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            String url = getter.listUrl.get(item);
                            Toast.makeText(mContext, getter.listUrl.get(item), Toast.LENGTH_SHORT).show();
                            String songDetail = getItem(position).getTitle() + " - " + getItem(position).getArtist();
                            new DownloadTask(mContext, songDetail, getItem(position).getTitle()+" "+arrayList.get(item)).execute(url);
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }



        return convertView;
    }

    public static class CommentViewHolder{
        public TextView txtNameSong;
        public TextView txtNameArtist;
        public TextView txtRank;
        public ImageButton imgButton;
    }

}

