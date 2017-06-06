package com.example.xenom.opticsmenu.Light.Refraction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xenom.opticsmenu.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/*
This class is responsible for generating diagrams and questions that test principles of light's refraction through various lenses.
 */

public class Snell_Quiz extends AppCompatActivity {

    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private Button generateGraphBtn;
    private double[] datapoints;
    private double n1,n2,theta1,theta2;
    int beamlength =  10;
    EditText textN1, textTheta1, textN2, textTheta2; //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snell__quiz);
        series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0,0),
        });

        /**
         * Setting some graph settings (no gridlines, background image, etc)
         */

        graph = (GraphView) findViewById(R.id.graph);
        //This segment of code will set the bounds for the graph.
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-10);
        graph.getViewport().setMaxX(10);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-10);
        graph.getViewport().setMaxY(10);


        //Sets background
        int imageResource = getResources().getIdentifier("@drawable/graph_background", null, getPackageName());
        Drawable graph_background = getResources().getDrawable(imageResource);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            graph.setBackground(graph_background);
        }

        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);// It will remove the background grids
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);// remove horizontal x labels and line
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);


        /**
         * Sets Button OnClickListener and onLongClickListener for graph
         */
        Button checkansbtn = (Button)findViewById(R.id.checkAnsBtn);

        checkansbtn.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View v) {
                checkAns();                                                             //Checks answers when pressed
            }
        });

        graph.setOnLongClickListener(new View.OnLongClickListener()
        {


            @Override
            public boolean onLongClick(View v) {
                generateGraph();
                return false;
            }
        });

    }


    public void generateGraph() {

        /**
         *This function is responsible for generating the graph. It also fills in the EditText fields with useful information.
         * */
        graph.removeAllSeries(); //Clears Graph
        setDataPoints();        //Generates datapoints

        double x1 = datapoints[0]; //Adds datapoints
        double y1 = datapoints[1];
        double x2 = datapoints[2];
        double y2 = datapoints[3];
        /**
         * Sometimes x2 and y2 are NaN here, even though they've been properly initialized in
         * generateDataPoints(). Working on a fix. But, for now this while loop should suffice.
         */

        while (Double.isNaN(x2))
        {
            graph.removeAllSeries();
            setDataPoints();
            x1 = datapoints[0]; //Adds datapoints
            y1 = datapoints[1];
            x2 = datapoints[2];
            y2 = datapoints[3];
        }



        series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(x1, y1),
                new DataPoint(0, 0), //We have defined the origin to be the point where the light beam changes mediums
                new DataPoint(x2, y2)
        });


        graph.addSeries(series);
        graph.addSeries(generateThetaOneCircle());
        graph.addSeries(generateThetaTwoCircle());
        generateText();


    }



    public void setDataPoints() {
        datapoints = generateDataPoints();
    }


    public double[] generateDataPoints() {


        /*
        Snell's law will calculate the trajectory of the light waves
        n1 sin theta1 = n2 sin theta2
        Reference: https://www.math.ubc.ca/~cass/courses/m309-01a/chu/Fundamentals/snell.htm
        n1, n2, and theta1 can be randomly generated. theta2 will be calculated using Snell's law.
        Based on this, a diagram can be generated using the GraphView library
        NOTE: We want both line segments to be the same length, so the code will take that into account.
        This function generates random data each time it is called.
        */
        Random rand = new Random(); //Randomly generate number seed
        double tmin = Math.toRadians(45);
        double tmax = Math.toRadians(90);
        theta1 = rand.nextDouble()*(tmax - tmin) + tmin; //Range is 45 deg to 90 deg. Units here are in rads
        double x1; //Datapoints of the segments of the beam of light
        double y1;
        double x2;
        double y2;
        double nmin = 1.0;
        double nmax = 4.0;
        n1 = rand.nextDouble()*(nmax - nmin) + nmin; //Generate index of refraction randomly from 1.0 (air) to 4.0 (Germanium)
        n2 = rand.nextDouble()*(nmax - nmin) + nmin;

        //Solving for theta2: theta2 = asin(n1/n2 * sin (theta1))
        theta2 = (double) Math.asin(n1/n2*Math.sin(theta1));

        /**
         * Basic Trigonometry allows us to create our diagrams. We want both line segments (the refracted beam of light)
         * to be the same length.
         * So, we define their x and y coordinates such that the root of the sum of their squares is equal. i.e., Pythagoras' Theorem
         * sqrt(x1^2 + y1^2) = sqrt(x2^2 + y2^2)
         * We define the origin as where the light beam changes medium, and select an arbitrary length for the hypotenus.
         * This really doesn't affect calculations. It's just for GraphView to render. I've declared it already, int beamlength.
         * It doesn't have to be an int, it can be anything really. Just as long as it remains within the bounds of the graph
         */

        //This segment of code calculates the actual DataPoints to be returned.

        x1 = -1 * beamlength *Math.cos(theta1); //Negative because beam 1 comes from the left side of the graph
        y1 = beamlength * Math.sin(theta1); //Positive because beam 1 comes from the top
        x2 = beamlength * Math.cos(theta2); //Positive because of explanation above, just flipped
        y2 = -beamlength * Math.sin(theta2); // Read above. If the explanation is confusing, the diagram will explain it




        if (Double.isNaN(theta2)){          //Sometimes theta2 is NaN, so just keep generating points until it exists
            generateDataPoints();
        }

        double [] datapoints ={x1 ,y1, x2, y2};




        return datapoints;


    }


    public Series generateThetaOneCircle() {

        /**
         * quarter circle equation: y = sqrt(R^2 - x^2)
         * x is self explanatory.
         * R will be defined as the distance from the center of the circle to the midpoint of each line segment
         * Since we defined the length of each line segment to be 10, R = 5
         *
         * This is used to generate an arc that labels theta one in the diagram.
         */
        double x, y;
        x = datapoints[0]/2; //Start at x midpoint of the first line segment
        y = 0;          //Initial value of y is y1
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        while (x < 0)
        {
            x = x+0.01;
            y = Math.sqrt(Math.pow(beamlength/2,2)-Math.pow(x,2));
            series.appendData(new DataPoint(x,y),true,500);

        }

        series.setColor(Color.BLACK);
        series.setThickness(2);
        return series;
    }

    public Series generateThetaTwoCircle() {

        /**
         * quarter circle equation: y = sqrt(R^2 - x^2)
         * x is self explanatory.
         * R will be defined as the distance from the center of the circle to the midpoint of each linesegment
         *
         * This is used to generate an arc that labels theta two in the diagram.
         * Diagram with explanation will be included
         */
        double x, y;
        x = 0;
        y = 0;
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        while(x < datapoints[2]/2)           //datapoints[2] is x2
        {
            y = -Math.sqrt(Math.pow(beamlength/2,2)-Math.pow(x,2));
            series.appendData(new DataPoint(x,y),true,500);
            x = x+0.01;

        }
        series.setColor(Color.RED);
        series.setThickness(2);
        return series;
    }

    public void checkAns(){

        textTheta2 = (EditText)findViewById(R.id.editTheta2);
        double theta2 = Double.parseDouble(textTheta2.getText().toString());         //Gets text from theta2 EditText, parses for double.
        double ratio = Math.abs((Math.toRadians(90 - theta2) - this.theta2) / this.theta2)

                ; //Divides theta2 from input field to calculated theta2.

        if (ratio <= 0.05 ) {             //If ratio is within 0.05, (answer is within 5% of true value) user is correct
            //This is 90-theta2 because the thetas have been defines as the angles without an arc in the diagram.

            AnsPrompt(true);                  //Correct Answer Prompt

        }
        else {

            AnsPrompt(false);

        }


    }

    public void AnsPrompt(boolean correct) {

        /**
         * If correct/incorrect, positive button prompt generated that notifies the user.
         * If correct, generates new graph.
         */


        if (correct) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Correct!")
                    .setCancelable(false)
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            resetText();
            generateGraph();

        }

        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Sorry, that's not correct")
                    .setCancelable(false)
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    public void setSeries(LineGraphSeries<DataPoint> series) {
        this.series = series;
    }

    public void generateText() { //Fills EditTexts with info necessary to answer the question
        textN1 = (EditText)findViewById(R.id.editN1);
        textN2 = (EditText)findViewById(R.id.editN2);
        textTheta1 = (EditText)findViewById(R.id.editTheta1);

        textN1.setText("N1 = "+ round(n1,3));
        textN2.setText("N2 = "+ round(n2,3));
        textTheta1.setText("Theta2 = "+ round((90 - Math.toDegrees(theta2)),3));



    }
    public void resetText()
    {
        textN1.setText("N1 = ");
        textN2.setText("N2 = ");
        textTheta1.setText("Theta2 = ");
        textTheta2.setText("");
    }

    public static double round(double value, int places) {                  //Just a rounding function. Rounds a double to <places> decimals.
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }




}