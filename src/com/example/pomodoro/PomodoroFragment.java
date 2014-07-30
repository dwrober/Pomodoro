package com.example.pomodoro;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PomodoroFragment extends Fragment {
	public String taskName = "";
	
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
	}
	
	private void setTask() {
		TextView tv = (TextView) getActivity().findViewById(R.id.task_text);
		tv.setText(taskName);
	}
	
}
