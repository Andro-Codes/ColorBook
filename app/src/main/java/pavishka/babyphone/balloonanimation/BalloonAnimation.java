package pavishka.babyphone.balloonanimation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.work.WorkRequest;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BalloonAnimation extends View {
    ValueAnimator valueAnimator;
    MyMediaPlayer myMediaPlayer;
    ExecutorService executorService;
    private Context context;
    private Explosion[] explosions;
    private MovingBalloons movingBallonsOne;
    private MovingBalloons movingBallonsTwo;
    private OnAnimationEndListener onAnimationEndListener;
    private int topWidth;
    public boolean begin = false;
    private float currentX = -1.0f;
    private float currentY = -1.0f;
    public boolean isBalloonTouched = false;
    public boolean isFirstTime = false;
    private Random rand = new Random();
    private int spawnDelay = 140;
    private int speed = 1;
    private int timeCounter = 0;
    private int scoreCounter = 0;

    public int movingBallonsOnecount(){
        return movingBallonsOne != null ? movingBallonsOne.deadBalloons : 0;
    }
    public int movingBallonsTwocount(){
        return movingBallonsTwo != null ? movingBallonsTwo.deadBalloons : 0;
    }
    public int movingBallonsoneorTwocount(){
        return scoreCounter;
    }

    public interface OnAnimationEndListener {
        void onExplosion();

        void onFinish();
    }

    public BalloonAnimation(Context context) {
        super(context);
        int i = 0;
        this.context = context;
        if (calculateSize()) {
            this.myMediaPlayer = new MyMediaPlayer(context);
            this.movingBallonsOne = null;
            this.movingBallonsTwo = null;
            this.explosions = new Explosion[5];
            while (true) {
                Explosion[] explosionArr = this.explosions;
                if (i < explosionArr.length) {
                    explosionArr[i] = null;
                    i++;
                } else {
                    ValueAnimator ofInt = ValueAnimator.ofInt(0, 10);
                    this.valueAnimator = ofInt;
                    ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            if (((Integer) valueAnimator.getAnimatedValue()).intValue() <= 10) {
                                BalloonAnimation.this.invalidate();
                            }
                        }
                    });
                    this.valueAnimator.setDuration(WorkRequest.MIN_BACKOFF_MILLIS);
                    this.valueAnimator.setRepeatCount(-1);
                    return;
                }
            }
        }
    }

    public void addOnAnimationEndListener(OnAnimationEndListener onAnimationEndListener) {
        this.onAnimationEndListener = onAnimationEndListener;
    }

    public boolean calculateSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.heightPixels;
        int i2 = displayMetrics.widthPixels;
        if (i == 0 && i2 == 0) {
            return false;
        }
        this.spawnDelay = i / 12;
        Log.d("dsds", "spawnDelay: " + this.spawnDelay);
        TempBalloonData.BALLOON_AGE = i;
        TempBalloonData.SCREEN_WIDTH = i2;
        TempBalloonData.SCREEN_HEIGHT = i;
        Drawable drawable = ContextCompat.getDrawable(this.context, BalloonKeys.ballonPics[0]);
        TempBalloonData.BALLOON_WIDTH = drawable.getIntrinsicWidth();
        TempBalloonData.BALLOON_HEIGHT  = drawable.getIntrinsicHeight();
        Log.e("aaaaabbb",":first"+TempBalloonData.BALLOON_WIDTH);
        Log.e("aaaaabbb",":first"+TempBalloonData.BALLOON_HEIGHT);
        if (TempBalloonData.BALLOON_HEIGHT <= TempBalloonData.BALLOON_WIDTH) {
            float f =(float) TempBalloonData.BALLOON_HEIGHT / (float)TempBalloonData.BALLOON_WIDTH;
            Log.e("aaaaabbb",":BALLOON_HEIGHT : if:"+f);
            int i4 = i - (i / 3);
            TempBalloonData.BALLOON_WIDTH = i4;
            TempBalloonData.BALLOON_HEIGHT = (int) (i4 * f);
            Log.e("aaaaabbb",":last"+TempBalloonData.BALLOON_WIDTH);
            Log.e("aaaaabbb",":last"+TempBalloonData.BALLOON_HEIGHT);

        } else {
            float f2 = (float)TempBalloonData.BALLOON_WIDTH / (float)TempBalloonData.BALLOON_HEIGHT;
            Log.e("aaaaabbb",":BALLOON_HEIGHT : else:"+f2);
            int i5 = i2 - (i2 / 3);
            TempBalloonData.BALLOON_HEIGHT = i5;
            TempBalloonData.BALLOON_WIDTH = (int) (i5 * f2);
            Log.e("aaaaabbb",":last"+TempBalloonData.BALLOON_WIDTH);
            Log.e("aaaaabbb",":last"+TempBalloonData.BALLOON_HEIGHT);
        }
        Drawable drawable2 = ContextCompat.getDrawable(this.context, BalloonKeys.animals[0]);
        TempBalloonData.ANIMAL_WIDTH = drawable2.getIntrinsicWidth();
        int intrinsicHeight2 = drawable2.getIntrinsicHeight();
        TempBalloonData.ANIMAL_HEIGHT = intrinsicHeight2;
        int i6 = TempBalloonData.ANIMAL_WIDTH;
        Log.e("aaaaabbb",":first"+TempBalloonData.ANIMAL_WIDTH);
        Log.e("aaaaabbb",":first"+TempBalloonData.ANIMAL_HEIGHT);
        if (intrinsicHeight2 <= i6) {
            float f3 =(float)( (float)TempBalloonData.ANIMAL_HEIGHT /(float) TempBalloonData.ANIMAL_WIDTH);
            Log.e("aaaaabbb",":ANIMAL_HEIGHT : if:"+f3);
            int i7 = i - (i / 3);
            TempBalloonData.ANIMAL_WIDTH = i7;
            TempBalloonData.ANIMAL_HEIGHT = (int) (i7 * f3);
        } else {
            float f4 = (float)i6 / (float)TempBalloonData.ANIMAL_HEIGHT;
            Log.e("aaaaabbb",":ANIMAL_HEIGHT : else:"+f4);
            int i8 = i2 - (i2 / 3);
            TempBalloonData.ANIMAL_HEIGHT = i8;
            TempBalloonData.ANIMAL_WIDTH = (int) (i8 * f4);
        }

        return true;
    }

    public void doAction() {
        if (this.begin) {
            this.isBalloonTouched = true;
            MovingBalloons movingBalloons = this.movingBallonsOne;
            if (movingBalloons != null && movingBalloons.isAlive()) {
                Balloon[] balloons = this.movingBallonsOne.getBalloons();
                int length = balloons.length - 1;
                while (length > -1) {
                    if (balloons[length].isAlive() && balloons[length].isBalloonTouched(this.currentX, this.currentY)) {
                        int i = 0;
                        while (true) {
                            Explosion[] explosionArr = this.explosions;
                            if (i < explosionArr.length) {
                                if (explosionArr[i] != null) {
                                    if (explosionArr[i] != null && explosionArr[i].isDead()) {
                                        this.explosions[i].reset(this.currentX, this.currentY);
                                        playSound();
                                        break;
                                    }
                                    i++;
                                } else {
                                    explosionArr[i] = new Explosion(30, (int) this.currentX, (int) this.currentY);
                                    playSound();
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    } else {
                        length--;
                    }
                }
            }
            MovingBalloons movingBalloons2 = this.movingBallonsTwo;
            if (movingBalloons2 != null && movingBalloons2.isAlive()) {
                Balloon[] balloons2 = this.movingBallonsTwo.getBalloons();
                int length2 = balloons2.length - 1;
                while (length2 > -1) {
                    if (balloons2[length2].isAlive() && balloons2[length2].isBalloonTouched(this.currentX, this.currentY)) {
                        int i2 = 0;
                        while (true) {
                            Explosion[] explosionArr2 = this.explosions;
                            if (i2 < explosionArr2.length) {
                                if (explosionArr2[i2] != null) {
                                    if (explosionArr2[i2] != null && explosionArr2[i2].isDead()) {
                                        this.explosions[i2].reset(this.currentX, this.currentY);
                                        playSound();
                                        break;
                                    }
                                    i2++;
                                } else {
                                    explosionArr2[i2] = new Explosion(30, (int) this.currentX, (int) this.currentY);
                                    playSound();
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    } else {
                        length2--;
                    }
                }
            }
        }
        this.isBalloonTouched = false;
    }

    public boolean isItReady() {
        MovingBalloons movingBalloons = this.movingBallonsOne;
        return movingBalloons == null || movingBalloons.isDead();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isFirstTime) {
            startBalloonAnimations();
        }
        if (this.begin) {
            MovingBalloons movingBalloons = this.movingBallonsOne;
            if (movingBalloons != null && movingBalloons.isAlive()) {
                this.movingBallonsOne.floatBalloons(canvas);
            }
            MovingBalloons movingBalloons2 = this.movingBallonsTwo;
            if (movingBalloons2 != null && movingBalloons2.isAlive()) {
                this.movingBallonsTwo.floatBalloons(canvas);
            }
            int i = 0;
            if (this.timeCounter >= this.spawnDelay) {
                MovingBalloons movingBalloons3 = this.movingBallonsTwo;
                if (movingBalloons3 == null) {
                    this.timeCounter = 0;
                    this.movingBallonsTwo = new MovingBalloons(this.context, 3, this.topWidth, this.speed);
                } else if (movingBalloons3 == null || (!movingBalloons3.isDead() && !this.movingBallonsTwo.isItFirstTime())) {
                    MovingBalloons movingBalloons4 = this.movingBallonsOne;
                    if (movingBalloons4 != null && (movingBalloons4.isDead() || this.movingBallonsOne.isItFirstTime())) {
                        this.timeCounter = 0;
                        Log.d("dsds", "1 getting called");
                        this.movingBallonsOne.reset();
                    }
                } else {
                    this.timeCounter = 0;
                    Log.d("dsds", "1 getting called");
                    this.movingBallonsTwo.reset();
                }
            }
            while (true) {
                Explosion[] explosionArr = this.explosions;
                if (i >= explosionArr.length) {
                    break;
                }
                if (explosionArr[i] != null && explosionArr[i].isAlive()) {
                    if (this.explosions[i].isItFirsttime()) {
                        onExplosion();
                    }
                    this.explosions[i].startExplosion(canvas);
                }
                i++;
            }
        }
        int i2 = this.timeCounter;
        if (i2 <= this.spawnDelay) {
            this.timeCounter = i2 + 1;
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
        this.topWidth = i;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.isBalloonTouched) {
            return true;
        }
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

    public void playSound() {
        this.myMediaPlayer.playCustomSound(BalloonKeys.popSounds[this.rand.nextInt(2)]);
    }

    public void runThread() {
        try {
            this.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    BalloonAnimation.this.doAction();
                }
            });
        } catch (Exception unused) {
            Log.e("sddd", "");
        }
    }

    public void start(int i) {
        this.isFirstTime = true;
        this.executorService = Executors.newFixedThreadPool(4);
        MovingBalloons movingBalloons = this.movingBallonsOne;
        if (movingBalloons != null) {
            movingBalloons.setExecutor();
        }
        MovingBalloons movingBalloons2 = this.movingBallonsTwo;
        if (movingBalloons2 != null) {
            movingBalloons2.setExecutor();
        }
        if (this.explosions != null) {
            int i2 = 0;
            while (true) {
                Explosion[] explosionArr = this.explosions;
                if (i2 >= explosionArr.length) {
                    break;
                }
                if (explosionArr[i2] != null) {
                    explosionArr[i2].setExecutor();
                }
                i2++;
            }
        }
        ValueAnimator valueAnimator = this.valueAnimator;
        if (valueAnimator != null) {
            valueAnimator.removeAllListeners();
            this.valueAnimator.cancel();
        } else {
            ValueAnimator ofInt = ValueAnimator.ofInt(0, 10);
            this.valueAnimator = ofInt;
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    if (((Integer) valueAnimator2.getAnimatedValue()).intValue() <= 10) {
                        BalloonAnimation.this.invalidate();
                    }
                }
            });
            this.valueAnimator.setDuration(WorkRequest.MIN_BACKOFF_MILLIS);
            this.valueAnimator.setRepeatCount(-1);
        }
        this.begin = false;
        this.currentX = -1.0f;
        this.currentY = -1.0f;
        this.movingBallonsOne = null;
        this.movingBallonsTwo = null;
        this.speed = i;
        invalidate();
    }

    public void startBalloonAnimations() {
        MovingBalloons movingBalloons = this.movingBallonsOne;
        if (movingBalloons == null || movingBalloons.isDead()) {
            this.movingBallonsOne = new MovingBalloons(this.context, 3, this.topWidth, this.speed);
            this.begin = true;
        } else {
            this.movingBallonsOne.reset();
        }
        this.isFirstTime = false;
        this.valueAnimator.start();
    }

    public void stopAnimation() {
        int i = 0;
        this.begin = false;
        if (this.valueAnimator != null) {
            setVisibility(GONE);
            this.valueAnimator.removeAllListeners();
            this.valueAnimator.cancel();
            this.valueAnimator = null;
            this.isFirstTime = false;
            setVisibility(VISIBLE);
            this.movingBallonsOne = null;
            this.movingBallonsTwo = null;
            this.currentX = -1.0f;
            this.currentY = -1.0f;
            if (this.explosions != null) {
                while (true) {
                    Explosion[] explosionArr = this.explosions;
                    if (i >= explosionArr.length) {
                        break;
                    }
                    if (explosionArr[i] != null) {
                        explosionArr[i].stopThread();
                    }
                    i++;
                }
            }
            MovingBalloons movingBalloons = this.movingBallonsOne;
            if (movingBalloons != null) {
                movingBalloons.stopThread();
            }
            MovingBalloons movingBalloons2 = this.movingBallonsTwo;
            if (movingBalloons2 != null) {
                movingBalloons2.stopThread();
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
