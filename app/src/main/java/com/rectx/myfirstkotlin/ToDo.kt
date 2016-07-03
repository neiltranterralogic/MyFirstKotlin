package com.rectx.myfirstkotlin

import io.realm.RealmObject

/**
 * Created by thuan on 7/3/16.
 */
class ToDo: RealmObject() {
    var id: String? = null
    var title: String? = null
    var description: String? = null
}