package pavishka.coloring.book.Manager.ChooseColor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;


public class BrightnessSliderView extends ColorSliderView {
    public BrightnessSliderView(Context context) {
        super(context);
    }

    public BrightnessSliderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BrightnessSliderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override
    protected int init() {
        float[] fArr = new float[3];
        Color.colorToHSV(this.anInt, fArr);
        fArr[2] = this.aFloat1;
        return Color.HSVToColor(fArr);
    }

    @Override
    protected void vil2(Paint paint) {
        float[] fArr = new float[3];
        Color.colorToHSV(this.anInt, fArr);
        fArr[2] = 0.0f;
        int HSVToColor = Color.HSVToColor(fArr);
        fArr[2] = 1.0f;
        paint.setShader(new LinearGradient(0.0f, 0.0f, getWidth(), getHeight(), HSVToColor, Color.HSVToColor(fArr), Shader.TileMode.CLAMP));
    }

    @Override
    protected float chilesh(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        return fArr[2];
    }
}
