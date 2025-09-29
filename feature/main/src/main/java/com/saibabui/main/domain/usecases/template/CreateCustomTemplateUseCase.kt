package com.saibabui.main.domain.usecases.template

import com.saibabui.network.home.repositories.HomeRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class CreateCustomTemplateUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        file: MultipartBody.Part
    ) = homeRepository.createCustomTemplate(file)
}