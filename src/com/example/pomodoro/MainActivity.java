package com.example.pomodoro;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;
import android.widget.EditText;

public class MainActivity extends Activity {
	public static final String TAG = "Pomodoro";
	public static String[] task_list = new String[] { "Click add task from above menu to add a task"}; // implied first element

	
	public static FragmentTransaction ft=null;
	MyListFrag frag0=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		frag0 = new MyListFrag();
        
        // Add the fragment to the 'fragment_container' FrameLayout
       ft = getFragmentManager().beginTransaction();
           ft.add(R.id.fragment_container, frag0, "MAIN_FRAGMENT");  
           ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
       ft.commit();
	
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
					
				case R.id.show_list:
					showList();
					return true;	
					
				case R.id.save_list:
					saveList();
					return true;	

				default:
					return true;
				}

			}	
		
/// ~~~~~~~~~~~~~~~ SAVE LIST ~~~~~~~~~~~ ///		
		public void saveList()
		{
	    Log.i(TAG,"saveList --> "+ task_list.length +" elements.");
	    String PREFS_NAME="TASK LIST";
	    
	    SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putInt("listSize", task_list.length);
	    
	    for (int i=0;i<task_list.length;i++)
			{
	    	editor.putString("location"+i, i+1+") " +task_list[i]);
	    	}	
	    
	    
	    
	    // Apply the edits!
	    editor.apply();

	    // Get from the SharedPreferences
	    //SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
	    //int homeScore = settings.getInt("homeScore", 0);
			
			
		}
/// ~~~~~~~~~~~~~~~ SAVE LIST ~~~~~~~~~~~ ///		
		
		
		

		public void addTask()
			{
			// needs to call add_task and store new item to task_list string array
			//Toast.makeText(this, "Add new task", Toast.LENGTH_SHORT).show();
			setContentView(R.layout.add_task);
			EditText addTaskText=(EditText)findViewById(R.id.new_task_name);
			addTaskText.requestFocus();
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(addTaskText, InputMethodManager.SHOW_IMPLICIT);
			
			// find "add" button and attach listener
			// if overlay need to add->  B1.bringToFront();
			Button B1=(Button) findViewById(R.id.add_task_button); 
			
			B1.setOnClickListener(new View.OnClickListener() 
				{
				public void onClick(View v) 
					{
					EditText addTaskText=(EditText)findViewById(R.id.new_task_name);
					String newTask= addTaskText.getText().toString();					
					
					List<String> a = new ArrayList<String>();
					for (int i=0;i<task_list.length;i++)
						{
						//Log.i(TAG,"task_list.add="+task_list[i]);
						a.add(task_list[i]);
						}		
					
					a.add(newTask);
					task_list=new String [a.size()];
					a.toArray(task_list);
				    
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(addTaskText.getWindowToken(), 0);
					
					showList();
					
					}
				});
			
			
			} // end of add task
		
		
		
		
		
		
		
		public void showList()
		    {
			Toast.makeText(this, "Check List", Toast.LENGTH_SHORT).show();
			setContentView(R.layout.fragment_main);
			frag0 = new MyListFrag();
	        
	        // Add the fragment to the 'fragment_container' FrameLayout
	        ft = getFragmentManager().beginTransaction();
	        ft.add(R.id.fragment_container, frag0, "MAIN_FRAGMENT");  
	        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	        ft.commit();
		    }
		
		
		
		
////////	///////////// list fragment code starts here
		
		public static class MyListFrag extends ListFragment {
			String[] values;
			
			@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState) {
					return super.onCreateView( inflater,  container, savedInstanceState);
					}
			
			
			@Override
			public void onActivityCreated(Bundle b) {
				super.onActivityCreated(b);
/// add loads current task list here	    
				//values = new String[] { "Cat", "Mars", "Snow" };
	       	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
	       	        android.R.layout.simple_list_item_1, task_list);
	       	    setListAdapter(adapter);
			} //onActivity
			
			
			//////////////////////////
			
			@Override
			public void onListItemClick(ListView l, View v, int position, long id) {
			    Log.i(TAG,values[position] + " was clicked\n" + v);
							
				switch (position) 
				{

				case 0:
					Toast.makeText(getActivity(), "option 0 = static add item", Toast.LENGTH_SHORT).show();				
					//getActivity().setContentView(R.layout.add_task);

					// needs code to add new task and store to array
					/// !!!!! not functional !!!!!!
					//MainActivity.addTask();
					
					return;
					
								
				}
				
				
				v.setBackgroundColor(0xFF44FF99); // visual queue, change to green
				v.invalidate();
			}
				
			public static class MyImageFragment extends Fragment {

				  @Override
				  public View onCreateView(LayoutInflater inflater, ViewGroup container,
				    Bundle savedInstanceState) {
				      View myFragmentView = inflater.inflate(R.layout.add_task, container, false);
				  
				   return myFragmentView;
				  }

				}	
			

		}	
		
////////	//////////// list fragment code ends here	

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
