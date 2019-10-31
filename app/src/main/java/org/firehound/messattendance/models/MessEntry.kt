package org.firehound.messattendance.models

import java.util.ArrayList

class MessEntry {
    var messName: String? = null
    var date: ArrayList<DateEntry>? = null

    constructor(messName: String, date: ArrayList<DateEntry>) {
        this.messName = messName
        this.date = date
    }

    constructor() {}
}

