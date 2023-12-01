package pavishka.babyphone.balloonanimation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.util.Random;


public class Balloon {
    public static int MAXX_SPEED = 10;
    public static int MAXY_SPEED = 10;
    Bitmap ballonBitmap;
    Bitmap ballonScaledBitmap;
    Context context;
    Drawable face;
    Bitmap faceBitmap;
    Bitmap faceScaledBitmap;
    Drawable gBallon;
    Rect gBallonRect;
    private int height;
    LayerDrawable mergeDrawable;
    Bitmap mergedBitmap;
    int[] objects;
    Paint paint;
    Bitmap rotatedBitamp;
    int speed;
    double topHeight;
    int width;
    float aFloat;
    double aDouble;
    double aDouble1;
    double aDouble2;
    int anInt = 0;
    int anInt1 = 0;
    int anInt2 = 0;
    int anInt3 = 0;
    int anInt4 = 0;
    int anInt5 = 0;
    int anInt6 = 0;
    int anInt7 = 0;
    boolean isBalloonHIncreasing = true;
    boolean isBalloonWIncreasing = true;
    boolean isBalloonXScaling = false;
    boolean isBalloonYScaling = false;
    boolean isClockWise = false;
    boolean isFaceAdded = false;
    int anInt8 = 0;
    int anInt9 = 0;
    float aFloat1 = 10.0f;
    float aFloat2 = 0.0f;
    float aFloat3 = 0.3f;
    float aFloat4 = 0.9f;
    String object = "";
    int objectName = 0;
    OnBalloonActionListener onBalloonActionListener = null;
    float aFloat5 = 0.01f;
    float aFloat6 = 0.8f;
    float aFloat7 = 0.005f;
    Random random = new Random();
    int state = 0;



    public interface OnBalloonActionListener {
        void onKill();
    }

    public Balloon(Context context, int i, int i2) {
        this.speed = 0;
        this.context = context;
        this.aFloat = i;
        this.speed = i2;
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setColor(0xffff0000);
        setBalloonAnimConfig();
    }

    public void addOnBalloonActionListener(OnBalloonActionListener onBalloonActionListener) {
        this.onBalloonActionListener = onBalloonActionListener;
    }

    public void animateObjects() {
        int i = this.anInt;
        if (i == 0 || i == 3) {
            if (this.isBalloonHIncreasing) {
                int i2 = this.anInt3;
                if (i2 <= this.anInt2) {
                    this.anInt3 = i2 + this.anInt4;
                } else {
                    this.isBalloonHIncreasing = false;
                }
            } else {
                int i3 = this.anInt3;
                if (i3 >= 0) {
                    this.anInt3 = i3 - this.anInt4;
                } else {
                    int nextInt = this.random.nextInt(this.anInt5) + 1;
                    this.anInt2 = nextInt;
                    this.anInt4 = (nextInt / 20) + 1;
                    this.isBalloonHIncreasing = true;
                }
            }
            if (this.isBalloonWIncreasing) {
                int i4 = this.anInt7;
                if (i4 <= this.anInt6) {
                    this.anInt7 = i4 + this.anInt8;
                } else {
                    this.isBalloonWIncreasing = false;
                }
            } else {
                int i5 = this.anInt7;
                if (i5 >= 0) {
                    this.anInt7 = i5 - this.anInt8;
                } else {
                    int nextInt2 = this.random.nextInt(this.anInt9) + 1;
                    this.anInt6 = nextInt2;
                    this.anInt8 = (nextInt2 / 20) + 1;
                    this.isBalloonWIncreasing = true;
                }
            }
            double d = this.topHeight;
            int i6 = this.height;
            if (d > (-i6)) {
                float f = this.aFloat;
                int i7 = this.width;
                if (f > (-i7) && f < TempBalloonData.SCREEN_WIDTH + i7) {
                    Rect rect = this.gBallonRect;
                    int i8 = this.anInt7;
                    int i9 = (int) f;
                    rect.left = i9 + i8;
                    int i10 = (int) d;
                    int i11 = this.anInt3;
                    rect.top = i10 + i11;
                    rect.right = (i7 + i9) - i8;
                    rect.bottom = (i10 + i6) - i11;
                    if (this.anInt == 0) {
                        this.mergeDrawable.setBounds(rect);
                        return;
                    } else {
                        this.gBallon.setBounds(rect);
                        return;
                    }
                }
            }
            checkDeadStatus();
            return;
        }
        if (i == 2 || i == 5) {
            if (this.isBalloonXScaling) {
                float f2 = this.aFloat4;
                if (f2 <= 1.0f) {
                    this.aFloat4 = f2 + this.aFloat5;
                } else {
                    this.isBalloonXScaling = false;
                }
            } else {
                float f3 = this.aFloat4;
                if (f3 >= 0.9f) {
                    this.aFloat4 = f3 - this.aFloat5;
                } else {
                    this.isBalloonXScaling = true;
                }
            }
            if (this.isBalloonYScaling) {
                float f4 = this.aFloat6;
                if (f4 <= 1.0f) {
                    this.aFloat6 = f4 + this.aFloat7;
                } else {
                    this.isBalloonYScaling = false;
                }
            } else {
                float f5 = this.aFloat6;
                if (f5 >= 0.8f) {
                    this.aFloat6 = f5 - this.aFloat7;
                } else {
                    this.isBalloonYScaling = true;
                }
            }
        }
        if (this.isClockWise) {
            float f6 = this.aFloat2;
            if (f6 <= this.aFloat1) {
                this.aFloat2 = f6 + this.aFloat3;
            } else {
                this.isClockWise = false;
            }
        } else {
            float f7 = this.aFloat2;
            if (f7 >= -10.0f) {
                this.aFloat2 = f7 - this.aFloat3;
            } else {
                this.isClockWise = true;
            }
        }
    }

