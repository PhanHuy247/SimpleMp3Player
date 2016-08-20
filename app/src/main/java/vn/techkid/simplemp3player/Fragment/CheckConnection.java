package vn.techkid.simplemp3player.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * Created by HP on 8/20/2016.
 */
public class CheckConnection {
    private Context _context;

    public CheckConnection(Context context) {
        this._context = context;
    }

    public boolean checkMobileInternetConn() {
        //T?o d?i tuong ConnectivityManager d? tr? v? thông tin m?ng
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //N?u d?i tu?ng khác null
        if (connectivity != null) {
            //Nh?n thông tin m?ng
            NetworkInfo infoMobile = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (infoMobile != null) {
                //Tìm ki?m thi?t b? dã k?t n?i du?c internet chua
                if (infoMobile.isConnected()) {
                    return true;
                }
            }
            NetworkInfo infoWifi = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (infoWifi != null) {
                //Tìm ki?m thi?t b? dã k?t n?i du?c internet chua
                if (infoWifi.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
}
