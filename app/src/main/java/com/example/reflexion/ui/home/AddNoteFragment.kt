package com.example.reflexion.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.reflexion.R
import com.example.reflexion.databinding.AddNoteFragmentBinding
import com.example.reflexion.viewmodels.AddNoteViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    //if these values are changed, make sure
    private val KEY_TASK = "TASKS"
    private val KEY_DATE = "DATE"
    private val KEY_STARS = "STARS";


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.add_note_fragment, container, false)



        binding.mbtnReset.setOnClickListener {
            binding.etDescription.text = null
        }

        binding.mbtnSave.setOnClickListener {
            saveNote()
        }







        return binding.root
    }

    private fun saveNote() {

        //We will use keys to pu the data in the form of a map
        val s = binding.ratingAdd.rating.toString()
        val str = binding.etDescription.text.toString()

        val c = Calendar.getInstance().time

        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val formattedDate: String = df.format(c)

        //Create a map
        val taskMap = HashMap<String, Any>()


        taskMap[KEY_TASK] = str
        taskMap[KEY_DATE] = formattedDate
        taskMap[KEY_STARS] = s

        //Create a new channel reference
        //The document takes id
        //Get id


        //Get the user id
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val id = database.collection("myTasksCollection").document().id


        database.collection(userId!!).document(id).set(taskMap)
            .addOnSuccessListener {


                val imm: InputMethodManager =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().windowToken, 0)
                findNavController().navigate(R.id.action_addNoteFragment_to_nav_home)

            }
            .addOnFailureListener(object : OnFailureListener {
                override fun onFailure(p0: Exception) {
                    Log.e(TAG, p0.toString() + " " + p0.message.toString())

                }
            })



    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }



}
