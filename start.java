package drishti.assisted.com.drishti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by admin on 3/16/2015.
 */
public class Start extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        Thread timer = new Thread(){

            public void run(){

                try{
                    sleep(3000);
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }finally {
                    Intent begin = new Intent("drishti.assisted.com.drishti.MAINACTIVITY");
                    startActivity(begin);

                }
            }
        };
        timer.start();
    }
/*
    @Override
    protected void onPause() {
        super.onPause();
    }
    */
}
