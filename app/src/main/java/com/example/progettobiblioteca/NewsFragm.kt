package com.example.progettobiblioteca

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment


class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        val webView: WebView = view.findViewById(R.id.webview)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://www.vanityfair.it/article/film-tratti-da-libri-adattamenti")

        return view
    }
}
