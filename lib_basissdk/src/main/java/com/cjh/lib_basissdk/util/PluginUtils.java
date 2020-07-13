package com.cjh.lib_basissdk.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import com.cjh.lib_basissdk.R;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * @author: caijianhui
 * @date: 2019/7/11 14:45
 * @description:
 */
public final class PluginUtils {

    private static final String SYSTEM_PACKAGE_NAME = "android";

    private PluginUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Resources getPluginResources(String packageName) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            Log.i("hehhe", "apk path = " + Utils.getApp().getPackageManager().getApplicationInfo(packageName, 0).sourceDir);
            addAssetPath.invoke(assetManager, Utils.getApp().getPackageManager().getApplicationInfo(packageName, 0).sourceDir);
            Resources superRes = Utils.getApp().getResources();
            return new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int getIdentifierInner(final Resources resources,
                                          final String resourceName,
                                          final String defType,
                                          final String packageName,
                                          final boolean isSystem) {
        if (null == resources || UtilsBridge.isSpace(resourceName) || UtilsBridge.isSpace(defType)
                || UtilsBridge.isSpace(packageName)) {
            return 0;
        }
        return resources.getIdentifier(resourceName, defType, isSystem ? SYSTEM_PACKAGE_NAME : packageName);
    }

    public static int getThirdPartyDrawable(final Context pluginContext, final String drawableName,
                                            final String packageName) {
        if (isAppInstalled(packageName)) {
             return getInstalledIdentifier(pluginContext, drawableName, packageName, "drawable");
        }
        return getUnInstalledIdentifier(drawableName, packageName, "drawable");
    }

    public static Context createPluginContext(final String packageName) {
        try {
           return Utils.getApp().createPackageContext(packageName,
                    Context.CONTEXT_IGNORE_SECURITY | Context.CONTEXT_INCLUDE_CODE);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Utils.getApp();
    }

    /**
     * 加载未安装apk的资源
     *
     * */
    private static int getUnInstalledIdentifier(final String resourceName, final String packageName,
                                                final String defType) {
        if (TextUtils.isEmpty(resourceName) || TextUtils.isEmpty(packageName)
                || TextUtils.isEmpty(defType)) {
            return 0;
        }
        try {
            File file = Utils.getApp().getDir("dex", Context.MODE_PRIVATE);
            DexClassLoader dexClassLoader = new DexClassLoader(Utils.getApp().getPackageManager()
                    .getApplicationInfo(packageName, 0).sourceDir, file.getPath(),
                    null, ClassLoader.getSystemClassLoader());
            Class clazz = dexClassLoader.loadClass(packageName + ".R$" + defType);
            Field field = clazz.getDeclaredField(resourceName);
            int id = field.getInt(R.id.class);
            return id;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 加载已安装的apk的资源
     * @param packageName 应用的包名
     * @return 对应资源的id
     */
    private static int getInstalledIdentifier(final Context pluginContext, final String resourceName,
                                              final String packageName, final String defType) {
        if (null == pluginContext || TextUtils.isEmpty(resourceName)
                || TextUtils.isEmpty(packageName) || TextUtils.isEmpty(defType)) {
            return 0;
        }
        try {
            //第一个参数为包含dex的apk或者jar的路径，第二个参数为父加载器
            PathClassLoader pathClassLoader = new PathClassLoader(pluginContext.getPackageResourcePath(),
                    ClassLoader.getSystemClassLoader());
            //通过使用自身的加载器反射出defType类进而使用该类的功能
            //Class<?> clazz = pathClassLoader.loadClass(packageName + ".R$" + defType);
            //参数：1、类的全名，2、是否初始化类，3、加载时使用的类加载器
            Class<?> clazz = Class.forName(packageName + ".R$" + defType, true, pathClassLoader);
            //使用上述两种方式都可以，这里我们得到R类中的内部类defType，通过它得到对应的图片id，进而给我们使用
            Field field = clazz.getDeclaredField(resourceName);
            return field.getInt(R.id.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static boolean isAppInstalled(String packageName) {
        PackageManager pm = Utils.getApp().getPackageManager();
        boolean installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch(PackageManager.NameNotFoundException e){
            installed = false;
        }
        return installed;
    }

    private static Class getClassByType(String defType) {
        if ("drawable".equals(defType)) {
            return R.drawable.class;
        } else if ("mipmap".equals(defType)) {
            return R.mipmap.class;
        }
        return R.drawable.class;
    }

}
