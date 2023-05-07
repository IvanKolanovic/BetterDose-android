package com.ik3130.betterdose.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.ik3130.betterdose.data.models.Diary
import com.ik3130.betterdose.ui.Utils.Companion.TIME_FORMATTER
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class AlarmSchedulerImpl(private val context: Context) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun scheduler(item: Diary) {
        val zonedDatedTime = ZonedDateTime.of(
            LocalDateTime.from(TIME_FORMATTER.parse(item.takeAt)), ZoneId.systemDefault()
        )
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", "Time to take your ${item.drugName}")
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            zonedDatedTime.toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(item: Diary) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}