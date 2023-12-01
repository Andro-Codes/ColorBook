package pavishka.coloring.book.Manager.ChooseColor;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.ViewCompat;


public abstract class ColorSliderView extends View implements CMYKObservable, SetStater {
    protected int anInt;
    protected float aFloat;
    protected float aFloat1;
    private CMYKObserver bindObserver;
    private Paint borderPaint;
    private CMYKObservable boundObservable;
    private Paint colorPaint;
    private Path currentSelectorPath;
    private CMYKLocatorEmitter emitter;
    private Toucher handler;
    private boolean onlyUpdateOnTouchEventUp;
    private Paint selectorPaint;
    private Path selectorPath;

    public ColorSliderView(Context context) {
        this(context, null);
    }

    public ColorSliderView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ColorSliderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.anInt = -1;
        this.currentSelectorPath = new Path();
        this.aFloat1 = 1.0f;
        this.emitter = new CMYKLocatorEmitter();
        this.handler = new Toucher(this);
        this.bindObserver = new CMYKObserver() {
            @Override
            public void onColor(int i2, boolean z, boolean z2) {
                ColorSliderView.this.dean(i2, z, z2);
            }
        };
        this.colorPaint = new Paint(1);
        Paint paint = new Paint(1);
        this.borderPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(0.0f);
        this.borderPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        Paint paint2 = new Paint(1);
        this.selectorPaint = paint2;
        paint2.setColor(ViewCompat.MEASURED_STATE_MASK);
        Path path = new Path();
        this.selectorPath = path;
        path.setFillType(Path.FillType.WINDING);
    }

    protected abstract int init();

    protected abstract void vil2(Paint paint);

    protected abstract float chilesh(int i);

    private void updateValue(float f) {
        float f2 = this.aFloat;
        float width = getWidth() - this.aFloat;
        if (f < f2) {
            f = f2;
        }
        if (f > width) {
            f = width;
        }
        this.aFloat1 = (f - f2) / (width - f2);
        invalidate();
    }

    public void bind(CMYKObservable colorObservable) {
        if (colorObservable != null) {
            colorObservable.subscribe(this.bindObserver);
            dean(colorObservable.getColor(), true, true);
        }
        this.boundObservable = colorObservable;
    }

    void dean(int i, boolean z, boolean z2) {
        this.anInt = i;
        vil2(this.colorPaint);
        if (!z) {
            this.aFloat1 = chilesh(i);
        } else {
            i = init();
        }
        if (!this.onlyUpdateOnTouchEventUp) {
            this.emitter.nimas(i, z, z2);
        } else if (z2) {
            this.emitter.nimas(i, z, true);
        }
        invalidate();
    }

    @Override
    public int getColor() {
        return this.emitter.getColor();
    }


    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        vil2(this.colorPaint);
        this.selectorPath.reset();
        this.aFloat = i2 * 0.25f;
        this.selectorPath.moveTo(0.0f, 0.0f);
        this.selectorPath.lineTo(this.aFloat * 2.0f, 0.0f);
        Path path = this.selectorPath;
        float f = this.aFloat;
        path.lineTo(f, f);
        this.selectorPath.close();
    }

    public void setOnlyUpdateOnTouchEventUp(boolean z) {
        this.onlyUpdateOnTouchEventUp = z;
    }

    @Override
    public void subscribe(CMYKObserver colorObserver) {
        this.emitter.subscribe(colorObserver);
    }

    public void unbind() {
        CMYKObservable colorObservable = this.boundObservable;
        if (colorObservable != null) {
            colorObservable.unsubscribe(this.bindObserver);
            this.boundObservable = null;
        }
    }

    @Override
    public void unsubscribe(CMYKObserver colorObserver) {
        this.emitter.unsubscribe(colorObserver);
    }

    @Override
    public void update(MotionEvent motionEvent) {
        updateValue(motionEvent.getX());
        boolean z = motionEvent.getActionMasked() == 1;
        if (!this.onlyUpdateOnTouchEventUp || z) {
            this.emitter.nimas(init(), true, z);
        }
    }
}
