package pavishka.babyphone.rocketanimation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.work.WorkRequest;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pavishka.babyphone.balloonanimation.BalloonAnimation;


public class RocketAnimation extends View {
    ExecutorService executorService;
    private ValueAnimator animator;
    private Context context;
    private Explosion[] explosion;
    private LaunchableRockets launchableRocketsOne;
    private LaunchableRockets launchableRocketsTwo;
    private MyMediaPlayer mediaPlayer;
    private OnAnimationEndListener onAnimationEndListener;
    private int topHeight;
    private int topWidth;
    private boolean begin = false;
    private float currentX = -1.0f;
    private float currentY = -1.0f;
    private boolean isFirstTime = false;
    private boolean isRocketTouched = false;
    private int spawnDealay = 140;
    private int speed = 1;
    private int timeCounter = 0;
    private Random random = new Random();
    private int scoreCounter = 0;
    public int movingBallonsoneorTwocount(){
        return scoreCounter;
    }

    public void addOnAnimationEndListener(OnAnimationEndListener onAnimationEndListener) {
        this.onAnimationEndListener = onAnimationEndListener;
    }
    public interface OnAnimationEndListener {
        void onExplosion();
        void onFinish();
    }

    public RocketAnimation(Context context) {
        super(context);
        Explosion[] explosionArr;
        this.context = context;
        if (calculateSize()) {
            this.mediaPlayer = new MyMediaPlayer(context);
            this.launchableRocketsOne = null;
            this.launchableRocketsTwo = null;
            this.explosion = new Explosion[10];
            this.executorService = Executors.newFixedThreadPool(4);
            for (Explosion explosion : this.explosion) {
                Log.e("sddd", "");
            }
            ValueAnimator ofInt = ValueAnimator.ofInt(0, 10);
            this.animator = ofInt;
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    if (((Integer) valueAnimator.getAnimatedValue()).intValue() <= 10) {
                        RocketAnimation.this.invalidate();
                    }
                }
            });
            this.animator.setDuration(WorkRequest.MIN_BACKOFF_MILLIS);
            this.animator.setRepeatCount(-1);
            setVisibility(GONE);
        }
    }

    private boolean calculateSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.heightPixels;
        if (i == 0 && TempRocketData.screenWidth == 0) {
            return false;
        }
        this.spawnDealay = i / 18;
        int i2 = displayMetrics.widthPixels;
        TempRocketData.screenWidth = i2;
        Context context = this.context;
        if (context != null && i == 0 && i2 == 0) {
            Toast.makeText(context, "Height and width is not detected!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        TempRocketData.particleSpeed = i / 60;
        TempRocketData.rocketSpeed = i / 40;
        TempRocketData.particleLife = i / 12;
        Drawable drawable = ContextCompat.getDrawable(this.context, RocketKeys.rocketDrawables[0]);
        TempRocketData.rocketWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        TempRocketData.rocketHeight = intrinsicHeight;
        if (intrinsicHeight <= TempRocketData.rocketWidth) {
            float f = (float) TempRocketData.rocketHeight / (float) TempRocketData.rocketWidth;
            TempRocketData.starHeight = i / 36;
            int i3 = i / 3;
            TempRocketData.rocketWidth = i3;
            TempRocketData.rocketHeight = (int) (i3 * f);
            return true;
        }
        TempRocketData.starHeight = TempRocketData.screenWidth / 36;
        float f2 = (float) TempRocketData.rocketWidth / (float) TempRocketData.rocketHeight;
        int i4 = TempRocketData.screenWidth / 2;
        TempRocketData.rocketHeight = i4;
        TempRocketData.rocketWidth = (int) (i4 * f2);
        return true;
    }

    private void startRocketAnimations() {
        LaunchableRockets launchableRockets = this.launchableRocketsOne;
        if (launchableRockets == null || launchableRockets.isDead()) {
            this.launchableRocketsOne = new LaunchableRockets(this.context, this.topHeight, 3, this.topWidth, this.speed);
            this.begin = true;
        } else {
            this.launchableRocketsOne = null;
            this.launchableRocketsOne = new LaunchableRockets(this.context, this.topHeight, 3, this.topWidth, this.speed);
            this.begin = true;
        }
        this.isFirstTime = false;
        this.animator.start();
    }


    public void doAction() {
        if (this.begin) {
            this.isRocketTouched = true;
            LaunchableRockets launchableRockets = this.launchableRocketsOne;
            if (launchableRockets != null && launchableRockets.isAlive()) {
                Rocket[] rockets = this.launchableRocketsOne.getRockets();
                for (int length = rockets.length - 1; length > -1; length--) {
                    if (rockets[length].isAlive() && rockets[length].isRocketTouched(this.currentX, this.currentY)) {
                        int i = 0;
                        while (true) {
                            Explosion[] explosionArr = this.explosion;
                            if (i < explosionArr.length) {
                                if (explosionArr[i] == null) {
                                    explosionArr[i] = new Explosion(this.context, 40, (int) this.currentX, (int) this.currentY);
                                    setFireWork();
                                    break;
                                }
                                if (explosionArr[i] != null && explosionArr[i].isDead()) {
                                    this.explosion[i].reset(this.currentX, this.currentY);
                                    setFireWork();
                                    break;
                                }
                                i++;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
            LaunchableRockets launchableRockets2 = this.launchableRocketsTwo;
            if (launchableRockets2 != null && launchableRockets2.isAlive()) {
                Rocket[] rockets2 = this.launchableRocketsTwo.getRockets();
                for (int length2 = rockets2.length - 1; length2 > -1; length2--) {
                    if (rockets2[length2].isAlive() && rockets2[length2].isRocketTouched(this.currentX, this.currentY)) {
                        int i2 = 0;
                        while (true) {
                            Explosion[] explosionArr2 = this.explosion;
                            if (i2 < explosionArr2.length) {
                                if (explosionArr2[i2] == null) {
                                    explosionArr2[i2] = new Explosion(this.context, 40, (int) this.currentX, (int) this.currentY);
                                    setFireWork();
                                    break;
                                }
                                if (explosionArr2[i2] != null && explosionArr2[i2].isDead()) {
                                    this.explosion[i2].reset(this.currentX, this.currentY);
                                    setFireWork();
                                    break;
                                }
                                i2++;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        this.isRocketTouched = false;
    }


    public boolean isItReady() {
        LaunchableRockets launchableRockets = this.launchableRocketsOne;
        return launchableRockets == null || launchableRockets.isDead();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isFirstTime) {
            startRocketAnimations();
        }
        if (this.begin) {
            LaunchableRockets launchableRockets = this.launchableRocketsOne;
            if (launchableRockets != null && launchableRockets.isAlive()) {
                this.launchableRocketsOne.launchRockets(canvas);
            }
            LaunchableRockets launchableRockets2 = this.launchableRocketsTwo;
            if (launchableRockets2 != null && launchableRockets2.isAlive()) {
                this.launchableRocketsTwo.launchRockets(canvas);
            }
            int i = this.timeCounter + 1;
            this.timeCounter = i;
            int i2 = 0;
            if (i >= this.spawnDealay) {
                LaunchableRockets launchableRockets3 = this.launchableRocketsTwo;
                if (launchableRockets3 == null) {
                    this.timeCounter = 0;
                    this.launchableRocketsTwo = new LaunchableRockets(this.context, this.topHeight, 3, this.topWidth, this.speed);
                } else if (launchableRockets3 == null || (!launchableRockets3.isDead() && !this.launchableRocketsTwo.isItFirstTime())) {
                    LaunchableRockets launchableRockets4 = this.launchableRocketsOne;
                    if (launchableRockets4 != null && (launchableRockets4.isDead() || this.launchableRocketsOne.isItFirstTime())) {
                        this.timeCounter = 0;
                        this.launchableRocketsOne.reset();
                    }
                } else {
                    this.timeCounter = 0;
                    this.launchableRocketsTwo.reset();
                }
            }
            while (true) {
                Explosion[] explosionArr = this.explosion;
                if (i2 < explosionArr.length) {
                    if (explosionArr[i2] != null && explosionArr[i2].isAlive()) {
                        if (explosionArr[i2].isItFirsttime()) {
                            onExplosion();
                        }
                        this.explosion[i2].startExplosion(canvas);

                    }
                    i2++;
                } else {
                    return;
                }
            }
        }
    }
    public void onExplosion() {
        OnAnimationEndListener onAnimationEndListener = this.onAnimationEndListener;
        scoreCounter ++;
        if (onAnimationEndListener != null) {
            onAnimationEndListener.onExplosion();
        }
    }
    public void onFinish() {
        OnAnimationEndListener onAnimationEndListener = this.onAnimationEndListener;
        if (onAnimationEndListener != null) {
            onAnimationEndListener.onFinish();
        }
    }

    @Override
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.topHeight = i2;
        this.topWidth = i;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.currentX = motionEvent.getX();
            this.currentY = motionEvent.getY();
            runThread();
            return true;
        } else if (motionEvent.getActionMasked() != 5) {
            return true;
        } else {
            this.currentX = motionEvent.getX(motionEvent.getActionIndex());
            this.currentY = motionEvent.getY(motionEvent.getActionIndex());
            runThread();
            return true;
        }
    }

    public void runThread() {
        try {
            this.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    RocketAnimation.this.doAction();
                }
            });
        } catch (Exception unused) {
        }
    }

    public void setFireWork() {
        Random random = this.random;
        if (random != null) {
            MyMediaPlayer myMediaPlayer = this.mediaPlayer;
            if (myMediaPlayer != null) {
                myMediaPlayer.playCustomSound(RocketKeys.fireWorkSounds[random.nextInt(6)]);
            } else {
                Context context = this.context;
                if (context != null) {
                    MyMediaPlayer myMediaPlayer2 = new MyMediaPlayer(context);
                    this.mediaPlayer = myMediaPlayer2;
                    myMediaPlayer2.playCustomSound("firework_1");
                }
            }
            TempRocketData.starNo = this.random.nextInt(RocketKeys.startDrawables.length);
            return;
        }
        Random random2 = new Random();
        this.random = random2;
        MyMediaPlayer myMediaPlayer3 = this.mediaPlayer;
        if (myMediaPlayer3 != null) {
            myMediaPlayer3.playCustomSound(RocketKeys.fireWorkSounds[random2.nextInt(6)]);
        } else {
            Context context2 = this.context;
            if (context2 != null) {
                MyMediaPlayer myMediaPlayer4 = new MyMediaPlayer(context2);
                this.mediaPlayer = myMediaPlayer4;
                myMediaPlayer4.playCustomSound("firework_1");
            }
        }
        TempRocketData.starNo = this.random.nextInt(RocketKeys.startDrawables.length);
    }

    public void start(int i) {
        this.executorService = Executors.newFixedThreadPool(4);
        LaunchableRockets launchableRockets = this.launchableRocketsOne;
        if (launchableRockets != null) {
            launchableRockets.setExecutor();
        }
        if (this.launchableRocketsTwo != null) {
            this.launchableRocketsOne.setExecutor();
        }
        if (this.explosion != null) {
            int i2 = 0;
            while (true) {
                Explosion[] explosionArr = this.explosion;
                if (i2 >= explosionArr.length) {
                    break;
                }
                if (explosionArr[i2] != null) {
                    explosionArr[i2].setExecutor();
                }
                i2++;
            }
        }
        this.isFirstTime = true;
        setVisibility(VISIBLE);
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.removeAllListeners();
            this.animator.cancel();
        } else {
            ValueAnimator ofInt = ValueAnimator.ofInt(0, 10);
            this.animator = ofInt;
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    if (((Integer) valueAnimator2.getAnimatedValue()).intValue() <= 10) {
                        RocketAnimation.this.invalidate();
                    }
                }
            });
            this.animator.setDuration(WorkRequest.MIN_BACKOFF_MILLIS);
            this.animator.setRepeatCount(-1);
        }
        this.launchableRocketsOne = null;
        this.launchableRocketsTwo = null;
        this.begin = false;
        this.currentX = -1.0f;
        this.currentY = -1.0f;
        this.speed = i;
        setVisibility(VISIBLE);
        invalidate();
    }

    public void stopAnimation() {
        if (this.animator != null) {
            setVisibility(GONE);
            this.animator.removeAllListeners();
            this.animator.cancel();
            this.animator = null;
            int i = 0;
            this.isFirstTime = false;
            setVisibility(VISIBLE);
            this.launchableRocketsOne = null;
            this.launchableRocketsTwo = null;
            this.begin = false;
            this.currentX = -1.0f;
            this.currentY = -1.0f;
            if (this.explosion != null) {
                while (true) {
                    Explosion[] explosionArr = this.explosion;
                    if (i >= explosionArr.length) {
                        break;
                    }
                    if (explosionArr[i] != null) {
                        explosionArr[i].stopThread();
                    }
                    i++;
                }
            }
            LaunchableRockets launchableRockets = this.launchableRocketsOne;
            if (launchableRockets != null) {
                launchableRockets.stopThread();
            }
            LaunchableRockets launchableRockets2 = this.launchableRocketsTwo;
            if (launchableRockets2 != null) {
                launchableRockets2.stopThread();
            }
            ExecutorService executorService = this.executorService;
            if (executorService != null) {
                executorService.shutdown();
                this.executorService = null;
            }
            onFinish();
        }
    }
}
