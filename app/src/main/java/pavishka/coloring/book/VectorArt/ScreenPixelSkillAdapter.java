package pavishka.coloring.book.VectorArt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import pavishka.coloring.book.Activities.Screens.ScreenConstable;
import pavishka.coloring.book.Activities.Screens.ScreenOwnAuidioPlayer;
import pavishka.coloring.book.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ScreenPixelSkillAdapter extends RecyclerView.Adapter<ScreenPixelSkillAdapter.PixelViewHolder> {
    int anInt;
    int anInt1;
    FrameLayout.LayoutParams layoutParams;
    private ArrayList<JSONObject> list;
    private Context mCtx;
    private ScreenOwnAuidioPlayer myMediaPlayer;

    public ScreenPixelSkillAdapter(Context context, ArrayList<JSONObject> arrayList) {
        this.mCtx = context;
        this.list = arrayList;
        this.myMediaPlayer = new ScreenOwnAuidioPlayer(context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        this.anInt = displayMetrics.heightPixels;
        int i = displayMetrics.widthPixels;
        this.anInt1 = i;
        int i2 = (i / 2) - (i / 12);
        this.anInt = i2;
        if (ScreenCardingPixbasedActivity.gridType == 10) {
            this.anInt = i2 - (i2 % 10);
        } else {
            this.anInt = i2 - (i2 % 15);
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        this.layoutParams = layoutParams;
        layoutParams.height = Math.round(this.anInt);
        this.layoutParams.width = Math.round(this.anInt);
        FrameLayout.LayoutParams layoutParams2 = this.layoutParams;
        int i3 = this.anInt / 40;
        layoutParams2.setMargins(0, i3, 0, i3);
        this.layoutParams.gravity = 17;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    public void animateClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this.mCtx, R.anim.pop_in_low));
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void onBindViewHolder(PixelViewHolder pixelViewHolder, @SuppressLint("RecyclerView") final int i) {
        JSONObject jSONObject = this.list.get(i);
        ArrayList<Carding> arrayList = new ArrayList<>();
        Log.d("JSON_TESTING", jSONObject.toString());
        for (int i2 = 0; i2 < jSONObject.length() / 3; i2++) {
            try {
                int i3 = jSONObject.getInt(ScreenConstable.JSON_ROW + i2);
                int i4 = jSONObject.getInt(ScreenConstable.JSON_COLUMN + i2);
                arrayList.add(new Carding(i3, i4, jSONObject.getInt(ScreenConstable.JSON_COLOR + i2)));
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("JSON_TESTING", e.toString());
            }
        }
        pixelViewHolder.b.setLayoutParams(this.layoutParams);
        pixelViewHolder.a.setLayoutParams(this.layoutParams);
        pixelViewHolder.a.removeAllViewsInLayout();
        VectorBasedDrawing pixelDrawing = new VectorBasedDrawing(this.mCtx, null);
        pixelDrawing.setNumRows(ScreenCardingPixbasedActivity.gridType);
        pixelDrawing.setNumColumns(ScreenCardingPixbasedActivity.gridType);
        pixelDrawing.setPicture(arrayList);
        pixelViewHolder.a.addView(pixelDrawing);
        pixelViewHolder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenPixelSkillAdapter.this.animateClick(view);
                ScreenPixelSkillAdapter.this.myMediaPlayer.playSound(R.raw.click);
                Intent intent = new Intent(ScreenPixelSkillAdapter.this.mCtx, ScreenPixelSkillActivity.class);
                intent.putExtra(ScreenConstable.PICTURE_CODE, i);
                ScreenPixelSkillAdapter.this.mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public PixelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PixelViewHolder(this, LayoutInflater.from(this.mCtx).inflate(R.layout.pixelart_item, (ViewGroup) null));
    }


    public class PixelViewHolder extends RecyclerView.ViewHolder {
        LinearLayout a;
        LinearLayout b;

        public PixelViewHolder(ScreenPixelSkillAdapter pixelArtAdapter, View view) {
            super(view);
            this.a = (LinearLayout) view.findViewById(R.id.parent);
            this.b = (LinearLayout) view.findViewById(R.id.clicker);
        }
    }
}
