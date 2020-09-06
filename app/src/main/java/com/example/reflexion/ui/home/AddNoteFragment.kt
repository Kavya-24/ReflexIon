package com.example.reflexion.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.reflexion.R
import com.example.reflexion.databinding.AddNoteFragmentBinding
import com.example.reflexion.viewmodels.AddNoteViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.longToast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class AddNoteFragment : Fragment() {

    companion object {
        fun newInstance() = AddNoteFragment()
    }

    private val viewModel: AddNoteViewModel by viewModels()
    private lateinit var binding: AddNoteFragmentBinding

    private val TAG = AddNoteFragment::class.java.simpleName
    private val database = FirebaseFirestore.getInstance()

    private val KEY_TASK = "tasks"
    private val KEY_TIME = "date"
    private val KEY_STARS = "0";


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.add_note_fragment, container, false)


        binding.apply {
            if (etDescription.length() > 0) {
                mbtnReset.isEnabled = true
                mbtnSave.isEnabled = true
            }

            mbtnReset.setOnClickListener {
                etDescription.text = null
            }

            mbtnSave.setOnClickListener {
                saveNote()
            }

        }






        return binding.root
    }

    private fun saveNote() {

        //We will use keys to pu the data in the form of a map
        val s = binding.ratingAdd.numStars
        val str = binding.etDescription.text.toString()

        val c = Calendar.getInstance().time

        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val formattedDate: String = df.format(c)

        //Create a map
        val taskMap = HashMap<String, Any>()

        taskMap[KEY_TASK] = str
        taskMap[KEY_TIME] = df
        taskMap[KEY_STARS] = c

        //Create a new channel reference
        //The document takes id
        //Get id
        val id = database.collection("myTasksCollection").document().id

        database.collection("myTasksCollection").document(id).set(taskMap)
            .addOnSuccessListener {

                context?.longToast("Task added")

            }
            .addOnFailureListener(object : OnFailureListener {
                override fun onFailure(p0: Exception) {
                    context?.longToast(p0.toString())

                }
            })

    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}
