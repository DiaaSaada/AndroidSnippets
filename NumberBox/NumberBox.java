package com.boraq.sparaat.customviews;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;

import com.boraq.sparaat.helpers.InputFilterMinMax;

public class NumberBox extends android.support.v7.widget.AppCompatEditText {


    private static final String TAG = NumberBox.class.toString();
    private int min, max;
    private int val = 0;

    public NumberBox(Context context) {
        super(context);
        init();
    }

    public NumberBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumberBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setInputType(InputType.TYPE_CLASS_NUMBER);
    }


    public void setMinMax(int min, int max) {
        this.min = min;
        this.max = max;
        this.setFilters(new InputFilter[]{new InputFilterMinMax(min, max)});
    }


    public boolean isValid(int min, int max ) {
        try {
            this.val = Integer.parseInt(this.getText().toString().trim());
           // Log.v(TAG, "V" + val + "n" + min + "x" + max);
            if (this.val >= min && this.val <= max) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public int val() {
        try {
            return Integer.parseInt(this.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


}
