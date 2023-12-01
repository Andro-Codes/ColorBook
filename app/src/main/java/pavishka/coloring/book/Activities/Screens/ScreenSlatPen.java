package pavishka.coloring.book.Activities.Screens;

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
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.ViewCompat;

import com.plattysoft.leonids.ParticleSystem;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import pavishka.coloring.book.Activities.OtherScreens.ScreenTempDataStore;
import pavishka.coloring.book.R;


public class ScreenSlatPen extends View {
    private static int trashIconNormalSize = 44;
    public Bitmap canvasBitmap;
    public boolean isFirstTime;
    public Bitmap kidBitmap;
    public boolean kidBitmapDrawn;
    public boolean kidBitmapNeedDrawn;
    public int gapPlaySound = 0;
    public RectF imgRect = new RectF();
    public boolean isDraw = true;
    public boolean mIsStickerResizing = false;
    ScreenManualColorDrawingActivity drawActivity;
    int anInt;
    int anInt1;
    Canvas drawCanvas;
    Paint drawPaint;
    Path drawPath;
    RectF rectF;
    Bitmap bitmap;
    boolean aBoolean;
    int anInt2 = -1;
    RectF rectF1 = new RectF();
    boolean drawEraser = false;
    boolean aBoolean1 = false;
    private Paint canvasPaint;
    private Paint circlePaint;
    private Paint linePaint;
    private float mX;
    private float mY;
    private boolean startDraw;
    private int stickerStartingHeight;
    private int stickerStartingWidth;
    private ArrayList<Sticker> mStickers = new ArrayList<>();
    private boolean showTrash = false;
    private Bitmap textureBitmap = null;
    private float width = 8.0f;

