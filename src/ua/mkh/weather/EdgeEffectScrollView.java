package ua.mkh.weather;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

public class EdgeEffectScrollView extends android.widget.ScrollView {

	private static final int MAX_Y_OVERSCROLL_DISTANCE = 180;

    private Context mContext;
    private int mMaxYOverscrollDistance;
    
  public EdgeEffectScrollView(Context context){
    this(context, null);
  }

	public EdgeEffectScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.scrollViewStyle);
        mContext = context;
        initBounceScrollView();
	}

	public EdgeEffectScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(new ContextWrapperEdgeEffect(context), attrs, defStyle);
    init(context, attrs, defStyle);
	}

  private void init(Context context, AttributeSet attrs, int defStyle){
    int color = context.getResources().getColor(R.color.default_edgeeffect_color);

    if (attrs != null) {
      TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EdgeEffectView, defStyle, 0);
      color = a.getColor(R.styleable.EdgeEffectView_edgeeffect_color, color);
      a.recycle();
    }
    setEdgeEffectColor(color);
  }

  public void setEdgeEffectColor(int edgeEffectColor){
    ((ContextWrapperEdgeEffect)  getContext()).setEdgeEffectColor(edgeEffectColor);
  }
  
  private void initBounceScrollView()
  {
      //get the density of the screen and do some maths with it on the max overscroll distance
      //variable so that you get similar behaviors no matter what the screen size

      final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
          final float density = metrics.density;

      mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
  }

  @Override
  protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) 
  { 
      //This is where the magic happens, we have replaced the incoming maxOverScrollY with our own custom variable mMaxYOverscrollDistance; 
      return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);  
  }
}
