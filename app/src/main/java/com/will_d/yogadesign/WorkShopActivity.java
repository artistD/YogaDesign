package com.will_d.yogadesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.ShapeType;

public class WorkShopActivity extends AppCompatActivity {

    private final int BNV_WORK=0;
    private final int BNV_TIME=1;
    private final int BNV_STATISTICS=2;
    private final int BNV_SQUARE=3;

    private Fragment[] fragments = new Fragment[4];
    private FragmentManager manager;
    private FragmentTransaction tran;

    private NeumorphCardView cdNabWork;
    private NeumorphCardView cdNabTime;
    private NeumorphCardView cdNabStatistics;
    private NeumorphCardView cdNabSquare;

    private ImageView ivNabWork;
    private ImageView ivNabTime;
    private ImageView ivNabStatistics;
    private ImageView ivNabSquare;

    private DrawerLayout drawerLayout;

    private View viewLine;
    private ImageView ivBnvBlur;
    private Toolbar toolbarBlur;

//    private NeumorphCardView cdAddBtnItem;
//    private NeumorphCardView cdAddBtnSub;

    public View getViewLine() {
        return viewLine;
    }

    public ImageView getIvBnvBlur() {
        return ivBnvBlur;
    }

    public Toolbar getToolbarBlur() {
        return toolbarBlur;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop);

        drawerLayout = findViewById(R.id.drawer);

        cdNabWork = findViewById(R.id.cd_nab_work);
        cdNabTime = findViewById(R.id.cd_nab_time);
        cdNabStatistics = findViewById(R.id.cd_nab_statistics);
        cdNabSquare = findViewById(R.id.cd_nab_square);

        ivNabWork = findViewById(R.id.iv_nab_work);
        ivNabTime = findViewById(R.id.iv_nab_time);
        ivNabStatistics = findViewById(R.id.iv_nab_statistics);
        ivNabSquare = findViewById(R.id.iv_nab_square);

        viewLine = findViewById(R.id.view_line);
        ivBnvBlur = findViewById(R.id.iv_bnvBlur);


        manager = getSupportFragmentManager();
        tran = manager.beginTransaction();
        if (fragments[0] == null) {
            fragments[0] = new WorkShopWorkFragment();
            tran.add(R.id.container, fragments[0]);
        }
        tran.commit();
        BnvChangeState(cdNabWork, ivNabWork, R.drawable.ic_fragment_check_push); //처음에 클릭되있어야 되니까

    }


    @Override
    protected void onResume() {
        super.onResume();

        if (fragments[0] !=null){
            Log.i("TAG", "aaa");
            WorkShopWorkFragment workShopWorkFragment = (WorkShopWorkFragment) fragments[0];
            toolbarBlur =  workShopWorkFragment.getToolbarBlur();
        }
    }

    public void clickDrawer(View view) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.openDrawer(GravityCompat.START);
        }

    }

    public void clickNabWork(View view) {
        BnvFragmentChange(BNV_WORK, new WorkShopWorkFragment());
        BnvRevertState();
        BnvChangeState(cdNabWork, ivNabWork, R.drawable.ic_fragment_check_push);

    }

    public void clickNabTime(View view) {
        BnvFragmentChange(BNV_TIME, new WorkShopTimeFragment());
        BnvRevertState();
        BnvChangeState(cdNabTime, ivNabTime, R.drawable.ic_fragment_time_push);
    }

    public void clickNabStatistics(View view) {
        BnvFragmentChange(BNV_STATISTICS, new WorkShopStatisticsFragment());
        BnvRevertState();
        BnvChangeState(cdNabStatistics, ivNabStatistics, R.drawable.ic_fragment_statistics_push);
    }

    public void clickNabSquare(View view) {
        BnvFragmentChange(BNV_SQUARE, new WorkShopSquareFragment());
        BnvRevertState();
        BnvChangeState(cdNabSquare, ivNabSquare, R.drawable.ic_fragment_square_push);
    }


    public void BnvFragmentChange(int BNV_ARRAYNUM, Fragment bnVFragment){
        tran = manager.beginTransaction();
        tran.hide(fragments[0]);
        if(fragments[1]!=null) tran.hide(fragments[1]);
        if(fragments[2]!=null) tran.hide(fragments[2]);
        if(fragments[3]!=null) tran.hide(fragments[3]);
        if (fragments[BNV_ARRAYNUM] == null) {
            fragments[BNV_ARRAYNUM] = bnVFragment;
            Log.i("TAG", "Fragment");
            tran.add(R.id.container, fragments[BNV_ARRAYNUM]);
        }
        tran.setCustomAnimations(R.anim.fragment_fade, R.anim.fragment_none);
        tran.show(fragments[BNV_ARRAYNUM]);
        tran.commit();
    }

    public void BnvRevertState(){
        cdNabWork.setShapeType(ShapeType.FLAT);
        cdNabWork.setBackgroundColor(0x00);
        ivNabWork.setImageResource(R.drawable.ic_fragment_work);

        cdNabTime.setShapeType(ShapeType.FLAT);
        cdNabTime.setBackgroundColor(0x00);
        ivNabTime.setImageResource(R.drawable.ic_fragment_time);

        cdNabStatistics.setShapeType(ShapeType.FLAT);
        cdNabStatistics.setBackgroundColor(0x00);
        ivNabStatistics.setImageResource(R.drawable.ic_fragment_statistics);

        cdNabSquare.setShapeType(ShapeType.FLAT);
        cdNabSquare.setBackgroundColor(0x00);
        ivNabSquare.setImageResource(R.drawable.ic_fragment_square);
    }

    public void BnvChangeState(NeumorphCardView cdNab, ImageView ivNab, int nabRes){
        cdNab.setShapeType(ShapeType.PRESSED);
        cdNab.setBackgroundColor(0xFF9999ff);
        ivNab.setImageResource(nabRes);
    }
}