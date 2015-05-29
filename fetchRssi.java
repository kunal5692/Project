package drishti.assisted.com.drishti;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * Created by admin on 3/20/2015.
 */
public class FetchRssi {

    /**
     * wifi status
     */
    WifiManager wifiManager;
    WifiInfo wifiInfo;
    Context context;

    public FetchRssi(Context context) {
        this.context = context;
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * scan RSSI value
     */
    public String scan() {
        String[] access_points = {"AndroidAP", "Mannish", "Vipul-Ubuntu"};
        int maxCount=0;
        int apCount = 0;
        StringBuffer stringBuffer = new StringBuffer("");
        //wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

        if (wifiManager.isWifiEnabled()) {
            if (wifiManager.startScan()) {
                List<ScanResult> scanResults = wifiManager.getScanResults();
                if (scanResults != null) {
                    //filter by AP name
                    for (int index = 0; index < scanResults.size(); index++) {
                        ScanResult scan_result = scanResults.get(index);
                        //if((scan_result.SSID).contains(ACCESS_POINT_FILTER_NAME)) {
                        for (String temp: access_points) {
                            if ((scan_result.SSID).contains(temp)) {
                                stringBuffer.append("" + scan_result.SSID + ":" + scan_result.level);
                                apCount++;
                                if (apCount == maxCount)
                                    break;

                            }
                        }
                    }
                }
            }

        }
        return (apCount + new String(stringBuffer));
    }

}
