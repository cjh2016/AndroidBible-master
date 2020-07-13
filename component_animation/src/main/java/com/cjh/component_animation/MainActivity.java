package com.cjh.component_animation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private boolean enable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LottieAnimationView mirror = findViewById(R.id.lottieAnimationView);
        mirror.addLottieOnCompositionLoadedListener(
                new LottieOnCompositionLoadedListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onCompositionLoaded(LottieComposition lottieComposition) {
                        //过滤所有的keypath
                        List<KeyPath> list = mirror.resolveKeyPath(
                                new KeyPath("**"));
                        //通过匹配关键字的深度，来过滤需要改变颜色的keypath
                        for (KeyPath path : list) {
                            Log.d("mirror", path.keysToString());
                            //通过匹配关键字的深度对深度为1 和2 的填充色进行修改
                            if (path.matches("填充 1", 2)||path.matches("填充 1", 1) ) {
                                mirror.addValueCallback(path,
                                        //修改对应keypath的填充色的属性值
                                        LottieProperty.COLOR,
                                        new SimpleLottieValueCallback<Integer>() {
                                            @Override
                                            public Integer getValue(
                                                    LottieFrameInfo<Integer> lottieFrameInfo) {
                                                return Color.parseColor("#ffcccccc");
                                            }
                                        });
                            }else if(path.matches("描边 1", 2) ){ //通过匹配关键字的深度修改深度为2 的描边色进行修改
                                mirror.addValueCallback(path,
                                        //修改对应keypath的描边色的属性值
                                        LottieProperty.STROKE_COLOR,
                                        new SimpleLottieValueCallback<Integer>() {
                                            @Override
                                            public Integer getValue(
                                                    LottieFrameInfo<Integer> lottieFrameInfo) {
                                                //修改对应keypath的描边色
                                                return Color.parseColor("#ffcccccc");
                                            }
                                        });
                            }
                        }
                    }
                });


    }

    public void call(Context context) {
        Uri uri = Uri.parse("content://com.szhr.launcher2.sys_custom_provider");
        ContentResolver resolver = context.getContentResolver();
        Bundle bundle = new Bundle();
        bundle.putString("packageName", "com.iflytek.xiri");
        bundle.putInt("newState", enable ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                : PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
        resolver.call(uri, "setApplicationEnabledSetting", "", bundle);


        bundle.putString("packageName", "com.boll.languagepoint");

        resolver.call(uri, "setApplicationEnabledSetting", "", bundle);

        enable = !enable;

        int isEnable = getPackageManager().getApplicationEnabledSetting("com.boll.languagepoint");

        Log.e("cjcj", "isEnable = " + isEnable);
    }


    public void call2(Context context) {
        Uri uri = Uri.parse("content://com.szhr.launcher2.sys_provider");
        ContentResolver resolver = context.getContentResolver();
        Bundle bundle = new Bundle();
        bundle.putString("packageName", "com.szhr.xiri");

        resolver.call(uri, "opHal", "r", bundle);

    }


    public void switchApp(View view) {
        call(getApplicationContext());
    }



}
