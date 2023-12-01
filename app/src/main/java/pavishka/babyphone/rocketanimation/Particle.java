package pavishka.babyphone.rocketanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import java.util.Random;


public class Particle {
    public static int DEFAULT_LIFETIME;
    public static final int MAX_DIMENSION;
    public static final int MAX_SPEED;
    private int color;
    private Context context;
    private int lifetime;
    private Random random;
    private Drawable star;
    private int width;
    private float aFloat;
    private double aDouble;
    private float aFloat1;
    private double aDouble1;
    private OnParticleActionListener particleActionListener = null;
    private int state = 0;
    private int age = 0;



    public interface OnParticleActionListener {
        void onDead();
    }

    static {
        int i = TempRocketData.screenWidth;
        DEFAULT_LIFETIME = i / 10;
        MAX_DIMENSION = i / 24;
        MAX_SPEED = i / 80;
    }

    public Particle(Context context, float f, float f2) {
        this.context = context;
        this.aFloat = f;
        this.aFloat1 = f2;
        Random random = new Random();
        this.random = random;
        this.width = random.nextInt(MAX_DIMENSION);
        int nextInt = TempRocketData.screenWidth / (this.random.nextInt(20) + 6);
        DEFAULT_LIFETIME = nextInt;
        this.lifetime = nextInt;
        Random random2 = this.random;
        int i = MAX_SPEED;
        this.aDouble = random2.nextInt(i * 2) - i;
        double nextInt2 = this.random.nextInt(i * 2) - i;
        this.aDouble1 = nextInt2;
        double d = this.aDouble;
        Double.isNaN(nextInt2);
        Double.isNaN(nextInt2);
        if ((d * d) + (nextInt2 * nextInt2) > i * i) {
            this.aDouble = d * 0.7d;
            Double.isNaN(nextInt2);
            this.aDouble1 = nextInt2 * 0.7d;
        }
        this.color = Color.argb(255, this.random.nextInt(255), this.random.nextInt(255), this.random.nextInt(255));
        this.star = ContextCompat.getDrawable(context, RocketKeys.startDrawables[TempRocketData.starNo]);
    }

    public void addOnParticleActionListener(OnParticleActionListener onParticleActionListener) {
        this.particleActionListener = onParticleActionListener;
    }

    public void checkDeadStatus() {
        if (this.state != 1) {
            onDead();
            this.state = 1;
        }
    }

    public void draw(Canvas canvas) {
        int i = (int) this.aFloat;
        int i2 = (int) this.aFloat1;
        Drawable drawable = this.star;
        int i3 = this.width;
        drawable.setBounds(i, i2, i + i3, i3 + i2);
        this.star.draw(canvas);
    }

    public boolean isAlive() {
        return this.state == 0;
    }

    public void onDead() {
        OnParticleActionListener onParticleActionListener = this.particleActionListener;
        if (onParticleActionListener != null) {
            onParticleActionListener.onDead();
        }
    }

    public void reset(float f, float f2) {
        if (this.context != null) {
            this.aFloat = f;
            this.aFloat1 = f2;
            int nextInt = TempRocketData.screenWidth / (this.random.nextInt(20) + 6);
            DEFAULT_LIFETIME = nextInt;
            this.lifetime = nextInt;
            this.age = 0;
            this.color = Color.argb(255, this.random.nextInt(255), this.random.nextInt(255), this.random.nextInt(255));
            this.star = ContextCompat.getDrawable(this.context, RocketKeys.startDrawables[TempRocketData.starNo]);
            this.state = 0;
        }
    }

    public void update() {
        if (this.state != 1) {
            double d = this.aFloat;
            double d2 = this.aDouble;
            Double.isNaN(d);
            this.aFloat = (float) (d + d2);
            double d3 = this.aFloat1;
            double d4 = this.aDouble1;
            Double.isNaN(d3);
            this.aFloat1 = (float) (d3 + d4);
            int i = this.color;
            int i2 = (i >>> 24) - 2;
            if (i2 <= 0) {
                checkDeadStatus();
            } else {
                this.color = (i & ViewCompat.MEASURED_SIZE_MASK) + (i2 << 24);
                this.age++;
            }
            if (this.age >= this.lifetime) {
                checkDeadStatus();
            }
        }
    }
}
