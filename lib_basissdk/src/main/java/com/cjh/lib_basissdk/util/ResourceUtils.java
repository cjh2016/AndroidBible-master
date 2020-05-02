package com.cjh.lib_basissdk.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.RawRes;
import androidx.core.content.ContextCompat;

/**
 * 资源相关工具
 */
public final class ResourceUtils {
	
	private static final int BUFFER_SIZE = 8 * 1024;
	private static final String SYSTEM_PACKAGE_NAME = "android";
	
	private ResourceUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}
	
	public static String getFileFromAssets(final Context context, final String fileName) {
		
		if (null == context || isSpace(fileName)) return null;
		StringBuilder sb = new StringBuilder("");
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(context.getResources().getAssets().open(fileName));
			br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			CloseUtils.closeIO(isr, br);
		}
	}
	
	public static String getFileFromRaw(final Context context, int resId) {
		if (null == context) return null;
		StringBuilder sb = new StringBuilder();
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(context.getResources().openRawResource(resId));
			br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			CloseUtils.closeIO(isr, br);
		}
	}
	
	public static List<String> getFileToListFromAssets(final Context context, final String fileName) {
        if (context == null || isSpace(fileName)) return null; 
        List<String> fileContent = new ArrayList<String>();
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(context.getResources().getAssets().open(fileName));
            br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
            br.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
        	CloseUtils.closeIO(isr, br);
        }
	}
	
	public static List<String> getFileToListFromRaw(final Context context, final int resId) {
        if (context == null) return null;
        List<String> fileContent = new ArrayList<String>();
        InputStreamReader isr = null;
        BufferedReader reader = null;
        try {
            isr = new InputStreamReader(context.getResources().openRawResource(resId));
            reader = new BufferedReader(isr);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
        	CloseUtils.closeIO(isr, reader);
        }
	}
	
	private static byte[] is2Bytes(final InputStream is) {
		if (is == null) return null;
		ByteArrayOutputStream os = null;
		try {
			os = new ByteArrayOutputStream();
			byte[] b = new byte[BUFFER_SIZE];
			int len;
			while ((len = is.read(b, 0, BUFFER_SIZE)) != -1) {
				os.write(b, 0, len);
			}
			return os.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	
	private static List<String> is2List(final InputStream is,
										final String charsetName) {
		BufferedReader reader = null;
		try {
			List<String> list = new ArrayList<String>();
			if (isSpace(charsetName)) {
				reader = new BufferedReader(new InputStreamReader(is));
			} else {
				reader = new BufferedReader(new InputStreamReader(is, charsetName));
			}
			String line;
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static int getId(Context context, String resourceName) {
        return getIdentifierInner(context, resourceName, "id", false);
    }

    public static int getLayoutId(Context context, String resourceName) {
        return getIdentifierInner(context, resourceName, "layout", false);
    }

    public static int getStringId(Context context, String resourceName) {
        return getIdentifierInner(context, resourceName, "string", false);
    }

    public static int getDrawableId(Context context, String resourceName) {
        return getIdentifierInner(context, resourceName, "drawable", false);
    }

    public static int getMipmapId(Context context, String resourceName) {
        return getIdentifierInner(context, resourceName, "mipmap", false);
    }

    public static int getColorId(Context context, String resourceName) {
        return getIdentifierInner(context, resourceName, "color", false);
    }

    public static int getDimenId(Context context, String resourceName) {
        return getIdentifierInner(context, resourceName, "dimen", false);
    }

    public static int getAttrId(Context context, String resourceName) {
        return getIdentifierInner(context, resourceName, "attr", false);
    }

    public static int getStyleId(Context context, String resourceName) {
        return getIdentifierInner(context, resourceName, "style", false);
    }

    public static int getAnimId(Context context, String resourceName) {
        return getIdentifierInner(context, resourceName, "anim", false);
    }

    public static int getArrayId(Context context, String resourceName) {
        return getIdentifierInner(context, resourceName, "array", false);
    }

    public static int getIntegerId(Context context, String resourceName) {
        return getIdentifierInner(context, resourceName, "integer", false);
    }

    public static int getBoolId(Context context, String resourceName) {
        return getIdentifierInner(context, resourceName, "bool", false);
    }
	
	private static int getIdentifierInner(final Context context, 
										  final String resourceName, 
										  final String defType,
										  final boolean isSystem) {
		if (null == context || isSpace(resourceName) || isSpace(defType)) 
			return 0;
		return context.getResources().getIdentifier(resourceName, defType, 
				isSystem ? SYSTEM_PACKAGE_NAME : context.getPackageName());
	}
	
	/**
     * 根据Android系统版本，调用版本API中的获取颜色方法
     * According to the Android version, calls the method for color of version API
     *
     * @param activity activity
     * @param resId    resource id
     * @return color
     */
    public static int getColor(Activity activity, int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.getResources().getColor(resId, activity.getTheme());
        } else {
            return activity.getResources().getColor(resId);
        }
    }
	
	/**
     * 根据Android系统版本，调用版本API中的获取Drawable方法
     * According to the Android version, calls the method for drawable of version API
     *
     * @param activity activity
     * @param resId    resource id
     * @return color
     */
    @SuppressLint("NewApi")
	public static Drawable getDrawable(Activity activity, int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return activity.getTheme().getDrawable(resId);
        } else {
            return activity.getResources().getDrawable(resId);
        }
    }
	
	/**
	 * 判断是字符串是否为空
	 * @param s
	 * @return
	 */
	private static boolean isSpace(final String s) {
		if (null == s) return true;
		for (int i = 0, len = s.length(); i < len; ++i) {
			if (!Character.isWhitespace(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/** 
	 * 还没测试
     * 获取自定义属性的资源ID 
     * @param context   上下文 
     * @param attrRes   自定义属性 
     * @return  resourceId 
     */  
    public static int getResourceId(Context context, int attrRes)  
    {  
        TypedValue typedValue = new TypedValue();  
        context.getTheme().resolveAttribute(attrRes, typedValue, true);  
        return typedValue.resourceId;  
    }  
    
    /** 
     * 还没测试
     * 获取colorPrimary的颜色,需要V7包的支持 
     * @param context 上下文 
     * @return 0xAARRGGBB 
     */  
    public static int getColorPrimary(Context context){  
        Resources res = context.getResources();  
        int attrRes =res.getIdentifier("colorPrimary","attr",context.getPackageName());  
        if(attrRes == 0){  
            return 0xFF009688;  
        }  
        return ContextCompat.getColor(context,getResourceId(context,attrRes));
    }  
    
    
    /**
     * 根据资源id获取资源名称:
     */
    public static String getResourceName(@IdRes int resId) {
    	return Utils.getApp().getResources().getResourceName(resId);
    }
	
	
	
	/**
     * Return the drawable by identifier.
     *
     * @param id The identifier.
     * @return the drawable by identifier
     */
    public static Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(Utils.getApp(), id);
    }

    /**
     * Return the id identifier by name.
     *
     * @param name The name of id.
     * @return the id identifier by name
     */
    public static int getIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "id", Utils.getApp().getPackageName());
    }

    /**
     * Return the string identifier by name.
     *
     * @param name The name of string.
     * @return the string identifier by name
     */
    public static int getStringIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "string", Utils.getApp().getPackageName());
    }

    /**
     * Return the color identifier by name.
     *
     * @param name The name of color.
     * @return the color identifier by name
     */
    public static int getColorIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "color", Utils.getApp().getPackageName());
    }

    /**
     * Return the dimen identifier by name.
     *
     * @param name The name of dimen.
     * @return the dimen identifier by name
     */
    public static int getDimenIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "dimen", Utils.getApp().getPackageName());
    }

    /**
     * Return the drawable identifier by name.
     *
     * @param name The name of drawable.
     * @return the drawable identifier by name
     */
    public static int getDrawableIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "drawable", Utils.getApp().getPackageName());
    }

    /**
     * Return the mipmap identifier by name.
     *
     * @param name The name of mipmap.
     * @return the mipmap identifier by name
     */
    public static int getMipmapIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "mipmap", Utils.getApp().getPackageName());
    }

    /**
     * Return the layout identifier by name.
     *
     * @param name The name of layout.
     * @return the layout identifier by name
     */
    public static int getLayoutIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "layout", Utils.getApp().getPackageName());
    }

    /**
     * Return the style identifier by name.
     *
     * @param name The name of style.
     * @return the style identifier by name
     */
    public static int getStyleIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "style", Utils.getApp().getPackageName());
    }

    /**
     * Return the anim identifier by name.
     *
     * @param name The name of anim.
     * @return the anim identifier by name
     */
    public static int getAnimIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "anim", Utils.getApp().getPackageName());
    }

    /**
     * Return the menu identifier by name.
     *
     * @param name The name of menu.
     * @return the menu identifier by name
     */
    public static int getMenuIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "menu", Utils.getApp().getPackageName());
    }

    /**
     * Copy the file from assets.
     *
     * @param assetsFilePath The path of file in assets.
     * @param destFilePath   The path of destination file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean copyFileFromAssets(final String assetsFilePath, final String destFilePath) {
        boolean res = true;
        try {
            String[] assets = Utils.getApp().getAssets().list(assetsFilePath);
            if (assets != null && assets.length > 0) {
                for (String asset : assets) {
                    res &= copyFileFromAssets(assetsFilePath + "/" + asset, destFilePath + "/" + asset);
                }
            } else {
                res = UtilsBridge.writeFileFromIS(
                        destFilePath,
                        Utils.getApp().getAssets().open(assetsFilePath)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    /**
     * Return the content of assets.
     *
     * @param assetsFilePath The path of file in assets.
     * @return the content of assets
     */
    public static String readAssets2String(final String assetsFilePath) {
        return readAssets2String(assetsFilePath, null);
    }

    /**
     * Return the content of assets.
     *
     * @param assetsFilePath The path of file in assets.
     * @param charsetName    The name of charset.
     * @return the content of assets
     */
    public static String readAssets2String(final String assetsFilePath, final String charsetName) {
        try {
            InputStream is = Utils.getApp().getAssets().open(assetsFilePath);
            byte[] bytes = UtilsBridge.inputStream2Bytes(is);
            if (bytes == null) return "";
            if (UtilsBridge.isSpace(charsetName)) {
                return new String(bytes);
            } else {
                try {
                    return new String(bytes, charsetName);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the content of file in assets.
     *
     * @param assetsPath The path of file in assets.
     * @return the content of file in assets
     */
    public static List<String> readAssets2List(final String assetsPath) {
        return readAssets2List(assetsPath, "");
    }

    /**
     * Return the content of file in assets.
     *
     * @param assetsPath  The path of file in assets.
     * @param charsetName The name of charset.
     * @return the content of file in assets
     */
    public static List<String> readAssets2List(final String assetsPath,
                                               final String charsetName) {
        try {
            return UtilsBridge.inputStream2Lines(Utils.getApp().getResources().getAssets().open(assetsPath), charsetName);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    /**
     * Copy the file from raw.
     *
     * @param resId        The resource id.
     * @param destFilePath The path of destination file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean copyFileFromRaw(@RawRes final int resId, final String destFilePath) {
        return UtilsBridge.writeFileFromIS(
                destFilePath,
                Utils.getApp().getResources().openRawResource(resId)
        );
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId The resource id.
     * @return the content of resource in raw
     */
    public static String readRaw2String(@RawRes final int resId) {
        return readRaw2String(resId, null);
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId       The resource id.
     * @param charsetName The name of charset.
     * @return the content of resource in raw
     */
    public static String readRaw2String(@RawRes final int resId, final String charsetName) {
        InputStream is = Utils.getApp().getResources().openRawResource(resId);
        byte[] bytes = UtilsBridge.inputStream2Bytes(is);
        if (bytes == null) return null;
        if (UtilsBridge.isSpace(charsetName)) {
            return new String(bytes);
        } else {
            try {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId The resource id.
     * @return the content of file in assets
     */
    public static List<String> readRaw2List(@RawRes final int resId) {
        return readRaw2List(resId, "");
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId       The resource id.
     * @param charsetName The name of charset.
     * @return the content of file in assets
     */
    public static List<String> readRaw2List(@RawRes final int resId,
                                            final String charsetName) {
        return UtilsBridge.inputStream2Lines(Utils.getApp().getResources().openRawResource(resId), charsetName);
    }
	
	
}
