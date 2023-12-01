package pavishka.babyphone.balloonanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MovingBalloons {
    Random random;
    private int balloonName;
    private Balloon[] balloons;
    private int randNo;
    private int anInt;
    private boolean isFirstTime = true;
    public int deadBalloons = 0;
    ExecutorService b = Executors.newFixedThreadPool(2);
    private int state = 0;

    public MovingBalloons(Context context, int i, int i2, int i3) {
        this.balloonName = 0;
        Random random = new Random();
        this.random = random;
        this.balloonName = random.nextInt(100);
        this.anInt = i2;
        this.balloons = new Balloon[i];
        for (int i4 = 0; i4 < this.balloons.length; i4++) {
            int nextInt = this.random.nextInt(8);
            this.randNo = nextInt;
            switch (nextInt) {
                case 0:
                    this.balloons[i4] = new Balloon(context, i2 / 4, i3);
                    break;
                case 1:
                    this.balloons[i4] = new Balloon(context, i2 / 2, i3);
                    break;
                case 2:
                    this.balloons[i4] = new Balloon(context, i2 / 3, i3);
                    break;
                case 3:
                    this.balloons[i4] = new Balloon(context, i2 / 6, i3);
                    break;
                case 4:
                    this.balloons[i4] = new Balloon(context, i2 / 7, i3);
                    break;
                case 5:
                    this.balloons[i4] = new Balloon(context, i2 / 5, i3);
                    break;
                case 6:
                    this.balloons[i4] = new Balloon(context, i2 / 8, i3);
                    break;
                case 7:
                    this.balloons[i4] = new Balloon(context, i2 / 9, i3);
                    break;
            }
            this.balloons[i4].addOnBalloonActionListener(new Balloon.OnBalloonActionListener() {
                @Override
                public void onKill() {
                    MovingBalloons.liekm(MovingBalloons.this);
                }
            });
        }
    }

    static int liekm(MovingBalloons movingBalloons) {
        int i = movingBalloons.deadBalloons;
        movingBalloons.deadBalloons = i + 1;
        return i;
    }

    public void floatBalloons(Canvas canvas) {
        Balloon[] balloonArr;
        this.isFirstTime = false;
        int i = 0;
        while (true) {
            balloonArr = this.balloons;
            if (i >= balloonArr.length) {
                break;
            }
            if (balloonArr[i] != null && balloonArr[i].isAlive()) {
                Log.d("dsds", "3 getting called");
                this.balloons[i].draw(canvas);
            }
            i++;
        }
        if (this.deadBalloons >= balloonArr.length) {
            this.state = 1;
            this.deadBalloons = 0;
            this.isFirstTime = true;
            return;
        }
        runThread();
    }

    public Balloon[] getBalloons() {
        return this.balloons;
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

    public void reset() {
        this.deadBalloons = 0;
        this.isFirstTime = true;
        for (int i = 0; i < this.balloons.length; i++) {
            int nextInt = this.random.nextInt(8);
            this.randNo = nextInt;
            switch (nextInt) {
                case 0:
                    this.balloons[i].reset(this.anInt / 4);
                    break;
                case 1:
                    this.balloons[i].reset(this.anInt / 2);
                    break;
                case 2:
                    this.balloons[i].reset(this.anInt / 3);
                    break;
                case 3:
                    this.balloons[i].reset(this.anInt / 6);
                    break;
                case 4:
                    this.balloons[i].reset(this.anInt / 7);
                    break;
                case 5:
                    this.balloons[i].reset(this.anInt / 5);
                    break;
                case 6:
                    this.balloons[i].reset(this.anInt / 8);
                    break;
                case 7:
                    this.balloons[i].reset(this.anInt / 9);
                    break;
            }
        }
        this.state = 0;
    }

    public void runThread() {
        try {
            this.b.execute(new Runnable() {
                @Override
                public void run() {
                    MovingBalloons.this.updateBalloonPosition();
                }
            });
        } catch (Exception unused) {
            Log.e("sddd", "");
        }
    }

    public void setExecutor() {
        this.b = Executors.newFixedThreadPool(2);
    }

    public void stopThread() {
        this.b.shutdown();
        this.b = null;
    }

    public void updateBalloonPosition() {
        int i = 0;
        while (true) {
            Balloon[] balloonArr = this.balloons;
            if (i < balloonArr.length) {
                if (balloonArr[i] != null && balloonArr[i].isAlive()) {
                    this.balloons[i].update();
                }
                i++;
            } else {
                return;
            }
        }
    }
}
