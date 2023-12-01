package pavishka.babyphone.balloonanimation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;


public class TempBalloonData {
    public static int ANIMAL_HEIGHT = 0;
    public static int ANIMAL_WIDTH = 0;
    public static int BALLOON_AGE = 0;
    public static int BALLOON_HEIGHT = 0;
    public static int BALLOON_WIDTH = 0;
    public static String OBJECT = "";
    public static int SCREEN_HEIGHT = 0;
    public static int SCREEN_WIDTH = 0;

    public static Bitmap getCombinedBitmap(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, new Matrix(), null);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, (Paint) null);
        return createBitmap;
    }
}
