package com.example.pomodoro;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pomodoro.PomodoroController;

public class PomodoroFragment extends Fragment {
	public String taskName = "";
	private int duration = 10000;
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
	    
		Button finishBtn = (Button) getActivity().findViewById(R.id.finish_btn);
		finishBtn.setOnClickListener(new View.OnClickListener() {
	
	        @Override
	        public void onClick(View v) {
	        	Map params = new HashMap();
	        	Map pomo = new HashMap();
	        	params.put("user","david");
	        	pomo.put("pomodoro", params);
	        	
	        	JSONObject jo = new JSONObject(pomo);
	        	//StringEntity se = new StringEntity(holder.toString());
	        	
	        	PomodoroController pc = new PomodoroController();
	        	pc.data = jo;
	        	pc.execute("http://pacific-sands-1151.herokuapp.com/pomodoros.json");
	        }
	    });
	}
	
	private void stopTimer() {
		cdt.cancel();
		
		TextView tv = (TextView) getActivity().findViewById(R.id.task_text);
		TextView timerText = (TextView) getActivity().findViewById(R.id.timer_text);
		timerText.setText(getFormattedTime(duration));
	}
	
	private void startTimer() {
		//int duration = 25*60*1000;
		TextView timerText = (TextView) getActivity().findViewById(R.id.timer_text);
		cdt = new CountDownTimer(duration, 500) {
			
		public void onTick(long millisUntilFinished) {
	  		TextView timerText = (TextView) getActivity().findViewById(R.id.timer_text);
	  		timerText.setText(getFormattedTime(millisUntilFinished));
		}

            public void onFinish() {
            	TextView timerText = (TextView) getActivity().findViewById(R.id.timer_text);
            	timerText.setText("done!");
        		Button b = (Button) getActivity().findViewById(R.id.start_btn);
        	    b.setText("start");
            }
         }.start();	
	}
	
	private String getFormattedTime(long millisUntilFinished) {
	    int seconds = (int)millisUntilFinished/1000;
	  	int minutes = seconds / 60;
	  	seconds = seconds % 60;
	  	return String.format("%d:%02d", minutes, seconds);
	}
	
	private void setTask() {
		TextView tv = (TextView) getActivity().findViewById(R.id.task_text);
		TextView timerText = (TextView) getActivity().findViewById(R.id.timer_text);
		tv.setText("Task: " + taskName);
		timerText.setText(getFormattedTime(duration));
	}
}
