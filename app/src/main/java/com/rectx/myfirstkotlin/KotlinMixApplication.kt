package com.rectx.myfirstkotlin

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by thuan on 7/3/16.
 */

class KotlinMixApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        var config: RealmConfiguration? = RealmConfiguration.Builder(applicationContext).name("kotlinmix.realm").build()
        Realm.setDefaultConfiguration(config)
        Realm.deleteRealm(config)
    }
}