    public void calFirstAnimVars() {
        this.gBallonRect = new Rect();
        int i = this.height / 14;
        this.anInt2 = i;
        this.anInt5 = i;
        this.anInt4 = (i / 20) + 1;
        int i2 = this.width / 14;
        this.anInt6 = i2;
        this.anInt9 = i2;
        this.anInt8 = (i2 / 20) + 1;
    }

    public void checkDeadStatus() {
        if (this.state != 1) {
            this.state = 1;
            onKill();
            int i = this.anInt;
            if (i == 0 || i == 3) {
                this.gBallonRect.set(0, 0, 0, 0);
                if (this.anInt == 0) {
                    this.mergeDrawable.setBounds(this.gBallonRect);
                } else {
                    this.gBallon.setBounds(this.gBallonRect);
                }
            } else {
                Bitmap bitmap = this.rotatedBitamp;
                if (bitmap != null) {
                    bitmap.recycle();
                    this.rotatedBitamp = null;
                }
            }
        }
    }

    public void draw(Canvas canvas) {
        animateObjects();
        if (this.isFaceAdded) {
            if (this.state == 1) {
                killBalloon();
            } else if (this.anInt == 0) {
                this.mergeDrawable.draw(canvas);
            } else {
                double d = this.topHeight;
                int i = this.height;
                int i2 = -i;
                if (d > i2) {
                    float f = this.aFloat;
                    if (f > i2 && f < TempBalloonData.SCREEN_WIDTH + i) {
                        if (this.rotatedBitamp != null) {
                            Matrix matrix = new Matrix();
                            matrix.setTranslate(this.aFloat, (float) this.topHeight);
                            if (this.anInt == 2) {
                                matrix.postScale(this.aFloat4, this.aFloat6, this.aFloat + (this.width / 2.0f), ((float) this.topHeight) + (this.height / 2.0f));
                            }
                            matrix.postRotate(this.aFloat2, (this.width / 2) + this.aFloat, (this.height / 2) + ((float) this.topHeight));
                            canvas.drawBitmap(this.rotatedBitamp, matrix, this.paint);
                            return;
                        }
                        return;
                    }
                }
                checkDeadStatus();
            }
        } else if (this.state == 1) {
            killBalloon();
        } else if (this.anInt == 3) {
            this.gBallon.draw(canvas);
        } else {
            double d2 = this.topHeight;
            int i3 = this.height;
            int i4 = -i3;
            if (d2 > i4) {
                float f2 = this.aFloat;
                if (f2 > i4 && f2 < TempBalloonData.SCREEN_WIDTH + i3) {
                    if (this.rotatedBitamp != null) {
                        Matrix matrix2 = new Matrix();
                        matrix2.setTranslate(this.aFloat, (float) this.topHeight);
                        matrix2.postRotate(this.aFloat2, (this.width / 2) + this.aFloat, (this.height / 2) + ((float) this.topHeight));
                        if (this.anInt == 5) {
                            matrix2.postScale(this.aFloat4, this.aFloat6, this.aFloat + (this.width / 2.0f), ((float) this.topHeight) + (this.height / 2.0f));
                        }
                        canvas.drawBitmap(this.rotatedBitamp, matrix2, this.paint);
                        return;
                    }
                    return;
                }
            }
            checkDeadStatus();
        }
    }

