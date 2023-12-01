package pavishka.coloring.book.Manager.PhotoEditor;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import pavishka.coloring.book.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;


public class ScreenChoosePhotoActivity extends Activity {
    public static final String INTENT_ASPECT_RATIO_X = "aspect_ratio_x";
    public static final String INTENT_ASPECT_RATIO_Y = "aspect_ratio_Y";
    public static final String INTENT_BITMAP_MAX_HEIGHT = "max_height";
    public static final String INTENT_BITMAP_MAX_WIDTH = "max_width";
    public static final String INTENT_IMAGE_COMPRESSION_QUALITY = "compression_quality";
    public static final String INTENT_IMAGE_PICKER_OPTION = "image_picker_option";
    public static final String INTENT_LOCK_ASPECT_RATIO = "lock_aspect_ratio";
    public static final String INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT = "set_bitmap_max_width_height";
    private static final String TAG = "ImagePickerActivity";
    public static String fileName;
    static int anInt;
    static int anInt1;
    private int aspectRatioX = 16;
    private int aspectRatioY = 9;
    private int imageCompression = 80;
    private int bitmapMaxHeight = 1000;
    private int bitmapMaxWidth = 1000;
    private boolean lockAspectRatio = false;
    private boolean setBitmapMaxWidthHeight = false;

    public static void clearCache(Context context) {
        File file = new File(context.getExternalCacheDir(), "camera");
        if (file.exists() && file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                file2.delete();
            }
        }
    }

    private static String queryName(ContentResolver contentResolver, Uri uri) {
        Cursor query = contentResolver.query(uri, null, null, null, null);
        if (query == null) {
            return "";
        }
        int columnIndex = query.getColumnIndex("_display_name");
        query.moveToFirst();
        String string = query.getString(columnIndex);
        query.close();
        return string;
    }

    public static void showImagePickerOptions(Context context, final PickerOptionListener pickerOptionListener) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        anInt = displayMetrics.heightPixels;
        int i = displayMetrics.widthPixels;
        anInt1 = i;
        int i2 = i - (i / 9);
        anInt1 = i2;
        anInt = i2 - (i2 / 3);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        int i3 = anInt;
        layoutParams.height = i3;
        layoutParams.width = anInt1;
        layoutParams.setMargins(0, 0, 0, i3 / 10);
        layoutParams.gravity = 17;
        int i4 = Build.VERSION.SDK_INT >= 19 ? 5894 : 0;
        try {
            final Dialog dialog = new Dialog(context, R.style.AlertDialogCustom);
            dialog.getWindow().setFlags(8, 8);
            dialog.getWindow().getDecorView().setSystemUiVisibility(i4);
            dialog.setContentView(R.layout.dialog_image_picker);
            dialog.setCancelable(false);
            ((ImageView) dialog.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    pickerOptionListener.onCancelSelected();
                }
            });
            ((ImageView) dialog.findViewById(R.id.camera)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    pickerOptionListener.onTakeCameraSelected();
                }
            });
            ((ImageView) dialog.findViewById(R.id.gallery)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    pickerOptionListener.onChooseGallerySelected();
                }
            });
            dialog.show();
            dialog.getWindow().clearFlags(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chooseImageFromGallery() {
        Dexter.withActivity(this).withPermissions("android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }

            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    if (intent.resolveActivity(ScreenChoosePhotoActivity.this.getPackageManager()) != null) {
                        ScreenChoosePhotoActivity.this.startActivityForResult(intent, 1);
                    }
                }
            }
        }).check();
    }

    private void cropImage(Uri uri) {
        Uri fromFile = Uri.fromFile(new File(getCacheDir(), queryName(getContentResolver(), uri)));
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(this.imageCompression);
        options.setToolbarColor(ContextCompat.getColor(this, R.color.ucropcolor));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.ucropcolor));

        if (this.lockAspectRatio) {
            options.withAspectRatio(this.aspectRatioX, this.aspectRatioY);
        }
        if (this.setBitmapMaxWidthHeight) {
            options.withMaxResultSize(this.bitmapMaxWidth, this.bitmapMaxHeight);
        }
        UCrop.of(uri, fromFile).withOptions(options).start(this);
    }

    public Uri getCacheImagePath(String str) {
        File file = new File(getExternalCacheDir(), "camera");
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(file, str);
        return FileProvider.getUriForFile(this, getPackageName() + ".provider", file2);
    }

    private void handleUCropResult(Intent intent) {
        if (intent == null) {
            setResultCancelled();
        } else {
            setResultOk(UCrop.getOutput(intent));
        }
    }

    private void setResultCancelled() {
        setResult(0, new Intent());
        finish();
    }

    private void setResultOk(Uri uri) {
        Intent intent = new Intent();
        intent.putExtra("path", uri);
        setResult(-1, intent);
        finish();
    }

    private void takeCameraImage() {
        Dexter.withActivity(this).withPermissions("android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }

            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    ScreenChoosePhotoActivity.fileName = System.currentTimeMillis() + ".jpg";
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra("output", ScreenChoosePhotoActivity.this.getCacheImagePath(ScreenChoosePhotoActivity.fileName));
                    if (intent.resolveActivity(ScreenChoosePhotoActivity.this.getPackageManager()) != null) {
                        ScreenChoosePhotoActivity.this.startActivityForResult(intent, 0);
                    }
                }
            }
        }).check();
    }

    @Override
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i != 0) {
            if (i != 1) {
                if (i != 69) {
                    if (i != 96) {
                        setResultCancelled();
                        return;
                    }
                    Throwable error = UCrop.getError(intent);
                    String str = TAG;
                    Log.e(str, "Crop error: " + error);
                    setResultCancelled();
                } else if (i2 == -1) {
                    handleUCropResult(intent);
                } else {
                    setResultCancelled();
                }
            } else if (i2 == -1) {
                cropImage(intent.getData());
            } else {
                setResultCancelled();
            }
        } else if (i2 == -1) {
            cropImage(getCacheImagePath(fileName));
        } else {
            setResultCancelled();
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_image_picker);
        Intent intent = getIntent();
        if (intent == null) {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            return;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        anInt = displayMetrics.heightPixels;
        anInt1 = displayMetrics.widthPixels;
        this.aspectRatioX = intent.getIntExtra(INTENT_ASPECT_RATIO_X, this.aspectRatioX);
        this.aspectRatioY = intent.getIntExtra(INTENT_ASPECT_RATIO_Y, this.aspectRatioY);
        this.imageCompression = intent.getIntExtra(INTENT_IMAGE_COMPRESSION_QUALITY, this.imageCompression);
        this.lockAspectRatio = intent.getBooleanExtra(INTENT_LOCK_ASPECT_RATIO, false);
        this.setBitmapMaxWidthHeight = intent.getBooleanExtra(INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, false);
        this.bitmapMaxWidth = intent.getIntExtra(INTENT_BITMAP_MAX_WIDTH, this.bitmapMaxWidth);
        this.bitmapMaxHeight = intent.getIntExtra(INTENT_BITMAP_MAX_HEIGHT, this.bitmapMaxHeight);
        if (intent.getIntExtra(INTENT_IMAGE_PICKER_OPTION, -1) == 0) {
            takeCameraImage();
        } else {
            chooseImageFromGallery();
        }
    }


    public interface PickerOptionListener {
        void onCancelSelected();

        void onChooseGallerySelected();

        void onTakeCameraSelected();
    }
}
