package com.github.skosvall.nextlvl

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MobileGamesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile_games)

        val dareOrDrinkButton = findViewById<Button>(R.id.DOR_button)
        val neverHaveIEverButton = findViewById<Button>(R.id.neverHaveIEverButton)
        val submitTextFull = findViewById<TextView>(R.id.submitText)
        val clickableString = getString(R.string.submit_your_own)

        makeTextLink(submitTextFull, clickableString, false, Color.RED, action = {
            val intent = Intent(this, AddSubmissionActivity::class.java)
            startActivity(intent)
        })

        dareOrDrinkButton.setOnClickListener {
            val intent = Intent(this, PrepareDareOrDrinkActivity::class.java)
            startActivity(intent)
        }

        neverHaveIEverButton.setOnClickListener {
            startActivity(Intent(this, PlayNeverHaveIEverActivity::class.java))
        }
    }

    private fun makeTextLink(textView: TextView, str: String, underlined: Boolean, color: Int?, action: (() -> Unit)? = null) {
        val spannableString = SpannableString(textView.text)
        Log.d("Test: ", spannableString.toString())
        val textColor = color ?: textView.currentTextColor
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                action?.invoke()
            }
            override fun updateDrawState(drawState: TextPaint) {
                super.updateDrawState(drawState)
                drawState.isUnderlineText = underlined
                drawState.color = textColor
            }
        }
        val index = spannableString.indexOf(str)
        spannableString.setSpan(clickableSpan, index, index + str.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.highlightColor = Color.TRANSPARENT
    }

}
