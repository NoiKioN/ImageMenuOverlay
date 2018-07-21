package com.nnightknights.imagemenuoverlay;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class MenuOverlay extends FrameLayout {
    private OverlayMenuItem[] menuItems;

    public MenuOverlay(Context context) {
        this(context, null);
    }
    public MenuOverlay(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MenuOverlay(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
