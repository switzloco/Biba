/**
 * 
 */
package n.switzloco.beerbatterycharger;

import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * @author nswitzer
 *
 */
public class MainFrag extends Fragment {
	
	
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
	
	public OnButtonListener bCallBack; 
	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static MainFrag newInstance(int sectionNumber) {
		MainFrag fragment = new MainFrag();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}
	
    // Container Activity must implement this interface
    public interface OnButtonListener {
        public void onFragSetUp();
    	public void onBankButtonPress();
        public void onChargeButtonPress(String workTotal);
        public void onDrinkButtonPress();
        public void workSpinnerSelect(String workout);
        public void setMainFrag(String tag);
        public void setBankFrag(String tag);
		public void setRates();
		public void setListFrag(String tag);
    }
    
    public void setListView(List<String> workList){
    	ListView listItem = (ListView) getActivity().findViewById(R.id.list);
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, workList);
    	if(listItem!=null){listItem.setAdapter(adapter);}
    	Log.d("aaa","setting list view");
    }
    
    public void setBeerText(String beersRemaining){

    	TextView beerText = (TextView) getActivity().findViewById(R.id.drinksUpper);

    	if(beerText!=null){beerText.setText(beersRemaining);}
	
    }
    
    public void setBankText(String beersBanked){
    	TextView bankText = (TextView) getActivity().findViewById(R.id.beerBalanceText);
    	
    	if(bankText!=null){bankText.setText(beersBanked + " beers banked!");}
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            bCallBack = (OnButtonListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Integer position = (getArguments().getInt(
				ARG_SECTION_NUMBER) );
		
		hideKeyBoard();
		
		rootView= inflater.inflate(R.layout.fragment_main, container,
				false);
		
		switch (position) {
		case 0:
			rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			//onCreateMain(rootView); //bCallBack.onBankButtonPress(position);
			return rootView;
		case 1:
			rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			onCreateMain(rootView);
			//onCreateMain(rootView);
			return rootView;
		case 2:
			rootView = inflater.inflate(R.layout.fragment_two, container,
					false);
			
			onCreateBank(rootView);
			return rootView;
		case 3:
			rootView = inflater.inflate(R.layout.list_frag, container,
					false);
			onCreateList(rootView);
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
	
	public void onResume(){
		super.onResume();
		bCallBack.onFragSetUp();
	}
	
	public void onPause(){
		super.onPause();
	}
	
	public void setExchangeText(String exchange){
		TextView exchangeText = (TextView) rootView.findViewById(R.id.textView2);
		exchangeText.setText(exchange +" "+ workUnit + " = 1 beer");
	}
	
	public void onCreateBank(View view){
	
		final String tag2 = this.getTag();
		
		bCallBack.setBankFrag(tag2);
	}
	
	public void onCreateList(View view){
		final String tag2 = this.getTag();
		
		bCallBack.setListFrag(tag2);
	}
	
	public void bankBadge(int bankedDrinks){
		
		ImageView badge1 = (ImageView) rootView.findViewById(R.id.badgeView);
		
		drinksBanked= bankedDrinks;
		
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
	    
	}

	public void hideKeyBoard(){
		
		EditText textInput = null;
		
		if(rootView!=null){textInput = (EditText) rootView.findViewById(R.id.editText1);}
		
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
  		      Context.INPUT_METHOD_SERVICE);
    	
  		if(textInput!=null){imm.hideSoftInputFromWindow(textInput.getWindowToken(), 0);}
  		
  		Log.d("aaa","hiding keyboard");
	}
	
	public void onCreateMain(View view){
		ImageView bankButton = (ImageView) rootView.findViewById(R.id.imageView2);
		ImageView drinkButton = (ImageView) rootView.findViewById(R.id.imageView3);
		ImageView chargeButton = (ImageView) rootView.findViewById(R.id.imageView5);
		
		final String tag1 = this.getTag();
		
		bCallBack.setMainFrag(tag1);
		
		
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
		    	
		    	bCallBack.setRates();//setExchangeRates(workOutLevel);
		    	TextView unitText = (TextView) rootView.findViewById(R.id.textView3);
		    	
		    	ImageView avatar = (ImageView) rootView.findViewById(R.id.imageView1);

		    	String selection = parent.getItemAtPosition(pos).toString();
		    	curWorkout = selection;
		    	
		    	if(selection.equals("Running")){
		    		//curConversion = 1/runConvert;
		    		workUnit = getResources().getString(R.string.miles);
		    		avatar.setImageResource(R.drawable.babywalkerdroid);
		    	}
		    	if(selection.equals("Push-ups")){
		    		//curConversion = 1/pushUpConvert;
		    		workUnit = getResources().getString(R.string.reps);
		    		avatar.setImageResource(R.drawable.sportydroid);
		    	}
		        if(selection.equals("Pull-ups")){
		        	//curConversion = 1/pullConvert;	
		        	workUnit = getResources().getString(R.string.reps);
		        	avatar.setImageResource(R.drawable.sportydroid);
		    	}
		    	if(selection.equals("Walking")){
		    		//curConversion = 1/walkConvert;
		    		workUnit = getResources().getString(R.string.miles);
		    		avatar.setImageResource(R.drawable.babywalkerdroid);
		    	}
		    	if(selection.equals("Biking")){
		    		//curConversion = 1/bikeConvert;
		    		workUnit = getResources().getString(R.string.miles);
		    		avatar.setImageResource(R.drawable.sportydroid);
		    	}
		    	if(selection.equals("Lifting Weights")){
		    		//curConversion = 1/weightsConvert;
		    		workUnit = getResources().getString(R.string.poundreps);
		    		avatar.setImageResource(R.drawable.sportydroid);
		    	}
		    	if(selection.equals("Sports")){
		    		//curConversion=1/sportConvert;
		    		workUnit = getResources().getString(R.string.min);
		    		avatar.setImageResource(R.drawable.sportydroid);
		    	}

		    	unitText.setText(workUnit);
		    	bCallBack.workSpinnerSelect(selection);
		    	//exchangeText.setText(df.format(1/curConversion)+" "+workUnit + " = 1 beer");
		    	
		        // An item was selected. You can retrieve the selected item using
		        // parent.getItemAtPosition(pos)
		    }

		    @Override
			public void onNothingSelected(AdapterView<?> parent) {
		        // Another interface callback
		    }
			
        });

		//onClickListener for BankButton
		
		bankButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
    	    	
            	bCallBack.onBankButtonPress();
            }

        });
		
		//onClickListener for ChargeButton
		
		chargeButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				EditText workText = (EditText) rootView.findViewById(R.id.editText1);
				
				bCallBack.onChargeButtonPress(workText.getText().toString());}
		});
	
	
	
	//onClickListener for DrinkButton
	
		drinkButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//Change image to party guy!
				ImageView avatar = (ImageView) rootView.findViewById(R.id.imageView1);
				avatar.setImageResource(R.drawable.partydroid);
				//Update activity with drink 
				bCallBack.onDrinkButtonPress();
			}
			
		});
	
	
}}