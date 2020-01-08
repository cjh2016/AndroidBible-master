package com.cjh.component_videoplayer.playerbase.config;

import com.cjh.component_videoplayer.playerbase.player.BaseInternalPlayer;

import java.lang.reflect.Constructor;

/**
 * @author: caijianhui
 * @date: 2019/8/9 14:30
 * @description:
 */
public class PlayerLoader {

    public static BaseInternalPlayer loadInternalPlayer(int decoderPlanId){
        BaseInternalPlayer internalPlayer = null;
        try {
            Object decoderInstance = getDecoderInstance(decoderPlanId);
            if(decoderInstance instanceof BaseInternalPlayer){
                internalPlayer = (BaseInternalPlayer) decoderInstance;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return internalPlayer;
    }

    public static Object getDecoderInstance(int planId){
        Object instance = null;
        try{
            Class clz = getSDKClass(PlayerConfig.getPlan(planId).getClassPath());
            if(clz!=null){
                Constructor constructor = getConstructor(clz);
                if(constructor!=null){
                    instance = constructor.newInstance();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return instance;
    }

    private static Constructor getConstructor(Class clz){
        Constructor result = null;
        try{
            result = clz.getConstructor();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private static Class getSDKClass(String classPath){
        Class result = null;
        try {
            result = Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

}
