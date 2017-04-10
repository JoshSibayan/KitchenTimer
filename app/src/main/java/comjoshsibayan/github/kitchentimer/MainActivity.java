package comjoshsibayan.github.kitchentimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "test_timer";

    private int seconds = 0;
    private CountDownTimer timer = null;
    private static final int ONE_SECOND_IN_MILLIS = 100;
    private int clickCount = 0;
    private boolean clicked = false;

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

        /*// Update preset buttons when plus is pressed
        final int m = seconds / 60;
        final int s = seconds % 60;

        TextView v1 = (TextView) findViewById(R.id.display);
        Button presetTime1 = (Button) findViewById(R.id.preset1);
        presetTime1.setText(String.format("%d:%02d", m, s));
        v1.setText(String.format("%d:%02d", m, s));*/
    }

    // If start button pressed then display time on button
    // This
    public void onClickPreset1(View v) {
        /*String preset1;
        final int m = seconds / 60;
        final int s = seconds % 60;

        TextView v1 = (TextView) findViewById(R.id.display);
        Button presetTime1 = (Button) findViewById(R.id.preset1);
        Button startButton = (Button) findViewById(R.id.button_start);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = true;
            }
        });

        if(clickCount == 1) {
            Log.d(LOG_TAG, "Start clicked once");
            Button stopButton = (Button) findViewById(R.id.button_stop);
            Button startButton2 = (Button) findViewById(R.id.button_start);

            startButton2.setEnabled(timer == null && seconds > 0);
            stopButton.setEnabled(timer != null && seconds > 0);
            preset1 = presetTime1.getText().toString();
            v1.setText(preset1);
            Log.d(LOG_TAG, "Displaying time1 " + preset1);
            //previous(m, s);
        } else {
            Log.d(LOG_TAG, "Start clicked " + clickCount + " times");
            TextView v2 = (TextView) findViewById(R.id.preset1);
            preset1 = v2.getText().toString();
            v1.setText(preset1);
            Log.d(LOG_TAG, "Preset1 time is " + preset1);

        }*/

        // onClickStart(v1);

        TextView v1 = (TextView) findViewById(R.id.display);
        Button presetTime1 = (Button) findViewById(R.id.preset1);

        v1.setText(presetTime1.getText());


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
            // clickCount++;

            Button presetTime1 = (Button) findViewById(R.id.preset1);
            Button presetTime2 = (Button) findViewById(R.id.preset2);
            Button presetTime3 = (Button) findViewById(R.id.preset3);

            int[] array = new int[2];
            array = displayTime();
            String FirstTime = String.format(String.format("%d:%02d", array[0], array[1]));

            // If times in display and any button are the same then keep same button
            // If times are different then shift times
            if(!presetTime1.getText().equals(FirstTime)) {
                presetTime3.setText(presetTime2.getText());
                presetTime2.setText(presetTime1.getText());
                presetTime1.setText(FirstTime);
            }
        }
    }

    public void onClickStop(View v) {
        cancelTimer();
        displayTime();

        Button presetTime1 = (Button) findViewById(R.id.preset1);
        Button presetTime2 = (Button) findViewById(R.id.preset2);
        Button presetTime3 = (Button) findViewById(R.id.preset3);

        int[] array = new int[2];
        array = displayTime();
        String FirstTime = String.format(String.format("%d:%02d", array[0], array[1]));

        // Shift time between buttons
        presetTime3.setText(presetTime2.getText());
        presetTime2.setText(presetTime1.getText());
        presetTime1.setText(FirstTime);
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    // Update time display
    private int[] displayTime() {
        Log.d(LOG_TAG, "Displaying time " + seconds);
        int m = seconds / 60;
        int s = seconds % 60;

        TextView v = (TextView) findViewById(R.id.display);
        v.setText(String.format("%d:%02d", m, s));

        // Button management
        Button stopButton = (Button) findViewById(R.id.button_stop);
        Button startButton = (Button) findViewById(R.id.button_start);
        startButton.setEnabled(timer == null && seconds > 0);
        stopButton.setEnabled(timer != null && seconds > 0);

        int array[] = {m, s};
        return array;
    }

}
