package com.will_d.yogadesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.ShapeType;

public class WorkShopActivity extends AppCompatActivity {

    private final int BNV_WORK=0;
    private final int BNV_TIMER=1;
    private final int BNV_MUSIC=2;

    private Fragment[] fragments = new Fragment[3];
    private FragmentManager manager;
    private FragmentTransaction tran;

    private NeumorphCardView cdNabWork;
    private NeumorphCardView cdNabTimer;
    private NeumorphCardView cdNabMusic;

    private ImageView ivNabWork;
    private ImageView ivNabTimer;
    private ImageView ivNabMusic;

    private DrawerLayout drawerLayout;

    private NeumorphCardView cdAddBtnItem;
    private NeumorphCardView cdAddBtnSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop);

        drawerLayout = findViewById(R.id.drawer);

        cdNabWork = findViewById(R.id.cd_nab_work);
        cdNabTimer = findViewById(R.id.cd_nab_timer);
        cdNabMusic = findViewById(R.id.cd_nab_music);

        ivNabWork = findViewById(R.id.iv_nab_work);
        ivNabTimer = findViewById(R.id.iv_nab_timer);
        ivNabMusic = findViewById(R.id.iv_nab_music);


        cdAddBtnItem = findViewById(R.id.cd_addbtn_item);
        cdAddBtnSub = findViewById(R.id.cd_addbtn_sub);

//        ObjectAnimator.ofFloat(cdAddBtnItem, "translationY", ).start();


        manager = getSupportFragmentManager();
        tran = manager.beginTransaction();
        if (fragments[0] == null) {
            fragments[0] = new WorkShopWorkFragment();
            tran.add(R.id.container, fragments[0]);
        }
        tran.commit();
        BnvChangeState(cdNabWork, ivNabWork, R.drawable.ic_fragment_check_push); //처음에 클릭되있어야 되니까

    }


    public void clickDrawer(View view) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.openDrawer(GravityCompat.START);
        }

    }

    public void clickGotoToolList(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.activity_horizontal_right, R.anim.activity_vertical_none);

    }

    public void clickNabWork(View view) {
        BnvFragmentChange(BNV_WORK, new WorkShopWorkFragment());
        BnvRevertState();
        BnvChangeState(cdNabWork, ivNabWork, R.drawable.ic_fragment_check_push);

    }

    public void clickNabTimer(View view) {
        BnvFragmentChange(BNV_TIMER, new WorkShopTimerFragment());
        BnvRevertState();
        BnvChangeState(cdNabTimer, ivNabTimer, R.drawable.ic_fragment_timer_push);
    }

    public void clickNabMusic(View view) {
        BnvFragmentChange(BNV_MUSIC, new WorkShopMusicFragment());
        BnvRevertState();
        BnvChangeState(cdNabMusic, ivNabMusic, R.drawable.ic_fragment_music_push);
    }



    public void BnvFragmentChange(int BNV_ARRAYNUM, Fragment bnVFragment){
        tran = manager.beginTransaction();
        tran.hide(fragments[0]);
        if(fragments[1]!=null) tran.hide(fragments[1]);
        if(fragments[2]!=null) tran.hide(fragments[2]);
        if (fragments[BNV_ARRAYNUM] == null) {
            fragments[BNV_ARRAYNUM] = bnVFragment;
            Log.i("TAG", "Fragment");
            tran.add(R.id.container, fragments[BNV_ARRAYNUM]);
        }
        tran.setCustomAnimations(R.anim.activity_alpha, R.anim.activity_vertical_none);
        tran.show(fragments[BNV_ARRAYNUM]);
        tran.commit();
    }

    public void BnvRevertState(){
        cdNabWork.setShapeType(ShapeType.FLAT);
        cdNabWork.setBackgroundColor(0x00);
        ivNabWork.setImageResource(R.drawable.ic_fragment_work);
        cdNabTimer.setShapeType(ShapeType.FLAT);
        cdNabTimer.setBackgroundColor(0x00);
        ivNabTimer.setImageResource(R.drawable.ic_fragment_timer);
        cdNabMusic.setShapeType(ShapeType.FLAT);
        cdNabMusic.setBackgroundColor(0x00);
        ivNabMusic.setImageResource(R.drawable.ic_fragment_music);
    }

    public void BnvChangeState(NeumorphCardView cdNab, ImageView ivNab, int nabRes){
        cdNab.setShapeType(ShapeType.PRESSED);
        cdNab.setBackgroundColor(0xFF9999ff);
        ivNab.setImageResource(nabRes);
    }

}