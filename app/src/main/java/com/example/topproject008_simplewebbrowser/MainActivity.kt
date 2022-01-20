package com.example.topproject008_simplewebbrowser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private val addressBar: EditText by lazy {
        findViewById(R.id.editTextAddressBar)
    }

    private val webView: WebView by lazy {
        findViewById(R.id.webView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        bindViews()
    }

//    명시된 URL 불러오는 로직
    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        webView.apply{
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true // 자바 스크립트를 사용할 것이라는 코드
            loadUrl("http://www.goolge.com")
        }
    }
//    이벤트를 바인딩
//    onEditorActionListener발생했을 때
//    actionId가 ACTION_DONE이면 웹페이지에 로딩하는 방식
    private fun bindViews() {
        addressBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                webView.loadUrl(v.text.toString())
            }
            return@setOnEditorActionListener false
        }
    }
}