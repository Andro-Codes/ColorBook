package pavishka.babyphone.rocketanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LaunchableRockets {
    private int randNo;
    private Rocket[] rockets;
    private int size;
    private int anInt;
    Random random = new Random();
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    private int deadRockets = 0;
    private boolean isFirstTime = true;
    private int state = 0;

    public LaunchableRockets(Context context, int i, int i2, int i3, int i4) {
        this.randNo = 1;
        this.anInt = i3;
        this.rockets = new Rocket[i2];
        for (int i5 = 0; i5 < this.rockets.length; i5++) {
            int nextInt = this.random.nextInt(4);
            this.randNo = nextInt;
            if (nextInt == 0) {
                this.rockets[i5] = new Rocket(context, i, i3 / 3, i4);
            } else if (nextInt == 1) {
                this.rockets[i5] = new Rocket(context, i, i3 / 2, i4);
            } else if (nextInt == 2) {
                this.rockets[i5] = new Rocket(context, i, i3 / 2, i4);
            } else if (nextInt == 3) {
                this.rockets[i5] = new Rocket(context, i, i3 / 3, i4);
            }
            this.rockets[i5].addOnRocektActionListener(new Rocket.OnRocektActionListener() {
                @Override
                public void onKill() {
                    LaunchableRockets.koti(LaunchableRockets.this);
                }
            });
        }
        this.size = i2;
    }

    static int koti(LaunchableRockets launchableRockets) {
        int i = launchableRockets.deadRockets;
        launchableRockets.deadRockets = i + 1;
        return i;
    }

    public Rocket[] getRockets() {
        return this.rockets;
    }

    public boolean isAlive() {
        return this.state == 0;
    }

    public boolean isDead() {
        return this.state == 1;
    }

    public boolean isItFirstTime() {
        return this.isFirstTime;
    }

    public void launchRockets(Canvas canvas) {
        Rocket[] rocketArr;
        this.isFirstTime = false;
        int i = 0;
        while (true) {
            rocketArr = this.rockets;
            if (i >= rocketArr.length) {
                break;
            }
            if (rocketArr[i] != null && rocketArr[i].isAlive()) {
                this.rockets[i].draw(canvas);
            }
            i++;
        }
        if (this.deadRockets >= rocketArr.length) {
            this.state = 1;
            this.deadRockets = 0;
            this.isFirstTime = true;
            return;
        }
        runThread();
    }

    public void reset() {
        this.deadRockets = 0;
        this.isFirstTime = true;
        for (int i = 0; i < this.rockets.length; i++) {
            int nextInt = this.random.nextInt(4);
            this.randNo = nextInt;
            if (nextInt == 0) {
                this.rockets[i].reset(this.anInt / 3);
            } else if (nextInt == 1) {
                this.rockets[i].reset(this.anInt / 2);
            } else if (nextInt == 2) {
                this.rockets[i].reset(this.anInt / 2);
            } else if (nextInt == 3) {
                this.rockets[i].reset(this.anInt / 3);
            }
        }
        this.state = 0;
    }

    public void runThread() {
        try {
            this.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    LaunchableRockets.this.updateRocket();
                }
            });
        } catch (Exception unused) {
            Log.e("sddd", "");
        }
    }

    public void setExecutor() {
        this.executorService = Executors.newFixedThreadPool(2);
    }

    public void stopThread() {
        this.executorService.shutdown();
        this.executorService = null;
    }

    public void updateRocket() {
        int i = 0;
        while (true) {
            Rocket[] rocketArr = this.rockets;
            if (i < rocketArr.length) {
                if (rocketArr[i] != null && rocketArr[i].isAlive()) {
                    this.rockets[i].update();
                }
                i++;
            } else {
                return;
            }
        }
    }
}
