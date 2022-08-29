package com.jio.homelauncher.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jio.homelauncher.app.databinding.LayoutSplashBinding
import java.util.*

class SplashActivity : AppCompatActivity() {

    private lateinit var binding : LayoutSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val runSplash : Timer = Timer()
        val showSplash = object : TimerTask(){
            override fun run() {
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
        runSplash.schedule(showSplash,3000)

    }
}