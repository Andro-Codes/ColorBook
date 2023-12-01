package pavishka.coloring.book.Manager.Chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.core.view.ViewCompat;

import com.plattysoft.leonids.ParticleSystem;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import pavishka.coloring.book.Activities.Screens.ScreenConstable;
import pavishka.coloring.book.Activities.Screens.ScreenOwnAuidioPlayer;
import pavishka.coloring.book.Activities.Screens.ScreenDsaActivity;
import pavishka.coloring.book.R;
import pavishka.coloring.book.Activities.OtherScreens.ScreenTempDataStore;


public class ChartManual extends View implements ScaleGestureDetector.OnScaleGestureListener, GestureDetector.OnGestureListener {
    public static Bitmap canvasBitmap = null;
    private static int stickerStartingHeight = 300;
    private static int stickerStartingWidth = 300;
    private static int trashIconNormalSize = 44;
    public Bitmap kidBitmap;
    public boolean kidBitmapDrawn;
    public boolean kidBitmapNeedDrawn;
    public int gapPlaySound = 0;
    public RectF imgRect = new RectF();
    public boolean isDraw = true;
    public boolean mIsStickerResizing = false;
    ScreenChartDrawActivity graphDrawActivity;
    float aFloat;
    float aFloat1;
    float aFloat2;
    float aFloat3;
    int anInt;
    int anInt1;
    RectF rectF;
    Bitmap bitmap;
    ScreenOwnAuidioPlayer myMediaPlayer;
    boolean aBoolean;
    RectF rectF1 = new RectF();
    int anInt2 = -1;
    float aFloat4 = 8.0f;
    float aFloat5 = 500.0f;
    private Paint canvasPaint;
    private float cellWidth;
    private Paint circlePaint;
    private Canvas drawCanvas;
    private Paint drawPaint;
    private Path drawPath;
    private GestureDetector gestureDetector;
    private float mX;
    private float mY;
    private float numColumns;
    private float numRows;
    private ScaleGestureDetector scaleGestureDetector;
    private Paint gridPaint = new Paint();
    private ArrayList<Sticker> mStickers = new ArrayList<>();
    private boolean showTrash = false;
    private Bitmap textureBitmap = null;
    private float width = 8.0f;
    private ChartManual drawingPicture = this;
    private boolean drawEraser = false;

    public ChartManual(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.graphDrawActivity = (ScreenChartDrawActivity) context;
        canvasBitmap = null;
        this.gridPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.gridPaint.setColor(getResources().getColor(R.color.black1));
        if (ScreenTempDataStore.getBuyChoise(getContext()) == 0) {
            this.aBoolean = false;
        } else {
            this.aBoolean = true;
        }
        setupDrawing();
        this.aFloat = 1.0f;
        this.aFloat1 = 1.0f;
        this.aFloat2 = 50.0f;
        this.aFloat3 = 50.0f;
        stickerStartingHeight = Math.round(ScreenConstable.drawWidth / 4.0f);
        stickerStartingWidth = Math.round(ScreenConstable.drawWidth / 4.0f);
        trashIconNormalSize = Math.round(ScreenConstable.drawWidth / 5.3f);
        this.rectF = new RectF();
        this.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stick_delete);
        this.myMediaPlayer = new ScreenOwnAuidioPlayer(context);
        this.scaleGestureDetector = new ScaleGestureDetector(context, this);
        this.gestureDetector = new GestureDetector(context, this);
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    private void calculateDimension() {
        this.numColumns = 10.0f;
        this.cellWidth = getWidth() / this.numColumns;
        this.numRows = getHeight() / this.cellWidth;
        invalidate();
    }

