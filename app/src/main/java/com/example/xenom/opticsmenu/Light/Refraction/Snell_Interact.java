package com.example.xenom.opticsmenu.Light.Refraction;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.xenom.opticsmenu.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Better comments as well as a continuation document will be added
 */

public class Snell_Interact extends AppCompatActivity {
    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private TextView textViewN1;
    private TextView textViewN2;
    private SeekBar seekBarN1;
    private SeekBar seekBarN2;
    double theta1 = 90; //Theta 1 will always be 90 degrees.
    double theta2;
    double N1 = 1.00; //Initialze both indexes of refraction to 1.00;
    double N2 = 1.00;
    int beamlength = 10; //Length of beam of light is 10 units.
    double[] DataPointsRight = {0, 0};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snell__interact);

        graph = (GraphView) findViewById(R.id.graph);
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

        //This segment of code will set the bounds for the graph.
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-10);
        graph.getViewport().setMaxX(10);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-10);
        graph.getViewport().setMaxY(10);
        seekBarN1.setMax(300);
        seekBarN2.setMax(300);
        series.setThickness(5);     //Set Line Thickness to 5


        //Sets background
        int imageResource = getResources().getIdentifier("@drawable/graph_background", null, getPackageName());
        Drawable graph_background = getResources().getDrawable(imageResource);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            graph.setBackground(graph_background);
        }
        //Erases all graph gridlines
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);// It will remove the background grids
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);// remove horizontal x labels and line
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);

        //Initialize series to default value;
        series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 10),       //Hard coded here because I'm lazy
                new DataPoint(0, 0), //We have defined the origin to be the point where the light beam changes mediums
                new DataPoint(0, -10)
        });

        graph.addSeries(series);
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
                graph.removeAllSeries();
                textViewN2.setText("Index of Refraction N2: " + N2);
                this.progress = progress;
                setN2(seekBarN2.getProgress() / 100.0 + 1.00);
                setThetaTwo();

                DataPointsRight = generateDataPointsRight();


                series = new LineGraphSeries<>(new DataPoint[]{
                        new DataPoint(0, 10),               //Hard-coded here because I'm lazy right now, I'll change it later
                        new DataPoint(0, 0),                //We have defined the origin to be the point where the light beam changes mediums
                        new DataPoint(DataPointsRight[0], DataPointsRight[1])
                });
                graph.addSeries(series);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textViewN2.setText("Index of Refraction N2: " + N2);

            }
        });


    }


    public double[] generateDataPointsRight() {
        double x, y;
        x = beamlength * Math.cos(Math.toRadians(theta2));        //Simple trigonometry & Snell's law dictates this relationship.
        y = -1 * beamlength * Math.sin(Math.toRadians(theta2));         //Negative because we know that the beam of light is travelling downward

        DataPointsRight[0] = x;
        DataPointsRight[1] = y;
        return DataPointsRight;


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

    public void generateGraph() {

        double x = 0;
        double y =0;
        double slope = DataPointsRight[1]/DataPointsRight[0];
        while(x < DataPointsRight[0])
        {



        }

    }
}

