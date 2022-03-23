package com.harets.notesapp.ui.add

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.harets.notesapp.R
import com.harets.notesapp.data.entity.Notes
import com.harets.notesapp.data.entity.Priority
import com.harets.notesapp.databinding.FragmentAddBinding
import com.harets.notesapp.ui.NotesViewModel
import com.harets.notesapp.ui.ViewModelFactory
import com.harets.notesapp.utils.ExtentionFunctions.setActionBar
import com.harets.notesapp.utils.HelperFunctions
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding as FragmentAddBinding

    // untuk mendapatkan akses Dao
    private val addViewModel by viewModels<NotesViewModel>()

//    private var _addViewModel: NotesViewModel? = null
//    private val addViewModel get() = _addViewModel as NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

//        _addViewModel = activity?.let { obtainViewModel(it) }

//        val navController = findNavController()
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//
//        binding.toolbarAdd.apply{
//            (requireActivity() as MainActivity).setSupportActionBar(this)
//            setupWithNavController(navController, appBarConfiguration)
//            navController.addOnDestinationChangedListener{ _, destination, _ ->
//                when(destination.id){
//                    R.id.addFragment -> setNavigationIcon(R.drawable.ic_left_arrow)
//                }
//            }
//        }

        binding.toolbarAdd.setActionBar(requireActivity())

        binding.spinnerPriorities.onItemSelectedListener = HelperFunctions.spinnerListener(context, binding.priorityIndicator)
    }

//    private fun obtainViewModel(activity: FragmentActivity): NotesViewModel {
//        val factory = ViewModelFactory.getInstance(activity.application)
//        return ViewModelProvider(activity, factory)[NotesViewModel::class.java]
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_save, menu)
        val action = menu.findItem(R.id.menu_save)
        action.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
            insertNotes()
        }
    }


    private fun insertNotes() {
        with(binding){
            val title = edtTitle.text.toString()
            val priority = spinnerPriorities.selectedItem.toString()
            val descriptionCompat = edtDescription.text.toString()

            val calendar = Calendar.getInstance().time
            val date = SimpleDateFormat("dd MMMMM yyyy", Locale.getDefault()).format(calendar)

//            addViewModel.insertData(Notes(
//                0,
//                title,
//                parceToPriority(priority),
//                descriptionCompat,
//                date
//            ))

            val note = Notes(
                0,
                title,
                parceToPriority(priority),
                descriptionCompat,
                date
            )

            if (edtTitle.text.isEmpty() || edtDescription.text.isEmpty()){
                edtTitle.setError("Please fill field")
                edtDescription.error = "Please fill field"
            }else{
                findNavController().navigate(R.id.action_addFragment_to_homeFragment)
                addViewModel.insertData(note)
                Log.i("AddFragment", "insertNotes: $note")
            }
        }
    }

    // untuk menggubah ke priority
    private fun parceToPriority(priority: String): Priority {
        val expenctedPriority = resources.getStringArray(R.array.priorities)
        return when (priority){
            expenctedPriority[0] -> Priority.HIGH // mengembalikan enum class hight
            expenctedPriority[1] -> Priority.MEDIUM
            expenctedPriority[2] -> Priority.LOW
            else -> Priority.HIGH
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

//findNavController().navigate(R.id.action_addFragment_to_homeFragment)