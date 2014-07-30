package com.example.pomodoro;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

public class MainActivity extends Activity {
	public static final String TAG = "Pomodoro";
	public static String[] task_list = new String[] { ""}; 
	public static String[] incomplete_task_text = new String[] {""}; // for incomplete text string from add task view

	public static FragmentTransaction ft=null;
	MyListFrag frag0=null;
	
	public PomodoroFragment pomActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		frag0 = new MyListFrag();
		loadList();
        
        // Add the fragment to the 'fragment_container' FrameLayout
//       ft = getFragmentManager().beginTransaction();
//           ft.add(R.id.fragment_container, frag0, "MAIN_FRAGMENT");  
//           ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//       ft.commit();
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
					Toast.makeText(this, "List Saved!", Toast.LENGTH_SHORT).show();
					return true;
					
				case R.id.load_list:
					loadList();
					return true;
					
				case R.id.delete_list:
					Toast.makeText(this, "All tasks cleared!", Toast.LENGTH_SHORT).show();
					deleteList();
					return true;	

				default:
					Toast.makeText(this, "What did you click on?", Toast.LENGTH_SHORT).show();
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
	    	editor.putString("location"+i, task_list[i]);
	    	}	
	    	    
	    // Apply the edits!
	    editor.apply();
	
		}
/// ~~~~~~~~~~~~~~~ SAVE LIST ~~~~~~~~~~~ ///
			
	
		
/// ~~~~~~~~~~~~~~~ LOAD LIST ~~~~~~~~~~~ ///	
		public void loadList()
		{
			Log.i(TAG,"loadList --> "+ task_list.length +" elements.");
		    
			String PREFS_NAME="TASK LIST";
		    SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
		    // ??? not needed as only reading from storage ???   SharedPreferences.Editor editor = settings.edit();
		    
		    String[] loaded_task_list = new String[settings.getInt("listSize", 0)];
			
		    for (int i=0;i<settings.getInt("listSize", 0);i++)
				{
		    	loaded_task_list[i]=settings.getString("location"+i, "0");
				}
		    task_list=loaded_task_list;
		    
		showList();	
		}
/// ~~~~~~~~~~~~~~~ LOAD LIST ~~~~~~~~~~~ ///
		
/// ~~~~~~~~~~~~~~~ DELETE LIST ~~~~~~~~~~~ ///
		public void deleteList()
		{
			task_list=new String[] {};
			showList();	
		}
		
/// ~~~~~~~~~~~~~~~ DELETE LIST ~~~~~~~~~~~ ///
		

		
/// ~~~~~~~~~~~~~~~ update incomplete_task_text ~~~~~~~~~~~ ///
public void setIncomplete(String newValue)
	{
    String PREFS_NAME="TASK LIST";
    SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString("incomplete_task_text", newValue);
    editor.apply();
	}		
/// ~~~~~~~~~~~~~~~ update incomplete_task_text ~~~~~~~~~~~ ///

/// ~~~~~~~~~~~~~~~ get incomplete_task_text ~~~~~~~~~~~ ///
public String getIncomplete()
	{
	Log.i(TAG,"getIncomplete Called ");
    String PREFS_NAME="TASK LIST";
    SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
    return settings.getString("incomplete_task_text","0");
	}

/// ~~~~~~~~~~~~~~~ get incomplete_task_text ~~~~~~~~~~~ ///
		
		
/// ~~~~~~~~~~~~~~~ ADD TASK ~~~~~~~~~~~ ///
		public void addTask()
			{
			// for testing
			//setIncomplete("");
			
			
			//  Toast.makeText(this, "Add new task", Toast.LENGTH_SHORT).show();
			setContentView(R.layout.add_task);
			EditText addTaskText=(EditText)findViewById(R.id.new_task_name);
			addTaskText.requestFocus();
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(addTaskText, InputMethodManager.SHOW_IMPLICIT);
			
			incomplete_task_text[0]=getIncomplete();
			
// autosave feature in edit text of add_task
		    
		       addTaskText.addTextChangedListener(new TextWatcher() {

		    	   public void afterTextChanged(Editable s) {}

		    	   public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

		    	   public void onTextChanged(CharSequence s, int start, int before, int count)
		    	   		{
		    		    setIncomplete(s.toString());
		    	   		}
		    	   
		    	  });
		              
		       
// end of autosave feature			
			
			Log.i(TAG, "incomplete text = " +incomplete_task_text[0]);
			// if unfinished text from last uncommitted add, re-enter it
			if (!incomplete_task_text[0].equals(""))
				{
				addTaskText.setText(incomplete_task_text[0]); // if fragment stored, replace content with fragment
				}
			else
				{
				addTaskText.setText("");
				}
			
			addTaskText.setSelection(addTaskText.getText().length());
			
			// find "add" button and attach listener
			// if overlay need to add->  B1.bringToFront();
			Button B1=(Button) findViewById(R.id.add_task_button); 
			
			B1.setOnClickListener(new View.OnClickListener() 
				{
				public void onClick(View v) 
					{
					setIncomplete("");
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
		
		
/// ~~~~~~~~~~~~~~~ ADD TASK ~~~~~~~~~~~ ///		
		
			
		
/// ~~~~~~~~~~~~~~~ SHOW LIST ~~~~~~~~~~~ ///		
		public void showList()
		    {
			//Toast.makeText(this, "Check List", Toast.LENGTH_SHORT).show();
			setContentView(R.layout.fragment_main);
			//frag0 = new MyListFrag();
	        
	        // Add the fragment to the 'fragment_container' FrameLayout
	        ft = getFragmentManager().beginTransaction();
	        ft.add(R.id.fragment_container, frag0, "MAIN_FRAGMENT");  
	        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	        ft.commit();
		    }
/// ~~~~~~~~~~~~~~~ SHOW LIST ~~~~~~~~~~~ ///		
		
		
		
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
			    Log.i(TAG, "Position " +position + " was clicked\n" + v);
			    String task = ((TextView)l.getChildAt(position)).getText().toString();
			    Log.i("ListItemSelected: ", task);
			    Toast.makeText(getActivity(), "Option "+ position+" clicked", Toast.LENGTH_SHORT).show();	
				
				selectDetail(task);
			}
			
			private void selectDetail(String task) {
				PomodoroFragment pomFragment = new PomodoroFragment();
				pomFragment.taskName = task;
				FragmentManager fragmentManager= getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.fragment_container, pomFragment);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
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