    public ScreenSlatPen(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.stickerStartingHeight = 300;
        this.stickerStartingWidth = 300;
        this.isFirstTime = true;
        this.startDraw = false;
        this.drawActivity = (ScreenManualColorDrawingActivity) context;
        this.startDraw = false;
        this.isFirstTime = true;
        if (ScreenTempDataStore.getBuyChoise(getContext()) == 0) {
            this.aBoolean = false;
        } else {
            this.aBoolean = true;
        }
        setupDrawing();
        this.stickerStartingHeight = Math.round(ScreenConstable.drawWidth / 4.0f);
        this.stickerStartingWidth = Math.round(ScreenConstable.drawWidth / 4.0f);
        trashIconNormalSize = Math.round(ScreenConstable.drawWidth / 5.3f);
        this.rectF = new RectF();
        this.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stick_delete);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ScreenSlatPen.this.startDraw = true;
            }
        }, 500L);
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
        ScreenManualColorDrawingActivity.iv.setX(f);
        ScreenManualColorDrawingActivity.iv.setY(f2);
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
        Paint paint3 = new Paint();
        this.linePaint = paint3;
        paint3.setAntiAlias(true);
        this.linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.linePaint.setStrokeJoin(Paint.Join.ROUND);
        this.linePaint.setStrokeCap(Paint.Cap.ROUND);
        this.linePaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.linePaint.setStrokeWidth(5.0f);
        setDefaultBrushSize();
    }

    public void addNewSticker(int i) {
        if (i != 0) {
            this.isDraw = false;
            Log.d("STICKER_TEST", "addNewSticker: adding new sticker to canvas.");
            this.mStickers.add(new Sticker(resizeBitmap(BitmapFactory.decodeResource(getResources(), i), this.stickerStartingHeight, this.stickerStartingWidth), i, 0, 200));
            invalidate();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawColor(-1);
        if (this.drawCanvas == null) {
            onSizeChanged(getWidth(), getHeight(), getWidth(), getWidth());
        }
        try {
            this.rectF.set(ScreenConstable.drawWidth - trashIconNormalSize, (ScreenConstable.drawHeight - trashIconNormalSize) - (ScreenConstable.drawHeight / 50.0f), ScreenConstable.drawWidth, ScreenConstable.drawHeight - (ScreenConstable.drawHeight / 50.0f));
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
            this.drawCanvas.drawPath(this.drawPath, this.drawPaint);
            this.rectF1.set(0.0f, 0.0f, ScreenConstable.drawWidth, ScreenConstable.drawHeight);
            Rect rect = null;
            canvas.drawBitmap(this.canvasBitmap, (Rect) null, this.rectF1, this.canvasPaint);
            if (!this.aBoolean) {
                canvas.drawLine(0.0f, ScreenConstable.drawHeight - Math.round(ScreenConstable.drawHeight / 12.7f), ScreenConstable.drawWidth, ScreenConstable.drawHeight - Math.round(ScreenConstable.drawHeight / 12.7f), this.linePaint);
                this.drawCanvas.drawLine(0.0f, ScreenConstable.drawHeight - Math.round(ScreenConstable.drawHeight / 12.7f), ScreenConstable.drawWidth, ScreenConstable.drawHeight - Math.round(ScreenConstable.drawHeight / 12.7f), this.linePaint);
            }
            if (ScreenConstable.SELECTED_TOOL == 2 && this.drawEraser) {
                if (this.gapPlaySound % 30 == 0) {
                    this.drawActivity.mediaPlayer.playSound(R.raw.eraser);
                }
                this.drawPaint.setShader(null);
                this.circlePaint.setColor(ViewCompat.MEASURED_STATE_MASK);
                canvas.drawCircle(ScreenConstable.eraseX, ScreenConstable.eraseY, ScreenConstable.eraseR, this.circlePaint);
                this.circlePaint.setColor(-1);
                float f = ScreenConstable.eraseX;
                float f2 = ScreenConstable.eraseY;
                double d = ScreenConstable.eraseR;
                Double.isNaN(d);
                canvas.drawCircle(f, f2, (float) (d * 0.9d), this.circlePaint);
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
            if (this.isFirstTime) {
                if (this.drawCanvas == null) {
                    onSizeChanged(getWidth(), getHeight(), getWidth(), getWidth());
                }
                if (this.kidBitmap != null && (!this.kidBitmapDrawn || this.kidBitmapNeedDrawn)) {
                    System.err.println("ooo::" + this.drawCanvas + "--" + this.kidBitmap + "---" + this.canvasPaint);
                    Rect rect5 = null;
                    this.drawCanvas.drawBitmap(this.kidBitmap, (Rect) null, this.imgRect, this.canvasPaint);
                    this.kidBitmapDrawn = true;
                    this.kidBitmapNeedDrawn = false;
                }
                Log.d("dsds", "called");
                this.isFirstTime = false;
                this.canvasBitmap.getPixels(ScreenConstable.expPixels, 0, ScreenConstable.drawWidth, 0, 0, ScreenConstable.drawWidth, ScreenConstable.drawHeight);
            }
            canvas.restore();
            super.dispatchDraw(canvas);
        } catch (Exception e) {
            Log.d("STICKER_TEST", "dispatchDraw: " + e);
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
            ScreenConstable.drawWidth = i;
            ScreenConstable.drawHeight = i2;
            if (this.canvasBitmap == null || this.drawCanvas == null) {
                this.canvasBitmap = Bitmap.createBitmap(ScreenConstable.drawWidth, ScreenConstable.drawHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(this.canvasBitmap);
                this.drawCanvas = canvas;
                canvas.drawColor(-1);
            }
            ScreenConstable.pixels = new int[ScreenConstable.drawWidth * ScreenConstable.drawHeight];
            ScreenConstable.expPixels = new int[ScreenConstable.drawWidth * ScreenConstable.drawHeight];
            PrintStream printStream = System.err;
            printStream.println("   MyConstant.pixels:" + ScreenConstable.pixels.length);
            ScreenManualColorDrawingActivity.getDrawActivity().insertBitmap();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.aBoolean1 && motionEvent.getPointerCount() == 1) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            ScreenConstable.eraseX = x;
            ScreenConstable.eraseY = y;
            int action = motionEvent.getAction() & 255;
            int i = -1;
            int i2 = 0;
            if (action == 0) {
                this.mX = motionEvent.getX();
                this.mY = motionEvent.getY();
                if (!this.isDraw) {
                    while (true) {
                        if (i2 >= this.mStickers.size()) {
                            break;
                        }
                        int i3 = (int) x;
                        int i4 = (int) y;
                        if (this.mStickers.get(i2).e.contains(i3, i4)) {
                            Log.d("STICKER_TEST", "touchEvent: touched a STICKER.");
                            this.anInt2 = i2;
                            this.anInt = i3;
                            this.anInt1 = i4;
                            break;
                        }
                        i2++;
                    }
                }
                if (this.isDraw && ScreenConstable.SELECTED_TOOL != 0) {
                    this.drawPaint.setStrokeWidth(ScreenConstable.brushSize);
                    if (ScreenConstable.SELECTED_TOOL != 2 && !ScreenManualColorDrawingActivity.ispatternClicked) {
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
                this.drawActivity.closePickerIfOpen();
            } else if (action == 1) {
                if (this.mStickers.size() > 0 && !this.isDraw) {
                    resetSticker((int) x, (int) y);
                }
                this.showTrash = false;
                try {
                    setPointOfSparkImage(this.mX, this.mY);
                    this.drawActivity.mediaPlayer.playColorRandomSound();
                    startOneShotParticle(ScreenManualColorDrawingActivity.iv);
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
                            this.canvasBitmap.getPixels(ScreenConstable.pixels, 0, ScreenConstable.drawWidth, 0, 0, ScreenConstable.drawWidth, ScreenConstable.drawHeight);
                            int pixel = this.canvasBitmap.getPixel((int) this.mX, (int) this.mY);
                            int red = Color.red(pixel);
                            int green = Color.green(pixel);
                            int blue = Color.blue(pixel);
                            if (red < 255 && red == green && red == blue) {
                                if (red <= 0) {
                                    return false;
                                }
                                invalidate();
                                ScreenDsaActivity queueLinearFloodFiller = new ScreenDsaActivity(i, ScreenConstable.DRAW_COLOR);
                                queueLinearFloodFiller.setTolerance(60);
                                queueLinearFloodFiller.floodFill((int) this.mX, (int) this.mY);
                                this.canvasBitmap.setPixels(ScreenConstable.pixels, 0, ScreenConstable.drawWidth, 0, 0, ScreenConstable.drawWidth, ScreenConstable.drawHeight);
                            }
                            i = pixel;
                            invalidate();
                            ScreenDsaActivity queueLinearFloodFiller2 = new ScreenDsaActivity(i, ScreenConstable.DRAW_COLOR);
                            queueLinearFloodFiller2.setTolerance(60);
                            queueLinearFloodFiller2.floodFill((int) this.mX, (int) this.mY);
                            this.canvasBitmap.setPixels(ScreenConstable.pixels, 0, ScreenConstable.drawWidth, 0, 0, ScreenConstable.drawWidth, ScreenConstable.drawHeight);
                        } else {
                            this.kidBitmapNeedDrawn = true;
                            this.drawPath.lineTo(this.mX, this.mY);
                            this.drawCanvas.drawPath(this.drawPath, this.drawPaint);
                            this.drawPath.reset();
                            this.drawEraser = false;
                        }
                    }
                    if (this.mStickers.isEmpty() && this.drawActivity.useTexture) {
                        this.drawActivity.useTexture = false;
                        this.isDraw = true;
                        ScreenConstable.TYPE_FILL = 0;
                        ScreenManualColorDrawingActivity drawActivity = this.drawActivity;
                        drawActivity.setBottomColorLayout(drawActivity.D(0));
                        this.drawActivity.setDefaultColor();
                        ScreenManualColorDrawingActivity.ispatternClicked = false;
                    }
                } catch (Exception e) {
                    Log.d("FILL_TEST", "onTouchEvent: " + e);
                    e.printStackTrace();
                }
                if (this.mStickers.isEmpty()) {
                    ScreenManualColorDrawingActivity drawActivity2 = this.drawActivity;
                    if (drawActivity2.useTexture) {
                        drawActivity2.useTexture = false;
                        this.isDraw = true;
                        ScreenConstable.TYPE_FILL = 0;
                        this.drawActivity.setBottomColorLayout(drawActivity2.D(0));
                        this.drawActivity.setDefaultColor();
                        ScreenManualColorDrawingActivity.ispatternClicked = false;
                    }
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
        }
        return this.startDraw;
    }

    public void removeSticker() {
        int i = this.anInt2;
        if (i != -1) {
            this.mStickers.remove(i);
            invalidate();
        }
    }

    public Bitmap resizeBitmap(Bitmap bitmap, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(this.stickerStartingWidth, this.stickerStartingHeight, Bitmap.Config.ARGB_8888);
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
        Bitmap bitmap = this.canvasBitmap;
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
        try {
            System.err.println("color cliked inside pattern");
            invalidate();
            Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(str, "drawable", this.drawActivity.getPackageName()));
            BitmapShader bitmapShader = new BitmapShader(decodeResource, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            this.drawPaint.setColor(-1);
            this.drawPaint.setShader(bitmapShader);
            int width = decodeResource.getWidth();
            int height = decodeResource.getHeight();
            ScreenConstable.ppWidth = width;
            ScreenConstable.ppHeight = height;
            if (ScreenConstable.selectedPatternBitmap != null) {
                ScreenConstable.selectedPatternBitmap.recycle();
                ScreenConstable.selectedPatternBitmap = null;
            }
            ScreenConstable.selectedPatternBitmap = Bitmap.createScaledBitmap(decodeResource, ScreenConstable.ppWidth, ScreenConstable.ppHeight, false);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void setTexture(String str) {
        try {
            if (!str.equals("")) {
                this.textureBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(str, "drawable", this.drawActivity.getPackageName())), getWidth(), getHeight(), false);
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
            this.isDraw = true;
            this.isFirstTime = true;
            this.drawPath = new Path();
            this.drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
            this.mStickers.clear();
            this.textureBitmap = null;
            ScreenConstable.TYPE_FILL = 0;
            ScreenManualColorDrawingActivity drawActivity = this.drawActivity;
            drawActivity.setBottomColorLayout(drawActivity.D(0));
            this.drawActivity.setDefaultColor();
            ScreenManualColorDrawingActivity.ispatternClicked = false;
            setKidsImage();
            invalidate();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void startOneShotParticle(View view) {
        new ParticleSystem(this.drawActivity, 5, (int) R.drawable.spark_bluedot, 200L).setSpeedRange(0.45f, 0.75f).oneShot(view, 4);
        new ParticleSystem(this.drawActivity, 5, (int) R.drawable.effect_star1, 300L).setSpeedRange(0.35f, 0.7f).oneShot(view, 3);
        new ParticleSystem(this.drawActivity, 5, (int) R.drawable.effect_star2, 400L).setSpeedRange(0.3f, 0.68f).oneShot(view, 2);
        new ParticleSystem(this.drawActivity, 5, (int) R.drawable.effect_star3, 250L).setSpeedRange(0.42f, 0.6f).oneShot(view, 4);
        new ParticleSystem(this.drawActivity, 5, (int) R.drawable.spark_yellowdot, 350L).setSpeedRange(0.37f, 0.65f).oneShot(view, 3);
    }


    public class Sticker {
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
            this.e = new Rect(i2, i3, ScreenSlatPen.this.stickerStartingWidth + i2, ScreenSlatPen.this.stickerStartingHeight + i3);
        }

        public void adjustRect() {
            if (this.c < 0) {
                this.c = 0;
            }
            if (this.d < 0) {
                this.d = 0;
            }
            int width = this.c + this.b.getWidth();
            float f = ScreenSlatPen.this.imgRect.right;
            if (width > f) {
                width = (int) f;
                this.c = width - this.b.getWidth();
            }
            int height = this.d + this.b.getHeight();
            float f2 = ScreenSlatPen.this.imgRect.bottom;
            if (height > f2) {
                height = (int) f2;
                this.d = height - this.b.getHeight();
            }
            Rect rect = this.e;
            rect.left = this.c;
            rect.top = this.d;
            rect.right = width;
            rect.bottom = height;
        }
    }
}
