package pavishka.babyphone.balloonanimation;

import android.graphics.Canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Explosion {
    private Particle[] particles;
    private int explosionTimes = 0;
    private boolean isFirstTime = true;
    private int state = 0;
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    public Explosion(int i, int i2, int i3) {
        this.particles = new Particle[i];
        for (int i4 = 0; i4 < this.particles.length; i4++) {
            Particle particle = new Particle(i2, i3);
            Particle[] particleArr = this.particles;
            particleArr[i4] = particle;
            particleArr[i4].addOnParticleActionListener(new Particle.OnParticleActionListener() {
                @Override
                public void onDead() {
                    Explosion.alex(Explosion.this);
                }
            });
        }
    }

    static int alex(Explosion explosion) {
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

    public boolean isItFirsttime() {
        return this.isFirstTime;
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
            if (particleArr[i] != null) {
                particleArr[i].draw(canvas);
            }
            i++;
        }
        if (this.explosionTimes == particleArr.length) {
            this.state = 1;
            this.explosionTimes = 0;
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
