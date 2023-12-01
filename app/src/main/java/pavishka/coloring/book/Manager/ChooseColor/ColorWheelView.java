package pavishka.coloring.book.Manager.ChooseColor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class ColorWheelView extends FrameLayout implements CMYKObservable, SetStater {
    private float centerX;
    private float centerY;
    private int currentColor;
    private PointF currentPoint;
    private CMYKLocatorEmitter emitter;
    private Toucher handler;
    private boolean onlyUpdateOnTouchEventUp;
    private float radius;
    private ColorWheelSelector selector;
    private float selectorRadiusPx;

    public ColorWheelView(Context context) {
        this(context, null);
    }

    public ColorWheelView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ColorWheelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.selectorRadiusPx = 27.0f;
        this.currentPoint = new PointF();
        this.currentColor = -65281;
        this.emitter = new CMYKLocatorEmitter();
        this.handler = new Toucher(this);
        this.selectorRadiusPx = getResources().getDisplayMetrics().density * 9.0f;
        ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        ColorWheelPalette colorWheelPalette = new ColorWheelPalette(context);
        int i2 = (int) this.selectorRadiusPx;
        colorWheelPalette.setPadding(i2, i2, i2, i2);
        addView(colorWheelPalette, layoutParams);
        ViewGroup.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
        ColorWheelSelector colorWheelSelector = new ColorWheelSelector(context);
        this.selector = colorWheelSelector;
        colorWheelSelector.setSelectorRadiusPx(this.selectorRadiusPx);
        addView(this.selector, layoutParams2);
    }

    private int getColorAtPoint(float f, float f2) {
        float f3 = f - this.centerX;
        float f4 = f2 - this.centerY;
        double sqrt = Math.sqrt((f3 * f3) + (f4 * f4));
        float[] fArr = {0.0f, 0.0f, 1.0f};
        fArr[0] = ((float) ((Math.atan2(f4, -f3) / 3.141592653589793d) * 180.0d)) + 180.0f;
        double d = this.radius;
        Double.isNaN(d);
        fArr[1] = Math.max(0.0f, Math.min(1.0f, (float) (sqrt / d)));
        return Color.HSVToColor(fArr);
    }

    private void updateSelector(float f, float f2) {
        float f3 = f - this.centerX;
        float f4 = f2 - this.centerY;
        double sqrt = Math.sqrt((f3 * f3) + (f4 * f4));
        double d = this.radius;
        if (sqrt > d) {
            double d2 = f3;
            Double.isNaN(d);
            Double.isNaN(d2);
            double d3 = d / sqrt;
            f3 = (float) (d2 * d3);
            double d4 = f4;
            Double.isNaN(d);
            Double.isNaN(d4);
            f4 = (float) (d4 * d3);
        }
        PointF pointF = this.currentPoint;
        pointF.x = f3 + this.centerX;
        pointF.y = f4 + this.centerY;
        this.selector.setCurrentPoint(pointF);
    }

    @Override
    public int getColor() {
        return this.emitter.getColor();
    }

    @Override
    protected void onMeasure(int i, int i2) {
        int min = Math.min(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i2));
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(min, MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(min, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        int paddingLeft = (i - getPaddingLeft()) - getPaddingRight();
        int paddingTop = (i2 - getPaddingTop()) - getPaddingBottom();
        float min = (Math.min(paddingLeft, paddingTop) * 0.5f) - this.selectorRadiusPx;
        this.radius = min;
        if (min >= 0.0f) {
            this.centerX = paddingLeft * 0.5f;
            this.centerY = paddingTop * 0.5f;
            setColor(this.currentColor, false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                update(motionEvent);
                return true;
            } else if (actionMasked != 2) {
                return super.onTouchEvent(motionEvent);
            }
        }
        this.handler.a(motionEvent);
        return true;
    }

    public void setColor(int i, boolean z) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        float f = fArr[1] * this.radius;
        double d = fArr[0] / 180.0f;
        Double.isNaN(d);
        float f2 = (float) (d * 3.141592653589793d);
        double d2 = f;
        double d3 = f2;
        double cos = Math.cos(d3);
        Double.isNaN(d2);
        double d4 = d2 * cos;
        double d5 = this.centerX;
        Double.isNaN(d5);
        float f3 = (float) (d4 + d5);
        double d6 = -f;
        double sin = Math.sin(d3);
        Double.isNaN(d6);
        double d7 = d6 * sin;
        double d8 = this.centerY;
        Double.isNaN(d8);
        updateSelector(f3, (float) (d7 + d8));
        this.currentColor = i;
        if (!this.onlyUpdateOnTouchEventUp) {
            this.emitter.nimas(i, false, z);
        }
    }

    public void setOnlyUpdateOnTouchEventUp(boolean z) {
        this.onlyUpdateOnTouchEventUp = z;
    }

    @Override
    public void subscribe(CMYKObserver colorObserver) {
        this.emitter.subscribe(colorObserver);
    }

    @Override
    public void unsubscribe(CMYKObserver colorObserver) {
        this.emitter.unsubscribe(colorObserver);
    }

    @Override
    public void update(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        boolean z = motionEvent.getActionMasked() == 1;
        if (!this.onlyUpdateOnTouchEventUp || z) {
            this.emitter.nimas(getColorAtPoint(x, y), true, z);
        }
        updateSelector(x, y);
    }
}
