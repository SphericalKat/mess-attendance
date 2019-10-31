package org.firehound.messattendance.models

class Student {
    var regNo: String? = null

    var meal = mutableMapOf("breakfast" to false, "lunch" to false, "dinner" to false)

    constructor(regNo: String) {
        this.regNo = regNo
    }

    constructor() {}
}
