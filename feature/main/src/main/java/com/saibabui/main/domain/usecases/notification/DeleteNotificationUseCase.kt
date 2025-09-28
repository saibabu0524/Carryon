package com.saibabui.main.domain.usecases.notification

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class DeleteNotificationUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(notificationId: Int) = 
        homeRepository.deleteNotification(notificationId)
}