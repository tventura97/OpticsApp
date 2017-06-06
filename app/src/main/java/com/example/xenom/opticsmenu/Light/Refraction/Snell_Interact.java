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

public class Snell_Interact extends AppCompatActivity {
    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private TextView textViewN1;
    private TextView textViewN2;
    private SeekBar seekBarN1;
    private SeekBar seekBarN2;
    double theta1;
    double theta2;
    double[] datapoints;
    double slope;
    final double [] x  = {-7.5, 7.5};
    double [] y = {0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snell__interact);

        graph = (GraphView) findViewById(R.id.graph);
        textViewN1 = (TextView) findViewById(R.id.textN1);
        textViewN2 = (TextView)findViewById(R.id.textN2);
        seekBarN1 = (SeekBar) findViewById(R.id.seekbarN1);
        seekBarN2 = (SeekBar)findViewById(R.id.seekbarN2);
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
                new DataPoint(-1, -1),
                new DataPoint(0, 0), //We have defined the origin to be the point where the light beam changes mediums
                new DataPoint(1, 1)
        });
        seekBarN1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                graph.removeAllSeries();
                textViewN1.setText("Index of Refraction: " + seekBar.getProgress());
                this.progress = progress;
                slope = seekBar.getProgress()/100.00;
                y[0] = slope * x[0];
                y[1] = slope * x[1];

                series = new LineGraphSeries<>(new DataPoint[]{
                        new DataPoint(x[0], y[0]),
                        new DataPoint(0, 0), //We have defined the origin to be the point where the light beam changes mediums
                        new DataPoint(x[1], y[1])
                });
                graph.addSeries(series);
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
                textViewN2.setText("Index of Refraction: " + seekBar.getProgress());
                this.progress = progress;
                slope = seekBar.getProgress()/100.00;
                y[0] = slope * x[0];
                y[1] = slope * x[1];

                series = new LineGraphSeries<>(new DataPoint[]{
                        new DataPoint(x[0], y[0]),
                        new DataPoint(0, 0), //We have defined the origin to be the point where the light beam changes mediums
                        new DataPoint(x[1], y[1])
                });
                graph.addSeries(series);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textViewN2.setText("Index of Refraction: " + progress);

            }
        });








    }
}
