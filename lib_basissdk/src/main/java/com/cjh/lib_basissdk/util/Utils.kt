package com.cjh.lib_basissdk.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.annotation.NonNull
import java.lang.IllegalArgumentException
import java.lang.NullPointerException
import java.lang.UnsupportedOperationException
import java.lang.ref.WeakReference
import java.lang.reflect.InvocationTargetException
import java.util.LinkedList

/**
 * @author: caijianhui
 * @date: 2019/5/28 9:47
 * @description:
 */
class Utils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        private var sApplication: Application? = null

        private var sTopActivityWeakRef: WeakReference<Activity>? = null

        val sActivityList = LinkedList<Activity>()

        private val mCallbacks = object : Application.ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                sActivityList.add(activity)
                setTopActivityWeakRef(activity)
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                if (!sActivityList.contains(activity)) return
                sActivityList.remove(activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

            }
        }

        fun removeActivity(activity: Activity) {
            sActivityList.remove(activity)
        }

        val isFullScreenChatMode: Boolean
                get() {
                    for (aActivity in sActivityList) {
                        if ("RobotActivity" == aActivity.javaClass.simpleName) {
                            return true
                        }
                    }
                    return false
                }

        fun init(@NonNull app: Application) {
            sApplication = app
            app.registerActivityLifecycleCallbacks(mCallbacks)
        }

        private fun setTopActivityWeakRef(activity: Activity) {
            if (null == sTopActivityWeakRef || activity != sTopActivityWeakRef?.get()) {
                sTopActivityWeakRef = WeakReference<Activity>(activity)
            }
        }

        val appNoAuto: Application?
            get() {
                if (null != sApplication) return sApplication
                throw NullPointerException("u should init first")
            }

        val app: Application?
            get() {
                if (null != sApplication) return sApplication
                try {
                    @SuppressLint("PrivateApi")
                    val activityThread = Class.forName("android.app.ActivityThread")
                    val at = activityThread.getMethod("currentActivityThread").invoke(null)
                    val app = activityThread.getMethod("getApplication").invoke(at)
                        ?: throw NullPointerException("u should init first")
                    init(app as Application)
                    return sApplication
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }
                throw NullPointerException("u should init first")
            }

        private val topActivity: Activity?
            get() {
                var activityThreadClass: Class<*>? = null
                try {
                    activityThreadClass = Class.forName("android.app.ActivityThread")
                    val activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null)
                    val activitiesField = activityThreadClass.getDeclaredField("mActivities")
                    activitiesField.isAccessible = true
                    val activities = activitiesField.get(activityThread) as Map<*, *>
                    for (activityRecord in activities.values) {
                        val activityRecordClass = activityRecord?.javaClass
                        val pausedField = activityRecordClass?.getDeclaredField("paused")
                        pausedField?.isAccessible = true
                        if (!pausedField!!.getBoolean(activityRecord)) {
                            val activityField = activityRecordClass.getDeclaredField("activity")
                            activityField.isAccessible = true
                            val activity = activityField.get(activityRecord) as Activity
                            sTopActivityWeakRef = WeakReference<Activity>(activity)
                            return activity
                        }
                    }
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: NoSuchFieldError) {
                    e.printStackTrace()
                }
                return null
            }

        val currActivity: Activity?
            get() {
                app
                return if (null != sTopActivityWeakRef) sTopActivityWeakRef?.get() else topActivity
            }
    }
}