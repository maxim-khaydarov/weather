package ua.mkh.weather;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class MyProgressBarGreen extends ProgressBar {

    public MyProgressBarGreen(Context context) {
        this(context, null);
    }

    public MyProgressBarGreen(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBarGreen(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setProgressDrawable(context.getResources().getDrawable(R.drawable.custom_progress_drawable_green));
    }
}
