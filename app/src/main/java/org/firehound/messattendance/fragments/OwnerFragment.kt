package org.firehound.messattendance.fragments


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_owner.*
import org.firehound.messattendance.R
import org.firehound.messattendance.adapters.StudentListAdapter
import org.firehound.messattendance.models.MessEntry
import org.firehound.messattendance.models.Student
import org.firehound.messattendance.utils.toast
import java.text.SimpleDateFormat
import java.util.*

class OwnerFragment : Fragment() {

    private val entries = mutableListOf<String>()
    private var studentList = mutableListOf<Student>()
    private lateinit var adapter: StudentListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val db = FirebaseFirestore.getInstance()
        super.onViewCreated(view, savedInstanceState)
        val prefs = activity!!.getSharedPreferences(DetailsFragment.prefId, Context.MODE_PRIVATE)
        val mess = prefs.getString("OWNER_MESS", "NOT_FOUND")
        adapter = StudentListAdapter(studentList)
        displayStudents.adapter = adapter
        displayStudents.layoutManager = LinearLayoutManager(requireContext())

        if (mess == "NOT_FOUND") {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, OwnerDetailsFragment())
                .disallowAddToBackStack()
                .commit()
        }

        getDataButton.setOnClickListener {
            val dateString = SimpleDateFormat("dd.MM.yyyy").format(Date())
            Log.d(this.tag, dateString)
            studentList.clear()
            val docRef = db.collection(mess!!).get()
            docRef.addOnSuccessListener { documents ->
                entries.clear()
                for (document in documents) {
                    val entry = document.toObject(MessEntry::class.java)
                    entry.date?.forEach {
                        Log.d("TAG", "${it.date} $dateString")
                        if (it.date!! == dateString) {
                            studentList.addAll(it.students!!)
                        }
                    }
                    Log.d("TAG", "$studentList")
                    adapter.notifyDataSetChanged()
                }
                if (studentList.isEmpty()) {
                    requireContext().toast("No entries found for today!")
                }
            }
        }
    }
}
