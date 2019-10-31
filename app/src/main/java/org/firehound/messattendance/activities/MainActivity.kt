package org.firehound.messattendance.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.firehound.messattendance.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        toolbar.title = "Mess Attendance"

        studentButton.setOnClickListener {
            val intent = Intent(this, SelectionActivity::class.java)
            intent.putExtra("isStudent", true)
            startActivity(intent)
        }

        ownerButton.setOnClickListener {
            val intent = Intent(this, SelectionActivity::class.java)
            intent.putExtra("isStudent", false)
            startActivity(intent)
        }
    }

}
