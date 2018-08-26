package com.jack.cpt.rollnumberview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private RollNumberTextView mRollNumberTextView;
    private int mCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRollNumberTextView = findViewById(R.id.roll_number_text_view);
        mRollNumberTextView.startAnimator(0, 6666666);
    }

    public void start(View view) {
        if (mCount == 1) {
            mRollNumberTextView.startAnimator(1212132, 5756521);
        } else if (mCount == 2) {
            mRollNumberTextView.startAnimator(5756521, 5367498);
        } else if (mCount == 3) {
            mRollNumberTextView.startAnimator(5367498, 6666666);
        }
        mCount++;
    }
}
