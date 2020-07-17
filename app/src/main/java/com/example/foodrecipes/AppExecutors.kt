package com.example.foodrecipes

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

object AppExecutors {

    private val networkIO: ScheduledExecutorService? = Executors.newScheduledThreadPool(3)

    fun networkIO(): ScheduledExecutorService{
        return networkIO!!
    }

}