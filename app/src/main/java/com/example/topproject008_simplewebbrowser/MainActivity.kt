package com.example.topproject008_simplewebbrowser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    private val imageButtonHome: ImageButton by lazy {
        findViewById(R.id.imageButtonHome)
    }

    private val addressBar: EditText by lazy {
        findViewById(R.id.editTextAddressBar)
    }

    private val imageButtonBack: ImageButton by lazy {
        findViewById(R.id.imageButtonBack)
    }

    private val imageButtonForward: ImageButton by lazy {
        findViewById(R.id.imageButtonForward)
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

    // back버튼 눌렀을 때
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    //    명시된 URL 불러오는 로직
    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true // 자바 스크립트를 사용할 것이라는 코드
            loadUrl(DEFAULT_URL)
        }
    }

    //    이벤트를 바인딩
//    onEditorActionListener발생했을 때
//    actionId가 ACTION_DONE이면 웹페이지에 로딩하는 방식
    private fun bindViews() {
//        홈버튼
        imageButtonHome.setOnClickListener {
            webView.loadUrl(DEFAULT_URL)
        }

        addressBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                webView.loadUrl(v.text.toString())
            }
            return@setOnEditorActionListener false
        }

//   imageButtonBack 클릭 이벤트 핸들링
//   뒤로가기
        imageButtonBack.setOnClickListener {
            webView.goBack()
        }

//    앞으로가기
        imageButtonForward.setOnClickListener {
            webView.goForward()
        }
    }

    companion object{
        private  const val DEFAULT_URL = "http://www.goolge.com"
    }
}