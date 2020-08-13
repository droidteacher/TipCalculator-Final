package hu.prooktatas.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val tag = "KZs"

    private lateinit var seekBar: SeekBar
    private lateinit var amountTextView: TextView
    private lateinit var totalTextViewLeft: TextView
    private lateinit var totalTextViewRight: TextView
    private lateinit var customPercentTextView: TextView
    private lateinit var defaultTipAmountTextView: TextView
    private lateinit var customTipAmountTextView: TextView


    private val defaultAmount = 1000
    private val defaultPercent = 0.15

    private val customPercent: Double
        get() {
            return seekBar.progress / 100.0
        }


    private val seekBarListener = object: SeekBar.OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            // do nothing
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            Log.d(tag, "progress: ${seekBar?.progress}, customPercent: $customPercent")
            updateUI()
        }

    }

    private val amountListener = object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            Log.d(tag, "afterTextChanged")
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            updateUI()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.variant)

        seekBar = findViewById(R.id.sbCustomTip)
        seekBar.max = 30
        seekBar.progress = 18
        seekBar.setOnSeekBarChangeListener(seekBarListener)

        amountTextView = findViewById(R.id.etAmount)
        amountTextView.text = defaultAmount.toString()
        amountTextView.addTextChangedListener(amountListener)

        totalTextViewLeft = findViewById(R.id.tvDefaultTipTotal)
        totalTextViewRight = findViewById(R.id.tvCustomTipTotal)
        customPercentTextView = findViewById(R.id.tvCustomPercent)
        defaultTipAmountTextView = findViewById(R.id.tvDefaultTipAmount)
        customTipAmountTextView = findViewById(R.id.tvCustomTipAmount)
    }

    override fun onStart() {
        super.onStart()
        updateUI()
    }

    private fun updateUI() {
        customPercentTextView.text = "${seekBar.progress}%"

        val amount = when (amountTextView.text.isNotEmpty()) {
            true -> amountTextView.text.toString().toInt()
            else -> 0
        }

        val tipCustom = (amount * customPercent).toInt()
        val tipDefault = (amount * defaultPercent).toInt()

        val totalCustom = amount + tipCustom
        val totalDefault = amount + tipDefault

        customTipAmountTextView.text = "$tipCustom"
        defaultTipAmountTextView.text = "$tipDefault"

        totalTextViewLeft.text = "$totalDefault"
        totalTextViewRight.text = "$totalCustom"
    }



}