    public void init() {
        int i2 = TempBalloonData.SCREEN_HEIGHT;
        double nextInt = i2 + (i2 / (this.random.nextInt(6) + 8));
        this.topHeight = nextInt;
        this.aDouble1 = nextInt;
        int i3 = this.height;
        MAXY_SPEED = i3 / 80;
        int i4 = this.width;
        MAXX_SPEED = i4 / 30;
        int i5 = this.speed;
        if (i5 <= 0 || i5 >= 80 || i3 <= 0) {
            this.aDouble = this.random.nextInt((anInt7 / 4) * 2) - (MAXX_SPEED / 4);
            int nextInt2 = this.random.nextInt(MAXY_SPEED);
            int i6 = MAXY_SPEED;
            this.aDouble2 = nextInt2 + (i6 / 2) + (i6 / 6);
            return;
        }
        MAXY_SPEED = i3 / (80 - i5);
        MAXX_SPEED = i4 / 20;
        float nextFloat = this.random.nextFloat() * (MAXX_SPEED / 16);
        double d = nextFloat;
        if (d > 0.9d) {
            Double.isNaN(d);
        } else if (d > 0.6d) {
            Double.isNaN(d);
        } else if (d > 0.5d) {
            Double.isNaN(d);
        } else {
            double d2 = 0.0d;
            if (d == 0.0d) {
                int nextInt3 = this.random.nextInt(3);
                if (nextInt3 == 0) {
                    Double.isNaN(d);
                    d2 = d + 0.41d;
                } else if (nextInt3 == 2) {
                    Double.isNaN(d);
                    d2 = d - 0.41d;
                }
                nextFloat = (float) d2;
            }
            if (this.random.nextInt(2) != 0) {
                this.aDouble = nextFloat;
            } else {
                this.aDouble = -nextFloat;
            }
            int nextInt4 = this.random.nextInt(MAXY_SPEED);
            int i7 = MAXY_SPEED;
            double d3 = nextInt4 + (i7 / 2) + (i7 / 6);
            this.aDouble2 = d3;
            double nextFloat2 = this.random.nextFloat() * 1.0f;
            Double.isNaN(d3);
            Double.isNaN(nextFloat2);
            this.aDouble2 = d3 + nextFloat2;
        }
        this.random.nextInt(2);
        int nextInt5 = this.random.nextInt(MAXY_SPEED);
        int i8 = MAXY_SPEED;
        double d4 = nextInt5 + (i8 / 2) + (i8 / 6);
        this.aDouble2 = d4;
        double nextFloat3 = this.random.nextFloat() * 1.0f;
        Double.isNaN(d4);
        Double.isNaN(nextFloat3);
        this.aDouble2 = d4 + nextFloat3;
    }

    public boolean isAlive() {
        return this.state == 0;
    }

