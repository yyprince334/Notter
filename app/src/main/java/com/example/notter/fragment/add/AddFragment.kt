package com.example.notter.fragment.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notter.fragment.SharedViewModel
import com.example.notter.R
import com.example.notter.data.models.NotterData
import com.example.notter.data.viewModel.NotterViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private val mNotterViewModel: NotterViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_add, container, false)

        setHasOptionsMenu(true)

        view.priority_spinner.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDB()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDB() {
        val mTitle = title_et.text.toString()
        val mPriority = priority_spinner.selectedItem.toString()
        val mDescription = description_et.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation) {

            val newData = NotterData(
                0,
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )
            mNotterViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully add!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill all the text field", Toast.LENGTH_SHORT)
                .show()
        }
    }


}