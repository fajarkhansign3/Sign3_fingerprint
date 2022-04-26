package com.fajarkhan.sign3fingerprint

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.os.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.fajarkhan.sign3fingerprint.databinding.ActivityFingerprintProBinding
import com.fingerprintjs.android.fpjs_pro.Configuration
import com.fingerprintjs.android.fpjs_pro.FingerprintJSFactory
import java.lang.StringBuilder
import java.time.ZonedDateTime

class FingerprintProActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFingerprintProBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFingerprintProBinding.inflate(layoutInflater)
        setContentView(binding.root)



//        // Init interface
//        val factory = FingerprintJSFactory(binding.aspWebview.context.applicationContext)
//        val configuration = Configuration(
//            apiToken = "s3QrX0jzHadUkdowQpmX"
//        )
//        val fpjsInterface = factory.createInterface(configuration)
//
//// Add interface to the webview
//        binding.aspWebview.addJavascriptInterface(
//            fpjsInterface,
//            "fpjs-pro-android"
//        )
//
//        // Use embedded webview in the app instead of the default new app
//        binding.aspWebview.setWebViewClient(WebViewClient())
//
//// Enable javascript inside the webview
//        val webSettings: WebSettings =  binding.aspWebview.getSettings()
//        webSettings.javaScriptEnabled = true
//
//// Load url with the injected and configured FingerprintJS Pro agent
//        binding.aspWebview.loadUrl("https://sign3-test-webview.herokuapp.com/")


        // Initialization
        val factory = FingerprintJSFactory(applicationContext)
        val configuration = Configuration(
            apiToken = "pLhab1dpZUs8IRsv1wfT"
        )



        val fpjsClient = factory.createInstance(
            configuration
        )



        fpjsClient.getVisitorId(
            listener = { visitorId ->
                val visitorIds = visitorId.visitorId
                val androidId = visitorId.androidId
                val gsfId = visitorId.gsfId
                val mediaDrmId = visitorId.mediaDrmId
                val s67 = visitorId.s67
                val manufacturerName = getPhoneDeviceName()
                val applicationsNamesList = getInstalledApps(false).toString()
                val systemApplicationsList = getInstalledApps(true).toString()
                val timezone = ZonedDateTime.now().getZone().toString()

                val bm = applicationContext.getSystemService(BATTERY_SERVICE) as BatteryManager
                val batLevel:Int = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)


                runOnUiThread {
                    binding.visitorId.text = "visitor Id : $visitorIds"
                    binding.androidId.setText("androidId : $androidId")
                    binding.gsfId.setText("gsfId : $gsfId")
                    binding.mediaDrmId.setText("mediaDrmId : $mediaDrmId")
                    binding.s67.setText("s67 : $s67")
                    binding.manufacturerName.setText("manufacturerName : $manufacturerName")
                    binding.applicationsNamesList.setText("applicationsNamesList : $applicationsNamesList")
                    binding.systemApplicationsList.setText("systemApplicationsList : $systemApplicationsList")
                    binding.timezone.setText("timezone : $timezone")
                    binding.batLevel.setText("batLevel : $batLevel")


                }

            },
            errorListener = { error ->
                Handler(Looper.getMainLooper()).post {
                    binding.visitorId.text = "Error description : $error.description \n\n Request ID : ${error.requestId}"
                }

            })
    }

    fun getPhoneDeviceName():String {
        val model = Build.MODEL // returns model name
        return model;
    }

    private fun getInstalledApps(isSystem: Boolean): StringBuilder {
        val sb = StringBuilder()
        val packs = packageManager.getInstalledPackages(0)
        for (i in packs.indices) {
            val p = packs[i]
            if (isSystemPackage(p,isSystem)) {
                val appName = p.applicationInfo.loadLabel(packageManager).toString()
                val icon = p.applicationInfo.loadIcon(packageManager)
                sb.append(p.applicationInfo.packageName)
            }
        }
        return sb
    }


    private fun isSystemPackage(pkgInfo: PackageInfo, isSystem: Boolean): Boolean {
        return if(isSystem)
            pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
        else
            pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0
    }
}