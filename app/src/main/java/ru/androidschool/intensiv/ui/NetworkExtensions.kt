package ru.androidschool.intensiv.ui

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.Locale

fun <T> Single<T>.applySchedulers(
    subscribeOnScheduler: Scheduler = Schedulers.io(),
    observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()
): Single<T> {
    return this.subscribeOn(subscribeOnScheduler)
        .observeOn(observeOnScheduler)
}

fun getLanguage(): String = Locale.getDefault().toLanguageTag()