package com.placidvision.jesuscalls.viewpager;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.placidvision.jesuscalls.CategoryInfo;
import com.placidvision.jesuscalls.R;
import com.placidvision.jesuscalls.VideoActivity;
import com.placidvision.jesuscalls.VideoInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CustomViewPager extends ViewPager {
    private Context context;
    private LinearLayout dotLayout;
    private int currentPos = 8000;
    private CustomDurationScroller scroller;
    private ScrollHandler handler;
    private static final int SCROLL = 0;
    private static final int SCROLL_DELAY = 4000;

    void init() {
        handler = new ScrollHandler(this);

        setViewPagerScroller();

        setPageTransformer(true, new ZoomOutPageTransformer());

        sendScrollMessage();

        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                currentPos = position;
                setDotIndicator(dotLayout, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    public CustomViewPager(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void setViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);

            scroller = new CustomDurationScroller(getContext(), (Interpolator)interpolatorField.get(null));
            scrollerField.set(this, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setDotLayout(LinearLayout dotLayout) {
        this.dotLayout = dotLayout;
        setDotIndicator(dotLayout, currentPos);
    }

    public void setArrayAdapter(ArrayList<VideoInfo> videoInfos) {
        setAdapter(new SlideAdapter(videoInfos));
    }

    public void next() {
        stopScroll();
        setCurrentItem(++currentPos);
        setDotIndicator(dotLayout, currentPos);
        startScroll();
    }

    public void previous() {
        stopScroll();
        setCurrentItem(--currentPos, true);
        setDotIndicator(dotLayout, currentPos);
        startScroll();

    }

    public void startScroll() {
        sendScrollMessage();
    }

    public void stopScroll() {
        handler.removeMessages(SCROLL);
    }

    void setDotIndicator(LinearLayout dotLayout, int position) {
        position %= 4;

        if (dotLayout.getChildCount() > 0)
            dotLayout.removeAllViews();

        ImageView[] imageViews = new ImageView[4];

        for (int i = 0; i < 4; ++i) {
            imageViews[i] = new ImageView(context);
            if (i == position)
                imageViews[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));
            else
                imageViews[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.inactive_dot));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(4, 0, 4, 0);
            dotLayout.addView(imageViews[i], layoutParams);
        }

    }

    public void sendScrollMessage() {
        handler.removeMessages(SCROLL);
        handler.sendEmptyMessageDelayed(SCROLL, SCROLL_DELAY);
    }

    static class ScrollHandler extends Handler {
        private CustomViewPager viewPager;

        ScrollHandler(CustomViewPager viewPager) {
            this.viewPager = viewPager;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == SCROLL) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    viewPager.sendScrollMessage();
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                startScroll();
                break;
            case MotionEvent.ACTION_DOWN:
                stopScroll();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    class SlideAdapter extends PagerAdapter {
        private ArrayList<VideoInfo> videos;

        public SlideAdapter(ArrayList<VideoInfo> videos) {
            super();
            this.videos = videos;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            int pos = position % videos.size();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = (View) inflater.inflate(R.layout.slide_show_item, container, false);
            Glide.with(context)
                    .load(videos.get(pos).getThumbnailURL())
                    .placeholder(R.drawable.jc)
                    .into((ImageView) view.findViewById(R.id.image_view));
            container.addView(view);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, VideoActivity.class);
                    intent.putExtra("video", videos.get(pos));
                    context.startActivity(intent);
                }
            });
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            container.removeView((View) object);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

}
