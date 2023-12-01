package pavishka.coloring.book.Manager.ChooseColor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;


public class ScreenBateScrollerView extends ColorSliderView {
    Bitmap backgroundBitmap;
    Canvas backgroundCanvas;

    public ScreenBateScrollerView(Context context) {
        super(context);
    }

    public ScreenBateScrollerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ScreenBateScrollerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override
    protected int init() {
        float[] fArr = new float[3];
        Color.colorToHSV(this.anInt, fArr);
        return Color.HSVToColor((int) (this.aFloat1 * 255.0f), fArr);
    }

    @Override
    protected void vil2(Paint paint) {
        float[] fArr = new float[3];
        Color.colorToHSV(this.anInt, fArr);
        paint.setShader(new LinearGradient(0.0f, 0.0f, getWidth(), getHeight(), Color.HSVToColor(0, fArr), Color.HSVToColor(255, fArr), Shader.TileMode.CLAMP));
    }

    @Override
    protected float chilesh(int i) {
        return Color.alpha(i) / 255.0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = this.backgroundBitmap;
        float f = this.aFloat;
        canvas.drawBitmap(bitmap, f, f, (Paint) null);
        super.onDraw(canvas);
    }


    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        float f = this.aFloat;
        this.backgroundBitmap = Bitmap.createBitmap((int) (i - (2.0f * f)), (int) (i2 - f), Bitmap.Config.ARGB_8888);
        this.backgroundCanvas = new Canvas(this.backgroundBitmap);
    }
}
