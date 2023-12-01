package pavishka.coloring.book.Activities.Screens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.plattysoft.leonids.ParticleSystem;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import pavishka.coloring.book.Activities.OtherScreens.ScreenTempDataStore;
import pavishka.coloring.book.R;


public class ScreenDrawFuncAloe extends View {
    public static Canvas drawcanvas;
    public Bitmap canvasBitmap;
    public Bitmap kidBitmap;
    public boolean kidBitmapNeedDrawn;
    public int gapPlaySound = 0;
    public RectF imgRect = new RectF();
    ScreenManualTop drawActivityGlow;
    int anInt;
    Paint canvasPaint;
    Paint circlePaint;
    boolean aBoolean;
    Path drawPath;
    List<Integer> mColoursList;
    Paint mPaintLazer;
    Paint mPaintMain;
    float mX;
    float mY;
    RectF rectF = new RectF();
    int mCurrentColorIndex = 0;
    boolean drawEraser = false;

    public ScreenDrawFuncAloe(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.drawActivityGlow = (ScreenManualTop) context;
        setupDrawing();
        if (ScreenTempDataStore.getBuyChoise(getContext()) == 0) {
            this.aBoolean = false;
        } else {
            this.aBoolean = true;
        }
    }

    private int getColorIndex() {
        int size = this.mColoursList.size();
        PrintStream printStream = System.err;
        printStream.println("sizeee ::" + size + "--" + this.anInt);
        if (this.mCurrentColorIndex == size) {
            this.mCurrentColorIndex = 0;
        }
        int i = this.anInt;
        this.mCurrentColorIndex = i;
        return i;
    }

