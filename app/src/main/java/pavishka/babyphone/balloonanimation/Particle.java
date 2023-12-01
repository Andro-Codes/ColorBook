package pavishka.babyphone.balloonanimation;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.view.ViewCompat;

import java.util.Random;


public class Particle {
    public static int DEFAULT_LIFETIME;
    public static final int MAX_DIMENSION;
    public static final int MAX_SPEED;
    Random random;
    private int color;
    private float height;
    private int lifetime;
    private Paint paint;
    private float width;
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
        int i = TempBalloonData.SCREEN_WIDTH;
        DEFAULT_LIFETIME = i / 10;
        MAX_DIMENSION = i / 24;
        MAX_SPEED = i / 80;
    }

    public Particle(int i, int i2) {
        this.aFloat = i;
        this.aFloat1 = i2;
        Random random = new Random();
        this.random = random;
        float nextInt = random.nextInt(MAX_DIMENSION);
        this.width = nextInt;
        this.height = nextInt;
        int nextInt2 = TempBalloonData.SCREEN_WIDTH / (this.random.nextInt(20) + 6);
        DEFAULT_LIFETIME = nextInt2;
        this.lifetime = nextInt2;
        Random random2 = this.random;
        int i3 = MAX_SPEED;
        this.aDouble = random2.nextInt(i3 * 2) - i3;
        double nextInt3 = this.random.nextInt(i3 * 2) - i3;
        this.aDouble1 = nextInt3;
        double d = this.aDouble;
        Double.isNaN(nextInt3);
        Double.isNaN(nextInt3);
        if ((d * d) + (nextInt3 * nextInt3) > i3 * i3) {
            this.aDouble = d * 0.7d;
            Double.isNaN(nextInt3);
            this.aDouble1 = nextInt3 * 0.7d;
        }
        this.color = BalloonKeys.colorcodes[this.random.nextInt(8)];
        this.paint = new Paint(this.color);
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
        if (this.state == 0) {
            this.paint.setColor(this.color);
            canvas.drawCircle(this.aFloat, this.aFloat1, this.width / 2.0f, this.paint);
        }
    }

    public void onDead() {
        OnParticleActionListener onParticleActionListener = this.particleActionListener;
        if (onParticleActionListener != null) {
            onParticleActionListener.onDead();
        }
    }

    public void reset(float f, float f2) {
        this.aFloat = f;
        this.aFloat1 = f2;
        float nextInt = this.random.nextInt(MAX_DIMENSION);
        this.width = nextInt;
        this.height = nextInt;
        int nextInt2 = TempBalloonData.SCREEN_WIDTH / (this.random.nextInt(20) + 6);
        DEFAULT_LIFETIME = nextInt2;
        this.lifetime = nextInt2;
        this.age = 0;
        int i = BalloonKeys.colorcodes[this.random.nextInt(8)];
        this.color = i;
        this.paint.setColor(i);
        this.state = 0;
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
                this.paint.setAlpha(i2);
                this.age++;
            }
            if (this.age >= this.lifetime) {
                checkDeadStatus();
            }
        }
    }
}
