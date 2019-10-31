package org.firehound.messattendance.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*

import org.firehound.messattendance.R
import org.firehound.messattendance.utils.toast

class DetailsFragment : Fragment(), AdapterView.OnItemSelectedListener {
    companion object {
        val prefId = "com.firehound.messattendance.PREFS"
    }



    var mess = ""
    private val messList = arrayListOf("Select your mess", "Darling Mess", "PR Mess", "SKC Mess", "Food Park", "Foodcy")

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
        mess = messList[position]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, messList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        messSpinner.adapter = arrayAdapter

        messSpinner.onItemSelectedListener = this

        detailsSubmitButton.setOnClickListener {
            val regNo = regNoEditText.text

            if (mess == "" || mess == messList[0]) {
                Toast.makeText(context!!, "Select your mess!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (view.passwordTIL.editText?.text.toString().toLowerCase() != regNo.toString().toLowerCase()) {
                requireContext().toast("Incorrect password!")
                return@setOnClickListener
            }

            val sharedPref = activity!!.getSharedPreferences(prefId, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("REG_NO", regNo.toString())
                putString("MESS", mess)
                commit()
            }
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, StudentFragment()).disallowAddToBackStack().commit()
        }
    }
}
