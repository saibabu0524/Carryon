package com.saibabui.main.domain.usecases.profile

import com.saibabui.network.home.repositories.HomeRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class UploadAvatarUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(file: File) {
        val requestFile = file.asRequestBody("image/*".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
        homeRepository.uploadAvatar(multipartBody)
    }
}