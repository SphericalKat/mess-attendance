package org.firehound.messattendance.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_student_selection.*
import org.firehound.messattendance.R
import org.firehound.messattendance.fragments.OwnerFragment
import org.firehound.messattendance.fragments.StudentFragment

class SelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_selection)

        setSupportActionBar(toolbar2)
        val isStudent = intent.getBooleanExtra("isStudent", true)

        if (isStudent) {
            toolbar2.title = "Student options"
            supportFragmentManager.beginTransaction().replace(R.id.frag_container, StudentFragment()).commit()
        } else {
            toolbar2.title = "Owner options"
            supportFragmentManager.beginTransaction().replace(R.id.frag_container, OwnerFragment()).commit()
        }
    }
}
