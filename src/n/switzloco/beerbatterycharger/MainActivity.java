package n.switzloco.beerbatterycharger;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;





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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
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
	
	DecimalFormat df = new DecimalFormat("0.##");

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
		
		
		//Spinner set-up
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.workouts_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
	       // Restore preferences
	       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	       settings.edit();
	       drinksEarned = settings.getFloat("AdrinksEarned",0);
	       drinksBanked = settings.getInt("AdrinksBanked",0);
	       //setSilent(silent);
	       
	       //Set text
	       
	    	TextView beerText = (TextView) findViewById(R.id.drinksUpper);
	    	TextView exchangeText = (TextView) findViewById(R.id.textView2);
	    	//RelativeLayout frog = (RelativeLayout) findViewById(R.id.layoutRel);
	        
	    	//beerText.setText("bue");  //Double.toString(drinksEarned)); //df.format(drinksEarned));
	    	//Get workout level from prefs
	    	workOutLevel = settings.getString("wLevel","Advanced");
	    	//Set coefficients appropriately
	    	setExchangeRates(workOutLevel);
	    	
	    	//Create data base, will move later.
	    	//dbHelper = new workoutDBHelper(getApplicationContext());
	    	//workoutDBHelper mDbHelper = newOne.workoutDBHelper(getApplicationContext());
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

		public void chargeBattery(){
	    	Log.d("aaa","aaa");
			
			Context context = getActivity();
	    	CharSequence enterValText = "Please enter a Workout Value";
	    	int duration = Toast.LENGTH_SHORT;

	    	Toast toast = Toast.makeText(context, enterValText, duration);
	    	toast.show();
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			Integer position = (getArguments().getInt(
					ARG_SECTION_NUMBER) );
			
			
			View rootView= inflater.inflate(R.layout.fragment_main, container,
					false);
			
			switch (position) {
			case 0:
				rootView = inflater.inflate(R.layout.fragment_main, container,
						false);
				return rootView;
			case 1:
				rootView = inflater.inflate(R.layout.fragment_two, container,
						false);
				chargeBattery();
				return rootView;
			case 2:
				rootView = inflater.inflate(R.layout.fragment_main, container,
						false);
				onCreateMain(rootView);
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
		
		public void onCreateMain(View view){
			//Activity activity = getActivity();
			
			ImageView chargeButton = (ImageView) view.findViewById(R.id.imageView5);
	        
			chargeButton.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	    			Context context = getActivity();
	    	    	CharSequence enterValText = "Please enter a Workout Value";
	    	    	int duration = Toast.LENGTH_SHORT;

	    	    	Toast toast = Toast.makeText(context, enterValText, duration);
	    	    	toast.show();
	            }


	        });
		}
	}

}
