package com.example.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {

    lateinit var webview: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Brian's Website"
        webview = findViewById(R.id.webview)
        webviewSetup()
    }

    private fun webviewSetup() {
        webview.webViewClient = WebViewClient()

        webview.apply {
            loadUrl("http://www.brianveitch.com")
            settings.javaScriptEnabled = true
        }
    }

    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
        } else {
            super.onBackPressed()
        }
    }
}