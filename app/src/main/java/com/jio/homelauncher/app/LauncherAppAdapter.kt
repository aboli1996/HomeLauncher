package com.jio.homelauncher.app

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jio.homelauncher.app.databinding.LayoutAppListItemBinding
import com.jio.homelauncher.launchersdk.LauncherAppInfo
import com.jio.homelauncher.launchersdk.ModelLauncher
import java.util.logging.Logger

class LauncherAppAdapter  (var context : Context, list : ArrayList<ModelLauncher>)
    : RecyclerView.Adapter<LauncherAppAdapter.LauncherViewHolder>() {

    var appList: ArrayList<ModelLauncher> = ArrayList()
    val TAG = "LauncherAppAdapter"

    init {
        this.appList = list
    }

    fun updateList(list: List<ModelLauncher>) {
        Log.i(TAG,"update App list")
        appList = ArrayList()
        appList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LauncherViewHolder {
        val binding = LayoutAppListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LauncherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LauncherAppAdapter.LauncherViewHolder, position: Int) {
        val model: ModelLauncher = appList.get(position)
            Glide.with(holder.itemView.context).load(model.icon).
            error(context.resources.getDrawable(R.drawable.ic_baseline_broken_image_24)).into(holder.binding.imgAppIcon)

        holder.binding.txtAppName.text = model.appName
        holder.binding.txtVersionName.text = model.versionName
        val pm : PackageManager = context.packageManager
        val i : Intent? = pm.getLaunchIntentForPackage(model.packageName)
        if(i == null){
            holder.binding.txtStatus.setText(context.resources.getString(R.string.str_uninstalled))
        }else{
            holder.binding.txtStatus.setText(context.resources.getString(R.string.str_installed))
        }

        holder.binding.layoutConstraint.setOnClickListener(View.OnClickListener {
            LauncherAppInfo.launchApplication(context, model.packageName,context.resources.getString(R.string.app_name_splash))
        })

    }

    override fun getItemCount(): Int {
        Log.i(TAG," getItemCount -$appList.size")
        return appList.size
    }

    inner class LauncherViewHolder(val binding: LayoutAppListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}