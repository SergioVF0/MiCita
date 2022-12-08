package com.example.micita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//clase para puntuar la aplicación, formada por una ratingBar y un botón de confirmación
public class RatingActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Button submitButton = (Button) findViewById(R.id.submitButton);
        final RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.ratingBar);

        // onClick del botón
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String totalStars = "Total Stars:: " + simpleRatingBar.getNumStars();
                String rating = "Rating :: " + simpleRatingBar.getRating();
                Toast.makeText(getApplicationContext(), totalStars + "\n" + rating, Toast.LENGTH_LONG).show();
            }
        });
        getSupportActionBar().setTitle("Danos tu opinión");



    }




}

