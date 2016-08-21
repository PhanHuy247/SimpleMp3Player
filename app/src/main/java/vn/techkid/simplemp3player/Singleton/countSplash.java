package vn.techkid.simplemp3player.Singleton;

/**
 * Created by HP on 8/21/2016.
 */
public class countSplash {
    private static countSplash ourInstance = new countSplash();
    private int count;
    public static countSplash getInstance() {
        return ourInstance;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private countSplash() {
    }
}
