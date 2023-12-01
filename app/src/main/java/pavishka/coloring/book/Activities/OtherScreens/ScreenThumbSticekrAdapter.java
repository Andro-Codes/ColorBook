package pavishka.coloring.book.Activities.OtherScreens;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pavishka.coloring.book.Manager.Chart.ScreenChartDrawActivity;
import pavishka.coloring.book.Manager.PhotoEditor.NewImageActivity;
import pavishka.coloring.book.R;
import pavishka.coloring.book.Activities.Screens.ScreenManualColorDrawingActivity;
import pavishka.coloring.book.Activities.Screens.ScreenOwnAuidioPlayer;


public class ScreenThumbSticekrAdapter extends RecyclerView.Adapter<ScreenThumbSticekrAdapter.StickerViewHolder> {
    int anInt;
    ScreenOwnAuidioPlayer myMediaPlayer;
    private int activityCode;
    private ArrayList<Integer> list;
    private Context mCtx;

    public ScreenThumbSticekrAdapter(Context context, ArrayList<Integer> arrayList, int i) {
        this.mCtx = context;
        this.list = arrayList;
        this.activityCode = i;
        this.myMediaPlayer = new ScreenOwnAuidioPlayer(context);
        calculateSize();
    }

    private void calculateSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.mCtx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        this.anInt = i - (i / 9);
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void onBindViewHolder(StickerViewHolder stickerViewHolder, @SuppressLint("RecyclerView") final int i) {
        stickerViewHolder.b.setImageResource(this.list.get(i).intValue());
        stickerViewHolder.b.setColorFilter(this.mCtx.getResources().getColor(R.color.white));
        stickerViewHolder.a.setImageResource(this.list.get(i).intValue());
        stickerViewHolder.a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenThumbSticekrAdapter.this.myMediaPlayer.playSound(R.raw.click);
                if (ScreenThumbSticekrAdapter.this.activityCode == 0) {
                    ((ScreenManualColorDrawingActivity) ScreenThumbSticekrAdapter.this.mCtx).addStickerToCanvas(((Integer) ScreenThumbSticekrAdapter.this.list.get(i)).intValue());
                } else if (ScreenThumbSticekrAdapter.this.activityCode == 1) {
                    ((NewImageActivity) ScreenThumbSticekrAdapter.this.mCtx).addStickerToCanvas(((Integer) ScreenThumbSticekrAdapter.this.list.get(i)).intValue());
                } else if (ScreenThumbSticekrAdapter.this.activityCode == 2) {
                    ((ScreenChartDrawActivity) ScreenThumbSticekrAdapter.this.mCtx).addStickerToCanvas(((Integer) ScreenThumbSticekrAdapter.this.list.get(i)).intValue());
                }
            }
        });
    }

    @Override
    public StickerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(this.mCtx).inflate(R.layout.sticker_list_item, (ViewGroup) null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(-1, -1);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = Math.round(this.anInt / 3.2f);
        ((ViewGroup.MarginLayoutParams) layoutParams).width = Math.round(this.anInt / 3.2f);
        inflate.setLayoutParams(layoutParams);
        return new StickerViewHolder(inflate);
    }


    public static class StickerViewHolder extends RecyclerView.ViewHolder {
        ImageView a;
        ImageView b;

        public StickerViewHolder(View view) {
            super(view);
            this.a = (ImageView) view.findViewById(R.id.icon);
            this.b = (ImageView) view.findViewById(R.id.bg_icon);
        }
    }
}
