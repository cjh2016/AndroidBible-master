package com.cjh.component_videoplayer.playerbase.config;

import androidx.collection.SparseArrayCompat;

import com.cjh.component_videoplayer.playerbase.entity.DecoderPlan;
import com.cjh.component_videoplayer.playerbase.player.SysMediaPlayer;

/**
 * @author: caijianhui
 * @date: 2019/8/9 14:09
 * @description:
 */
public class PlayerConfig {

    public static final int DEFAULT_PLAN_ID = 0;

    //default decoder plan id is use System MediaPlayer.
    private static int defaultPlanId = DEFAULT_PLAN_ID;

    //decoder plans arrays.
    private static SparseArrayCompat<DecoderPlan> mPlans;

    //Whether or not use the default NetworkEventProducer.
    //default state false.
    private static boolean useDefaultNetworkEventProducer = false;

    private static boolean playRecordState = false;

    static {
        mPlans = new SparseArrayCompat<>(2);

        //add default plan
        DecoderPlan defaultPlan = new DecoderPlan(DEFAULT_PLAN_ID, SysMediaPlayer.class.getName(),"MediaPlayer");
        addDecoderPlan(defaultPlan);
        //set default plan id
        setDefaultPlanId(DEFAULT_PLAN_ID);
    }

    public static void addDecoderPlan(DecoderPlan plan){
        mPlans.put(plan.getIdNumber(), plan);
    }

    /**
     * setting default DecoderPlanId.
     * @param planId
     */
    public static void setDefaultPlanId(int planId){
        defaultPlanId = planId;
    }

    /**
     * get current DecoderPlanId.
     * @return
     */
    public static int getDefaultPlanId(){
        return defaultPlanId;
    }

    public static DecoderPlan getDefaultPlan(){
        return getPlan(defaultPlanId);
    }

    public static DecoderPlan getPlan(int planId){
        return mPlans.get(planId);
    }

    /**
     * Judging the legality of planId.
     * @param planId
     * @return
     */
    public static boolean isLegalPlanId(int planId){
        DecoderPlan plan = getPlan(planId);
        return plan!=null;
    }

    //if you want to use default NetworkEventProducer, set it true.
    public static void setUseDefaultNetworkEventProducer(boolean useDefaultNetworkEventProducer) {
        PlayerConfig.useDefaultNetworkEventProducer = useDefaultNetworkEventProducer;
    }

    public static boolean isUseDefaultNetworkEventProducer() {
        return useDefaultNetworkEventProducer;
    }

    public static void playRecord(boolean open){
        playRecordState = open;
    }

    public static boolean isPlayRecordOpen(){
        return playRecordState;
    }

}
