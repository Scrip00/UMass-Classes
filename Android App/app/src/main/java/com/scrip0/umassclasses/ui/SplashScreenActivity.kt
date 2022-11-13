package com.scrip0.umassclasses.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.scrip0.umassclasses.R


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_start_splash);
        val d = getDrawable(R.drawable.avd_anim) as AnimatedVectorDrawable
        val imageView: ImageView = findViewById(R.id.imageView)
        imageView.setImageDrawable(d)
        d.registerAnimationCallback(object : Animatable2.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                Log.d("LOLLMAO17", "ENDDDD")
                super.onAnimationEnd(drawable)
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            }
        })
        d.start()
    }
}