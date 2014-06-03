package n.switzloco.beerbatterycharger;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class SettingsActivity extends Activity {
	public static final String PREFS_NAME = "MyPrefsFile";
	public String workLevel;
	public double levelMulti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
	    updateExchanges();
	       

    }

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.home_menu:
	      	 Intent intent = new Intent(this, MainActivity.class);
	         startActivity(intent);
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }
    
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        
	       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	       SharedPreferences.Editor editor = settings.edit();
        
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_level1:
                if (checked)

	                editor.putString("wLevel",this.getResources().getString(R.string.level1));
	
	                // Commit the edits!
	                editor.commit();
                break;
            case R.id.radio_level2:
                if (checked)
	                editor.putString("wLevel",this.getResources().getString(R.string.level2));
            	
	                // Commit the edits!
	                editor.commit();
                break;
            case R.id.radio_level3:
                if (checked)
	                editor.putString("wLevel",this.getResources().getString(R.string.level3));
            	
	                // Commit the edits!
	                editor.commit();
                break;
            case R.id.radio_level4:
                if (checked)
	                editor.putString("wLevel",this.getResources().getString(R.string.level4));
            	
	                // Commit the edits!
	                editor.commit();
                break;
        }
        updateExchanges();
    }
    
    public void resetBank(View view){
	       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	       SharedPreferences.Editor editor = settings.edit();
    	
	       editor.putFloat("AdrinksEarned", 0);
	       editor.putInt("AdrinksBanked", 0);
	       editor.apply();
	       
	       
	    	Context context = getApplicationContext();
	    	CharSequence enterValText = "Bank reset!";
	    	int duration = Toast.LENGTH_SHORT;

	    	Toast toast = Toast.makeText(context, enterValText, duration);
	    	toast.show();
    }
    
    public void updateExchanges(){

	    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    workLevel = settings.getString("wLevel", this.getResources().getString(R.string.level3));
	    
		Double level3multi = Double.parseDouble(this.getResources().getString(R.string.level3Multi));
		Double level2multi = Double.parseDouble(this.getResources().getString(R.string.level2Multi));
		Double level1multi = Double.parseDouble(this.getResources().getString(R.string.level1Multi));
       
       //Get the correct radio button, mark as checked
       
       if(workLevel.equals(this.getResources().getString(R.string.level1)) ){
    	   RadioGroup curLevel = (RadioGroup) findViewById(R.id.radioGroup1);
    	   curLevel.check(R.id.radio_level1);
    	   levelMulti = level1multi;
       }
       else if(workLevel.equals(this.getResources().getString(R.string.level2)) ){
    	   RadioButton curLevel = (RadioButton) findViewById(R.id.radio_level2);
    	   curLevel.setChecked(true);
    	   levelMulti = level2multi;
       }
       else if(workLevel.equals(this.getResources().getString(R.string.level3)) ){
    	   RadioButton curLevel = (RadioButton) findViewById(R.id.radio_level3);
    	   curLevel.setChecked(true);
    	   levelMulti = level3multi;
       }
       else if(workLevel.equals(this.getResources().getString(R.string.level4)) ){
    	   RadioButton curLevel = (RadioButton) findViewById(R.id.radio_level4);
    	   curLevel.setChecked(true);
    	   levelMulti = level3multi;

       }
       
       String workouts[] = this.getResources().getStringArray(R.array.workouts_array);
	       
       
       LinearLayout linearLayout = (LinearLayout)findViewById(R.id.settingsLinear);
       
			Double runBase = levelMulti*Double.parseDouble(this.getResources().getString(R.string.runBase));
			Double pushBase = levelMulti*Double.parseDouble(this.getResources().getString(R.string.pushBase));
			Double pullBase = levelMulti*Double.parseDouble(this.getResources().getString(R.string.pullBase));
			Double walkBase = levelMulti*Double.parseDouble(this.getResources().getString(R.string.walkBase));		
			Double bikeBase = levelMulti*Double.parseDouble(this.getResources().getString(R.string.bikeBase));
			Double weightBase = levelMulti*Double.parseDouble(this.getResources().getString(R.string.weightBase));
			Double sportBase = levelMulti*Double.parseDouble(this.getResources().getString(R.string.sportBase));
		
	    	String strToPrint = "";
       for (int i = 0; i < workouts.length; i++) {
    	    
    	    String selection = workouts[i];
        	if(selection.equals("Running")){
        		strToPrint = strToPrint + "\n"+selection +": 1 beer = " + runBase.toString()+ " "+ this.getResources().getString(R.string.miles);
        	}
        	if(selection.equals("Push-ups")){
        		strToPrint = strToPrint + "\n"+selection + ": 1 beer = " + pushBase.toString()+ " "+ this.getResources().getString(R.string.reps);
        	}
            if(selection.equals("Pull-ups")){
            	strToPrint = strToPrint + "\n"+selection + ": 1 beer = " + pullBase.toString()+ " "+ this.getResources().getString(R.string.reps);
        	}
        	if(selection.equals("Walking")){
        		strToPrint = strToPrint + "\n"+selection + ": 1 beer = " + walkBase.toString() + " "+ this.getResources().getString(R.string.miles);
        	}
        	if(selection.equals("Biking")){
        		strToPrint = strToPrint + "\n"+selection + ": 1 beer = " + bikeBase.toString()+ " "+ this.getResources().getString(R.string.miles);
        	}
        	if(selection.equals("Lifting Weights")){
        		strToPrint = strToPrint + "\n"+selection + ": 1 beer = " + weightBase.toString()+ " "+ this.getResources().getString(R.string.poundreps);
        	}
        	if(selection.equals("Sports")){
        		strToPrint = strToPrint + "\n"+selection + ": 1 beer = " + sportBase.toString()+ " "+ this.getResources().getString(R.string.min);
        	}
	       
	       TextView valueTV = (TextView) findViewById(R.id.exchanges);
	       valueTV.setText(strToPrint);
	       //]valueTV.setId(5);
	       //valueTV.setLayoutParams(new LayoutParams(
	       //        LayoutParams.MATCH_PARENT,
	       //        LayoutParams.WRAP_CONTENT));
	       
    	}
    }
    
}
