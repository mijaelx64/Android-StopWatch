package app4.com.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;
import android.os.Handler;

public class StopWatchActivity extends Activity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        if(savedInstanceState!=null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    /**
     * Saving instance state when rotating screen and onDestroy() is called.
     * @param saveInstanceState save instance state object
     */
    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        saveInstanceState.putInt("seconds",this.seconds);
        saveInstanceState.putBoolean("running",this.running);
        saveInstanceState.putBoolean("wasRunning",wasRunning);
    }

    @Override
    protected void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (wasRunning)
            running = true;
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    private void runTimer(){
        final TextView timeView = (TextView) findViewById(R.id.time_view);

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {

                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;

                String time = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,secs);

                timeView.setText(time);

                if (running)
                    seconds++;

                handler.postDelayed(this,1000);
            }
        });
    }
}
