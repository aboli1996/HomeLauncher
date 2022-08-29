package com.jio.homelauncher.launchersdk

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.widget.Toast

object LauncherAppInfo {

    fun getLauncherAppList(context : Context) : ArrayList<ModelLauncher> {
        val appInfoList: MutableList<ModelLauncher> = mutableListOf()
        try {
            val pm: PackageManager = context.packageManager
            val i: Intent = Intent(Intent.ACTION_MAIN)
            i.addCategory(Intent.CATEGORY_HOME)
            i.addCategory(Intent.CATEGORY_LAUNCHER)
            val list: ArrayList<ResolveInfo> =
                pm.queryIntentActivities(i, 0) as ArrayList<ResolveInfo>
            for (modelInfo in list.indices) {
                val appName = pm.getApplicationLabel(
                    pm.getApplicationInfo(
                        list.get(modelInfo).activityInfo.packageName,
                        PackageManager.GET_META_DATA
                    )
                )
                val appIcon = pm.getApplicationIcon(
                    pm.getApplicationInfo(
                        list.get(modelInfo).activityInfo.packageName,
                        PackageManager.GET_META_DATA
                    )
                )
                val versionCode = pm.getPackageInfo(
                    list.get(modelInfo).activityInfo.packageName,
                    PackageManager.GET_ACTIVITIES
                ).versionCode
                val versionName = pm.getPackageInfo(
                    list.get(modelInfo).activityInfo.packageName,
                    PackageManager.GET_ACTIVITIES
                ).versionName
                val model = ModelLauncher(
                    appName.toString(),
                    list.get(modelInfo).activityInfo.packageName,
                    appIcon,
                    "",
                    versionCode,
                    versionName
                )
                appInfoList.add(model)

            }
            appInfoList.sortBy { it.appName }

        }catch (e : Exception){
            e.printStackTrace()
        }
        return appInfoList as ArrayList<ModelLauncher>
    }

    fun launchApplication(context : Context, packageName : String, title: String){
        val pm : PackageManager = context.packageManager
        val i : Intent? = pm.getLaunchIntentForPackage(packageName)
        if(i != null){
            context.startActivity(i)
        }else{
            Utils.getSimpleDialog(context, context.resources.getString(R.string.str_app_uninstalled_msg),title,
                object : AlertDialogListener {
                    override fun onConfirmed(action: Boolean) {
                        if(action){
                            context.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                                )
                            )
                        }
                    }
                }).show()

        }
    }
}