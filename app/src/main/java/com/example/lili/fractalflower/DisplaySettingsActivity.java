package com.example.lili.fractalflower;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class DisplaySettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action2", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = this.getIntent();

        /*
        String message = "don't forget to actually add settings!";

        TextView textView = new TextView(this);
        textView.setTextSize(30);
        textView.setText(message);
        */

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.display_settings);
        //linearLayout.addView(textView);

        //select center petals
        Switch mySwitch = (Switch) findViewById(R.id.centerPetalsSwitch);
        //set the switch to whatever center petals was
        mySwitch.setChecked(FlowerFactory.getCenterPetals());
        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    FlowerFactory.setCenterPetals(true);
                }else{
                    FlowerFactory.setCenterPetals(false);
                }
            }
        });

        spinner = (Spinner) findViewById(R.id.colorSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colorChoices, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
    }

    /*
    public void setCenterPetals(View view) {
        Toast t = Toast.makeText(this.getApplicationContext(), "switch is touched", Toast.LENGTH_SHORT);
        t.show();
    } */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        switch (item) {
            case "Pastel":
                FlowerFactory.setColor(FlowerFactory.FlowerColor.PASTEL);
                break;
            case "Purple":
                FlowerFactory.setColor(FlowerFactory.FlowerColor.PURPLE);
                break;
            case "Yellow":
                FlowerFactory.setColor(FlowerFactory.FlowerColor.YELLOW);
                break;
            case "Pink":
                FlowerFactory.setColor(FlowerFactory.FlowerColor.PINK);
                break;
            case "Blue":
                FlowerFactory.setColor(FlowerFactory.FlowerColor.BLUE);
                break;
            case "Orange":
                FlowerFactory.setColor(FlowerFactory.FlowerColor.ORANGE);
                break;
            default:
                break;
        }

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        spinner.setSelection(position);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(this.getApplicationContext(), "help!!", Toast.LENGTH_SHORT).show();
    }
}
