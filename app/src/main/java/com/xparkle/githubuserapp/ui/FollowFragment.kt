package com.xparkle.githubuserapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.xparkle.githubuserapp.data.response.ItemsItem
import com.xparkle.githubuserapp.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var followViewModel: FollowViewModel
    private var position: Int = 0
    private var username: String? = null
    private var listData: List<ItemsItem>? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        followViewModel = ViewModelProvider(this).get(FollowViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)


        if (position == 1) {
            followViewModel.getFollowers(username!!)
            followViewModel.listFollowers.observe(viewLifecycleOwner) { listFollowers ->
                listData = listFollowers
                val adapter = FollowAdapter()
                adapter.setData(listData)
                binding.rvUser.adapter = adapter
            }
        } else {
            followViewModel.getFollowing(username!!)
            followViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
                listData = listFollowing
                val adapter = FollowAdapter()
                adapter.setData(listData)
                binding.rvUser.adapter = adapter
            }
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progresBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}