    private List<Integer> initRainbowColors() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 100; i++) {
            arrayList.add(Integer.valueOf(Color.rgb((i * 255) / 100, 255, 0)));
        }
        for (int i2 = 100; i2 > 0; i2--) {
            arrayList.add(Integer.valueOf(Color.rgb(255, (i2 * 255) / 100, 0)));
        }
        for (int i3 = 0; i3 < 100; i3++) {
            arrayList.add(Integer.valueOf(Color.rgb(255, 0, (i3 * 255) / 100)));
        }
        for (int i4 = 100; i4 > 0; i4--) {
            arrayList.add(Integer.valueOf(Color.rgb((i4 * 255) / 100, 0, 255)));
        }
        for (int i5 = 0; i5 < 100; i5++) {
            arrayList.add(Integer.valueOf(Color.rgb(0, (i5 * 255) / 100, 255)));
        }
        for (int i6 = 100; i6 > 0; i6--) {
            arrayList.add(Integer.valueOf(Color.rgb(0, 255, (i6 * 255) / 100)));
        }
        arrayList.add(Integer.valueOf(Color.rgb(0, 255, 0)));
        Collections.shuffle(arrayList);
        return arrayList;
    }

    private void setBrushSize() {
        this.mPaintMain.setStrokeWidth(ScreenConstable.brushSize);
        this.mPaintLazer.setStrokeWidth(ScreenConstable.brushSize / 3);
    }

    private void setDefaultBrushSize() {
        ScreenConstable.brushSize = 20;
        this.mPaintMain.setStrokeWidth(20.0f);
        this.mPaintLazer.setStrokeWidth(ScreenConstable.brushSize / 3);
    }

    private void setPathEffect() {
        int i = ScreenConstable.anInt8;
        if (i == 2) {
            Path path = new Path();
            path.addCircle(0.0f, 0.0f, ScreenConstable.anInt14, Path.Direction.CCW);
            int i2 = ScreenConstable.anInt14;
            this.mPaintLazer.setPathEffect(new PathDashPathEffect(path, i2 == 5 ? 15.0f : (i2 == 8 || i2 != 10) ? 20.0f : 35.0f, 20.0f, PathDashPathEffect.Style.ROTATE));
        } else if (i == 3) {
            int i3 = ScreenConstable.anInt14;
            if (i3 == 5) {
                this.mPaintLazer.setPathEffect(new DashPathEffect(new float[]{15.0f, 15.0f, 15.0f, 15.0f}, 0.0f));
            } else if (i3 == 8) {
                this.mPaintLazer.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f, 25.0f, 25.0f}, 0.0f));
            } else if (i3 == 10) {
                this.mPaintLazer.setPathEffect(new DashPathEffect(new float[]{35.0f, 35.0f, 35.0f, 35.0f}, 0.0f));
            } else {
                this.mPaintLazer.setPathEffect(new DashPathEffect(new float[]{15.0f, 15.0f, 15.0f, 15.0f}, 0.0f));
            }
        } else if (i == 1) {
            this.mPaintLazer.setPathEffect(new CornerPathEffect(100.0f));
        }
    }

    private void setPointOfSparkImage(float f, float f2) {
        ScreenManualTop.iv.setX(f);
        ScreenManualTop.iv.setY(f2);
    }

    private void setupDrawing() {
        this.mColoursList = initRainbowColors();
        this.drawPath = new Path();
        this.canvasPaint = new Paint(4);
        Paint paint = new Paint(1);
        this.circlePaint = paint;
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        Paint paint2 = new Paint();
        this.mPaintMain = paint2;
        paint2.setAntiAlias(true);
        this.mPaintMain.setPathEffect(new CornerPathEffect(100.0f));
        this.mPaintMain.setStyle(Paint.Style.STROKE);
        this.mPaintMain.setStrokeJoin(Paint.Join.ROUND);
        this.mPaintMain.setStrokeCap(Paint.Cap.ROUND);
        this.mPaintMain.setMaskFilter(new BlurMaskFilter(17.0f, BlurMaskFilter.Blur.NORMAL));
        this.mPaintMain.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        Paint paint3 = new Paint();
        this.mPaintLazer = paint3;
        paint3.setAntiAlias(true);
        this.mPaintLazer.setStyle(Paint.Style.STROKE);
        this.mPaintLazer.setStrokeJoin(Paint.Join.ROUND);
        this.mPaintLazer.setStrokeCap(Paint.Cap.ROUND);
        this.mPaintLazer.setColor(-1);
        this.mPaintLazer.setMaskFilter(new BlurMaskFilter(1.0f, BlurMaskFilter.Blur.NORMAL));
        this.mPaintLazer.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        setDefaultBrushSize();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        try {
            this.rectF.set(0.0f, 0.0f, ScreenConstable.drawWidth, ScreenConstable.drawHeight);
            if (!this.aBoolean) {
                this.imgRect.set(0.0f, 0.0f, ScreenConstable.drawWidth, ScreenConstable.drawHeight - Math.round(ScreenConstable.drawHeight / 12.7f));
            } else {
                this.imgRect.set(0.0f, 0.0f, ScreenConstable.drawWidth, ScreenConstable.drawHeight);
            }
            int i = this.gapPlaySound + 1;
            this.gapPlaySound = i;
            if (i == 100) {
                this.gapPlaySound = 0;
            }
            canvas.save();
            canvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
            Rect rect = null;
            canvas.drawBitmap(this.canvasBitmap, (Rect) null, this.rectF, this.canvasPaint);
            canvas.drawPath(this.drawPath, this.mPaintMain);
            canvas.drawPath(this.drawPath, this.mPaintLazer);
            Bitmap bitmap = this.kidBitmap;
            if (bitmap != null) {
                Rect rect2 = null;
                canvas.drawBitmap(bitmap, (Rect) null, this.imgRect, this.canvasPaint);
            }
            if (ScreenConstable.SELECTED_TOOL == 2 && this.drawEraser) {
                if (this.gapPlaySound % 30 == 0) {
                    this.drawActivityGlow.mediaPlayer.playSound(R.raw.eraser);
                }
                this.circlePaint.setColor(ViewCompat.MEASURED_STATE_MASK);
                canvas.drawCircle(ScreenConstable.eraseX, ScreenConstable.eraseY, ScreenConstable.eraseR, this.circlePaint);
                this.circlePaint.setColor(-1);
                float f = ScreenConstable.eraseX;
                float f2 = ScreenConstable.eraseY;
                double d = ScreenConstable.eraseR;
                Double.isNaN(d);
                canvas.drawCircle(f, f2, (float) (d * 0.9d), this.circlePaint);
            }
            canvas.restore();
            super.dispatchDraw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        int i5 = ScreenConstable.onSizeCalled + 1;
        ScreenConstable.onSizeCalled = i5;
        if (i5 < 2) {
            if (ScreenConstable.drawWidth == 0 || ScreenConstable.drawHeight == 0) {
                ScreenConstable.drawWidth = 700;
                ScreenConstable.drawHeight = 700;
            }
            if (this.canvasBitmap == null) {
                this.canvasBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                drawcanvas = new Canvas(this.canvasBitmap);
            }
            ScreenConstable.drawWidth = i;
            ScreenConstable.drawHeight = i2;
            ScreenConstable.pixels = new int[ScreenConstable.drawWidth * ScreenConstable.drawHeight];
            ScreenManualTop.getDrawActivity().insertBitmap();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        ScreenConstable.eraseX = x;
        ScreenConstable.eraseY = y;
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.mX = motionEvent.getX();
            this.mY = motionEvent.getY();
            this.mPaintMain.setStrokeWidth(ScreenConstable.brushSize);
            setPathEffect();
            if (ScreenConstable.SELECTED_TOOL == 2) {
                int color = ContextCompat.getColor(this.drawActivityGlow, R.color.black1);
                this.mPaintMain.setColor(color);
                this.mPaintLazer.setColor(color);
                this.mPaintMain.setStrokeWidth(ScreenConstable.ERASER_WIDTH);
            } else if (ScreenConstable.aBoolean1) {
                this.mPaintLazer.setColor(-1);
                this.anInt = new Random().nextInt(600) + 1;
                this.mPaintMain.setColor(this.mColoursList.get(getColorIndex()).intValue());
            } else {
                setPathColor(ScreenConstable.DRAW_COLOR);
                setBrushSize();
            }
            this.drawPath.moveTo(x, y);
            this.drawEraser = true;
        } else if (action == 1) {
            setPointOfSparkImage(x, y);
            startOneShotParticle(ScreenManualTop.iv);
            this.drawActivityGlow.mediaPlayer.playColorRandomSound();
            this.kidBitmapNeedDrawn = true;
            this.drawPath.lineTo(this.mX, this.mY);
            drawcanvas.drawPath(this.drawPath, this.mPaintMain);
            drawcanvas.drawPath(this.drawPath, this.mPaintLazer);
            this.drawPath.reset();
            this.drawEraser = false;
        } else if (action != 2) {
            return false;
        } else {
            if (ScreenConstable.SELECTED_TOOL == 2) {
                int color2 = ContextCompat.getColor(this.drawActivityGlow, R.color.black1);
                this.mPaintMain.setColor(color2);
                this.mPaintLazer.setColor(color2);
                this.mPaintMain.setStrokeWidth(ScreenConstable.ERASER_WIDTH);
            } else {
                setBrushSize();
                invalidate();
            }
            float abs = Math.abs(x - this.mX);
            float abs2 = Math.abs(y - this.mY);
            if (abs >= 0.0f || abs2 >= 0.0f) {
                Path path = this.drawPath;
                float f = this.mX;
                float f2 = this.mY;
                path.quadTo(f, f2, (f + x) / 2.0f, (f2 + y) / 2.0f);
                this.mX = x;
                this.mY = y;
            }
        }
        invalidate();
        return true;
    }


    public void setPathColor(int i) {
        if (!ScreenConstable.aBoolean1) {
            this.mPaintLazer.setColor(-1);
            this.mPaintMain.setColor(i);
            this.mPaintMain.setStrokeWidth(ScreenConstable.brushSize);
            this.mPaintLazer.setStrokeWidth(ScreenConstable.brushSize / 3);
        }
    }

    public void startNew() {
        drawcanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void startOneShotParticle(View view) {
        new ParticleSystem(this.drawActivityGlow, 5, (int) R.drawable.spark_bluedot, 200L).setSpeedRange(0.45f, 0.75f).oneShot(view, 4);
        new ParticleSystem(this.drawActivityGlow, 5, (int) R.drawable.effect_star1, 300L).setSpeedRange(0.35f, 0.7f).oneShot(view, 3);
        new ParticleSystem(this.drawActivityGlow, 5, (int) R.drawable.effect_star2, 400L).setSpeedRange(0.3f, 0.68f).oneShot(view, 2);
        new ParticleSystem(this.drawActivityGlow, 5, (int) R.drawable.effect_star3, 250L).setSpeedRange(0.42f, 0.6f).oneShot(view, 4);
        new ParticleSystem(this.drawActivityGlow, 5, (int) R.drawable.spark_yellowdot, 350L).setSpeedRange(0.37f, 0.65f).oneShot(view, 3);
    }
}
