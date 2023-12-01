package pavishka.babyphone.rocketanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Explosion {
    private Particle[] particles;
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    private int explosionTimes = 0;
    private int state = 0;
    private boolean isFirstTime = true;
    public boolean isItFirsttime() {
        return this.isFirstTime;
    }

    public Explosion(Context context, int i, float f, float f2) {
        this.particles = new Particle[i];
        for (int i2 = 0; i2 < this.particles.length; i2++) {
            Particle particle = new Particle(context, f, f2);
            Particle[] particleArr = this.particles;
            particleArr[i2] = particle;
            particleArr[i2].addOnParticleActionListener(new Particle.OnParticleActionListener() {
                @Override
                public void onDead() {
                    Explosion.alec(Explosion.this);
                }
            });
        }
    }

    static int alec(Explosion explosion) {
        int i = explosion.explosionTimes;
        explosion.explosionTimes = i + 1;
        return i;
    }

    public boolean isAlive() {
        return this.state == 0;
    }

    public boolean isDead() {
        return this.state == 1;
    }

    public void reset(float f, float f2) {
        this.isFirstTime = true;
        this.explosionTimes = 0;
        int i = 0;
        while (true) {
            Particle[] particleArr = this.particles;
            if (i < particleArr.length) {
                particleArr[i].reset(f, f2);
                i++;
            } else {
                this.state = 0;
                return;
            }
        }
    }

    public void runThread() {
        try {
            this.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Explosion.this.updateExplosion();
                }
            });
        } catch (Exception unused) {
            Log.e("sddd", "");
        }
    }

    public void setExecutor() {
        this.executorService = Executors.newFixedThreadPool(2);
    }

    public void startExplosion(Canvas canvas) {
        Particle[] particleArr;
        int i = 0;
        while (true) {
            particleArr = this.particles;
            if (i >= particleArr.length) {
                break;
            }
            if (particleArr[i] != null && particleArr[i].isAlive()) {
                this.particles[i].draw(canvas);
            }
            i++;
        }
        if (this.explosionTimes == particleArr.length) {
            this.state = 1;
            this.explosionTimes = 0;
            return;
        }
        if (this.isFirstTime) {
            this.isFirstTime = false;
        }
        runThread();
    }

    public void stopThread() {
        this.executorService.shutdown();
        this.executorService = null;
    }

    public void updateExplosion() {
        int i = 0;
        while (true) {
            Particle[] particleArr = this.particles;
            if (i < particleArr.length) {
                if (particleArr[i] != null) {
                    particleArr[i].update();
                }
                i++;
            } else {
                return;
            }
        }
    }
}
