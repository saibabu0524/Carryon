package com.saibabui.main.domain.usecases.template

import com.saibabui.network.home.repositories.HomeRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class CreateCustomTemplateUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        templateName: String,
        templateDescription: String,
        category: String?,
        file: MultipartBody.Part
    ) = homeRepository.createCustomTemplate(templateName, templateDescription, category, file)
}