    private void moveSticker(int i, int i2) {
        try {
            int i3 = i - this.anInt;
            int i4 = i2 - this.anInt1;
            this.anInt = i;
            this.anInt1 = i2;
            this.mStickers.get(this.anInt2).c += i3;
            this.mStickers.get(this.anInt2).d += i4;
            this.mStickers.get(this.anInt2).adjustRect();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void resetSticker(int i, int i2) {
        if (this.rectF.contains(i, i2)) {
            removeSticker();
        }
        this.anInt2 = -1;
        this.mIsStickerResizing = false;
    }

    private void setDefaultBrushSize() {
        ScreenConstable.brushSize = 20;
        this.drawPaint.setStrokeWidth(20.0f);
    }

    private void setPointOfSparkImage(float f, float f2) {
        ScreenChartDrawActivity.iv.setX(f);
        ScreenChartDrawActivity.iv.setY(f2);
    }

    private void setupDrawing() {
        this.drawPath = new Path();
        Paint paint = new Paint();
        this.drawPaint = paint;
        paint.setAntiAlias(true);
        this.drawPaint.setStyle(Paint.Style.STROKE);
        this.drawPaint.setStrokeJoin(Paint.Join.ROUND);
        this.drawPaint.setStrokeCap(Paint.Cap.ROUND);
        this.drawPaint.setPathEffect(new CornerPathEffect(10.0f));
        this.canvasPaint = new Paint(4);
        Paint paint2 = new Paint(1);
        this.circlePaint = paint2;
        paint2.setColor(ViewCompat.MEASURED_STATE_MASK);
        setDefaultBrushSize();
    }

    public void addNewSticker(int i) {
        if (i != 0) {
            this.isDraw = false;
            Log.d("STICKER_TEST", "addNewSticker: adding new sticker to canvas.");
            this.mStickers.add(new Sticker(resize_Bitmap(BitmapFactory.decodeResource(getResources(), i), stickerStartingHeight, stickerStartingWidth), i, 0, 200));
            invalidate();
        }
    }

    public Bitmap createFlippedBitmap(Bitmap bitmap, boolean z, boolean z2) {
        Matrix matrix = new Matrix();
        float f = 1.0f;
        float f2 = z ? -1.0f : 1.0f;
        if (z2) {
            f = -1.0f;
        }
        matrix.postScale(f2, f, bitmap.getWidth() / 2.0f, bitmap.getHeight() / 2.0f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (this.numRows == 0.0f || this.numColumns == 0.0f) {
            calculateDimension();
        }
        if (this.drawCanvas == null) {
            onSizeChanged(ScreenConstable.drawWidth, ScreenConstable.drawHeight, ScreenConstable.drawWidth, ScreenConstable.drawHeight);
        }
        if (!this.aBoolean) {
            int i = ScreenConstable.drawHeight;
            this.imgRect.set(0.0f, 0.0f, ScreenConstable.drawWidth, i - Math.round(i / 12.7f));
        } else {
            this.imgRect.set(0.0f, 0.0f, ScreenConstable.drawWidth, ScreenConstable.drawHeight);
        }
        int i2 = ScreenConstable.drawHeight;
        int i3 = ScreenConstable.drawHeight;
        RectF rectF = this.rectF;
        int i4 = ScreenConstable.drawWidth;
        int i5 = trashIconNormalSize;
        float f = i3;
        rectF.set(i4 - i5, (i2 - i5) - (i2 / 50.0f), ScreenConstable.drawWidth, f - (f / 50.0f));
        this.rectF1.set(0.0f, 0.0f, ScreenConstable.drawWidth, ScreenConstable.drawHeight);
        try {
            int i6 = this.gapPlaySound + 1;
            this.gapPlaySound = i6;
            int i7 = 0;
            if (i6 == 100) {
                this.gapPlaySound = 0;
            }
            canvas.save();
            Rect rect = null;
            canvas.drawBitmap(canvasBitmap, (Rect) null, this.rectF1, this.canvasPaint);
            if (!(this.numColumns == 0.0f || this.numRows == 0.0f)) {
                int i8 = 0;
                while (true) {
                    float f2 = i8;
                    if (f2 >= this.numColumns) {
                        break;
                    }
                    if (i8 == 0) {
                        this.gridPaint.setStrokeWidth(7.0f);
                    } else {
                        this.gridPaint.setStrokeWidth(1.0f);
                    }
                    float f3 = this.cellWidth;
                    canvas.drawLine(f2 * f3, 0.0f, f2 * f3, getHeight(), this.gridPaint);
                    i8++;
                }
                while (true) {
                    float f4 = i7;
                    if (f4 >= this.numRows) {
                        break;
                    }
                    if (i7 == 0) {
                        this.gridPaint.setStrokeWidth(7.0f);
                    } else {
                        this.gridPaint.setStrokeWidth(1.0f);
                    }
                    canvas.drawLine(0.0f, f4 * this.cellWidth, getWidth(), f4 * this.cellWidth, this.gridPaint);
                    i7++;
                }
            }
            this.drawCanvas.drawPath(this.drawPath, this.drawPaint);
            if (ScreenConstable.SELECTED_TOOL == 2 && this.drawEraser) {
                if (this.gapPlaySound % 30 == 0) {
                    this.graphDrawActivity.mediaPlayer.playSound(R.raw.eraser);
                }
                this.drawPaint.setShader(null);
                this.circlePaint.setColor(ViewCompat.MEASURED_STATE_MASK);
                canvas.drawCircle(ScreenConstable.eraseX, ScreenConstable.eraseY, ScreenConstable.eraseR, this.circlePaint);
                this.circlePaint.setColor(-1);
                float f5 = ScreenConstable.eraseX;
                float f6 = ScreenConstable.eraseY;
                double d = ScreenConstable.eraseR;
                Double.isNaN(d);
                canvas.drawCircle(f5, f6, (float) (d * 0.9d), this.circlePaint);
            }
            Bitmap bitmap = this.kidBitmap;
            if (bitmap != null) {
                Rect rect2 = null;
                canvas.drawBitmap(bitmap, (Rect) null, this.imgRect, this.canvasPaint);
            }
            Bitmap bitmap2 = this.textureBitmap;
            if (bitmap2 != null) {
                Rect rect3 = null;
                canvas.drawBitmap(bitmap2, (Rect) null, this.rectF1, this.canvasPaint);
            }
            if (this.mStickers.size() > 0) {
                Iterator<Sticker> it = this.mStickers.iterator();
                while (it.hasNext()) {
                    Sticker next = it.next();
                    canvas.drawBitmap(next.b, next.c, next.d, next.a);
                }
            }
            if (this.showTrash) {
                Rect rect4 = null;
                Paint paint = null;
                canvas.drawBitmap(this.bitmap, (Rect) null, this.rectF, (Paint) null);
            }
            canvas.restore();
            super.dispatchDraw(canvas);
        } catch (Exception e) {
            Log.d("DRAW_ERROR", "dispatchDraw: " + e);
        }
    }

    public void flipPicture() {
        if (this.kidBitmap != null) {
            this.drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
            this.mStickers.clear();
            this.kidBitmap = createFlippedBitmap(this.kidBitmap, true, false);
            setKidsImage();
            invalidate();
        }
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.e("sddd", "");
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        if (scaleGestureDetector.getScaleFactor() > 1.0f) {
            this.aFloat += 0.03f;
            this.aFloat1 += 0.03f;
        } else {
            this.aFloat -= 0.03f;
            this.aFloat1 -= 0.03f;
        }
        if (this.aFloat > 2.0f) {
            this.aFloat = 2.0f;
        }
        if (this.aFloat1 > 2.0f) {
            this.aFloat1 = 2.0f;
        }
        if (this.aFloat < 1.0f) {
            this.aFloat = 1.0f;
        }
        if (this.aFloat1 < 1.0f) {
            this.aFloat1 = 1.0f;
        }
        Log.d("SCALE_TEST", "scaleX: " + this.aFloat + " scaleY: " + this.aFloat1);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        this.isDraw = false;
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        Log.e("sddd", "");
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        float f3 = this.aFloat2 + f;
        this.aFloat2 = f3;
        float f4 = this.aFloat3 + f2;
        this.aFloat3 = f4;
        if (f3 < 0.0f) {
            this.aFloat2 = 0.0f;
        }
        if (f4 < 0.0f) {
            this.aFloat3 = 0.0f;
        }
        Log.d("SCROLL_TEST", "offsetX: " + this.aFloat2 + " offsetY: " + this.aFloat3);
        this.graphDrawActivity.setPivot(this.aFloat2, this.aFloat3);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.e("sddd", "");
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
            if (canvasBitmap == null) {
                canvasBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                this.drawCanvas = new Canvas(canvasBitmap);
            }
            ScreenConstable.drawWidth = i;
            ScreenConstable.drawHeight = i2;
            ScreenConstable.pixels = new int[ScreenConstable.drawWidth * ScreenConstable.drawHeight];
            PrintStream printStream = System.err;
            printStream.println("  MyConstant.pixels:" + ScreenConstable.pixels.length);
            ScreenChartDrawActivity.getGraphDrawActivity().insertBitmap();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.scaleGestureDetector.onTouchEvent(motionEvent);
        this.gestureDetector.onTouchEvent(motionEvent);
        int i = 0;
        if (motionEvent.getPointerCount() != 1) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        ScreenConstable.eraseX = x;
        ScreenConstable.eraseY = y;
        int action = motionEvent.getAction() & 255;
        int i2 = -1;
        if (action == 0) {
            this.mX = motionEvent.getX();
            this.mY = motionEvent.getY();
            if (!this.isDraw) {
                while (true) {
                    if (i >= this.mStickers.size()) {
                        break;
                    }
                    int i3 = (int) x;
                    int i4 = (int) y;
                    if (this.mStickers.get(i).e.contains(i3, i4)) {
                        Log.d("STICKER_TEST", "touchEvent: touched a STICKER.");
                        this.anInt2 = i;
                        this.anInt = i3;
                        this.anInt1 = i4;
                        break;
                    }
                    i++;
                }
            }
            if (this.isDraw && ScreenConstable.SELECTED_TOOL != 0) {
                this.drawPaint.setStrokeWidth(ScreenConstable.brushSize);
                if (ScreenConstable.SELECTED_TOOL != 2 && !ScreenChartDrawActivity.ispatternClicked) {
                    setPathColor(ScreenConstable.DRAW_COLOR);
                } else if (ScreenConstable.SELECTED_TOOL == 2) {
                    this.drawPaint.setShader(null);
                    this.drawPaint.setColor(-1);
                    this.drawPaint.setStrokeWidth(ScreenConstable.ERASER_WIDTH);
                } else {
                    this.drawPaint.setColor(-1);
                }
                this.drawPath.moveTo(x, y);
                this.drawEraser = true;
            }
            this.graphDrawActivity.closePickerIfOpen();
        } else if (action == 1) {
            if (this.mStickers.size() > 0 && !this.isDraw) {
                resetSticker((int) x, (int) y);
            }
            this.showTrash = false;
            try {
                setPointOfSparkImage(this.mX, this.mY);
                this.graphDrawActivity.mediaPlayer.playColorRandomSound();
                startOneShotParticle(ScreenChartDrawActivity.iv);
                if (this.isDraw) {
                    if (ScreenConstable.SELECTED_TOOL == 0) {
                        if (this.kidBitmap != null && (!this.kidBitmapDrawn || this.kidBitmapNeedDrawn)) {
                            PrintStream printStream = System.err;
                            printStream.println("ooo::" + this.drawCanvas + "--" + this.kidBitmap + "---" + this.canvasPaint);
                            Rect rect = null;
                            this.drawCanvas.drawBitmap(this.kidBitmap, (Rect) null, this.imgRect, this.canvasPaint);
                            this.kidBitmapDrawn = true;
                            this.kidBitmapNeedDrawn = false;
                        }
                        canvasBitmap.getPixels(ScreenConstable.pixels, 0, ScreenConstable.drawWidth, 0, 0, ScreenConstable.drawWidth, ScreenConstable.drawHeight);
                        int pixel = canvasBitmap.getPixel((int) this.mX, (int) this.mY);
                        int red = Color.red(pixel);
                        int green = Color.green(pixel);
                        int blue = Color.blue(pixel);
                        if (red < 255 && red == green && red == blue) {
                            if (red <= 0) {
                                return false;
                            }
                            invalidate();
                            ScreenDsaActivity queueLinearFloodFiller = new ScreenDsaActivity(i2, ScreenConstable.DRAW_COLOR);
                            queueLinearFloodFiller.setTolerance(60);
                            queueLinearFloodFiller.floodFill((int) this.mX, (int) this.mY);
                            canvasBitmap.setPixels(ScreenConstable.pixels, 0, ScreenConstable.drawWidth, 0, 0, ScreenConstable.drawWidth, ScreenConstable.drawHeight);
                        }
                        i2 = pixel;
                        invalidate();
                        ScreenDsaActivity queueLinearFloodFiller2 = new ScreenDsaActivity(i2, ScreenConstable.DRAW_COLOR);
                        queueLinearFloodFiller2.setTolerance(60);
                        queueLinearFloodFiller2.floodFill((int) this.mX, (int) this.mY);
                        canvasBitmap.setPixels(ScreenConstable.pixels, 0, ScreenConstable.drawWidth, 0, 0, ScreenConstable.drawWidth, ScreenConstable.drawHeight);
                    } else {
                        this.kidBitmapNeedDrawn = true;
                        this.drawPath.lineTo(this.mX, this.mY);
                        this.drawCanvas.drawPath(this.drawPath, this.drawPaint);
                        this.drawPath.reset();
                        this.drawEraser = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.mStickers.isEmpty()) {
                this.isDraw = true;
            }
        } else if (action != 2) {
            return false;
        } else {
            if (!this.isDraw) {
                if (this.anInt2 != -1) {
                    int i5 = (int) x;
                    int i6 = (int) y;
                    moveSticker(i5, i6);
                    this.showTrash = true;
                    if (this.rectF.contains(i5, i6)) {
                        this.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stick_delete_sel);
                    } else {
                        this.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stick_delete);
                    }
                } else {
                    this.showTrash = false;
                }
                if (this.mIsStickerResizing) {
                    moveSticker((int) x, (int) y);
                }
            }
            if (this.isDraw && ScreenConstable.SELECTED_TOOL != 0) {
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
        }
        invalidate();
        return true;
    }

    public void removeSticker() {
        int i = this.anInt2;
        if (i != -1) {
            this.mStickers.remove(i);
            invalidate();
            this.myMediaPlayer.playSound(R.raw.random_whiparound);
        }
    }

    public Bitmap resize_Bitmap(Bitmap bitmap, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(stickerStartingWidth, stickerStartingHeight, Bitmap.Config.ARGB_8888);
        float f = i2;
        float width = f / bitmap.getWidth();
        float f2 = i;
        float height = f2 / bitmap.getHeight();
        float f3 = f / 2.0f;
        float f4 = f2 / 2.0f;
        Matrix matrix = new Matrix();
        matrix.setScale(width, height, f3, f4);
        Canvas canvas = new Canvas(createBitmap);
        canvas.setMatrix(matrix);
        canvas.drawBitmap(bitmap, f3 - (bitmap.getWidth() / 2.0f), f4 - (bitmap.getHeight() / 2.0f), new Paint(2));
        return createBitmap;
    }

    public void setKidsImage() {
        invalidate();
        Bitmap bitmap = canvasBitmap;
        if (bitmap != null) {
            bitmap.eraseColor(-1);
        } else {
            onSizeChanged(ScreenConstable.drawWidth, ScreenConstable.drawHeight, ScreenConstable.drawWidth, ScreenConstable.drawHeight);
        }
        this.kidBitmapDrawn = false;
    }

    public void setPathColor(int i) {
        System.err.println("color cliked inside color");
        this.drawPaint.setShader(null);
        this.drawPaint.setColor(i);
    }

    public void setPattern(String str) {
        System.err.println("color cliked inside pattern");
        invalidate();
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(str, "drawable", this.graphDrawActivity.getPackageName()));
        Shader.TileMode tileMode = Shader.TileMode.REPEAT;
        BitmapShader bitmapShader = new BitmapShader(decodeResource, tileMode, tileMode);
        this.drawPaint.setColor(-1);
        this.drawPaint.setShader(bitmapShader);
    }

    public void setTexture(String str) {
        try {
            if (!str.equals("")) {
                this.textureBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(str, "drawable", this.graphDrawActivity.getPackageName())), getWidth(), getHeight(), false);
            } else {
                this.textureBitmap = null;
            }
            this.isDraw = false;
            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startNew() {
        try {
            this.drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
            this.mStickers.clear();
            setKidsImage();
            this.textureBitmap = null;
            ScreenConstable.TYPE_FILL = 0;
            ScreenChartDrawActivity graphDrawActivity = this.graphDrawActivity;
            graphDrawActivity.milind(graphDrawActivity.H(0));
            this.graphDrawActivity.setDefaultColor();
            ScreenChartDrawActivity.ispatternClicked = false;
            invalidate();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void startOneShotParticle(View view) {
        new ParticleSystem(this.graphDrawActivity, 5, (int) R.drawable.spark_bluedot, 200L).setSpeedRange(0.45f, 0.75f).oneShot(view, 4);
        new ParticleSystem(this.graphDrawActivity, 5, (int) R.drawable.effect_star1, 300L).setSpeedRange(0.35f, 0.7f).oneShot(view, 3);
        new ParticleSystem(this.graphDrawActivity, 5, (int) R.drawable.effect_star2, 400L).setSpeedRange(0.3f, 0.68f).oneShot(view, 2);
        new ParticleSystem(this.graphDrawActivity, 5, (int) R.drawable.effect_star3, 250L).setSpeedRange(0.42f, 0.6f).oneShot(view, 4);
        new ParticleSystem(this.graphDrawActivity, 5, (int) R.drawable.spark_yellowdot, 350L).setSpeedRange(0.37f, 0.65f).oneShot(view, 3);
    }


    public static class Sticker {
        Paint a;
        Bitmap b;
        int c;
        int d;
        Rect e;

        Sticker(Bitmap bitmap, int i, int i2, int i3) {
            Paint paint = new Paint();
            this.a = paint;
            paint.setAntiAlias(true);
            this.c = i2;
            this.d = i3;
            this.b = bitmap;
            this.e = new Rect(i2, i3, ChartManual.stickerStartingWidth + i2, ChartManual.stickerStartingHeight + i3);
        }

        public void adjustRect() {
            Rect rect = this.e;
            int i = this.c;
            rect.left = i;
            rect.top = this.d;
            rect.right = i + this.b.getWidth();
            this.e.bottom = this.d + this.b.getHeight();
        }
    }


}
