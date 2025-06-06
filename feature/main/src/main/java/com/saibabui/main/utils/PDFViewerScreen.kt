package com.saibabui.main.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.rajat.pdfviewer.HeaderData
import com.rajat.pdfviewer.PdfRendererView
import com.rajat.pdfviewer.compose.PdfRendererViewCompose
import com.rajat.pdfviewer.util.PdfSource


@Composable
fun PDFViewerScreen(modifier: Modifier = Modifier, pdfUrl: String) {
    PdfRendererViewCompose(
        source = PdfSource.Remote(url = pdfUrl),
        lifecycleOwner = LocalLifecycleOwner.current,
        modifier = Modifier,
        headers = HeaderData(mapOf("Authorization" to "123456789")),
        statusCallBack = object : PdfRendererView.StatusCallBack {
            // Override functions here
            override fun onPdfRenderStart() {

            }
        },
        zoomListener = object : PdfRendererView.ZoomListener {
            // Override functions here
            override fun onZoomChanged(isZoomedIn: Boolean, scale: Float) {

            }
        }
    )
}