package com.example.pomodoro;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PomodoroFragment extends Fragment {
	public String taskName = "";
	long startTime = 0;
	
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
	                timerHandler.removeCallbacks(timerRunnable);
	                b.setText("start");
	            } else {
	                startTime = System.currentTimeMillis();
	                timerHandler.postDelayed(timerRunnable, 0);
	                b.setText("stop");
	            }
	        }
	    });
	}
	
	private void setTask() {
		TextView tv = (TextView) getActivity().findViewById(R.id.task_text);
		TextView timerText = (TextView) getActivity().findViewById(R.id.timer_text);
		tv.setText("Task: " + taskName);
		timerText.setText("25:00");
	}
	
	Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            TextView timerText = (TextView) getActivity().findViewById(R.id.timer_text);
            timerText.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };

}
