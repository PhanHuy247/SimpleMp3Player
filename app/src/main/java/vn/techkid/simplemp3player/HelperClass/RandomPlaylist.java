package vn.techkid.simplemp3player.HelperClass;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Laptop88 on 8/12/2016.
 */
public class RandomPlaylist {
    private ArrayList<Integer> integers = new ArrayList<>();
    private int n;
    public ArrayList<Integer> getIntegers() {
        return integers;
    }


    public void setIntegers(ArrayList<Integer> integers) {
        this.integers = integers;
    }

    public RandomPlaylist(int n){
        this.n = n;
        for (int i = 0; i < n; i++) {
            integers.add(i);
        }

    }
    public int getRandomPos(){
        if (integers.size()==1){
            for (int i = 1; i < n+1; i++) {
                integers.add(i);
            }
            return integers.remove(0);
        }
        int rand = (int)(Math.random()*integers.size());
        Log.d("random", integers.get(rand)+"");
        return integers.remove(rand);
    }

}
