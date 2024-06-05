package com.xparkle.githubuserapp.ui.favorite

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xparkle.githubuserapp.R
import com.xparkle.githubuserapp.databinding.ActivityFavUserBinding
import com.xparkle.githubuserapp.helper.Helper
import com.xparkle.githubuserapp.helper.RecyclerSwipeCallback
import com.xparkle.githubuserapp.ui.ViewModelFactory

class FavUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavUserBinding
    private lateinit var viewModel: FavUserViewModel
    private lateinit var favAdapter: FavUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favAdapter = FavUserAdapter()
        binding.rvFavUsers.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = favAdapter
        }

        viewModel = obtainViewModel(this@FavUserActivity)
        viewModel.getAllFavUser().observe(this) { favoriteList ->
            if (favoriteList != null) {
                favAdapter.setListFavorites(favoriteList)
            }
        }

        val swipeCallback = object : RecyclerSwipeCallback() {
            @SuppressLint("NotifyDataSetChanged")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val username = favAdapter.getSwipedUsername(position)
                binding.rvFavUsers.adapter?.let {
                    it.notifyItemRemoved(position)
                    it.notifyDataSetChanged()
                    viewModel.unsetFavorite(username)
                    Toast.makeText(this@FavUserActivity, "$username dihapus dari favorite", Toast.LENGTH_SHORT).show()
                }
            }
        }
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.rvFavUsers)

        binding.imgRemoveAll.setOnClickListener {
            if (favAdapter.itemCount > 0) {
                MaterialAlertDialogBuilder(this@FavUserActivity)
                    .setTitle("Hapus Semua Favorite ?")
                    .setMessage("Anda akan menghapus semua data favorite?")
                    .setCancelable(true)
                    .setIcon(R.drawable.baseline_delete_24)
                    .setPositiveButton("Hapus") { _: DialogInterface, _: Int ->
                        viewModel.removeAllFavorites()
                        Helper.toast(this@FavUserActivity, "Data favorite dihapus")
                    }
                    .setNegativeButton("Batalkan") { _: DialogInterface, _: Int -> }
                    .show()
            } else {
                Helper.toast(this@FavUserActivity, "Kamu tidak memiliki data favorite")
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavUserViewModel::class.java]
    }
}