package com.nnightknights.imagemenuoverlay;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class ImageMenuOverlay extends FrameLayout {
    private MenuOverlay menuOverlay;

    public ImageMenuOverlay(@NonNull Context context) {
        this(context, null);
    }

    public ImageMenuOverlay(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageMenuOverlay(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
