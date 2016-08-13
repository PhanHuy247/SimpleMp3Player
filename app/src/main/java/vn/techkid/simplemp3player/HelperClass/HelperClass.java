package vn.techkid.simplemp3player.HelperClass;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;

import vn.techkid.simplemp3player.Model.Song;

/**
 * Created by Laptop88 on 8/12/2016.
 */
public class HelperClass {
    private ArrayList<Integer> integers = new ArrayList<>();
    private int n;


    public HelperClass(int n){
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
        return integers.remove(rand);
    }



}
