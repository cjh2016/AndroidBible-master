package com.cjh.lib_common.spinner;

import java.util.List;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.cjh.lib_common.R;


/**
 * @author angelo.marchesin
 */
@SuppressWarnings("unused")
public class NiceSpinner extends TextView {

    private static final int MAX_LEVEL = 10000;
    private static final int DEFAULT_ELEVATION = 16;
    private static final String INSTANCE_STATE = "instance_state";
    private static final String SELECTED_INDEX = "selected_index";
    private static final String IS_POPUP_SHOWING = "is_popup_showing";

    private int mSelectedIndex;
    private Drawable mDrawable;
    private PopupWindow mPopup;
    private ListView mListView;
    private NiceSpinnerBaseAdapter mAdapter;
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    private boolean mHideArrow;

    @SuppressWarnings("ConstantConditions")
    public NiceSpinner(Context context) {
        super(context);
        init(context, null);
    }

    public NiceSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NiceSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(SELECTED_INDEX, mSelectedIndex);

        if (mPopup != null) {
            bundle.putBoolean(IS_POPUP_SHOWING, mPopup.isShowing());
            dismissDropDown();
        }

        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable savedState) {
        if (savedState instanceof Bundle) {
            Bundle bundle = (Bundle) savedState;

            mSelectedIndex = bundle.getInt(SELECTED_INDEX);

            if (mAdapter != null) {
                setText(mAdapter.getItemInDataset(mSelectedIndex).toString());
                mAdapter.notifyItemSelected(mSelectedIndex);
            }

            if (bundle.getBoolean(IS_POPUP_SHOWING)) {
                if (mPopup != null) {
                    // Post the show request into the looper to avoid bad token exception
                    post(new Runnable() {
                        @Override
                        public void run() {
                            showDropDown();
                        }
                    });
                }
            }

            savedState = bundle.getParcelable(INSTANCE_STATE);
        }

        super.onRestoreInstanceState(savedState);
    }

    private void init(Context context, AttributeSet attrs) {
        Resources resources = getResources();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NiceSpinner);
        int defaultPadding = resources.getDimensionPixelSize(R.dimen.one_and_a_half_grid_unit);

        setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        setPadding(resources.getDimensionPixelSize(R.dimen.three_grid_unit), defaultPadding, defaultPadding,
            defaultPadding);
        setClickable(true);
        setBackgroundResource(R.drawable.selector);

        mListView = new ListView(context);
        // Set the spinner's id into the listview to make it pretend to be the right parent in
        // onItemClick
        mListView.setId(getId());
        mListView.setDivider(null);
        mListView.setItemsCanFocus(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*if (position >= mSelectedIndex && position < mAdapter.getCount()) {
                    position++;
                }*/

                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(parent, view, position, id);
                }

                if (mOnItemSelectedListener != null) {
                    mOnItemSelectedListener.onItemSelected(parent, view, position, id);
                }

                mAdapter.notifyItemSelected(position);
                mSelectedIndex = position;
                setText(mAdapter.getItemInDataset(position).toString());
                dismissDropDown();
            }
        });

        mPopup = new PopupWindow(context);
        mPopup.setContentView(mListView);
        mPopup.setOutsideTouchable(true);
        mPopup.setFocusable(true);

        mPopup.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.popup_border));

        mPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!mHideArrow) {
                    animateArrow(false);
                }
            }
        });

        mHideArrow = typedArray.getBoolean(R.styleable.NiceSpinner_hideArrow, false);
        if (!mHideArrow) {
            Drawable basicDrawable = ContextCompat.getDrawable(context, R.drawable.arrow);
            int resId = typedArray.getColor(R.styleable.NiceSpinner_arrowTint, -1);

            if (basicDrawable != null) {
                mDrawable = DrawableCompat.wrap(basicDrawable);

                if (resId != -1) {
                    DrawableCompat.setTint(mDrawable, resId);
                }
            }
            setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable, null);
        }

        typedArray.recycle();
    }

    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    /**
     * Set the default spinner item using its index
     * 
     * @param position the item's position
     */
    public void setSelectedIndex(int position) {
        if (mAdapter != null) {
            if (position >= 0 && position <= mAdapter.getCount()) {
                mAdapter.notifyItemSelected(position);
                mSelectedIndex = position;
                setText(mAdapter.getItemInDataset(position).toString());
            } else {
                throw new IllegalArgumentException("Position must be lower than adapter count!");
            }
        }
    }

    public void addOnItemClickListener(@NonNull AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(@NonNull AdapterView.OnItemSelectedListener onItemSelectedListener) {
        mOnItemSelectedListener = onItemSelectedListener;
    }

    public <T> void attachDataSource(@NonNull List<T> dataset) {
        mAdapter = new NiceSpinnerAdapter(getContext(), dataset);
        setAdapterInternal(mAdapter);
    }

    public void setAdapter(@NonNull ListAdapter adapter) {
        mAdapter = new NiceSpinnerAdapterWrapper(getContext(), adapter);
        setAdapterInternal(mAdapter);
    }

    private void setAdapterInternal(@NonNull NiceSpinnerBaseAdapter adapter) {
        mListView.setAdapter(adapter);
        setText(adapter.getItemInDataset(mSelectedIndex).toString());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mPopup.setWidth(MeasureSpec.getSize(widthMeasureSpec));
        mPopup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!mPopup.isShowing()) {
            	mPopup.setWidth(getWidth());
                showDropDown();
            } else {
                dismissDropDown();
            }
        }

        return super.onTouchEvent(event);
    }

    private void animateArrow(boolean shouldRotateUp) {
        int start = shouldRotateUp ? 0 : MAX_LEVEL;
        int end = shouldRotateUp ? MAX_LEVEL : 0;
        ObjectAnimator animator = ObjectAnimator.ofInt(mDrawable, "level", start, end);
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        animator.start();
    }

    public void dismissDropDown() {
        if (!mHideArrow) {
            animateArrow(false);
        }
        mPopup.dismiss();
    }

    public void showDropDown() {
        if (!mHideArrow) {
            animateArrow(true);
        }
        mPopup.showAsDropDown(this);
    }

    public void setTintColor(@ColorRes int resId) {
        if (mDrawable != null && !mHideArrow) {
            DrawableCompat.setTint(mDrawable, getResources().getColor(resId));
        }
    }
    
    /**
     * 重写添加边框,并可以动态设置颜色
     */
    @Override
    protected void onDraw(Canvas canvas) {
    	Paint paint = new Paint();
    	paint.setColor(Color.BLUE);
    	paint.setStyle(Style.STROKE.STROKE);
    	paint.setStrokeWidth(2);
    	canvas.drawRect(0, 0, this.getWidth(), getHeight(), paint);
    	super.onDraw(canvas);
    }
}
