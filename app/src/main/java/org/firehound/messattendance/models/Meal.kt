package org.firehound.messattendance.models

class Meal {
    var mealName: String? = null
    var willAttend: Boolean = false

    constructor() {}

    constructor(mealName: String, willAttend: Boolean) {
        this.mealName = mealName
        this.willAttend = willAttend
    }
}
