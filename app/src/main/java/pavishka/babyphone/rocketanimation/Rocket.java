package pavishka.babyphone.rocketanimation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.Random;


public class Rocket {
    private static int defaultLifetime;
    private static int maxxSpeed;
    private static int maxySpeed;
    private Bitmap bmpOriginal;
    private Bitmap bmpResult;
    private int color;
    private int colorIndex;
    private Context context;
    private float currentDegree;
    private int height;
    private Paint paint;
    private Random random;
    private Bitmap rotatedBitamp;
    private int speed;
    private double topHeight;
    private float aFloat;
    private double aDouble;
    private double aDouble1;
    private double aDouble2;
    private OnRocektActionListener onRocektActionListener = null;
    private int rocketName = 0;
    private int state = 0;
    private int lifetime = defaultLifetime;
    private int age = 0;
    private int width = TempRocketData.rocketWidth;


    public interface OnRocektActionListener {
        void onKill();
    }

    static {
        int i = TempRocketData.rocketSpeed;
        maxySpeed = i;
        maxxSpeed = i;
    }

    public Rocket(Context context, int i, int i2, int i3) {
        this.speed = 0;
        this.context = context;
        this.aFloat = i2;
        defaultLifetime = i;
        Random random = new Random();
        this.random = random;
        double nextInt = i + (i / (random.nextInt(2) + 2));
        this.topHeight = nextInt;
        this.aDouble1 = nextInt;
        this.speed = i3;
        int i4 = TempRocketData.rocketHeight;
        this.height = i4;
        maxySpeed = i4 / 28;
        this.color = Color.argb(255, this.random.nextInt(255), this.random.nextInt(255), this.random.nextInt(255));
        this.paint = new Paint(this.color);
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setColor(0xffff0000);
        this.paint.setStrokeWidth(20.0f);
        this.colorIndex = this.random.nextInt(7);
        setRocketConfig();
    }

    public void addOnRocektActionListener(OnRocektActionListener onRocektActionListener) {
        this.onRocektActionListener = onRocektActionListener;
    }

    public void checkDeadStatus() {
        if (this.state != 1) {
            onKill();
            this.state = 1;
            Bitmap bitmap = this.rotatedBitamp;
            if (bitmap != null) {
                bitmap.recycle();
                this.rotatedBitamp = null;
            }
        }
    }

    public void draw(Canvas canvas) {
        if (this.state != 1) {
            double d = this.topHeight;
            int i = this.height;
            int i2 = -i;
            if (d > i2) {
                float f = this.aFloat;
                if (f > i2 && f < TempRocketData.screenWidth + i) {
                    Bitmap bitmap = this.rotatedBitamp;
                    if (bitmap != null) {
                        canvas.drawBitmap(bitmap, f, (int) d, this.paint);
                        return;
                    }
                    return;
                }
            }
            checkDeadStatus();
            return;
        }
        killRocket();
    }

    public boolean isAlive() {
        return this.state == 0;
    }


    public boolean isRocketTouched(float f, float f2) {
        int i;
        if (this.currentDegree == 0.0f) {
            i = this.width;
        } else {
            i = this.width + (this.height / 3);
        }
        float f3 = i;
        double d = this.topHeight;
        float f4 = this.aFloat;
        if (f <= f4 || f >= f4 + f3) {
            return false;
        }
        double d2 = f2;
        if (d2 <= d) {
            return false;
        }
        double d3 = this.height;
        Double.isNaN(d3);
        if (d2 >= d + d3) {
            return false;
        }
        this.state = 1;
        onKill();
        return true;
    }

    public void killRocket() {
        this.state = 1;
        Bitmap bitmap = this.rotatedBitamp;
        if (bitmap != null) {
            bitmap.recycle();
            this.rotatedBitamp = null;
        }
    }

    public void onKill() {
        OnRocektActionListener onRocektActionListener = this.onRocektActionListener;
        if (onRocektActionListener != null) {
            onRocektActionListener.onKill();
        }
    }

    public void reset(int i) {
        this.rocketName = this.random.nextInt(100);
        this.aFloat = i;
        this.topHeight = this.aDouble1;
        this.bmpOriginal = null;
        this.bmpResult = null;
        this.rotatedBitamp = null;
        this.state = 0;
        this.lifetime = defaultLifetime;
        this.age = 0;
        this.paint.setColor(0xffff0000);
        this.colorIndex = this.random.nextInt(7);
        setRocketConfig();
    }

    public void setRocketConfig() {
        int i;
        int i2;
        int i3;
        int i4 = this.speed;
        if (i4 <= 0 || i4 >= 36 || (i2 = this.height) <= 0) {
            maxxSpeed = this.width / 20;
            this.aDouble = this.random.nextInt(1) - (maxxSpeed / 4);
            this.aDouble2 = this.random.nextInt(maxySpeed / 3) + (maxySpeed / 2);
        } else {
            maxySpeed = i2 / (36 - i4);
            maxxSpeed = this.width / 20;
            this.aDouble = this.random.nextInt(1) - (maxxSpeed / 4);
            this.aDouble2 = this.random.nextInt(maxySpeed / 3) + (maxySpeed / 2);
        }
        int nextInt = this.random.nextInt(4);
        if (nextInt == 0) {
            double d = this.aDouble2;
            this.aDouble2 = d + (d / 8.0d);
        } else if (nextInt == 0) {
            double d2 = this.aDouble2;
            this.aDouble2 = d2 - (d2 / 3.0d);
        }
        if (nextInt == 0) {
            double d3 = this.aDouble2;
            this.aDouble2 = d3 + (d3 / 10.0d);
        } else {
            double d4 = this.aDouble2;
            this.aDouble2 = d4 - (d4 / 9.0d);
        }
        double d5 = this.aFloat;
        double d6 = this.aDouble;
        Double.isNaN(d5);
        Double.isNaN(d5);
        double d7 = (d6 + d5) - d5;
        double d8 = this.topHeight;
        float degrees = (float) Math.toDegrees(Math.atan2(d7, (this.aDouble2 + d8) - d8));
        this.currentDegree = degrees;
        if (degrees < 0.0f) {
            this.aFloat += TempRocketData.screenWidth / 3;
        } else {
            this.aFloat -= TempRocketData.screenWidth / 3;
        }
        this.colorIndex = this.random.nextInt(7);
        Bitmap decodeResource = BitmapFactory.decodeResource(this.context.getResources(), RocketKeys.rocketDrawables[this.colorIndex]);
        this.bmpOriginal = decodeResource;
        this.bmpResult = Bitmap.createScaledBitmap(decodeResource, this.width, this.height, false);
        Matrix matrix = new Matrix();
        matrix.postRotate(this.currentDegree);
        this.rotatedBitamp = Bitmap.createBitmap(this.bmpResult, 0, 0, this.width, this.height, matrix, true);
        this.bmpOriginal.recycle();
        this.bmpOriginal = null;
        this.bmpResult.recycle();
        this.bmpResult = null;
    }

    public void update() {
        if (this.state != 1) {
            double d = this.aFloat;
            double d2 = this.aDouble;
            Double.isNaN(d);
            this.aFloat = (float) (d + d2);
            this.topHeight -= this.aDouble2;
            int i = this.age + 1;
            this.age = i;
            if (i >= this.lifetime + this.height) {
                this.state = 1;
            }
        }
    }
}
