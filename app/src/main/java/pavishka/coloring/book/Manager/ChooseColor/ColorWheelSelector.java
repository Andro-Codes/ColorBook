package pavishka.coloring.book.Manager.ChooseColor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.view.ViewCompat;


public class ColorWheelSelector extends View {
    private PointF currentPoint;
    private Paint selectorPaint;
    private float selectorRadiusPx;

    public ColorWheelSelector(Context context) {
        this(context, null);
    }

    public ColorWheelSelector(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ColorWheelSelector(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.selectorRadiusPx = 27.0f;
        this.currentPoint = new PointF();
        Paint paint = new Paint(1);
        this.selectorPaint = paint;
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.selectorPaint.setStyle(Paint.Style.STROKE);
        this.selectorPaint.setStrokeWidth(2.0f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        PointF pointF = this.currentPoint;
        float f = pointF.x;
        float f2 = this.selectorRadiusPx;
        float f3 = pointF.y;
        canvas.drawLine(f - f2, f3, f + f2, f3, this.selectorPaint);
        PointF pointF2 = this.currentPoint;
        float f4 = pointF2.x;
        float f5 = pointF2.y;
        float f6 = this.selectorRadiusPx;
        canvas.drawLine(f4, f5 - f6, f4, f5 + f6, this.selectorPaint);
        PointF pointF3 = this.currentPoint;
        canvas.drawCircle(pointF3.x, pointF3.y, this.selectorRadiusPx * 0.66f, this.selectorPaint);
    }

    public void setCurrentPoint(PointF pointF) {
        this.currentPoint = pointF;
        invalidate();
    }

    public void setSelectorRadiusPx(float f) {
        this.selectorRadiusPx = f;
    }
}
