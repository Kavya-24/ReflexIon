package com.example.reflexion.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reflexion.R
import com.example.reflexion.adapters.OnItemClickListener
import com.example.reflexion.adapters.TasksAdapter
import com.example.reflexion.databinding.FragmentHomeBinding
import com.example.reflexion.models.Task
import com.example.reflexion.viewmodels.HomeViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.longToast

class HomeFragment : Fragment(), OnItemClickListener {

    private val homeViewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding

    //Get reference to the document reference
    private val database = FirebaseFirestore.getInstance()
//    private val taskReference = database.document("myTasksCollection")

    private lateinit var adapter: TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)


        binding.fabAdd.setOnClickListener {
            this.findNavController().navigate(R.id.action_nav_home_to_addNoteFragment)
        }

        adapter = TasksAdapter(this)

        //Init the recycler view
        binding.apply {
            val layoutManager = LinearLayoutManager(context)
            rvNotes.layoutManager = layoutManager
            rvNotes.adapter = adapter
        }

        getDocuments()


        return binding.root
    }

    private fun getDocuments() {

        //Create a list
        val adapterList: MutableList<Task> = mutableListOf()

//        taskReference.get()
//            .addOnSuccessListener { documentSnapshot ->
//                if (documentSnapshot.exists()) {
//                    //This has collections and documents
//
//                } else {
//                    context?.longToast("Unable to get the data")
//                }
//            }
//            .addOnFailureListener(object : OnFailureListener {
//                override fun onFailure(p0: Exception) {
//
//
//                }
//            })

    }

    override fun clickThisItem(_listItem: Task) {
        val bundle = bundleOf(
            " hdj" to "bhdjk"
        )

        findNavController().navigate(R.id.action_nav_home_to_openTaskFragment, bundle)
    }
}