    public boolean isBalloonTouched(float f, float f2) {
        float f3 = this.width;
        float f4 = this.height * 0.6f;
        double d = this.topHeight;
        float f5 = this.aFloat;
        if (f <= f5 || f >= f5 + f3) {
            return false;
        }
        double d2 = f2;
        if (d2 <= d) {
            return false;
        }
        double d3 = f4;
        Double.isNaN(d3);
        if (d2 >= d + d3) {
            return false;
        }
        this.state = 1;
        onKill();
        TempBalloonData.OBJECT = this.object;
        return true;
    }


    public void killBalloon() {
        int i = this.anInt;
        if (i == 0 || i == 3) {
            this.gBallonRect.set(0, 0, 0, 0);
            if (this.anInt == 0) {
                this.mergeDrawable.setBounds(this.gBallonRect);
            } else {
                this.gBallon.setBounds(this.gBallonRect);
            }
        } else {
            Bitmap bitmap = this.rotatedBitamp;
            if (bitmap != null) {
                bitmap.recycle();
                this.rotatedBitamp = null;
            }
        }
    }

    public void onKill() {
        if (onBalloonActionListener != null) {
            onBalloonActionListener.onKill();
        }
    }

    public void reset(int i) {
        this.anInt = 0;
        this.aFloat = i;
        this.topHeight = this.aDouble1;
        this.gBallon = null;
        this.face = null;
        this.mergeDrawable = null;
        this.anInt2 = 0;
        this.anInt3 = 0;
        this.anInt4 = 0;
        this.anInt5 = 0;
        this.anInt6 = 0;
        this.anInt7 = 0;
        this.anInt8 = 0;
        this.anInt9 = 0;
        this.aFloat1 = 10.0f;
        this.aFloat2 = 0.0f;
        this.aFloat3 = 0.3f;
        this.aFloat4 = 0.9f;
        this.aFloat5 = 0.01f;
        this.aFloat6 = 0.8f;
        this.aFloat7 = 0.005f;
        this.ballonBitmap = null;
        this.rotatedBitamp = null;
        this.ballonScaledBitmap = null;
        this.faceBitmap = null;
        this.faceScaledBitmap = null;
        this.mergedBitmap = null;
        this.isBalloonWIncreasing = true;
        this.isClockWise = false;
        this.isBalloonXScaling = false;
        this.isBalloonYScaling = false;
        this.state = 0;
        setBalloonAnimConfig();
    }

    public void selectObejctType(int i) {
        this.anInt1 = i;
        if (i == 0) {
            this.isFaceAdded = true;
            this.objects = BalloonKeys.ballonPics;
            this.object = "balloon";
            setSizeWithFace();
        } else if (i == 1) {
            this.isFaceAdded = true;
            this.objects = BalloonKeys.stars;
            this.object = "star";
            setSizeWithFace();
        } else if (i == 2) {
            this.isFaceAdded = false;
            this.objects = BalloonKeys.animals;
            setSizeOfAnimals();
        } else if (i == 3) {
            this.isFaceAdded = false;
            this.objects = BalloonKeys.donuts;
            this.object = "donut";
            setSizeWithFace();
        } else if (i == 4) {
            this.isFaceAdded = true;
            this.objects = BalloonKeys.hearts;
            this.object = "heart";
            setSizeWithFace();
        }
        init();
    }

