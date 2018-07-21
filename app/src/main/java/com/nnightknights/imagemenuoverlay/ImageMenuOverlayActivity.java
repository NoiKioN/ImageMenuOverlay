package com.nnightknights.imagemenuoverlay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ImageMenuOverlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        OverlayMenuItem overlayMenuItem = findViewById(R.id.overlayMenuItem);
    }

}
