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

import vn.techkid.simplemp3player.Adapter.AdapterCategory;
import vn.techkid.simplemp3player.Model.Category;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryTraditionalVietNamFragment extends Fragment {
    ArrayList<Category> listCategory;
    RecyclerView recyclerView;
    AdapterCategory adapter;


    public CategoryTraditionalVietNamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_category_traditional_viet_nam, container, false);
        setupView(view);
        getActivity().setTitle("Category VietNam");
        listCategory = new ArrayList<>();
        createDataForListCategory();
        adapter = new AdapterCategory(listCategory,getActivity());
        LinearLayoutManager linearManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdapterCategory.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Log.d("POSITION", String.valueOf(postion));
            }
        });
        return view;
    }

    private void createDataForListCategory() {
        listCategory.add(new Category("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));

    }

    private void setupView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.listCategory);
    }

}
