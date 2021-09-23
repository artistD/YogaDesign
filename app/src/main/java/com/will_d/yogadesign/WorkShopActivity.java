package com.will_d.yogadesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.will_d.yogadesign.set.WorkShopStatisticsFragment;
import com.will_d.yogadesign.square.WorkShopSquareFragment;
import com.will_d.yogadesign.worktoday.WorkShopTimeFragment;
import com.will_d.yogadesign.worktoday.WorkShopTodolistFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.ShapeType;

public class WorkShopActivity extends AppCompatActivity {

    private final int BNV_WORK=0;
    private final int BNV_TIME=1;
    private final int BNV_SQUARE=2;
    private final int BNV_STATISTICS=3;
    private final int TODOLIST=4;


    private Fragment[] fragments = new Fragment[5];
    private FragmentManager manager;
    private FragmentTransaction tran;

    private NeumorphCardView cdNabWork;
    private NeumorphCardView cdNabTime;
    private NeumorphCardView cdNabSquare;
    private NeumorphCardView cdNabStatistics;

    private ImageView ivNabWork;
    private ImageView ivNabTime;
    private ImageView ivNabSquare;
    private ImageView ivNabStatistics;

    private CardView meterialCdTodolist;
    private ImageView ivTodolistBack;


    private View viewLine;
    private ImageView ivBnvBlur;
    private RelativeLayout toolbarBlur;
    private ImageView meterialIvTodolistBlur;

    private int isNav;



//    private NeumorphCardView cdAddBtnItem;
//    private NeumorphCardView cdAddBtnSub;


    public CardView getMeterialCdTodolist() {
        return meterialCdTodolist;
    }

    public ImageView getMeterialIvTodolistBlur() {
        return meterialIvTodolistBlur;
    }

    public View getViewLine() {
        return viewLine;
    }

    public ImageView getIvBnvBlur() {
        return ivBnvBlur;
    }

    public RelativeLayout getToolbarBlur() {
        return toolbarBlur;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop);

        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        boolean isFirstCompair = pref.getBoolean("isFirstCompair", false);
        Log.i("First", isFirstCompair + "");
        if (isFirstCompair){
            long now = System.currentTimeMillis();
            Date date = new Date(now);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String dayStr = sdf.format(date);
            editor.putString("dayCompairison", dayStr);
            Log.i("First", dayStr);
            editor.putBoolean("isFirstCompair", false);
            editor.commit();
        }


        cdNabWork = findViewById(R.id.cd_nab_work);
        cdNabTime = findViewById(R.id.cd_nab_time);
        cdNabSquare = findViewById(R.id.cd_nab_square);
        cdNabStatistics = findViewById(R.id.cd_nab_statistics);

        ivNabWork = findViewById(R.id.iv_nab_work);
        ivNabTime = findViewById(R.id.iv_nab_time);
        ivNabSquare = findViewById(R.id.iv_nab_square);
        ivNabStatistics = findViewById(R.id.iv_nab_statistics);

        meterialCdTodolist = findViewById(R.id.meterial_cd_todolist);
        ivTodolistBack = findViewById(R.id.iv_todolist_back);

        viewLine = findViewById(R.id.view_line);
        ivBnvBlur = findViewById(R.id.iv_bnvBlur);
        meterialIvTodolistBlur = findViewById(R.id.meterial_iv_todolist_blur);


        manager = getSupportFragmentManager();
        tran = manager.beginTransaction();
        if (fragments[0] == null) {
            fragments[0] = new WorkShopWorkFragment();
            tran.add(R.id.container, fragments[0]);
        }
        tran.commit();
        BnvChangeState(cdNabWork, ivNabWork, R.drawable.ic_fragment_work2_push); //처음에 클릭되있어야 되니까
        isNav = BNV_WORK;

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



    public void clickTodolist(View view) {
        BnvFragmentChange(TODOLIST, new WorkShopTodolistFragment());

        meterialCdTodolist.setVisibility(View.INVISIBLE);
        ivTodolistBack.setVisibility(View.VISIBLE);
        ivBnvBlur.setVisibility(View.VISIBLE);

    }

