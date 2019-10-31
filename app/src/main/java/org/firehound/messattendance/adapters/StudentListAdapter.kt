package org.firehound.messattendance.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import org.firehound.messattendance.R
import org.firehound.messattendance.models.Student

class StudentListAdapter(private var studentList: MutableList<Student>) :
    RecyclerView.Adapter<StudentViewHolder>() {
    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, null)
        return StudentViewHolder(view)
    }

    override fun getItemCount() = studentList.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.itemView.regNoText.text = studentList[position].regNo
        holder.itemView.isComingBreakfast.text = if (studentList[position].meal["breakfast"]!!) "Will attend breakfast" else "Won't attend breakfast"
        holder.itemView.isComingLunch.text = if (studentList[position].meal["lunch"]!!) "Will attend lunch" else "Won't attend lunch"
        holder.itemView.isComingDinner.text = if (studentList[position].meal["dinner"]!!) "Will attend dinner" else "Won't attend dinner"
    }

}

class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)