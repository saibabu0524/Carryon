package com.saibabui.main.presentation.ui.pdfviewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException

class ResumePreviewViewModel : ViewModel() {
    private val _pdfState = MutableStateFlow<PdfState>(PdfState.Loading)
    val pdfState: StateFlow<PdfState> = _pdfState.asStateFlow()

    fun loadPdf(resumeId: String) {
        viewModelScope.launch {
            _pdfState.value = PdfState.Loading
            try {
                val url = getResumePdfUrl(resumeId)
                val file = downloadPdf(url)
                _pdfState.value = PdfState.Success(file)
            } catch (e: Exception) {
                _pdfState.value = PdfState.Error("Failed to load PDF: ${e.message}")
            }
        }
    }

    private suspend fun downloadPdf(url: String): File = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Failed to download file: $response")

        val tempFile = File.createTempFile("resume", ".pdf")
        response.body?.byteStream()?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        tempFile
    }
}

// Placeholder function to get the PDF URL (replace with your actual API)
fun getResumePdfUrl(resumeId: String): String {
    return "https://example.com/resumes/$resumeId.pdf"
}