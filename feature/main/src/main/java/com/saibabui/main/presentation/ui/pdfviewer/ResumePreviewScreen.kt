package com.saibabui.main.presentation.ui.pdfviewer

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.saibabui.ui.PrimaryButton

@Composable
fun PdfViewerScreen(
    viewModel: PdfViewerViewModel = PdfViewerViewModel(),
    url: String = "https://pdfobject.com/pdf/sample.pdf"
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // WebView to display the PDF
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            viewModel.setLoading(true)
                            viewModel.setError(null)
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            viewModel.setLoading(false)
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            if (request?.isForMainFrame == true) {
                                viewModel.setError("Failed to load PDF: ${error?.description}")
                                viewModel.setLoading(false)
                            }
                        }
                    }
                    loadUrl("https://docs.google.com/gview?embedded=true&url=$url")
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Loading indicator
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        // Error message
        errorMessage?.let { message ->
            Text(
                text = message,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Button to use the template
        Button(
            onClick = { viewModel.useTemplate() },
            enabled = !isLoading && errorMessage == null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("Use this template")
        }
    }
}