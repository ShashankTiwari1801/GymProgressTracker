package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class CalendarScroller {
    Context context;
    LinearLayout scroller;
    HorizontalScrollView horizontalScrollView;
    LinearLayout LLContent;

    public CalendarScroller(Context context, LinearLayout scroller){
        this.context = context;
        this.scroller = scroller;
        load();
    }
    public void load(){
        horizontalScrollView = (HorizontalScrollView) scroller.getChildAt(0);
        LLContent = (LinearLayout) horizontalScrollView.getChildAt(0);
    }
    public LinearLayout getRoot(){
        return scroller;
    }
    public LinearLayout getContentRoot(){
        return LLContent;
    }
    public HorizontalScrollView getHorizontalScrollView(){
        return horizontalScrollView;
    }
}
