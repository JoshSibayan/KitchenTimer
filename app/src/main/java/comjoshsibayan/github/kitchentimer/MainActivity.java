package comjoshsibayan.github.kitchentimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final private String LOG_TAG = "test_timer";

    private int seconds = 0;
    private CountDownTimer timer = null;
    private static final int ONE_SECOND_IN_MILLIS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayTime();
    }

    public void onClickPlus(View v) {
        seconds += 60;
        displayTime();
    }

    public void onClickMinus(View v) {
        seconds = Math.max(0, seconds - 60);
        displayTime();
    }

    public void onReset(View v) {
        seconds = 0;
        cancelTimer();
        displayTime();
    }

    public void onClickStart(View v) {
        if (seconds == 0) {
            cancelTimer();
        }
        if (timer == null) {
            // We create a new timer.
            timer = new CountDownTimer(seconds * ONE_SECOND_IN_MILLIS, ONE_SECOND_IN_MILLIS) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d(LOG_TAG, "Tick at " + millisUntilFinished);
                    seconds = Math.max(0, seconds - 1);
                    displayTime();
                }

                @Override
                public void onFinish() {
                    seconds = 0;
                    timer = null;
                    displayTime();
                }
            };
            timer.start();
        }
    }

    public void onClickStop(View v) {
        cancelTimer();
        displayTime();
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    // Update time display
    private void displayTime() {
        Log.d(LOG_TAG, "Displaying time " + seconds);
        TextView v = (TextView) findViewById(R.id.display);
        int m = seconds / 60;
        int s = seconds % 60;
        v.setText(String.format("%d:%02d", m, s));

        // Button management
        Button stopButton = (Button) findViewById(R.id.button_stop);
        Button startButton = (Button) findViewById(R.id.button_start);
        startButton.setEnabled(timer == null && seconds > 0);
        stopButton.setEnabled(timer != null && seconds > 0);
    }
}
