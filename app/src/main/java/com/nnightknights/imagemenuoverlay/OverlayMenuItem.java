package com.nnightknights.imagemenuoverlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

class OverlayMenuItem extends View {
    private PreferredItemType preferredItemType = PreferredItemType.TEXT;
    private String textRepresentation = "Menu Item";
    private TextPaint textPaint;
    private StaticLayout textStaticLayout;
    private Shape menuItemBackground;
    private Paint menuItemBackgroundPaint;
    private float width;
    private float height;

    @Nullable
    private Bitmap image;
    @Nullable
    private Paint imagePaint;
    private BitmapDrawType bitmapDrawType = BitmapDrawType.CUT;

    public OverlayMenuItem(@Nullable Context context) {
        this(context, null);
    }

    public OverlayMenuItem(@Nullable Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverlayMenuItem(@Nullable Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMenuItem();
    }

    public enum PreferredItemType {
        TEXT, BITMAP, BITMAP_WITH_TEXT
    }

    public enum BitmapDrawType {
        CUT, RESIZE_CUT, PADDED, PADDED_CUT_TO_SHAPE
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch(preferredItemType){
            case TEXT: {
                drawText(canvas);
                break;
            }
            case BITMAP: {
                drawBitmap(canvas);
                break;
            }
            case BITMAP_WITH_TEXT: {
                drawBitmapWithText(canvas);
                break;
            }
        }
    }

    private void initMenuItem() {
        textRepresentation = "Hello World";
        textPaint = new TextPaint();
        textPaint.setTextSize(18 * getResources().getDisplayMetrics().density);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textPaint.setColor(getResources().getColor(android.R.color.white, getContext().getTheme()));
        } else {
            textPaint.setColor(getResources().getColor(android.R.color.white));
        }
        textPaint.setAntiAlias(true);

        setRectMenuItemBackground();
        menuItemBackgroundPaint = new Paint();
        menuItemBackgroundPaint.setAntiAlias(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            menuItemBackgroundPaint.setColor(getResources().getColor(android.R.color.black, getContext().getTheme()));
        } else {
            menuItemBackgroundPaint.setColor(getResources().getColor(android.R.color.black));
        }

        redraw();
    }

    private void redraw(){
        switch(preferredItemType) {
            case TEXT: {
                redrawText();
                break;
            }
            case BITMAP: {
                redrawBitmap();
                break;
            }
            case BITMAP_WITH_TEXT: {
                redrawBitmapText();
                break;
            }
        }
    }

    private void redrawText(){
        width = textPaint.measureText(textRepresentation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textStaticLayout = StaticLayout.Builder.obtain(textRepresentation, 0, textRepresentation.length(), textPaint, (int) width)
                    .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                    .setBreakStrategy(Layout.BREAK_STRATEGY_BALANCED)
                    .setLineSpacing(0, 1f)
                    .setIncludePad(true)
                    .setMaxLines(2)
                    .build();
        } else {
            textStaticLayout = new StaticLayout(textRepresentation, textPaint, (int) width, Layout.Alignment.ALIGN_NORMAL, 1f, 0, true);
        }
        resizeBackground(getPaddingStart() + getPaddingEnd() + textStaticLayout.getWidth(), getPaddingTop() + getPaddingBottom() + textStaticLayout.getHeight());
        invalidate();
    }

    private void redrawBitmap() {
        resizeBackground(width, height);
        if (image != null){
            switch (bitmapDrawType) {
                case CUT: {

                }
                case PADDED: {

                }
                case RESIZE_CUT: {

                }
                case PADDED_CUT_TO_SHAPE: {

                }
            }
        }
    }

    private void redrawBitmapText() {
    }

    private void drawText(Canvas canvas) {
        menuItemBackground.draw(canvas, menuItemBackgroundPaint);
        canvas.save();
        canvas.translate(getPaddingStart(), getPaddingTop());
        textStaticLayout.draw(canvas);
        canvas.restore();
    }

