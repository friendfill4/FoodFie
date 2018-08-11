package com.friendfill.foodifie;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SampleActivity extends AppCompatActivity {
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;
    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.bind(this);
        int parentLayoutWidth, triangleWidth;

        parentLayoutWidth = linearlayout.getWidth();
        triangleWidth = imageView.getWidth();

        int qtdTriangle = (int) Math.round((double) parentLayoutWidth / (double) triangleWidth);

        int newTriangleWidth = parentLayoutWidth / qtdTriangle;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(newTriangleWidth, imageView.getHeight());

        int trianglesSumWidth = newTriangleWidth * qtdTriangle;
        int diff = parentLayoutWidth - trianglesSumWidth;

        for (int i = 0; i < qtdTriangle; i++) {
            ImageView ivTriangle = new ImageView(getApplicationContext());
            ivTriangle.setImageResource(R.drawable.triangle);
            ivTriangle.setLayoutParams(layoutParams);

            int plusPixel = diff > 0 ? 1 : 0;

            LinearLayout.LayoutParams triangleLayoutParams = new LinearLayout.LayoutParams(newTriangleWidth + plusPixel, imageView.getHeight());
            ivTriangle.setScaleType(ImageView.ScaleType.FIT_XY);
            ivTriangle.setColorFilter(plusPixel == 1 ? Color.GREEN : Color.WHITE);
            ivTriangle.setLayoutParams(triangleLayoutParams);

            linearlayout.addView(ivTriangle);

            diff--;
        }
    }
}
