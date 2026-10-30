package com.omk.spinwin

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.Color
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlin.random.Random

class MainActivity : Activity() {
    private var coins = 100
    private var spinning = false
    private lateinit var coinText: TextView
    private lateinit var resultText: TextView
    private lateinit var spinButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(28, 32, 28, 24)
            setBackgroundColor(Color.rgb(16, 21, 43))
        }

        val title = TextView(this).apply {
            text = "🎡 OMK SPIN & WIN"
            textSize = 28f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
        }
        root.addView(title, matchWrap())

        coinText = TextView(this).apply {
            text = "🪙 Coins: $coins"
            textSize = 22f
            setTextColor(Color.rgb(255, 215, 80))
            gravity = Gravity.CENTER
            setPadding(0, 28, 0, 24)
        }
        root.addView(coinText, matchWrap())

        val wheel = TextView(this).apply {
            text = "🎁\n\nSPIN\n\n🎯"
            textSize = 34f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setBackgroundColor(Color.rgb(48, 62, 112))
            setPadding(30, 28, 30, 28)
        }
        root.addView(wheel, LinearLayout.LayoutParams(420, 420).apply {
            gravity = Gravity.CENTER
            bottomMargin = 24
        })

        resultText = TextView(this).apply {
            text = "Spin karo aur virtual coins jeeto!"
            textSize = 17f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(0, 8, 0, 20)
        }
        root.addView(resultText, matchWrap())

        spinButton = Button(this).apply {
            text = "SPIN NOW"
            textSize = 19f
            setOnClickListener { spin() }
        }
        root.addView(spinButton, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ))

        val adNote = TextView(this).apply {
            text = "Ad space (test mode) • Ads se developer ko earning ho sakti hai"
            textSize = 12f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 22, 0, 6)
        }
        root.addView(adNote, matchWrap())

        // Google test banner ad. Replace with your own AdMob ad-unit ID before release.
        val adView = AdView(this).apply {
            setAdSize(AdSize.BANNER)
            adUnitId = "ca-app-pub-3940256099942544/6300978111"
            loadAd(AdRequest.Builder().build())
        }
        root.addView(adView, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply { gravity = Gravity.CENTER })

        setContentView(root)
    }

    private fun spin() {
        if (spinning) return
        if (coins < 10) {
            resultText.text = "Coins kam hain! Demo mein free coins reset kar sakte hain."
            coins += 50
            coinText.text = "🪙 Coins: $coins"
            return
        }
        spinning = true
        coins -= 10
        coinText.text = "🪙 Coins: $coins"
        spinButton.isEnabled = false
        resultText.text = "Wheel ghoom raha hai..."
        spinButton.postDelayed({
            val prize = listOf(0, 5, 10, 20, 50, 100).random()
            coins += prize
            coinText.text = "🪙 Coins: $coins"
            resultText.text = if (prize == 0) "Aaj koi prize nahi. Phir try karo!" else "🎉 Mubarak! $prize virtual coins mile!"
            spinButton.isEnabled = true
            spinning = false
        }, 1800)
    }

    private fun matchWrap() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )
}