    private void drawBitmap(Canvas canvas) {
        menuItemBackground.draw(canvas, menuItemBackgroundPaint);
        canvas.save();
        canvas.translate(getPaddingStart(), getPaddingTop());
        if (image != null) {
            canvas.drawBitmap(image, 0, 0, imagePaint);
        } else {
            throw new IllegalStateException("Image is not set.");
        }
        canvas.restore();
    }

    private void drawBitmapWithText(Canvas canvas) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (int) menuItemBackground.getWidth();
        int height = (int) menuItemBackground.getHeight();

        if (preferredItemType == PreferredItemType.TEXT) {
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthRequirement = MeasureSpec.getSize(widthMeasureSpec);
            if (widthMode == MeasureSpec.EXACTLY) {
                width = widthRequirement;
            } else {
                width = textStaticLayout.getWidth() + getPaddingLeft() + getPaddingRight();
                if (widthMode == MeasureSpec.AT_MOST) {
                    if (width > widthRequirement) {
                        width = widthRequirement;

                        textStaticLayout = new StaticLayout(textRepresentation, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
                    }
                }
            }

            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightRequirement = MeasureSpec.getSize(heightMeasureSpec);
            if (heightMode == MeasureSpec.EXACTLY) {
                height = heightRequirement;
            } else {
                height = textStaticLayout.getHeight() + getPaddingTop() + getPaddingBottom();
                if (heightMode == MeasureSpec.AT_MOST) {
                    height = Math.min(height, heightRequirement);
                }
            }
        }
        setMeasuredDimension(width, height);
    }

    public void setWidthPx(float widthPx) {
        this.width = width;
    }

    public void setWidthDp(float widthDp) {
        setWidthPx(dpToPx(widthDp));
    }

    public void setHeightPx(float heightPx) {
        this.height = height;
    }

    public void setHeightDp(float heightDp) {
        setHeightPx(dpToPx(heightDp));
    }

    public void setPreferredItemType(PreferredItemType preferredItemType) {
        this.preferredItemType = preferredItemType;
    }

    public void setTextRepresentation(String textRepresentation) {
        this.textRepresentation = textRepresentation;
    }

    public void setTextPaint(Color textPaint) {
        this.textPaint.setColor(textPaint.hashCode());
    }

    public void setImage(@NonNull Bitmap image) {
        this.image = image;
    }

    public void setBitmapResource(int bitmapResource){
        image = BitmapFactory.decodeResource(getResources(), bitmapResource);
    }

    public void setImagePaint(Paint imagePaint) {
        imagePaint = new Paint();
        this.imagePaint = imagePaint;
    }

    public void setBitmapDrawType(BitmapDrawType bitmapDrawType) {
        this.bitmapDrawType = bitmapDrawType;
    }

    public void setMenuItemBackground(@Nullable Shape menuItemBackground) {
        this.menuItemBackground = menuItemBackground;
    }

    public void setRectMenuItemBackground() {
        setMenuItemBackground(new RectShape());
    }

    public void setArcMenuItemBackground(float startAngle,float sweepAngle) {
        setMenuItemBackground(new ArcShape(startAngle, sweepAngle));
    }

    public void setOvalMenuItemBackground(){
        setMenuItemBackground(new OvalShape());
    }

    public void setRoundRectMenuItemBackground(@Nullable float[] outerRadius, @Nullable RectF inset, @Nullable float[] innerRadius){
        setMenuItemBackground(new RoundRectShape(outerRadius, inset, innerRadius));
    }

    public void setPathMenuItemBackground(@NonNull Path path, float defaultWidth, float defaultHeight){
        setMenuItemBackground(new PathShape(path, defaultWidth, defaultHeight));
    }

    public void setMenuItemBackgroundPaint(Color menuItemBackgroundPaint) {
        this.menuItemBackgroundPaint.setColor(menuItemBackgroundPaint.hashCode());
    }

    private void resizeBackground(float width, float height){
        if (menuItemBackground == null){
            throw new IllegalStateException("The menu item background is not set. You must call one of the background setters before resizing the background!");
        }
        menuItemBackground.resize(width, height);
    }

    private float dpToPx(float dp){
        return dp * getResources().getDisplayMetrics().density;
    }
}
