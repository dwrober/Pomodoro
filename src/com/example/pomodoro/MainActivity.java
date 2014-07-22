package com.example.pomodoro;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	
	
	
	}// end of oncreate
	
	/// my menu code
		@SuppressLint("NewApi")
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {

			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) 
			{
			switch (item.getItemId()) 
				{

				case R.id.add_task:
					addTask();
					return true;
					
				case R.id.check_list:
					checkList();
					return true;	

				default:
					return true;
				}

			}	

		public void addTask()
		{
			Toast.makeText(this, "Add new task", Toast.LENGTH_SHORT).show();
		}
		
		public void checkList()
		{
			Toast.makeText(this, "Check List", Toast.LENGTH_SHORT).show();
			//setContentView(R.layout.activity_main);
		}
		
		

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
