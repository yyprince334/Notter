package com.example.notter.fragment.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notter.fragment.SharedViewModel
import com.example.notter.R
import com.example.notter.adapter.ListAdapter
import com.example.notter.data.models.NotterData
import com.example.notter.data.viewModel.NotterViewModel
import com.example.notter.databinding.FragmentListBinding
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment(), SearchView.OnQueryTextListener{

    private val mSharedViewModel: SharedViewModel by viewModels()

    private val mNotterViewModel: NotterViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //DataBinding
        _binding = FragmentListBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.msharedViewModel = mSharedViewModel


        setUpRecyclerView()

        mNotterViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })


        setHasOptionsMenu(true)

        return binding.root

    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }

        //To delete Item
        swipeToDelete(recyclerView)
    }

    // Swipe TO delete Item
    private fun swipeToDelete(recyclerView: RecyclerView) {
        val onSwipeToDelete = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                mNotterViewModel.deleteItem(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                //Restore Delete Item
                restoreDeleteItem(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(onSwipeToDelete)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    //Restore Delete Item
    private fun restoreDeleteItem(view: View, deletedItem: NotterData) {
        val snackBar = Snackbar.make(view, "Deleted '${deletedItem.title}'", Snackbar.LENGTH_LONG)
        snackBar.setAction("Undo") {
            mNotterViewModel.insertData(deletedItem)
        }
        snackBar.show()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_deleteAll -> deleteAllFromData()
            R.id.menu_priority_high -> mNotterViewModel.sortByHighPriority.observe(this, Observer { adapter.setData(it) })
            R.id.menu_priority_low -> mNotterViewModel.sortByLowPriority.observe(this, Observer { adapter.setData(it) })

        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllFromData() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mNotterViewModel.deleteAll()

            Toast.makeText(
                requireContext(),
                "Successfully Delete All",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete All")
        builder.setMessage("Are you sure you want to delete all items")
        builder.create().show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchThroughDatabase(query)
        }
        return true
    }



    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchThroughDatabase(query)
        }
        return true

    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery ="%${query}%"

        mNotterViewModel.searchDatabase(searchQuery).observe(this, Observer {list ->
            list?.let {
                adapter.setData(it)
            }

        })

    }
}