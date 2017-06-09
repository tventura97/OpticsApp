package com.example.xenom.opticsmenu.Light.Refraction;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.xenom.opticsmenu.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

/**
 * Better comments as well as a continuation document will be added
 * MPAndroidChart uses floats for some reason so just cast everything to a float
 */

public class Snell_Interact extends AppCompatActivity {
    private LineChart graph;
    private TextView textViewN1;
    private TextView textViewN2;
    private SeekBar seekBarN1;
    private SeekBar seekBarN2;
    double theta1 = 90; //Theta 1 will always be 90 degrees.
    double theta2;
    double N1 = 1.00; //Initialze both indexes of refraction to 1.00;
    double N2 = 1.00;
    int beamlength = 10; //Length of beam of light is 10 units.
    double[] DataPointsRight = {0, 0};      //x = DataPoints[0]; y = DataPoints[1]



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snell__interact);
        setTitle("Snell's Law");
        graph = (LineChart)findViewById(R.id.graph);
        textViewN1 = (TextView) findViewById(R.id.textN1);
        textViewN2 = (TextView) findViewById(R.id.textN2);
        seekBarN1 = (SeekBar) findViewById(R.id.seekbarN1);
        seekBarN2 = (SeekBar) findViewById(R.id.seekbarN2);
        textViewN1.setText("Index of Refraction: " + seekBarN1.getProgress());
        textViewN2.setText("Index of Refraction: " + seekBarN2.getProgress());

        /**
         * We want the maximum value of the seekbar to be 300 because the highest index of refraction we will be using is 4.00, but the lowest will be 1.00 (4.00 - 1.00 = 3.00)
         * . This means we can increment by 0.01 when the user alters the index of refraction using the seekbar to watch what happens to the light beams as they pass through the different mediums.
         */

        seekBarN1.setMax(300);
        seekBarN2.setMax(300);

        configureGraph();   // Configures graph formatting settings



        seekBarN1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewN1.setText("Index of Refraction: " + seekBar.getProgress());
                this.progress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textViewN1.setText("Index of Refraction: " + progress);

            }
        });

        seekBarN2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                graph.clear();
                textViewN2.setText("Index of Refraction N2: " + N2);
                setN2(seekBarN2.getProgress() / 100.0 + 1.00);
                setThetaTwo();
                this.progress = progress;
                generateDataPointsRight();
                generateGraph();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {



            }
        });

        initializeGraph();              //Initializes graph


    }


    public void generateDataPointsRight() {
        double x, y;
        x = beamlength * Math.cos(Math.toRadians(theta2));        //Simple trigonometry & Snell's law dictates this relationship.
        y = -1 * beamlength * Math.sin(Math.toRadians(theta2));         //Negative because we know that the beam of light is travelling downward

        DataPointsRight[0] = x;
        DataPointsRight[1] = y;


    }

    /**
     * These functions use Snell's law to calculate the angles (Much like Snell_Quix)
     */


    public void setThetaTwo() {
        //For the sake of consistency, theta is converted to degrees here because the entire program is written assuming the global values of theta2 are in degrees
        theta2 = Math.toDegrees(Math.asin(N1 / N2 * Math.sin(Math.toRadians(theta1))));


    }

    public void setN2(double N2) {
        this.N2 = N2;
    }

    /**
     * Graph formatting settings
     * All grid lines disabled
     * All labels disabled
     * Legend disabled
     * Background image set to @drawable/graph_background
     * X-limits set to -10:10
     * Y-limits set to -10:10
     * Touch interactivity disabled
     */

    public void configureGraph() {

        /**
         * When setting the graph background, the image MUST be the same dimensions as the LineChart, or graph.
         */
        int imageResource = getResources().getIdentifier("@drawable/graph_background", null, getPackageName());
        graph.setBackgroundResource(imageResource);

        graph.getAxisLeft().setDrawGridLines(false);
        graph.getAxisLeft().setDrawAxisLine(false);
        graph.getAxisLeft().setDrawLabels(false);
        graph.getAxisRight().setDrawAxisLine(false);
        graph.getAxisRight().setDrawGridLines(false);
        graph.getAxisRight().setDrawLabels(false);
        graph.getXAxis().setDrawLabels(false);
        graph.getXAxis().setDrawAxisLine(false);
        graph.getXAxis().setDrawGridLines(false);
        graph.getLegend().setEnabled(false);
        graph.getDescription().setEnabled(false);
        graph.setTouchEnabled(false);
        graph.getXAxis().setAxisMinimum((float)-10.0);
        graph.getXAxis().setAxisMaximum((float) 10.0);
        graph.getAxisLeft().setAxisMinimum((float)-10.0);
        graph.getAxisLeft().setAxisMaximum((float)10.0);

    }

    public void generateGraph()
    {
        double x = 0.0;
        double y = 20.0;                    //Starting our graph from 20
        double x_end = DataPointsRight[0];
        double y_end = DataPointsRight[1];
        List<Entry> entries = new ArrayList<Entry>();

        /**
         * This loop will render the initial light beam. (before it crosses into the second medium)
         */


        while (y >= 0)
        {
            y = y-0.1;
            entries.add(new Entry((float) x, (float) y));
        }

        /**
         * Loop adds 100 datapoints to the chart. This will render the refracted light beam (after it crosses into the second medium)
         */
        for (int i = 0; i < 100; i++)
        {
            y = y + y_end/100;
            x = x + x_end/100;
            entries.add(new Entry((float) x, (float) y));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label");
        dataSet.setDrawCircles(false);
        dataSet.setLineWidth((float) 20.0);
        dataSet.setColor(Color.RED);
        LineData lineData = new LineData(dataSet);
        graph.setData(lineData);                // Gives the graph the data generated
        graph.invalidate();                     // Renders graph
        //graph.animateX(750);                    // Animates the formation of the data. (milliseconds)




    }

    public void initializeGraph()
    {
        double x = 0.0;
        double y = 10.0;
        List<Entry> entries = new ArrayList<Entry>();
        while (y > -10)
        {
            y = y-0.1;
            entries.add(new Entry((float) x, (float) y));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label");
        dataSet.setDrawCircles(false);
        dataSet.setLineWidth((float) 20.0);
        dataSet.setColor(Color.RED);
        LineData lineData = new LineData(dataSet);
        graph.setData(lineData);                // Gives the graph the data generated
        graph.invalidate();                     // Renders graph
        graph.animateX(2000);                    // Animates the formation of the data. (milliseconds)


    }
}

