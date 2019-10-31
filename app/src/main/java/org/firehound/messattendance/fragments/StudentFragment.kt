package org.firehound.messattendance.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_student.*
import org.firehound.messattendance.R
import org.firehound.messattendance.models.DateEntry
import org.firehound.messattendance.models.MessEntry
import org.firehound.messattendance.models.Student
import org.firehound.messattendance.utils.toast
import java.text.SimpleDateFormat
import java.util.*


class StudentFragment : Fragment() {

    var isAttending = false
    var meal = ""

    data class Elem(
        val isAttending: Boolean,
        val regNo: String,
        val time: Timestamp
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val db = FirebaseFirestore.getInstance()
        super.onViewCreated(view, savedInstanceState)
        val prefs = activity!!.getSharedPreferences(DetailsFragment.prefId, Context.MODE_PRIVATE)
        val regNo = prefs.getString("REG_NO", "NOT_FOUND")
        val mess = prefs.getString("MESS", "NOT_FOUND")

        if (regNo == "NOT_FOUND" || mess == "NOT_FOUND") {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, DetailsFragment()).disallowAddToBackStack().commit()
        }

        when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in 7..9 -> {
                timeText.text = "Attendance for breakfast"
                meal = "breakfast"
            }
            in 12..14 -> {
                timeText.text = "Attendance for lunch"
                meal = "lunch"
            }
            in 19..21 -> {
                timeText.text = "Attendance for dinner"
                meal = "dinner"
            }
            else -> {
                timeText.text = "There's no meal available right now"
                radioGroup.visibility = View.GONE
                submitButton.visibility = View.GONE
            }
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            isAttending = checkedId == radioAttend.id
        }

        submitButton.setOnClickListener {
            val dateString = SimpleDateFormat("dd.MM.yyyy").format(Date())
            val docRef = db.collection(mess!!).document(dateString)
            docRef.get().addOnSuccessListener { documentSnapshot ->
                var entry = documentSnapshot.toObject(MessEntry::class.java)
                if (entry == null) {
                    val dates = arrayListOf(
                        DateEntry(
                            dateString, arrayListOf(
                                Student(regNo!!)
                            )
                        )
                    )
                    dates[0].students?.get(0)?.meal?.put(meal, isAttending)
                    entry = MessEntry(
                        mess,
                        dates
                    )
                    db.collection(mess).document(dateString).set(entry)
                } else {
                    var studExists = false
                    var dateExists = false
                    entry.date?.forEach {
                        if (it.date == dateString) { // if today's date matches
                            dateExists = true
                            it.students?.forEach { student ->

                                if (student.regNo == regNo) { // if current student exists
                                    studExists = true
                                    student.meal[meal] = isAttending
                                }
                            }
                        }
                    }

                    if (!dateExists) {
                        val student = Student(regNo!!)
                        student.meal[meal] = isAttending
                        entry.date?.add(
                            DateEntry(
                                dateString,
                                arrayListOf(student)
                            )
                        )
                    }

                    if (!studExists && dateExists) {
                        entry.date?.forEach {
                            if (it.date == dateString) {
                                val student = Student(regNo!!)
                                student.meal[meal] = isAttending
                                it.students?.add(student)
                            }
                        }
                    }
                }
                docRef.set(entry)
                requireContext().toast("Successfully submitted.")
            }
        }
    }
}

