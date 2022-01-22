package com.example.topproject008_simplewebbrowser

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.ContentLoadingProgressBar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

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

    private val refreshLayut: SwipeRefreshLayout by lazy {
        findViewById(R.id.refreshLayout)
    }

    private val webView: WebView by lazy {
        findViewById(R.id.webView)
    }

    private val progressBar: ContentLoadingProgressBar by lazy {
        findViewById(R.id.progressBar)
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
            webChromeClient = WebChromeClient()
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
                val loadingUrl = v.text.toString()
                if(URLUtil.isNetworkUrl(loadingUrl)){
                    webView.loadUrl(loadingUrl)
                }else{
                    webView.loadUrl("http://$loadingUrl")
                }
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
//     새로고침
        refreshLayut.setOnRefreshListener {
            webView.reload()
        }

    }

    inner class WebViewClient : android.webkit.WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)

            progressBar.show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            refreshLayut.isRefreshing = false
            progressBar.hide()
            imageButtonBack.isEnabled = webView.canGoBack()
            imageButtonForward.isEnabled = webView.canGoForward()
            addressBar.setText(url)
        }
    }

    inner class WebchromeClient : android.webkit.WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progressBar.progress = newProgress
        }
    }

    companion object {
        private const val DEFAULT_URL = "http://www.goolge.com"
    }

}