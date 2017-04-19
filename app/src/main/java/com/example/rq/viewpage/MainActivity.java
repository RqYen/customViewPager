package com.example.rq.viewpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private JikeViewPager jikeViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jikeViewPager = (JikeViewPager)findViewById(R.id.vp);
    }

    public void page2(View view) {
        jikeViewPager.setPageIndex(2);
    }
    public void page1(View view) {
        jikeViewPager.setPageIndex(1);
    }
}
