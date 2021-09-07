package com.will_d.yogadesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WorkShopActivity extends AppCompatActivity {


    Fragment[] fragments = new Fragment[3];
    FragmentManager manager;
    FragmentTransaction tran;

    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop);

        drawerLayout = findViewById(R.id.drawer);

        manager = getSupportFragmentManager();
        tran = manager.beginTransaction();

        if (fragments[0] == null) {
            fragments[0] = new WorkShopWorkFragment();
            tran.add(R.id.container, fragments[0]);
        }

        tran.commit();

    }


    public void clickDrawer(View view) {

        if (!drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.openDrawer(GravityCompat.START);
        }

    }

    public void clickGotoToolList(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void clickNabWork(View view) {
        tran = manager.beginTransaction();
        tran.hide(fragments[0]);
        if(fragments[1]!=null) tran.hide(fragments[1]);
        if(fragments[2]!=null) tran.hide(fragments[2]);

            tran.show(fragments[0]);
            tran.commit();


    }

    public void clickNabTimer(View view) {
        tran = manager.beginTransaction();
        tran.hide(fragments[0]);
        if(fragments[1]!=null) tran.hide(fragments[1]);
        if(fragments[2]!=null) tran.hide(fragments[2]);
        if (fragments[1] == null) {
            fragments[1] = new WorkShopTimerFragment();
            tran.add(R.id.container, fragments[1]);

        }
        tran.commit();
        tran.show(fragments[1]);

    }

    public void clickNabMusic(View view) {
        tran = manager.beginTransaction();
        tran.hide(fragments[0]);
        if(fragments[1]!=null) tran.hide(fragments[1]);
        if(fragments[2]!=null) tran.hide(fragments[2]);
        if (fragments[2] == null) {
            fragments[2] = new WorkShopMusicFragment();
            tran.add(R.id.container, fragments[2]);
        }
        tran.show(fragments[2]);
        tran.commit();

    }
}