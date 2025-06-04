package com.saibabui.main.data

import com.saibabui.ui.Template


interface TemplateApiService {
    fun getTemplates(): List<Template>
}