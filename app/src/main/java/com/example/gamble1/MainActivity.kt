package com.example.gamble1

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var betTextView: TextView
    private lateinit var amountTextView: TextView
    private lateinit var slotImageViews: List<ImageView>
    private lateinit var enterSpinActivityButton : ImageView
    private var betAmount: Int = 0
    private var balance: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enterSpinActivityButton = findViewById(R.id.spin)
        enterSpinActivityButton.setOnClickListener {
            val intent = Intent(this, SpinActivity::class.java)
            startActivity(intent)
        }


        betTextView = findViewById(R.id.bet)
        amountTextView = findViewById(R.id.amount)
        slotImageViews = listOf(
            findViewById(R.id.img1),
            findViewById(R.id.img2),
            findViewById(R.id.img3),
            findViewById(R.id.img4),
            findViewById(R.id.img5),
            findViewById(R.id.img6),
            findViewById(R.id.img7),
            findViewById(R.id.img8),
            findViewById(R.id.img9)
        )


        balance = 100
        updateBalanceText()


        val playButton = findViewById<ImageView>(R.id.play)
        playButton.setOnClickListener {
            play()
        }

        val plusButton = findViewById<ImageView>(R.id.plus)
        plusButton.setOnClickListener {
            increaseBet()
        }

        val minusButton = findViewById<ImageView>(R.id.minus)
        minusButton.setOnClickListener {
            decreaseBet()
        }
    }

    private fun play() {
        if (betAmount == 0) {

            return
        }

        balance -= betAmount
        updateBalanceText()


        val random = Random()
        for (imageView in slotImageViews) {
            val animatorSet = AnimatorSet()
            val duration = 2000L
            val animators = mutableListOf<AnimatorSet>()
            val initialRotation = ObjectAnimator.ofFloat(imageView, View.ROTATION, 0f, 360f)
            initialRotation.duration = duration
            initialRotation.interpolator = AccelerateDecelerateInterpolator()

            val finalRotation = ObjectAnimator.ofFloat(imageView, View.ROTATION, 0f, 360f)
            finalRotation.duration = duration
            finalRotation.startDelay = duration
            finalRotation.interpolator = AccelerateDecelerateInterpolator()

            animatorSet.playSequentially(initialRotation, finalRotation)
            animatorSet.start()

            animators.add(animatorSet)
        }


        for (imageView in slotImageViews) {
            val randomImageId = resources.getIdentifier(
                "img_${random.nextInt(7) + 1}",
                "drawable",
                packageName
            )
            imageView.setImageResource(randomImageId)
        }
        val isWin = random.nextDouble() <= 0.55
        if (isWin) {

            balance += betAmount * 2
        }


        updateBalanceText()
    }

    private fun increaseBet() {
        betAmount += 25
        if (betAmount > balance) {
            betAmount = balance
        }
        updateBetText()
    }

    private fun decreaseBet() {
        betAmount -= 25
        if (betAmount < 0) {
            betAmount = 0
        }
        updateBetText()
    }

    private fun updateBalanceText() {
        amountTextView.text = balance.toString()
    }

    private fun updateBetText() {
        betTextView.text = betAmount.toString()
    }
}