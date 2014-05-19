package n.switzloco.beerbatterycharger;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;










//import n.switzloco.beerbatterycharger.workoutDBcontract.workoutEntry;
//import n.switzloco.beerbatterycharger.workoutDBcontract.workoutEntry;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		
		
	}

	

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 4 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		DecimalFormat df = new DecimalFormat("0.##");
		public MainActivity activity = (MainActivity) getActivity();
		View rootView;
		public double AdrinksEarned = 0;
		public double drinks = 0;
		public double runConvert = 0.5;
		public double pushUpConvert = .005;
		public double walkConvert = .2;
		public double bikeConvert = .1;
		public double weightsConvert = .00015;
		public double pullConvert=.025;
		public double sportConvert=.0333;
		public static final String PREFS_NAME = "MyPrefsFile";
		public String workUnit;
		public String workOutLevel;
		public String curWorkout;
			
	    public double curConversion;
		
		public double drinksEarned = 0;
		public int drinksBanked = 0;


		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}



		public PlaceholderFragment() {
			
		}

		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			Integer position = (getArguments().getInt(
					ARG_SECTION_NUMBER) );
			
			
			rootView= inflater.inflate(R.layout.fragment_main, container,
					false);
			
			switch (position) {
			case 0:
				rootView = inflater.inflate(R.layout.fragment_main, container,
						false);
				return rootView;
			case 1:
				rootView = inflater.inflate(R.layout.fragment_main, container,
						false);
				onCreateMain(rootView);
				return rootView;
			case 2:
				rootView = inflater.inflate(R.layout.fragment_two, container,
						false);
				onCreateBank(rootView);
				return rootView;
			case 3:
				rootView = inflater.inflate(R.layout.fragment_two, container,
						false);
				return rootView;
			}
			//TextView textView = (TextView) rootView
					//.findViewById(R.id.section_label);
			
			return rootView;
			//textView.setText(secNum);
			
			//if(secNum.equals("3")){
			//	View rootView2 = inflater.inflate(R.layout.fragment_two, container, false);
			//	return rootView2;
			//}
			//return rootView;
		}
		
	    public void haveADrink(){
	    	TextView beerText = (TextView) rootView.findViewById(R.id.drinksUpper);

	    	ImageView avatar = (ImageView) rootView.findViewById(R.id.imageView1);
	    	
	    	drinksEarned = drinksEarned - 1;
	    	
	    	beerText.setText(df.format(drinksEarned));
	    	
	    	avatar.setImageResource(R.drawable.partydroid);
	    	
		       SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
		       SharedPreferences.Editor editor = settings.edit();
		       editor.putFloat("AdrinksEarned", (float) drinksEarned);
		       editor.putInt("AdrinksBanked", drinksBanked);
		       


		       // Commit the edits!
		       editor.commit();
		       
		       //Double blueblue = (double) 0;
		       //blueblue = (double) settings.getFloat("AdrinksEarned",0);
		       
		       
		    	//Context context = (Context) getActivity();
		    	//CharSequence enterValText = df.format(blueblue) + " drinks earned";
		    	//int duration = Toast.LENGTH_SHORT;

		    	//Toast toast = Toast.makeText(context, enterValText, duration);
		    	//toast.show();	
	    }
	    
	    @Override
		public void onStop(){
		       // We need an Editor object to make preference changes.
		       // All objects are from android.context.Context
		       SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
		       SharedPreferences.Editor editor = settings.edit();
		       editor.putFloat("AdrinksEarned", (float) drinksEarned);
		       editor.putInt("AdrinksBanked", drinksBanked);
		       
		       

		       // Commit the edits!
		       editor.commit();
	    	
	    	super.onStop();

	     }
	    
	    public void chargeBattery(View view) throws FileNotFoundException{
	    	double workoutVal;
	    	Editable workoutS0;
	    	String workoutS;
	    	double beerEarned = 0;
	    	
	    	setExchangeRates(workOutLevel);
	    	
	    	// Pull value
	    	
	    	EditText workDone = (EditText) rootView.findViewById(R.id.editText1);
	    	
	    	// Convert to number

	    	
	    	workoutS0 = workDone.getText();
	    	workoutS = "0" + workoutS0.toString();
	    	

		    	workoutVal = Double.parseDouble(workoutS.toString());

		    	Log.d("oy","oy");
		    	
		    	// Calculate beers earned
		    if (workoutVal > 0){
		    	beerEarned = workoutVal*curConversion;
		    	
		    	// Add to beers earned
		    	
		    	drinksEarned = drinksEarned + beerEarned;
		    	
		    	// Refresh beers earned
		    	
		    	TextView beerText = (TextView) rootView.findViewById(R.id.drinksUpper);
		    
		    	beerText.setText(df.format(drinksEarned));
		    	
		    	//Toast message with workout
		    	
		    	Context context = (Context) getActivity();
		    	CharSequence enterValText = df.format(drinksEarned) + " drinks earned";
		    	int duration = Toast.LENGTH_SHORT;

		    	Toast toast = Toast.makeText(context, enterValText, duration);
		    	toast.show();	
		    	
		    	//Hide keyboard
		    	
		    	InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
		    		      Context.INPUT_METHOD_SERVICE);
		    		imm.hideSoftInputFromWindow(workDone.getWindowToken(), 0);
		    		
				       SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
				       SharedPreferences.Editor editor = settings.edit();
				       editor.putFloat("AdrinksEarned", (float) drinksEarned);
				       editor.putInt("AdrinksBanked", drinksBanked);

				       // Commit the edits!
				       editor.commit();	
		    		
		    	//Write file with data
		    		
		    		Log.d("ooo","crash before db opened");
		    		
		    		 // Gets the data repository in write mode
		    	    //SQLiteDatabase db = dbHelper.getWritableDatabase();

		    	    Date nowTime = new Date();
		    	    // Create a new map of values, where column names are the keys
		    	    ContentValues values = new ContentValues();
		    	    //values.put(workoutEntry.COLUMN_NAME_ENTRY_ID, id);
		    	    //values.put(workoutEntry.COLUMN_NAME_WORKOUT, workoutS);
		    	    //values.put(workoutEntry.COLUMN_NAME_DRINKS_EARNED, beerEarned);
		    	    //values.put(workoutEntry.COLUMN_NAME_WORKOUT_VALUE, workoutVal);
		    	    //values.put(workoutEntry.COLUMN_NAME_DATE, nowTime.toString());

		    	    // Insert the new row, returning the primary key value of the new row
		    	    long newRowId;
		    	   // newRowId = db.insert(
		    	   //         workoutEntry.TABLE_NAME,
		    	   //         workoutEntry.COLUMN_NAME_NULLABLE,
		    	   //          values);
		    	    
		    	    //db.close();
		    	    
		    	    
		    }
		    else{
		    	Context context = (Context) getActivity();
		    	CharSequence enterValText = "Please enter a Workout Value";
		    	int duration = Toast.LENGTH_SHORT;

		    	Toast toast = Toast.makeText(context, enterValText, duration);
		    	toast.show();	
		    
		    }
		    


	    }
	    
	    public void onCreateBank(View view){
	    	// Restore preferences
		       SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
		       settings.edit();
		       drinksBanked = settings.getInt("AdrinksBanked",0);
		       
		    	TextView beerText = (TextView) rootView.findViewById(R.id.beerBalanceText);
		        
		    	beerText.setText(df.format(drinksBanked) + " drinks banked");
		    	
		        // Instantiate the gesture detector with the
		        // application context and an implementation of
		        // GestureDetector.OnGestureListener
		        // Set the gesture detector as the double tap
		        // listener.
		
	        	ImageView badge1 = (ImageView) rootView.findViewById(R.id.badgeView);
		        if(drinksBanked > 1 && drinksBanked < 10){

		        	badge1.setBackgroundResource(R.drawable.greatstart); 
		        }
		        else if(drinksBanked >= 10 && drinksBanked <25){
		        	badge1.setBackgroundResource(R.drawable.badgeten);
		        }
		        else if(drinksBanked >= 25 && drinksBanked <50){
		        	badge1.setBackgroundResource(R.drawable.badgetwofive);
		        }
		        else if(drinksBanked >=50 && drinksBanked <100){
		        	badge1.setBackgroundResource(R.drawable.badgefifty);
		        }
		        else if(drinksBanked >= 100){
		        	badge1.setBackgroundResource(R.drawable.badgehundred);
		        }
		        beerText.setText(df.format(drinksBanked) + " drinks banked");
		        
	    }
	    
		public void onCreateMain(View view){
			//MainActivity activity = (MainActivity) getActivity();
			
			// Get data from personal file
			
			
			// Create Listeners
			
			// Set Exchange Rates?
			//Spinner set-up
			Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner1);
			// Create an ArrayAdapter using the string array and a default spinner layout
			//ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, textArrayResId, textViewResId)
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
			        R.array.workouts_array, android.R.layout.simple_spinner_item);
			// Specify the layout to use when the list of choices appears
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			    @Override
				public void onItemSelected(AdapterView<?> parent, View view, 
			            int pos, long id) {
			    	
			    	setExchangeRates(workOutLevel);
			    	TextView unitText = (TextView) rootView.findViewById(R.id.textView3);
			    	TextView exchangeText = (TextView) rootView.findViewById(R.id.textView2);
			    	
			    	ImageView avatar = (ImageView) rootView.findViewById(R.id.imageView1);

			    	String selection = parent.getItemAtPosition(pos).toString();
			    	curWorkout = selection;
			    	
			    	if(selection.equals("Running")){
			    		curConversion = 1/runConvert;
			    		workUnit = getResources().getString(R.string.miles);
			    		avatar.setImageResource(R.drawable.babywalkerdroid);
			    	}
			    	if(selection.equals("Push-ups")){
			    		curConversion = 1/pushUpConvert;
			    		workUnit = getResources().getString(R.string.reps);
			    		avatar.setImageResource(R.drawable.sportydroid);
			    	}
			        if(selection.equals("Pull-ups")){
			        	curConversion = 1/pullConvert;	
			        	workUnit = getResources().getString(R.string.reps);
			        	avatar.setImageResource(R.drawable.sportydroid);
			    	}
			    	if(selection.equals("Walking")){
			    		curConversion = 1/walkConvert;
			    		workUnit = getResources().getString(R.string.miles);
			    		avatar.setImageResource(R.drawable.babywalkerdroid);
			    	}
			    	if(selection.equals("Biking")){
			    		curConversion = 1/bikeConvert;
			    		workUnit = getResources().getString(R.string.miles);
			    		avatar.setImageResource(R.drawable.sportydroid);
			    	}
			    	if(selection.equals("Lifting Weights")){
			    		curConversion = 1/weightsConvert;
			    		workUnit = getResources().getString(R.string.poundreps);
			    		avatar.setImageResource(R.drawable.sportydroid);
			    	}
			    	if(selection.equals("Sports")){
			    		curConversion=1/sportConvert;
			    		workUnit = getResources().getString(R.string.min);
			    		avatar.setImageResource(R.drawable.sportydroid);
			    	}

			    	unitText.setText(workUnit);
			    	exchangeText.setText(df.format(1/curConversion)+" "+workUnit + " = 1 beer");
			    	
			        // An item was selected. You can retrieve the selected item using
			        // parent.getItemAtPosition(pos)
			    }

			    @Override
				public void onNothingSelected(AdapterView<?> parent) {
			        // Another interface callback
			    }
				
	        });


		       // Restore preferences
		       SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
		       settings.edit();
		       drinksEarned = settings.getFloat("AdrinksEarned",0);
		       drinksBanked = settings.getInt("AdrinksBanked",0);
		       //setSilent(silent);
		       
		       //Set text
		       
		    	TextView beerText = (TextView) rootView.findViewById(R.id.drinksUpper);
		    	TextView exchangeText = (TextView) rootView.findViewById(R.id.textView2);
		    	//RelativeLayout frog = (RelativeLayout) findViewById(R.id.layoutRel);
		        

		    	beerText.setText(df.format(drinksEarned));
		    	//Get workout level from prefs
		    	workOutLevel = settings.getString("wLevel","Intermediate");
		    	//Set coefficients appropriately
		    	
		    	//setExchangeRates(workOutLevel);
		    	
		    	//Create data base, will move later.
		    	//dbHelper = new workoutDBHelper(getApplicationContext());
		    	//workoutDBHelper mDbHelper = newOne.workoutDBHelper(getApplicationContext());
			
			ImageView drinkButton = (ImageView) rootView.findViewById(R.id.imageView3);
			
			drinkButton.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	    	    	haveADrink();
	            }

	        });
			
			ImageView chargeButton = (ImageView) rootView.findViewById(R.id.imageView5);
			
			chargeButton.setOnClickListener(new OnClickListener(){
				
				public void onClick(View v){
					try {
						chargeBattery(v);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			ImageView bankButton = (ImageView) rootView.findViewById(R.id.imageView2);
			
			bankButton.setOnClickListener(new OnClickListener(){
				
				public void onClick(View v){
					bankDrink(v);
				}
			});
		}
		
		public void bankDrink(View v){
	    	TextView beerText = (TextView) getActivity().findViewById(R.id.drinksUpper);
	    	
	    	//if drinks available, put one in the bank!
	    	
	    	if(drinksEarned >=1){ 
	    		drinksEarned= drinksEarned - 1;
	    		beerText.setText(df.format(drinksEarned));
	    		drinksBanked= drinksBanked + 1;
	    		
		    	Context context = getActivity();
		    	CharSequence enterValText = df.format(drinksBanked) + " drinks banked!";
		    	int duration = Toast.LENGTH_SHORT;

		    	Toast toast = Toast.makeText(context, enterValText, duration);
		    	toast.show();
		    	
			       SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
			       SharedPreferences.Editor editor = settings.edit();
			       editor.putFloat("AdrinksEarned", (float) drinksEarned);
			       editor.putInt("AdrinksBanked", drinksBanked);

			       // Commit the edits!
			       editor.commit();	
	    		}
	    	else{
		    	Context context = getActivity();
		    	CharSequence enterValText = "You haven't earned enough drinks to bank one yet!";
		    	int duration = Toast.LENGTH_SHORT;

		    	Toast toast = Toast.makeText(context, enterValText, duration);
		    	toast.show();	
	    	}
	    	

		}
		
		public void setExchangeRates(String worklevel){
			
			Double runBase = Double.parseDouble(this.getResources().getString(R.string.runBase));
			Double pushBase = Double.parseDouble(this.getResources().getString(R.string.pushBase));
			Double pullBase = Double.parseDouble(this.getResources().getString(R.string.pullBase));
			Double walkBase = Double.parseDouble(this.getResources().getString(R.string.walkBase));		
			Double bikeBase = Double.parseDouble(this.getResources().getString(R.string.bikeBase));
			Double weightBase = Double.parseDouble(this.getResources().getString(R.string.weightBase));
			Double sportBase = Double.parseDouble(this.getResources().getString(R.string.sportBase));
			
			Double level3multi = Double.parseDouble(this.getResources().getString(R.string.level3Multi));
			Double level2multi = Double.parseDouble(this.getResources().getString(R.string.level2Multi));
			Double level1multi = Double.parseDouble(this.getResources().getString(R.string.level1Multi));
			
			if(worklevel.equals( this.getResources().getString(R.string.level1))){
				
				runConvert = level1multi*runBase;
				pushUpConvert = level1multi*pushBase;
				walkConvert = level1multi*walkBase;
				bikeConvert = level1multi*bikeBase;
				weightsConvert = level1multi*weightBase;
				sportConvert = level1multi*sportBase;
				pullConvert = level1multi*pullBase;
			}
			else if(worklevel.equals(this.getResources().getString(R.string.level2))){
				runConvert = level2multi*runBase;
				pushUpConvert = level2multi*pushBase;
				walkConvert = level2multi*walkBase;
				bikeConvert = level2multi*bikeBase;
				weightsConvert = level2multi*weightBase;
				sportConvert = level2multi*sportBase;
				pullConvert = level2multi*pullBase;
			}
			else if(worklevel.equals(this.getResources().getString(R.string.level3))){
				runConvert = level3multi*runBase;
				pushUpConvert = level3multi*pushBase;
				walkConvert = level3multi*walkBase;
				bikeConvert = level3multi*bikeBase;
				weightsConvert = level3multi*weightBase;
				sportConvert = level3multi*sportBase;
				pullConvert = level3multi*pullBase;
			}
			else if(worklevel.equals(this.getResources().getString(R.string.level4))){
				runConvert = level3multi*runBase;
				pushUpConvert = level3multi*pushBase;
				walkConvert = level3multi*pullBase;
				bikeConvert = level3multi*bikeBase;
				weightsConvert = level3multi*weightBase;
				sportConvert = level3multi*sportBase;
				pullConvert = level3multi*pullBase;
			}

		}
	}

}
