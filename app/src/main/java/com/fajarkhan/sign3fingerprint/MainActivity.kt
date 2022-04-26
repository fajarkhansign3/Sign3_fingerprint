package com.fajarkhan.sign3fingerprint

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fajarkhan.sign3fingerprint.databinding.ActivityMainBinding
import com.fingerprintjs.android.fingerprint.Configuration
import com.fingerprintjs.android.fingerprint.FingerprinterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialization
        val fingerprinter = FingerprinterFactory
            .getInstance(applicationContext, Configuration(version = 3))


// Usage
        fingerprinter.getFingerprint { fingerprintResult ->
            val fingerprint = fingerprintResult.fingerprint
            binding.fingerprint.text = "Fingerprint : $fingerprint"
        }

        fingerprinter.getDeviceId { result ->
            val deviceId = result.deviceId
            binding.deviceId.text = "Device Id : $deviceId"
        }

        binding.copyfingerprint.setOnClickListener { copyTextToClipboard(binding.fingerprint) }
        binding.copydeviceId.setOnClickListener { copyTextToClipboard(binding.deviceId) }


    }

    private fun copyTextToClipboard(textView: TextView) {
        val textToCopy = textView.text
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_LONG).show()
    }
}