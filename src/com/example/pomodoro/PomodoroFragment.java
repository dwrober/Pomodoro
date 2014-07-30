package com.example.pomodoro;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PomodoroFragment extends Fragment {
	public String taskName = "";
	long startTime = 0;
	private CountDownTimer cdt;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,    		
		Bundle savedInstanceState) {
        // Inflate the layout for this fragment		
        return inflater.inflate(R.layout.fragment_pomodoro, container, false);
    }
	
	@Override
	public void onStart() {
		super.onStart();
		setTask();
		setupTimer();
	}
	
	private void setupTimer() {
		Button b = (Button) getActivity().findViewById(R.id.start_btn);
	    b.setText("start");
	    b.setOnClickListener(new View.OnClickListener() {
	
	        @Override
	        public void onClick(View v) {
	            Button b = (Button) v;
	            if (b.getText().equals("stop")) {
	                stopTimer();
	                b.setText("start");
	            } else {
	                startTimer();
	                b.setText("stop");
	            }
	        }
	    });
	}
	
	private void stopTimer() {
		cdt.cancel();
	}
	
	private void startTimer() {
		//int duration = 25*60*1000;
		int duration = 10000;
		TextView timerText = (TextView) getActivity().findViewById(R.id.timer_text);
		cdt = new CountDownTimer(duration, 1000) {
			
		public void onTick(long millisUntilFinished) {
		    int seconds = (int)millisUntilFinished/1000;
		  	int minutes = seconds / 60;
		  	seconds = seconds % 60;

	  		TextView timerText = (TextView) getActivity().findViewById(R.id.timer_text);
	  		timerText.setText(String.format("%d:%02d", minutes, seconds));
		}

            public void onFinish() {
            	TextView timerText = (TextView) getActivity().findViewById(R.id.timer_text);
            	timerText.setText("done!");
            }
         }.start();	
	}
	
	private void setTask() {
		TextView tv = (TextView) getActivity().findViewById(R.id.task_text);
		TextView timerText = (TextView) getActivity().findViewById(R.id.timer_text);
		tv.setText("Task: " + taskName);
		timerText.setText("25:00");
	}
}
