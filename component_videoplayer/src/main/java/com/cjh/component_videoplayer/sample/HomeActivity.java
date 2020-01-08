package com.cjh.component_videoplayer.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cjh.component_videoplayer.R;
import com.cjh.component_videoplayer.playerbase.config.PlayerConfig;
import com.cjh.component_videoplayer.playerbase.entity.DecoderPlan;

/**
 * @author: caijianhui
 * @date: 2019/8/29 15:32
 * @description:
 */
public class HomeActivity extends AppCompatActivity {

    private TextView mInfo;
    private Toolbar mToolBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mToolBar = findViewById(R.id.id_toolbar);
        mInfo = findViewById(R.id.tv_info);

        setSupportActionBar(mToolBar);

        updateDecoderInfo();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.switchIjkPlayer) {
            PlayerConfig.setDefaultPlanId(App.PLAN_ID_IJK);
            updateDecoderInfo();
        } else if (itemId == R.id.switchMediaPlayer) {
            PlayerConfig.setDefaultPlanId(PlayerConfig.DEFAULT_PLAN_ID);
            updateDecoderInfo();
        } else if (itemId == R.id.switchExoPlayer) {
            PlayerConfig.setDefaultPlanId(App.PLAN_ID_EXO);
            updateDecoderInfo();
        } else if (itemId == R.id.inputUrlPlay) {
            //intentTo(InputUrlPlayActivity.class);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDecoderInfo();
    }

    private void updateDecoderInfo() {
        DecoderPlan defaultPlan = PlayerConfig.getDefaultPlan();
        mInfo.setText("当前解码方案为:" + defaultPlan.getDesc());
    }

    public void useBaseVideoView(View view){
        //intentTo(BaseVideoViewActivity.class);
    }

    public void useWindowVideoView(View view){
        //intentTo(WindowVideoViewActivity.class);
    }

    public void useFloatWindow(View view){
        //intentTo(FloatWindowActivity.class);
    }

    public void viewPagerPlay(View view){
        //intentTo(ViewPagerPlayActivity.class);
    }

    public void singleListPlay(View view){
        //intentTo(ListPlayActivity.class);
    }

    public void multiListPlay(View view){
        //intentTo(MultiListActivity.class);
    }

    public void shareAnimationVideos(View view){
        //intentTo(ShareAnimationActivityA.class);
    }



    private void intentTo(Class<? extends Activity> cls) {
        Intent intent = new Intent(getApplicationContext(), cls);
        startActivity(intent);
    }


}
