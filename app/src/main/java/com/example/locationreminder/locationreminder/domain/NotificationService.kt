package com.example.locationreminder.locationreminder.domain

import com.example.locationreminder.locationreminder.domain.model.Reminder


interface NotificationService {
    suspend fun showReminderNotification(reminder: Reminder)
}
