package pavishka.coloring.book.VectorArt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import pavishka.coloring.book.Activities.OtherScreens.MainActivity;
import pavishka.coloring.book.Activities.Screens.ScreenMyRevenueView;
import pavishka.coloring.book.Activities.Screens.ScreenConstable;
import pavishka.coloring.book.Activities.Screens.ScreenLanguage;
import pavishka.coloring.book.Activities.Screens.ScreenOwnAuidioPlayer;
import pavishka.coloring.book.R;


public class ScreenCardingPixbasedActivity extends Activity implements View.OnClickListener {
    public static int gridType;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    RecyclerView recyclerView;
    ScreenOwnAuidioPlayer myMediaPlayer;
    ArrayList<JSONObject> jsonObjects;

    private void animateClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_in_low));
    }

    private void initIds() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.imageView2 = (ImageView) findViewById(R.id.tick_easy);
        this.imageView3 = (ImageView) findViewById(R.id.tick_hard);
        this.imageView = (ImageView) findViewById(R.id.tick_easy);
        this.imageView1 = (ImageView) findViewById(R.id.tick_hard);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.imageView2.setOnClickListener(this);
        this.imageView3.setOnClickListener(this);
        this.jsonObjects = new ArrayList<>();
        this.myMediaPlayer = new ScreenOwnAuidioPlayer(getApplicationContext());
    }

    private void loadList() {
        try {
            JSONArray jSONArray = new JSONObject(getJSONFromAsset()).getJSONArray(ScreenConstable.JSON_ARRAY_NAME);
            this.jsonObjects.clear();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                this.jsonObjects.add(jSONObject);
                Log.d("JSON_TESTING", jSONObject.toString());
            }
            ScreenPixelSkillAdapter pixelArtAdapter = new ScreenPixelSkillAdapter(this, this.jsonObjects);
            this.recyclerView.setHasFixedSize(true);
            this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            this.recyclerView.setAdapter(pixelArtAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setCheck(ImageView imageView) {
        this.imageView.setBackgroundResource(0);
        this.imageView1.setBackgroundResource(0);

    }

    protected void alises() {
        getWindow().getDecorView().setSystemUiVisibility(7686);
    }

    public void finishActivity() {
        ScreenConstable.showNewApp = true;
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public String getJSONFromAsset() {
        InputStream inputStream;
        try {
            if (gridType == 10) {
                inputStream = getAssets().open("Grid/GridDesign10.json");
            } else {
                inputStream = getAssets().open("Grid/GridDesign15.json");
            }
            byte[] bArr = new byte[inputStream.available()];
            inputStream.read(bArr);
            inputStream.close();
            return new String(bArr, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    @Override
    public void onClick(View view) {
        animateClick(view);
        this.myMediaPlayer.playSound(R.raw.click);
        int id = view.getId();
        if (id == R.id.tick_easy) {
            gridType = 10;
            loadList();
            setCheck(this.imageView);
        } else if (id == R.id.tick_hard) {
            gridType = 15;
            loadList();
            setCheck(this.imageView1);
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        new ScreenLanguage().setUpLocale(this);
        setContentView(R.layout.activity_grid_pixel_art);

        gridType = 10;
        initIds();
        loadList();
        setCheck(this.imageView);
        new ScreenMyRevenueView(this).setad((FrameLayout) findViewById(R.id.adView));
    }

    @Override
    protected void onResume() {
        super.onResume();
        alises();
    }
}
