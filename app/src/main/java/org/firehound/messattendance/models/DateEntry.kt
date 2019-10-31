package org.firehound.messattendance.models

import java.util.*

class DateEntry {
    var date: String? = null
    var students: ArrayList<Student>? = null

    constructor(date: String, students: ArrayList<Student>) {
        this.date = date
        this.students = students
    }

    constructor() {}
}