package pavishka.coloring.book.Activities.Screens;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import java.util.List;

import pavishka.coloring.book.R;


public class ScreenFifaActivity extends ScreenManualColorDrawingActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        new ScreenLanguage().setUpLocale(this);
        setContentView(R.layout.activity_fifa);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        drawActivity = this;
        initialize();
        initializeOnSizeChangedValue();
        initializeMediaPlayer();
        findByViewIds();
        setUpColorPicker();
        List<ScreenInfo> D = D(0);
        setBottomColorLayout(D);
        drawerImplementationForBrush();
        drawerImplementationForColor();
        chooseDrawingType();
        setDefaultColor();
        refreshData(D);
    }


    @Override
    public void onResume() {
        C(this.drawerLayout1);
        disableBrushDrawer(this.drawerLayout);
        super.onResume();
        hideNavigation();
        dec();
    }
}