    public void setBalloonAnimConfig() {
        if (this.context != null) {
            selectObejctType(this.random.nextInt(5));
            int nextInt = this.random.nextInt(this.objects.length);
            this.objectName = this.random.nextInt(100);
            if (this.isFaceAdded) {
                int nextInt2 = this.random.nextInt(3);
                this.anInt = nextInt2;
                if (nextInt2 == 0) {
                    this.gBallon = ContextCompat.getDrawable(this.context, this.objects[nextInt]);
                    this.face = ContextCompat.getDrawable(this.context, BalloonKeys.facePics[this.random.nextInt(7)]);
                    this.mergeDrawable = new LayerDrawable(new Drawable[]{this.gBallon, this.face});
                    calFirstAnimVars();
                } else if (nextInt2 == 1 || nextInt2 == 2) {
                    Resources resources = this.context.getResources();
                    int[] iArr = this.objects;
                    Bitmap decodeResource = BitmapFactory.decodeResource(resources, iArr[this.random.nextInt(iArr.length)]);
                    this.ballonBitmap = decodeResource;
                    Log.e("aaaaa",":"+width);
                    Log.e("aaaaa",":"+height);
                    Log.e("aaaaa",":"+(ballonBitmap == null));

                    this.ballonScaledBitmap = Bitmap.createScaledBitmap(decodeResource, this.width, this.height, false);
                    Bitmap decodeResource2 = BitmapFactory.decodeResource(this.context.getResources(), BalloonKeys.facePics[this.random.nextInt(7)]);
                    this.faceBitmap = decodeResource2;
                    this.faceScaledBitmap = Bitmap.createScaledBitmap(decodeResource2, this.width, this.height, false);
                    this.ballonBitmap.recycle();
                    this.faceBitmap.recycle();
                    this.ballonBitmap = null;
                    this.faceBitmap = null;
                    this.mergedBitmap = TempBalloonData.getCombinedBitmap(this.ballonScaledBitmap, this.faceScaledBitmap);
                    this.ballonScaledBitmap.recycle();
                    this.faceScaledBitmap.recycle();
                    this.ballonScaledBitmap = null;
                    this.faceScaledBitmap = null;
                    this.rotatedBitamp = Bitmap.createBitmap(this.mergedBitmap, 0, 0, this.width, this.height, new Matrix(), true);
                    this.mergedBitmap.recycle();
                    this.mergedBitmap = null;
                }
            } else {
                int nextInt3 = this.random.nextInt(3) + 3;
                this.anInt = nextInt3;
                if (nextInt3 == 3) {
                    int i = this.anInt1;
                    if (i == 5) {
                        this.object = (nextInt + 1) + "";
                    } else if (i == 2) {
                        this.object = BalloonKeys.animalsSounds[nextInt];
                    }
                    this.gBallon = ContextCompat.getDrawable(this.context, this.objects[nextInt]);
                    calFirstAnimVars();
                    return;
                }
                int nextInt4 = this.random.nextInt(this.objects.length);
                int i2 = this.anInt1;
                if (i2 == 5) {
                    this.object = (nextInt4 + 1) + "";
                } else if (i2 == 2) {
                    this.object = BalloonKeys.animalsSounds[nextInt4];
                }
                Bitmap decodeResource3 = BitmapFactory.decodeResource(this.context.getResources(), this.objects[nextInt4]);
                this.ballonBitmap = decodeResource3;
                Log.e("aaaaa",":"+width);
                Log.e("aaaaa",":"+height);
                Log.e("aaaaa",":"+(decodeResource3 == null));
                this.ballonScaledBitmap = Bitmap.createScaledBitmap(decodeResource3, this.width, this.height, false);
                this.ballonBitmap.recycle();
                this.ballonBitmap = null;
                this.rotatedBitamp = Bitmap.createBitmap(this.ballonScaledBitmap, 0, 0, this.width, this.height, new Matrix(), true);
                this.ballonScaledBitmap.recycle();
                this.ballonScaledBitmap = null;
            }
        }
    }

    public void setSizeOfAnimals() {
        this.width = TempBalloonData.ANIMAL_WIDTH;
        this.height = TempBalloonData.ANIMAL_HEIGHT;
        Log.e("aaaaa",":setSizeOfAnimals"+width);
        Log.e("aaaaa",":setSizeOfAnimals"+height);
    }


    public void setSizeWithFace() {
        this.width = TempBalloonData.BALLOON_WIDTH;
        this.height = TempBalloonData.BALLOON_HEIGHT;
        Log.e("aaaaa",":setSizeWithFace"+width);
        Log.e("aaaaa",":setSizeWithFace"+height);
    }

    public void update() {
        if (this.state != 1) {
            double d = this.aFloat;
            double d2 = this.aDouble;
            Double.isNaN(d);
            this.aFloat = (float) (d + d2);
            this.topHeight -= this.aDouble2;
        }
    }
}
