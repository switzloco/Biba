/**
 * 
 */
package n.switzloco.beerbatterycharger;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



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
        public void onBankButtonPress(String tag);
        public void onChargeButtonPress();
        public void onDrinkButtonPress();
        public void setMainFrag(String tag);
        public void setBankFrag(String tag);
    }
    
    public void setBeerText(String beersRemaining){

    	TextView beerText = (TextView) getActivity().findViewById(R.id.drinksUpper);

    	beerText.setText(beersRemaining);
    	
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
			//onCreateBank(rootView);
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
		ImageView drinkButton = (ImageView) rootView.findViewById(R.id.imageView3);
		
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
		
		
		
		
		drinkButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
    	    	//getToasty();
            	bCallBack.onBankButtonPress(tag1);
            }

        });
	
	}
	
	
}
