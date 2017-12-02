package com.example.shivam.myinternshipproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseCategoryActivity extends AppCompatActivity implements View.OnClickListener{
    Button news_btn,education_btn,sports_btn,science_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        news_btn = (Button)findViewById(R.id.category_news);
        science_btn = (Button)findViewById(R.id.category_science);
        sports_btn = (Button)findViewById(R.id.category_sports);
       education_btn = (Button)findViewById(R.id.category_education);
       news_btn.setOnClickListener(this);
        sports_btn.setOnClickListener(this);
        science_btn.setOnClickListener(this);
        education_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ChooseCategoryActivity.this,CategoryActivity.class);

        switch (view.getId()){
            case R.id.category_news:
            intent.putExtra("category","news");
                startActivity(intent);
            break;
            case R.id.category_sports:
                intent.putExtra("category","sports");
                startActivity(intent);
                break;
            case R.id.category_science:
                intent.putExtra("category","science");
                startActivity(intent);
                break;
            case R.id.category_education:
                intent.putExtra("category","education");
                startActivity(intent);
                break;
        }

    }
}
