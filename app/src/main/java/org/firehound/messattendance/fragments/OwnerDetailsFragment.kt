package org.firehound.messattendance.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_owner_details.*
import kotlinx.android.synthetic.main.fragment_owner_details.view.*

import org.firehound.messattendance.R
import org.firehound.messattendance.utils.toast

/**
 * A simple [Fragment] subclass.
 */
class OwnerDetailsFragment : Fragment(), AdapterView.OnItemSelectedListener {
    var mess = ""
    private val messList = arrayListOf("Select your mess", "Darling Mess", "PR Mess", "SKC Mess", "Food Park", "Foodcy")

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
        mess = messList[position]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, messList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ownerMessSpinner.adapter = arrayAdapter
        ownerMessSpinner.onItemSelectedListener = this

        ownerDetailsSubmitButton.setOnClickListener {
            if (mess == "" || mess == messList[0]) {
                Toast.makeText(context!!, "Select your mess!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (view.passwordTIL.editText?.text.toString() != "password") {
                requireContext().toast("Incorrect password!")
                return@setOnClickListener
            }

            val sharedPref = activity!!.getSharedPreferences(DetailsFragment.prefId, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("OWNER_MESS", mess)
                commit()
            }
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, OwnerFragment()).disallowAddToBackStack().commit()
        }
    }
}
