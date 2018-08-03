package com.poject.fragments.guest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poject.R;
import com.poject.fragments.buyer.CarCreateGuetFragment;
import com.poject.helpers.AppCache;
import com.poject.helpers.AppConfig;
import com.poject.helpers.LocaleUtil;
import com.poject.models.WelcomeSlide;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class IntoFragment extends SparaatGuestBaseFragment implements View.OnClickListener {


    private static final String TAG = IntoFragment.class.toString();
    @BindView(R.id.dropdown_client_lang)
    TextView langSwitcher;


    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            updateDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

     

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    private void initView() {

        viewPager = this.mView.findViewById(R.id.pager);
        dotsLayout = this.mView.findViewById(R.id.layoutDots);

        // adding bottom dots
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        addBottomDots(0);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        Button btnLogin = this.mView.findViewById(R.id.btn_login1);
        Button btnReg = this.mView.findViewById(R.id.btn_register1);
        Button btRequest = this.mView.findViewById(R.id.btn_request);

        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(this);
        btRequest.setOnClickListener(this);

        String deviceLang = Locale.getDefault().getLanguage();
        String preferredLang = AppCache.getLanguage();
        lg("initView deviceLang" + deviceLang);
        lg("initView lang" + preferredLang);


        if (preferredLang == null) {  // set language in cache if cache was cleared on logout
            Log.e(TAG, " set language in cache if cache was cleared ");
            AppCache.putLanguage(deviceLang);
            preferredLang = deviceLang;
        }

        if (preferredLang.equals("en")) {
            langSwitcher.setText("العربية");
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/janna-bold.ttf");
            langSwitcher.setTypeface(tf);
        } else {
            langSwitcher.setText("English");
        }


    }

    

    


    


    private void addBottomDots(int currentPage) {
        dots = new TextView[3];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dotsLayout.addView(dots[i]);

            if (i == 0) {
                dots[i].setTextColor(Color.YELLOW);
            } else {
                dots[i].setTextColor(Color.WHITE);
            }
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.WHITE);

        updateDots(0);
    }

    private void updateDots(int currentPage) {
        for (int i = 0; i < dots.length; i++) {
            if (i == currentPage) {
                dots[i].setTextColor(Color.YELLOW);
            } else {
                dots[i].setTextColor(Color.WHITE);
            }
        }
    }


    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        ArrayList<WelcomeSlide> welcomeSlides;
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {

            welcomeSlides = WelcomeSlide.getAll(getActivity());

        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.partial_welcome_slide, container, false);
            container.addView(view);
            ImageView iv = view.findViewById(R.id.slide_img);
            TextView tv1 = view.findViewById(R.id.slide_title);
            TextView tv2 = view.findViewById(R.id.slide_body);

            iv.setImageResource(welcomeSlides.get(position).ImageResID);
            tv1.setText(welcomeSlides.get(position).title);
            tv2.setText(welcomeSlides.get(position).description);
            return view;
        }

        @Override
        public int getCount() {
            return welcomeSlides.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}
