package vn.techkid.simplemp3player.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class CategoryFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    static View view;
    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Category");
        if(view == null){
            view =  inflater.inflate(R.layout.fragment_category, container, false);
            setUpView(view);
            Log.d("phanhuy","phanhuy");
        }

        return view;
    }
    private ViewPagerAdapter setUpViewPage() {
            adapter.addFrag(new CategoryInVietNam(),"VietNam");
            adapter.addFrag(new CategoryInKorea()," Korea ");
            adapter.addFrag(new CategoryInUSUK()," US-UK ");
            adapter.addFrag(new CategoryChina()," China ");
            adapter.addFrag(new CategoryJapan()," Japan ");
            Log.d("huy", String.valueOf(adapter.getCount()));

        return adapter;
    }

    private void setUpView(View view) {
        adapter = new ViewPagerAdapter(getFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.viewpagercategory);
        viewPager.setAdapter(setUpViewPage());
        tabLayout = (TabLayout) view.findViewById(R.id.tabscategory);
        tabLayout.setupWithViewPager(viewPager);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(android.support.v4.app.Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
