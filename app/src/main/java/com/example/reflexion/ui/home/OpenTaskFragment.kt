package com.example.reflexion.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.reflexion.R
import com.example.reflexion.databinding.OpenTaskFragmentBinding
import com.example.reflexion.viewmodels.OpenTaskViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OpenTaskFragment : Fragment() {

    companion object {
        fun newInstance() = OpenTaskFragment()
    }

    private val TAG = OpenTaskFragment::class.java.simpleName

    private val viewModel: OpenTaskViewModel by viewModels()
    private lateinit var binding: OpenTaskFragmentBinding

    private val database = FirebaseFirestore.getInstance()

    //Get user id reference
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.open_task_fragment, container, false)

        //This will get arguments from the home fragment
        //This will get the rating from argumnets as well

        //Extract the arguments
        val stars = arguments?.getString("stars")!!
        val task = arguments?.getString("task")!!
        val date = arguments?.getString("date")!!

        binding.rating.rating = stars.toFloat()
        binding.tvDescriptionOpen.text = task
        binding.tv1.text = date

        binding.tvShare.setOnClickListener {
            shareNote()

        }

        binding.tvDelete.setOnClickListener {
            deleteThisTask()
        }


        return binding.root
    }

    private fun shareNote() {

        val shareIntent = Intent(Intent.ACTION_SEND)
        val args = arguments

        val str = args?.getString("task")!!
        shareIntent.type = "text/plain"

        shareIntent.putExtra(Intent.EXTRA_TEXT, str)
        startActivity(shareIntent)

    }

    private fun deleteThisTask() {
        //Get database and user reference
        val toBeDeleted = arguments?.getString("id")!!


        val collectionReference =
            database.collection(userId!!)
        collectionReference.document(toBeDeleted).delete()
            .addOnSuccessListener {
                findNavController().navigate(R.id.action_openTaskFragment_to_nav_home)


            }
            .addOnFailureListener(object : OnFailureListener {
                override fun onFailure(p0: Exception) {
                    Log.e(TAG, p0.message.toString())
                }
            })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
