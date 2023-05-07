package com.ik3130.betterdose.scheduler

import com.ik3130.betterdose.data.models.Diary

interface AlarmScheduler {
    fun scheduler(item: Diary)
    fun cancel(item: Diary)
}