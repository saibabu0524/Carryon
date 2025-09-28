package com.saibabui.main.domain.usecases.notification

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class MarkAllNotificationsAsReadUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() = homeRepository.markAllNotificationsAsRead()
}