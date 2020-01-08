package com.cjh.component_videoplayer.sample.play;

import android.content.Context;

import com.cjh.component_videoplayer.playerbase.receiver.GroupValue;
import com.cjh.component_videoplayer.playerbase.receiver.ReceiverGroup;
import com.cjh.component_videoplayer.sample.cover.LoadingCover;

import static com.cjh.component_videoplayer.sample.play.DataInter.ReceiverKey.KEY_COMPLETE_COVER;
import static com.cjh.component_videoplayer.sample.play.DataInter.ReceiverKey.KEY_CONTROLLER_COVER;
import static com.cjh.component_videoplayer.sample.play.DataInter.ReceiverKey.KEY_ERROR_COVER;
import static com.cjh.component_videoplayer.sample.play.DataInter.ReceiverKey.KEY_GESTURE_COVER;
import static com.cjh.component_videoplayer.sample.play.DataInter.ReceiverKey.KEY_LOADING_COVER;

/**
 * @author: caijianhui
 * @date: 2019/9/9 14:41
 * @description:
 */
public class ReceiverGroupManager {

    private static ReceiverGroupManager i;

    private ReceiverGroupManager(){
    }

    public static ReceiverGroupManager get(){
        if(null==i){
            synchronized (ReceiverGroupManager.class){
                if(null==i){
                    i = new ReceiverGroupManager();
                }
            }
        }
        return i;
    }

    public ReceiverGroup getLittleReceiverGroup(Context context){
        return getLiteReceiverGroup(context, null);
    }

    public ReceiverGroup getLittleReceiverGroup(Context context, GroupValue groupValue){
        ReceiverGroup receiverGroup = new ReceiverGroup(groupValue);
        receiverGroup.addReceiver(KEY_LOADING_COVER, new LoadingCover(context));
        //receiverGroup.addReceiver(KEY_COMPLETE_COVER, new CompleteCover(context));
        //receiverGroup.addReceiver(KEY_ERROR_COVER, new ErrorCover(context));
        return receiverGroup;
    }

    public ReceiverGroup getLiteReceiverGroup(Context context){
        return getLiteReceiverGroup(context, null);
    }

    public ReceiverGroup getLiteReceiverGroup(Context context, GroupValue groupValue){
        ReceiverGroup receiverGroup = new ReceiverGroup(groupValue);
        receiverGroup.addReceiver(KEY_LOADING_COVER, new LoadingCover(context));
        //receiverGroup.addReceiver(KEY_CONTROLLER_COVER, new ControllerCover(context));
        //receiverGroup.addReceiver(KEY_COMPLETE_COVER, new CompleteCover(context));
        //receiverGroup.addReceiver(KEY_ERROR_COVER, new ErrorCover(context));
        return receiverGroup;
    }

    public ReceiverGroup getReceiverGroup(Context context){
        return getReceiverGroup(context, null);
    }

    public ReceiverGroup getReceiverGroup(Context context, GroupValue groupValue){
        ReceiverGroup receiverGroup = new ReceiverGroup(groupValue);
        receiverGroup.addReceiver(KEY_LOADING_COVER, new LoadingCover(context));
        //receiverGroup.addReceiver(KEY_CONTROLLER_COVER, new ControllerCover(context));
        //receiverGroup.addReceiver(KEY_GESTURE_COVER, new GestureCover(context));
        //receiverGroup.addReceiver(KEY_COMPLETE_COVER, new CompleteCover(context));
        //receiverGroup.addReceiver(KEY_ERROR_COVER, new ErrorCover(context));
        return receiverGroup;
    }


}
