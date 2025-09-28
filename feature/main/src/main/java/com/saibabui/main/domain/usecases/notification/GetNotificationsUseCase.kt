package com.saibabui.main.domain.usecases.notification

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        page: Int = 1, 
        limit: Int = 10, 
        isRead: Boolean? = null, 
        notificationType: String? = null
    ) = homeRepository.getNotifications(page, limit, isRead, notificationType)
}