package dead.timer;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button start_button,stop_button;
    private TextView Timer_Textview;
    Handler TimerHandler;

    Long timeInMills=0L;
    Long updateTime=0L;
    Long startTime=0L;
    Long TimeSwap=0L;
    final Long postTimeDelayed=0l;

    boolean isTimerEnabled=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_button=findViewById(R.id.Start_Button);
        stop_button=findViewById(R.id.Stop_Button);
        Timer_Textview=findViewById(R.id.Timer_Text);

        TimerHandler=new Handler();
        start_button.setOnClickListener(this);
        stop_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

        switch (id){
            case R.id.Start_Button:

                if (isTimerEnabled){
                    //pause button
                    start_button.setText("Start");
                    TimerHandler.removeCallbacks(updateTimer);
                    isTimerEnabled=!isTimerEnabled;
                    TimeSwap+=timeInMills;


                }
                else {
                    //start button
                    start_button.setText("Pause");
                    startTime= SystemClock.uptimeMillis();
                    TimerHandler.postDelayed(updateTimer,postTimeDelayed);
                    isTimerEnabled=!isTimerEnabled;
                }

                break;
            case R.id.Stop_Button:
                Timer_Textview.setText("00:00:00");
                timeInMills=0L;
                updateTime=0L;
                startTime=0L;
                TimeSwap=0L;
                isTimerEnabled=false;
                TimerHandler.removeCallbacks(updateTimer);
                start_button.setText("Start");


                break;
                default:

                    break;
        }
    }

    Runnable updateTimer=new Runnable() {
        @Override
        public void run() {
            timeInMills=SystemClock.uptimeMillis()-startTime;
            updateTime=TimeSwap+timeInMills;

            int seconds= (int) (updateTime/1000);
            int minutes=seconds/60;
            seconds=seconds%60;
            int milliseconds= (int) (updateTime%1000);

            Timer_Textview.setText(String.format("%02d",minutes)+":"+String.format("%02d",seconds)+":"+String.format("%02d",milliseconds));

            TimerHandler.postDelayed(updateTimer,postTimeDelayed);


        }
    };
}
