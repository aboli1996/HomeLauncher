package com.jio.homelauncher.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.jio.homelauncher.app.databinding.LayoutHomeBinding
import com.jio.homelauncher.launchersdk.*

class HomeActivity : AppCompatActivity() {

    private var launcherAdapter : LauncherAppAdapter? = null
    private var appList : ArrayList<ModelLauncher> = ArrayList()
    var searchView : SearchView? = null
    val TAG = "HomeActivity"

    private lateinit var binding: LayoutHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appList = LauncherAppInfo.getLauncherAppList(this)
        setUpData(appList)

    }

    private fun setUpData(appList : ArrayList<ModelLauncher>){
        setSupportActionBar(binding.toolBar)

        binding.rvApp.layoutManager = GridLayoutManager(this, 2)
        binding.rvApp.itemAnimator = DefaultItemAnimator()
        launcherAdapter = LauncherAppAdapter(this, appList)
        binding.rvApp.adapter = launcherAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_home, menu)

        val search = menu?.findItem(R.id.action_search)
        searchView= search?.actionView as SearchView
        searchView!!.queryHint = resources.getString(R.string.str_hint_search)

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i(TAG,"searchQuery onQueryTextSubmit query - $query")
           val filterList = appList.filter{it.appName.contains(query.toString(), ignoreCase = true)}
                launcherAdapter?.updateList(filterList)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }

        })

        searchView!!.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener{
            override fun onViewAttachedToWindow(p0: View?) {

            }

            override fun onViewDetachedFromWindow(p0: View?) {
                Log.i(TAG,"searchQuery onViewDetachedFromWindow")
                launcherAdapter?.updateList(appList)
            }
        })


        return true
    }

    override fun onBackPressed() {
        if(searchView?.isShown == true){
            searchView!!.clearFocus()
            launcherAdapter?.updateList(appList)
        }else{
            Utils.getSimpleDialog(this,getString(R.string.str_msg_exit_app),getString(R.string.app_name_splash), object :
                AlertDialogListener {
                override fun onConfirmed(action: Boolean) {
                    if(action){
                        finish()
                    }
                }
            }).show()
        }

    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG,"onResume")
        val freshList = LauncherAppInfo.getLauncherAppList(this)
        if(freshList.size > appList.size){
            launcherAdapter?.updateList(freshList)
        }else{
            launcherAdapter?.updateList(appList)
        }

    }
}