package com.example.lili.fractalflower;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
        spinner.setSelection(FlowerFactory.getSpinnerPosition());
        spinner.setOnItemSelectedListener(this);

        RadioButton narrowPetal = (RadioButton)findViewById(R.id.setNarrowPetalButton);
        if (FlowerFactory.getShape() == Flower.PetalShape.NARROW) {
            narrowPetal.setChecked(true);
        }
        RadioButton mediumPetal = (RadioButton)findViewById(R.id.setMediumPetalButton);
        if (FlowerFactory.getShape() == Flower.PetalShape.MEDIUM) {
            mediumPetal.setChecked(true);
        }
        RadioButton widePetal = (RadioButton)findViewById(R.id.setWidePetalButton);
        if (FlowerFactory.getShape() == Flower.PetalShape.WIDE) {
            widePetal.setChecked(true);
        }

        RadioButton smallFlower = (RadioButton) findViewById(R.id.setSmallFlowerButton);
        if (FlowerFactory.getSize() == 1) {
            smallFlower.setChecked(true);
        }
        RadioButton mediumFlower = (RadioButton)findViewById(R.id.setMediumFlowerButton);
        if (FlowerFactory.getSize() == 2) {
            mediumFlower.setChecked(true);
        }
        RadioButton largeFlower = (RadioButton)findViewById(R.id.setLargeFlowerButton);
        if (FlowerFactory.getSize() == 3) {
            largeFlower.setChecked(true);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        switch (item) {
            case "Pastel":
                FlowerFactory.setColor(FlowerFactory.FlowerColor.PASTEL);
                FlowerFactory.setSpinnerPosition(0);
                break;
            case "Purple":
                FlowerFactory.setColor(FlowerFactory.FlowerColor.PURPLE);
                FlowerFactory.setSpinnerPosition(1);
                break;
            case "Yellow":
                FlowerFactory.setColor(FlowerFactory.FlowerColor.YELLOW);
                FlowerFactory.setSpinnerPosition(2);
                break;
            case "Pink":
                FlowerFactory.setColor(FlowerFactory.FlowerColor.PINK);
                FlowerFactory.setSpinnerPosition(3);
                break;
            case "Blue":
                FlowerFactory.setColor(FlowerFactory.FlowerColor.BLUE);
                FlowerFactory.setSpinnerPosition(4);
                break;
            case "Orange":
                FlowerFactory.setColor(FlowerFactory.FlowerColor.ORANGE);
                FlowerFactory.setSpinnerPosition(5);
                break;
            default:
                FlowerFactory.setSpinnerPosition(0);
                break;
        }

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        spinner.setSelection(position);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(this.getApplicationContext(), "help!!", Toast.LENGTH_SHORT).show();
    }

    public void onPetalRadioButtonClicked(View view) {
        //Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case (R.id.setNarrowPetalButton):
                if (checked) {
                    FlowerFactory.setShape(Flower.PetalShape.NARROW);
                }
                break;
            case (R.id.setMediumPetalButton):
                if (checked) {
                    FlowerFactory.setShape(Flower.PetalShape.MEDIUM);
                }
                break;
            case (R.id.setWidePetalButton):
                if (checked) {
                    FlowerFactory.setShape(Flower.PetalShape.WIDE);
                }
                break;
            default:
                break;
        }
    }

    public void onSizeRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case (R.id.setSmallFlowerButton):
                if (checked) {
                    FlowerFactory.setSize(1);
                }
                break;
            case (R.id.setMediumFlowerButton):
                if (checked) {
                    FlowerFactory.setSize(2);
                }
                break;
            case(R.id.setLargeFlowerButton):
                if (checked) {
                    FlowerFactory.setSize(3);
                }
                break;
            default:
                break;
        }
    }
}
