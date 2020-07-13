package com.cjh.component_regular_expression;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cjh.lib_basissdk.util.FileIOUtils;
import com.cjh.lib_basissdk.util.ResourceUtils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //regexBook(readAssets("regular3.txt"));
        regexWidget("regular4.txt");
    }

    private void regexBook(String target) {
        String regex = "《.*?》";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        while (matcher.find()) {
            //Log.e(TAG, matcher.group());
            System.out.println(matcher.group());
        }
    }

    private String readAssets(String name) {
        return ResourceUtils.readAssets2String(name);
    }

    private static void regexWidget(String target) {
        String regex = "$\\{.*?}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

}
