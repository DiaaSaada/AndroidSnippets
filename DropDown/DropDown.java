 

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;



import java.util.List;

public class DropDown extends LinearLayout {


    private static final String TAG = DropDown.class.toString();
    Context context;
    Spinner dropdown;
    TextView label_dropdown;
    TextInputLayout input_layout_dropdown;
    List dataSet;

    public DropDown(Context context, ViewGroup parent, String uploadEndPoint) {
        super(context);
        initView(context);
    }

    public DropDown(Context context) {
        super(context);
        initView(context);
    }


    public DropDown(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DropDown(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DropDown(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void initView(Context ctx) {

        context = ctx;

        View layout = inflate(getContext(), R.layout.custom_dropdown, this);
        dropdown = layout.findViewById(R.id.dropdown);
        input_layout_dropdown = layout.findViewById(R.id.input_layout_dropdown);
        label_dropdown = layout.findViewById(R.id.label_dropdown);


    }

    public void setupDropdown(List dataSet) {
        setupDropdown(dataSet, null);
    }

    public void setupDropdown(List dataSet, String label) {

        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.dropdown_selected_item, dataSet);
        adapter.setDropDownViewResource(R.layout.dropdown_list_item);
        dropdown.setAdapter(adapter);
        if (label == null) {
            label_dropdown.setText(dropdown.getAdapter().getItem(0).toString());
        } else {
            label_dropdown.setText(label);
        }

        setOnItemSelectedListener(null);
    }

    public void setOnItemSelectedListener(final AdapterView.OnItemSelectedListener callback) {
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "position" + position);
                if (position > 0) {
                    Log.e(TAG, "VISIBLE");
                    label_dropdown.setVisibility(VISIBLE);
                } else {
                    Log.e(TAG, "GONE");
                    label_dropdown.setVisibility(GONE);
                }
                if (callback != null) {
                    callback.onItemSelected(parent, view, position, id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e(TAG, "onNothingSelected");
                label_dropdown.setVisibility(GONE);
                if (callback != null) {
                    callback.onNothingSelected(parent);
                }
            }
        });
    }


    public int getSelectedItemPosition() {
        return dropdown.getSelectedItemPosition();
    }

    public Object getSelectedItem() {
        return dropdown.getSelectedItem();
    }

    public void setError(String error) {
        input_layout_dropdown.setError(error);
    }

    public void setSelection(int position) {
        dropdown.setSelection(position);
    }

    public void setAdapter(SpinnerAdapter adapter) {
        dropdown.setAdapter(adapter);
    }


    public void setSelection(int position, boolean animate) {
        dropdown.setSelection(position, animate);
    }

    public void readOnly(boolean read) {

        dropdown.setEnabled(false);
        dropdown.setFocusable(false);
        dropdown.setClickable(false);

    }

    public void setSelection(String selectedString, boolean animate) {

        for (int i = 0; i < dataSet.size(); i++) {
            if (dataSet.get(i).equals(selectedString)) {
                dropdown.setSelection(i , animate);
                return;
            }
        }
    }
}