    public void clickTodolistBack(View view) {


        switch (isNav){
            case 0:
                BnvFragmentChange(BNV_WORK, new WorkShopWorkFragment());
                BnvRevertState();
                BnvChangeState(cdNabWork, ivNabWork, R.drawable.ic_fragment_work2_push);

                meterialCdTodolist.setVisibility(View.VISIBLE);
                ivTodolistBack.setVisibility(View.INVISIBLE);
                ivBnvBlur.setVisibility(View.INVISIBLE);
                break;

            case 1:
                BnvFragmentChange(BNV_TIME, new WorkShopTimeFragment());
                BnvRevertState();
                BnvChangeState(cdNabTime, ivNabTime, R.drawable.ic_fragment_time_push);

                meterialCdTodolist.setVisibility(View.VISIBLE);
                ivTodolistBack.setVisibility(View.INVISIBLE);
                ivBnvBlur.setVisibility(View.INVISIBLE);
                break;

            case 2:
                BnvFragmentChange(BNV_SQUARE, new WorkShopSquareFragment());
                BnvRevertState();
                BnvChangeState(cdNabSquare, ivNabSquare, R.drawable.ic_fragment_square_push);

                meterialCdTodolist.setVisibility(View.VISIBLE);
                ivTodolistBack.setVisibility(View.INVISIBLE);
                ivBnvBlur.setVisibility(View.INVISIBLE);
                break;

            case 3:
                BnvFragmentChange(BNV_STATISTICS, new WorkShopStatisticsFragment());
                BnvRevertState();
                BnvChangeState(cdNabStatistics, ivNabStatistics, R.drawable.ic_fragment_statistics_push);

                meterialCdTodolist.setVisibility(View.VISIBLE);
                ivTodolistBack.setVisibility(View.INVISIBLE);
                ivBnvBlur.setVisibility(View.INVISIBLE);
                break;
        }

    }


    public void clickNabWork(View view) {
        fragments[0].onResume();
        BnvFragmentChange(BNV_WORK, new WorkShopWorkFragment());
        BnvRevertState();
        BnvChangeState(cdNabWork, ivNabWork, R.drawable.ic_fragment_work2_push);

        isNav = BNV_WORK;

    }

    public void clickNabTime(View view) {
        BnvFragmentChange(BNV_TIME, new WorkShopTimeFragment());
        BnvRevertState();
        BnvChangeState(cdNabTime, ivNabTime, R.drawable.ic_fragment_time_push);

        isNav = BNV_TIME;
    }

    public void clickNabSquare(View view) {
        BnvFragmentChange(BNV_SQUARE, new WorkShopSquareFragment());
        BnvRevertState();
        BnvChangeState(cdNabSquare, ivNabSquare, R.drawable.ic_fragment_square_push);

        isNav = BNV_SQUARE;
    }

    public void clickNabStatistics(View view) {
        BnvFragmentChange(BNV_STATISTICS, new WorkShopStatisticsFragment());
        BnvRevertState();
        BnvChangeState(cdNabStatistics, ivNabStatistics, R.drawable.ic_fragment_statistics_push);

        isNav = BNV_STATISTICS;
    }


    public void BnvFragmentChange(int BNV_ARRAYNUM, Fragment bnVFragment){
        tran = manager.beginTransaction();
//        tran.hide(fragments[0]);
//        if(fragments[1]!=null) tran.hide(fragments[1]);
//        if(fragments[2]!=null) tran.hide(fragments[2]);
//        if(fragments[3]!=null) tran.hide(fragments[3]);
//        if(fragments[4]!=null) tran.hide(fragments[4]);
        tran.remove(fragments[0]);
        if(fragments[1]!=null) tran.remove(fragments[1]);
        if(fragments[2]!=null) tran.remove(fragments[2]);
        if(fragments[3]!=null) tran.remove(fragments[3]);
        if(fragments[4]!=null) tran.remove(fragments[4]);

        if (fragments[BNV_ARRAYNUM] == null) {
            fragments[BNV_ARRAYNUM] = bnVFragment;
            Log.i("TAG", "Fragment");
        }
        tran.add(R.id.container, fragments[BNV_ARRAYNUM]);
        tran.setCustomAnimations(R.anim.fragment_fade, R.anim.fragment_none);
        tran.show(fragments[BNV_ARRAYNUM]);
        tran.commit();
    }

    public void BnvRevertState(){
        cdNabWork.setShapeType(ShapeType.FLAT);
        cdNabWork.setBackgroundColor(0x00);
        ivNabWork.setImageResource(R.drawable.ic_fragment_work2);

        cdNabTime.setShapeType(ShapeType.FLAT);
        cdNabTime.setBackgroundColor(0x00);
        ivNabTime.setImageResource(R.drawable.ic_fragment_time);

        cdNabSquare.setShapeType(ShapeType.FLAT);
        cdNabSquare.setBackgroundColor(0x00);
        ivNabSquare.setImageResource(R.drawable.ic_fragment_square);

        cdNabStatistics.setShapeType(ShapeType.FLAT);
        cdNabStatistics.setBackgroundColor(0x00);
        ivNabStatistics.setImageResource(R.drawable.ic_fragment_statistics);
    }

     public void BnvChangeState(NeumorphCardView cdNab, ImageView ivNab, int nabRes){
        cdNab.setShapeType(ShapeType.PRESSED);
        cdNab.setBackgroundColor(0xFF9999ff);
        ivNab.setImageResource(nabRes);
    }

}