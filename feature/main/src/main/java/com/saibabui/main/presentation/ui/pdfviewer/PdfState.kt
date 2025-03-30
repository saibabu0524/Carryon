package com.saibabui.main.presentation.ui.pdfviewer

import java.io.File

sealed class PdfState {
    object Loading : PdfState()
    data class Success(val file: File) : PdfState()
    data class Error(val message: String) : PdfState()
}