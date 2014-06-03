package n.switzloco.beerbatterycharger;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;

import n.switzloco.beerbatterycharger.MainFrag.OnButtonListener;
//import n.switzloco.beerbatterycharger.workoutDBcontract.workoutEntry;
//import n.switzloco.beerbatterycharger.workoutDBcontract.workoutEntry;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
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

public class MainActivity extends Activity implements OnButtonListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	DecimalFormat df = new DecimalFormat("0.##");
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
	public MainFrag main;
	public MainFrag bank;
		
    public double curConversion;
	
	public workoutDBHelper dbHelper;

	public double drinksEarned = 0;
	public int drinksBanked = 0;
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
		
		//mSectionsPagerAdapter.setPrimaryItem(container, position, object);

		//Initiate variables from Pref file
	       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	       settings.edit();
	       drinksEarned = settings.getFloat("AdrinksEarned",0);
	       drinksBanked = settings.getInt("AdrinksBanked",0);
	       
		//Set Exchange Rates based on intensity level
	    	//Get workout level from prefs
	    	workOutLevel = settings.getString("wLevel","Intermediate");
	    	//Set coefficients appropriately
	    	setExchangeRates(workOutLevel);
	    	
	    	//Create data base, will move later.
	    	dbHelper = new workoutDBHelper(getApplicationContext());
	    	//workoutDBHelper mDbHelper = newOne.workoutDBHelper(getApplicationContext());
	    	
	}
	
	   @Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle item selection
		    switch (item.getItemId()) {
		    case R.id.action_settings:
		      	 Intent intent = new Intent(this, SettingsActivity.class);
		         startActivity(intent);
		        return true;
		    default:
		        return super.onOptionsItemSelected(item);
		    }
		}

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	
	public void onStop(){
		super.onStop();
		
	       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	       SharedPreferences.Editor editor = settings.edit();
	       editor.putFloat("AdrinksEarned", (float) drinksEarned);
	       editor.putInt("AdrinksBanked", drinksBanked);

	       //  the edits!
	       editor.apply();	
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
	
	@Override
	public void onResume() {
	    super.onResume();  // Always call the superclass method first
	    
	    //Log.d("s","resumed");
	    
		//Initiate variables from Pref file
	    //   SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    //   settings.edit();
	    //  drinksEarned = settings.getFloat("AdrinksEarned",0);
	    //   drinksBanked = settings.getInt("AdrinksBanked",0);
	       
		//Set Exchange Rates based on intensity level
	    	//Get workout level from prefs
	   //	workOutLevel = settings.getString("wLevel","Intermediate");
	    	//Set coefficients appropriately
	    //	setExchangeRates(workOutLevel);
	    // reset Frags
	    
	   //onFragSetUp();
	}
	
	@Override
	public void onFragSetUp(){
		main.setBeerText(df.format(drinksEarned));
		
		if(bank!=null){
			//Log.d("bank frag reset","bank frag reset");
			//Log.d("bnak",df.format(drinksBanked));
			bank.bankBadge(drinksBanked);
			bank.setBankText(df.format(drinksBanked));
		}
	}
	@Override
	public void onBankButtonPress() {

    	//checked for sufficient beers
		
		if(drinksEarned >=1){ 
			//increment beersBanked
			
			//decrement beersEarned
			drinksEarned= drinksEarned - 1;
    		drinksBanked= drinksBanked + 1;
    		
			//update beersEarned in main frag
    		
    		main.setBeerText(df.format(drinksEarned));
    		
			//update beersBanked in bank fragment
	
    		bank.setBankText(df.format(drinksBanked));
    		
			//re-assess for badge?
    		
		}else{	    	
			Context context = this;
			CharSequence enterValText = "You don't have enough drinks to bank yet";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, enterValText, duration);
			toast.show();

		}
		
			//if insufficient beer, show toast
		
			//if sufficient beer to bank, add to bank
		bank.bankBadge(drinksBanked);

	}	
	
	@Override
	public void onChargeButtonPress(String workTotal) {
		String workoutS;
		double workoutVal;
		double drinksJustEarned;
		
    	workoutS = "0" + workTotal;
    	

	    	workoutVal = Double.parseDouble(workoutS.toString());	
		
		if(workoutVal > 0){

			//if something is entered into the form
		
				//convert workout to beer
			drinksJustEarned = workoutVal*curConversion;
				//add beer to beersEarned to total.
			drinksEarned = drinksJustEarned + drinksEarned;
				//update beersEarned in main fragment
			main.setBeerText(df.format(drinksEarned));
			
			
			Context context = this;
			CharSequence enterValText = "You just earned " + df.format(drinksJustEarned) +" drinks!";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, enterValText, duration);
			toast.show();
			
	    	main.hideKeyBoard();
	    		
			       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			       SharedPreferences.Editor editor = settings.edit();
			       editor.putFloat("AdrinksEarned", (float) drinksEarned);
			       editor.putInt("AdrinksBanked", drinksBanked);

			       //  the edits!
			       editor.apply();	
			
				//update the database?
		}else{
			//if nothing entered, show toast
			Context context = this;
			CharSequence enterValText = "Please enter your workout value!";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, enterValText, duration);
			toast.show();

		}
	}

	@Override
	public void onDrinkButtonPress() {
		
		//decrease beers consumed by 1
		drinksEarned = drinksEarned - 1;
		//update mainFrag
		main.setBeerText(df.format(drinksEarned));
		//update the database
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
			
			return MainFrag.newInstance(position+1);
			//return PlaceholderFragment.newInstance(position + 1);
			//MainActivity$PlaceholderFragment
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
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


	@Override
	public void setMainFrag(String tag) {
		// TODO Auto-generated method stub
    	main = (MainFrag) getFragmentManager().findFragmentByTag(tag);//FragmentByTag(tag1);
	}

	@Override
	public void setBankFrag(String tag) {
		// TODO Auto-generated method stub
    	bank = (MainFrag) getFragmentManager().findFragmentByTag(tag);//FragmentByTag(tag1);
	}

	@Override
	public void workSpinnerSelect(String workout) {
		// TODO Auto-generated method stub
		String selection = workout;
		
    	if(selection.equals("Running")){
    		curConversion = 1/runConvert;
    		workUnit = getResources().getString(R.string.miles);
    		//avatar.setImageResource(R.drawable.babywalkerdroid);
    	}
    	if(selection.equals("Push-ups")){
    		curConversion = 1/pushUpConvert;
    		workUnit = getResources().getString(R.string.reps);
    		//avatar.setImageResource(R.drawable.sportydroid);
    	}
        if(selection.equals("Pull-ups")){
        	curConversion = 1/pullConvert;	
        	workUnit = getResources().getString(R.string.reps);
        	//avatar.setImageResource(R.drawable.sportydroid);
    	}
    	if(selection.equals("Walking")){
    		curConversion = 1/walkConvert;
    		workUnit = getResources().getString(R.string.miles);
    		//avatar.setImageResource(R.drawable.babywalkerdroid);
    	}
    	if(selection.equals("Biking")){
    		curConversion = 1/bikeConvert;
    		workUnit = getResources().getString(R.string.miles);
    		//avatar.setImageResource(R.drawable.sportydroid);
    	}
    	if(selection.equals("Lifting Weights")){
    		curConversion = 1/weightsConvert;
    		workUnit = getResources().getString(R.string.poundreps);
    		//avatar.setImageResource(R.drawable.sportydroid);
    	}
    	if(selection.equals("Sports")){
    		curConversion=1/sportConvert;
    		workUnit = getResources().getString(R.string.min);
    		//vatar.setImageResource(R.drawable.sportydroid);
    	}
    	main.setExchangeText(df.format(1/curConversion));
	}

	@Override
	public void setRates() {
		// TODO Auto-generated method stub
		setExchangeRates(workOutLevel);
	}

}
