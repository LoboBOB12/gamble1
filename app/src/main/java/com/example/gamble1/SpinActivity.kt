package com.example.gamble1
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

import kotlin.random.Random

class SpinActivity : AppCompatActivity() {
    private var currentRotationAngle = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spin)

        if (savedInstanceState != null) {
            currentRotationAngle = savedInstanceState.getFloat(KEY_ROTATION_ANGLE, 0f)
            findViewById<ImageView>(R.id.koleso).rotation = currentRotationAngle
        }

        val kolesoImageView = findViewById<ImageView>(R.id.koleso)
        val playButtonImageView = findViewById<ImageView>(R.id.imageView5)

        playButtonImageView.setOnClickListener {
            spinWheel(kolesoImageView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putFloat(KEY_ROTATION_ANGLE, currentRotationAngle)
    }

    private fun spinWheel(kolesoImageView: ImageView) {
        val randomDegrees = Random.nextInt(360) + 1

        val rotateAnimation = RotateAnimation(
            currentRotationAngle, currentRotationAngle + randomDegrees.toFloat(),
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.duration = 3000
        rotateAnimation.fillAfter = true

        currentRotationAngle += randomDegrees.toFloat()

        kolesoImageView.startAnimation(rotateAnimation)
    }

    companion object {
        private const val KEY_ROTATION_ANGLE = "rotation_angle"
    }
}