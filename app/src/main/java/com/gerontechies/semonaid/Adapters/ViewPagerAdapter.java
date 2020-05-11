package com.gerontechies.semonaid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gerontechies.semonaid.R;

//REFERENCES - https://www.youtube.com/watch?v=C1yrpsmJApU
public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
     Integer [] images ;
     String [] text ;

    public ViewPagerAdapter(Context context, Integer[] images, String[] text){
        this.context = context;
        this.images = images;
        this.text = text;

    }

    @Override
    public int getCount() {
        return  text.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slideshow_layout1, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.slideImg);
        TextView textView = (TextView) view.findViewById(R.id.slideTxt);

        imageView.setImageResource(images[position]);
        textView.setText(text[position]);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);

        return  view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
