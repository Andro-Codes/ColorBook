package pavishka.coloring.book.Activities.Screens;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import pavishka.coloring.book.Activities.OtherScreens.ScreenAkarActivity;
import pavishka.coloring.book.Activities.OtherScreens.ScreenRamkadaActivity;
import pavishka.coloring.book.Activities.OtherScreens.ScreenMovableActivity;
import pavishka.coloring.book.Activities.OtherScreens.ScreenOwnProducts;
import pavishka.coloring.book.R;


public class ScreenPhotoAdapter extends RecyclerView.Adapter<ScreenPhotoAdapter.ViewHolder> {
    ScreenOwnAuidioPlayer myMediaPlayer;
    Bitmap bitmap;
    File file;
    private boolean clickable = true;
    private ArrayList<String> list;
    private Context mCtx;

    public ScreenPhotoAdapter(Context context, ArrayList<String> arrayList) {
        this.mCtx = context;
        this.list = arrayList;
        this.myMediaPlayer = new ScreenOwnAuidioPlayer(context);
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    public void animateClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this.mCtx, R.anim.pop_in_low));
    }


    public void disableClick() {
        this.clickable = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ScreenPhotoAdapter.this.clickable = true;
            }
        }, 1000L);
    }

    public Bitmap getPIctureBitmap(String str) {
        new File(str);
        return BitmapFactory.decodeFile(str);
    }


    public void finishActivityOnItemSelect() {
        ScreenManualColorDrawingActivity drawActivity = ScreenManualColorDrawingActivity.drawActivity;
        if (drawActivity != null) {
            drawActivity.finish();
        }
        ((ScreenGridColoringBook) this.mCtx).finish();
        int i = ScreenConstable.anInt5;
        if (i == 17) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreensuperheroActivity.class));
        } else if (i == 16) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenFifaActivity.class));
        } else if (i == 0) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenFourLagActivity.class));
        } else if (i == 1) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenRamkadaActivity.class));
        } else if (i == 2) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenAkarActivity.class));
        } else if (i == 3) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenEatActivity.class));
        } else if (i == 4) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenJuiceActivity.class));
        } else if (i == 5) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenMovableActivity.class));
        } else if (i == 6) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenABCActivity.class));
        } else if (i == 7) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenDailingActivity.class));
        } else if (i == 8) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenManualTop.class));
        } else if (i == 9) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenDashBoardActivity.class));
        } else if (i == 10) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenDecemberActivity.class));
        } else if (i == 11) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenPhotoFrameActivity.class));
        } else if (i == 13) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenOwnProducts.class));
        } else if (i == 12) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenTapkaJodoActivity.class));
        } else if (i == 14) {
            this.mCtx.startActivity(new Intent(this.mCtx, ScreenMapsFindActivity.class));
        }
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }


    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        try {
            if (i == 0) {
                this.file = new File(this.list.get(i));
                Glide.with(mCtx)
                        .load(R.drawable.ic_pencil)
                        .into(viewHolder.b);
            } else if (i >= ScreenConstable.selected_bitmapIds.length) {
                this.file = new File(this.list.get(i));
                Glide.with(mCtx)
                        .load(this.file)
                        .into(viewHolder.b);
            } else {
                Glide.with(mCtx)
                        .load(Integer.parseInt(this.list.get(i)))
                        .into(viewHolder.b);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        viewHolder.a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ScreenPhotoAdapter.this.clickable) {
                    ScreenPhotoAdapter.this.disableClick();
                    ScreenPhotoAdapter.this.animateClick(view);
                    ScreenPhotoAdapter.this.myMediaPlayer.playClickSound();
                    int i2 = i;
                    ScreenGridColoringBook.pos = i;
                    try {
                        if (i2 < ScreenConstable.selected_bitmapIds.length) {
                            ScreenPhotoAdapter imageAdapter = ScreenPhotoAdapter.this;
                            imageAdapter.bitmap = BitmapFactory.decodeResource(imageAdapter.mCtx.getResources(), Integer.parseInt((String) ScreenPhotoAdapter.this.list.get(i)));
                        } else {
                            ScreenPhotoAdapter imageAdapter2 = ScreenPhotoAdapter.this;
                            imageAdapter2.bitmap = imageAdapter2.getPIctureBitmap((String) imageAdapter2.list.get(i));
                        }
                    } catch (NumberFormatException e2) {
                        e2.printStackTrace();
                    }
                    int i3 = ScreenConstable.anInt5;
                    if (i3 == 0) {
                        int i4 = i;
                        if (i4 == 0) {
                            ScreenPhotoAdapter.this.finishActivityOnItemSelect();
                        } else {
                            ScreenPhotoAdapter imageAdapter3 = ScreenPhotoAdapter.this;
                            ScreenConstable.selectedImageFromBitmap = imageAdapter3.bitmap;
                            ScreenConstable.fromGridActivityColoringBook = true;
                            ScreenConstable.selectedTool = 0;
                            imageAdapter3.finishActivityOnItemSelect();
                        }
                    } else if (i3 == 5) {
                        int i5 = i;
                        if (i5 == 0) {
                            ScreenPhotoAdapter.this.finishActivityOnItemSelect();
                        } else {
                            ScreenPhotoAdapter imageAdapter4 = ScreenPhotoAdapter.this;
                            ScreenConstable.selectedImageFromBitmap = imageAdapter4.bitmap;
                            ScreenConstable.fromGridActivityColoringBook = true;
                            ScreenConstable.selectedTool = 0;
                            imageAdapter4.finishActivityOnItemSelect();
                        }
                    } else if (i3 == 3) {
                        int i6 = i;
                        if (i6 == 0) {
                            ScreenPhotoAdapter.this.finishActivityOnItemSelect();
                        } else {
                            ScreenPhotoAdapter imageAdapter5 = ScreenPhotoAdapter.this;
                            ScreenConstable.selectedImageFromBitmap = imageAdapter5.bitmap;
                            ScreenConstable.fromGridActivityColoringBook = true;
                            ScreenConstable.selectedTool = 0;
                            imageAdapter5.finishActivityOnItemSelect();
                        }
                    } else if (i3 == 6) {
                        int i7 = i;
                        if (i7 == 0) {
                            ScreenPhotoAdapter.this.finishActivityOnItemSelect();
                        } else {
                            ScreenPhotoAdapter imageAdapter6 = ScreenPhotoAdapter.this;
                            ScreenConstable.selectedImageFromBitmap = imageAdapter6.bitmap;
                            ScreenConstable.fromGridActivityColoringBook = true;
                            ScreenConstable.selectedTool = 0;
                            imageAdapter6.finishActivityOnItemSelect();
                        }
                    } else if (i3 == 2) {
                        int i8 = i;
                        if (i8 == 0) {
                            ScreenPhotoAdapter.this.finishActivityOnItemSelect();
                        } else {
                            ScreenPhotoAdapter imageAdapter7 = ScreenPhotoAdapter.this;
                            ScreenConstable.selectedImageFromBitmap = imageAdapter7.bitmap;
                            ScreenConstable.fromGridActivityColoringBook = true;
                            ScreenConstable.selectedTool = 0;
                            imageAdapter7.finishActivityOnItemSelect();
                        }
                    } else if (i3 == 4) {
                        int i9 = i;
                        if (i9 == 0) {
                            ScreenPhotoAdapter.this.finishActivityOnItemSelect();
                        } else {
                            ScreenPhotoAdapter imageAdapter8 = ScreenPhotoAdapter.this;
                            ScreenConstable.selectedImageFromBitmap = imageAdapter8.bitmap;
                            ScreenConstable.fromGridActivityColoringBook = true;
                            ScreenConstable.selectedTool = 0;
                            imageAdapter8.finishActivityOnItemSelect();
                        }
                    } else if (i3 == 1) {
                        int i10 = i;
                        if (i10 == 0) {
                            ScreenPhotoAdapter.this.finishActivityOnItemSelect();
                        } else {
                            ScreenPhotoAdapter imageAdapter9 = ScreenPhotoAdapter.this;
                            ScreenConstable.selectedImageFromBitmap = imageAdapter9.bitmap;
                            ScreenConstable.fromGridActivityColoringBook = true;
                            ScreenConstable.selectedTool = 0;
                            imageAdapter9.finishActivityOnItemSelect();
                        }
                    } else if (i3 == 7) {
                        int i11 = i;
                        if (i11 == 0) {
                            ScreenPhotoAdapter.this.finishActivityOnItemSelect();
                        } else {
                            ScreenPhotoAdapter imageAdapter10 = ScreenPhotoAdapter.this;
                            ScreenConstable.selectedImageFromBitmap = imageAdapter10.bitmap;
                            ScreenConstable.fromGridActivityColoringBook = true;
                            ScreenConstable.selectedTool = 0;
                            imageAdapter10.finishActivityOnItemSelect();
                        }
                    } else if (i3 == 9) {
                        int i12 = i;
                        if (i12 == 0) {
                            ScreenPhotoAdapter.this.finishActivityOnItemSelect();
                        } else {
                            ScreenPhotoAdapter imageAdapter11 = ScreenPhotoAdapter.this;
                            ScreenConstable.selectedImageFromBitmap = imageAdapter11.bitmap;
                            ScreenConstable.fromGridActivityColoringBook = true;
                            ScreenConstable.selectedTool = 0;
                            imageAdapter11.finishActivityOnItemSelect();
                        }
                    } else if (i3 == 11) {
                        int i13 = i;
                        if (i13 == 0) {
                            ScreenPhotoAdapter.this.finishActivityOnItemSelect();
                        } else {
                            ScreenPhotoAdapter imageAdapter12 = ScreenPhotoAdapter.this;
                            ScreenConstable.selectedImageFromBitmap = imageAdapter12.bitmap;
                            ScreenConstable.fromGridActivityColoringBook = true;
                            ScreenConstable.selectedTool = 0;
                            imageAdapter12.finishActivityOnItemSelect();
                        }
                    } else if (i3 == 10) {
                        int i14 = i;
                        if (i14 == 0) {
                            ScreenPhotoAdapter.this.finishActivityOnItemSelect();
                        } else {
                            ScreenPhotoAdapter imageAdapter13 = ScreenPhotoAdapter.this;
                            ScreenConstable.selectedImageFromBitmap = imageAdapter13.bitmap;
                            ScreenConstable.fromGridActivityColoringBook = true;
                            ScreenConstable.selectedTool = 0;
                            imageAdapter13.finishActivityOnItemSelect();
                        }
                    } else if (i3 == 12) {
                        if (i == 0) {
                            ScreenPhotoAdapter.this.finishActivityOnItemSelect();
                            return;
                        }
                        ScreenPhotoAdapter imageAdapter14 = ScreenPhotoAdapter.this;
                        ScreenConstable.selectedImageFromBitmap = imageAdapter14.bitmap;
                        ScreenConstable.fromGridActivityColoringBook = true;
                        ScreenConstable.selectedTool = 0;
                        imageAdapter14.finishActivityOnItemSelect();
                    } else if (i3 == 13) {
                        if (i == 0) {
                            ScreenPhotoAdapter.this.finishActivityOnItemSelect();
                            return;
                        }
                        ScreenPhotoAdapter imageAdapter15 = ScreenPhotoAdapter.this;
                        ScreenConstable.selectedImageFromBitmap = imageAdapter15.bitmap;
                        ScreenConstable.fromGridActivityColoringBook = true;
                        ScreenConstable.selectedTool = 0;
                        imageAdapter15.finishActivityOnItemSelect();
                    } else if (i3 != 14) {
                        int i15 = i;
                        if (i15 == 0) {
                            ScreenPhotoAdapter.this.finishActivityOnItemSelect();
                        } else {
                            ScreenPhotoAdapter imageAdapter16 = ScreenPhotoAdapter.this;
                            ScreenConstable.selectedImageFromBitmap = imageAdapter16.bitmap;
                            ScreenConstable.fromGridActivityColoringBook = true;
                            ScreenConstable.selectedTool = 0;
                            imageAdapter16.finishActivityOnItemSelect();
                        }
                    } else if (i == 0) {
                        ScreenPhotoAdapter.this.finishActivityOnItemSelect();
                    } else {
                        ScreenPhotoAdapter imageAdapter17 = ScreenPhotoAdapter.this;
                        ScreenConstable.selectedImageFromBitmap = imageAdapter17.bitmap;
                        ScreenConstable.fromGridActivityColoringBook = true;
                        ScreenConstable.selectedTool = 0;
                        imageAdapter17.finishActivityOnItemSelect();
                    }
                }
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.mCtx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.heightPixels;
        int i3 = displayMetrics.widthPixels;
        View inflate = LayoutInflater.from(this.mCtx).inflate(R.layout.grid_layout_view, (ViewGroup) null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(-1, -1);
        ((ViewGroup.MarginLayoutParams) layoutParams).width = (i3 / 2) - 30;
        ((ViewGroup.MarginLayoutParams) layoutParams).height = (i2 / 3) + 100;
        layoutParams.setMargins(15, 10, 0, 10);
        inflate.setLayoutParams(layoutParams);
        return new ViewHolder(this, inflate);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout a;
        ImageView b;

        public ViewHolder(ScreenPhotoAdapter imageAdapter, View view) {
            super(view);
            this.a = (FrameLayout) view.findViewById(R.id.imageView);
            this.b = (ImageView) view.findViewById(R.id.imageViewInside);
        }
    }
}
