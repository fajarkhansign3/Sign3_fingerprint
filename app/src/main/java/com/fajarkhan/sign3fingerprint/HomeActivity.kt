package com.fajarkhan.sign3fingerprint

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fajarkhan.sign3fingerprint.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindings = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bindings.root)


        bindings.FingerprintJS.setOnClickListener { startActivity(Intent(this@HomeActivity, MainActivity::class.java)) }

        bindings.FingerprintJSPro.setOnClickListener { startActivity(Intent(this@HomeActivity, FingerprintProActivity::class.java)) }
    }
}