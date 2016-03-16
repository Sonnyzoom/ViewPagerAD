package com.sonnyzoom.viewpagerad;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.sonnyzoom.viewpagerad.bean.DataInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    public static final String URL = "http://spark.api.xiami.com/sdk?v=sdk&method=mobile.sdk-image&device_id=de717e77-6330-3c13-b601-e3c6228d4f16&api_key=bbf59448b1d2a0254d033bfe3b4d8a30&call_id=1443063251736&api_sig=5c29b6c2ffed24d7f5b481d7deb0511e";
    public static final String TGA = "MainActivity";
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private List<DataInfo.DataEntity.ImgsEntity> imgs;
    private List<ImageView> imageViewList;

    private int currentIndex=300; //初始值可以设置大一点，防止左划到尽头。
    private long lastTime;

    private Gson gson;

    private boolean isCyclical = true;

    private Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {

            if (isCyclical) {
                if (System.currentTimeMillis() - lastTime > 2000) {
                    currentIndex++;
                    viewPager.setCurrentItem(currentIndex);
                    lastTime = System.currentTimeMillis();
                }
                //递归循环，图片切换速度3秒一张
                handler.postDelayed(r, 3000);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        imgs = new ArrayList<>();
        imageViewList = new ArrayList<>();
        gson = new Gson();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //拿到网络请求的数据
                doSomething(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TGA, "", volleyError);
            }
        });
        requestQueue.add(request);

    }

    private void doSomething(String s) {

        DataInfo info = gson.fromJson(s, DataInfo.class);
        DataInfo.DataEntity entity = info.getData();
        imgs = entity.getImgs();

        for (int i = 0; i < imgs.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Glide.with(this)
                    .load(imgs.get(i).getPic_url_yasha())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            imageViewList.add(imageView);
            ImageView dot = new ImageView(this);
            dot.setImageResource(R.drawable.point_normal);
            dot.setPadding(10, 5, 10, 5);
            linearLayout.addView(dot);

        }

        MyAdapter adapter = new MyAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentIndex);
        viewPager.addOnPageChangeListener(this);

        handler.post(r);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(final int position) {
        Log.e("位置：", position + "");
        currentIndex = position;
        int index = position % imageViewList.size();
        setCurrentSelector(index);
        lastTime = System.currentTimeMillis();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setCurrentSelector(int index) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ImageView child = (ImageView) linearLayout.getChildAt(i);
            if (i == index) {
                child.setImageResource(R.drawable.point_selected);
            } else {
                child.setImageResource(R.drawable.point_normal);
            }
        }
    }


    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;  //最大值，可以认为无限大，反正你划不到尽头就行了
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            //在0~imageViewList.size()之间循环
            int index = position % imageViewList.size();

            imageViewList.get(index).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //处理图片点击事件....
                    Log.e("点击图片：",position % imageViewList.size()+"");
                }
            });

            if (imageViewList.size() > 0) {
                View view = imageViewList.get(index);
                if (container.equals(view.getParent())) {
                    container.removeView(view);
                }
                container.addView(view);
                return view;
            }


            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            int index = position % imageViewList.size();
            container.removeView(imageViewList.get(index));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isCyclical = false; //Activity退出后，图片循环线程停止
    }
}
