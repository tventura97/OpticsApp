package com.example.xenom.opticsmenu.UI;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.xenom.opticsmenu.Light.LightMenu;
import com.example.xenom.opticsmenu.R;

/**
 * Created by xenom on 6/2/2017.
 */

public class CustomSwipeAdapter extends PagerAdapter {

    private int[] imageresources = {R.drawable.light, R.drawable.lens, R.drawable.mirror};
    private Context context;
    private LayoutInflater layoutInflater;
    private String[] titles = {"Light", "Lenses", "Mirrors"};
    ImageButton imageButton;
    TextView textView;

    public CustomSwipeAdapter(Context context)
    {
        this.context = context;

    }



    @Override
    public int getCount() {
        return imageresources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }


    /**
     * Creates each "slide" of the swipeview. There are 3 (as of right now) slides.
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = layoutInflater.inflate(R.layout.activity_fragment,container, false);
        imageButton = (ImageButton)itemView.findViewById(R.id.center_icon);
        textView = (TextView)itemView.findViewById(R.id.title);
        imageButton.setImageResource(imageresources[position]);
        textView.setText(titles[position]);
        container.addView(itemView);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(v.getContext(), LightMenu.class);
                v.getContext().startActivity(menu);
                /*
                switch(position) {
                    case 0:
                        Intent menu = new Intent(v.getContext(), LightMenu.class);
                        v.getContext().startActivity(menu);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
                */

            }
        });


        return itemView;
    }

    /**
     * Destroys each slide when the user moves away from it. Frees memory and ensures app remains fast
     */

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((ConstraintLayout) object);

    }



}
