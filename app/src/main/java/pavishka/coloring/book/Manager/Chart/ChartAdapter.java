package pavishka.coloring.book.Manager.Chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import pavishka.coloring.book.Activities.Screens.ScreenConstable;
import pavishka.coloring.book.Activities.Screens.ScreenOwnAuidioPlayer;
import pavishka.coloring.book.R;

import java.io.File;
import java.util.ArrayList;


public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.GraphViewHolder> {
    Bitmap bitmap;
    ScreenOwnAuidioPlayer myMediaPlayer;
    private ArrayList<String> list;
    private Context mCtx;

    public ChartAdapter(Context context, ArrayList<String> arrayList) {
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

    public Bitmap getPicture(String str) {
        return BitmapFactory.decodeFile(new File(str).getAbsolutePath());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }


    public void onBindViewHolder(GraphViewHolder graphViewHolder, @SuppressLint("RecyclerView") final int i) {
        if (i < ScreenConstable.selected_bitmapIds.length) {
            graphViewHolder.b.setImageResource(Integer.parseInt(this.list.get(i)));
        } else {
            graphViewHolder.b.setImageBitmap(getPicture(this.list.get(i)));
        }
        graphViewHolder.a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateClick(view);
                ScreenChartBluePrintGridActivity.pos = i;
                myMediaPlayer.playClickSound();
                try {
                    if (i < ScreenConstable.selected_bitmapIds.length) {
                        ChartAdapter graphAdapter = ChartAdapter.this;
                        graphAdapter.bitmap = BitmapFactory.decodeResource(graphAdapter.mCtx.getResources(), Integer.parseInt((String) list.get(i)));
                    } else {
                        ChartAdapter graphAdapter2 = ChartAdapter.this;
                        graphAdapter2.bitmap = graphAdapter2.getPicture((String) graphAdapter2.list.get(i));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                ChartAdapter graphAdapter3 = ChartAdapter.this;
                ScreenConstable.selectedImageFromBitmap = graphAdapter3.bitmap;
                ((ScreenChartBluePrintGridActivity) graphAdapter3.mCtx).finish();
                mCtx.startActivity(new Intent(mCtx, ScreenChartDrawActivity.class));
            }
        });
    }

    @Override
    public GraphViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
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
        return new GraphViewHolder(inflate);
    }



    public static class GraphViewHolder extends RecyclerView.ViewHolder {
        FrameLayout a;
        ImageView b;

        public GraphViewHolder(View view) {
            super(view);
            this.a = (FrameLayout) view.findViewById(R.id.imageView);
            this.b = (ImageView) view.findViewById(R.id.imageViewInside);
        }
    }
}
