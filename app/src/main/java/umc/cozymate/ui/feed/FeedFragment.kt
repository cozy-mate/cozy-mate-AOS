package umc.cozymate.ui.feed

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.cozymate.data.model.entity.PostInfo
import umc.cozymate.databinding.FragmentFeedBinding
import umc.cozymate.ui.viewmodel.FeedViewModel

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private val TAG = this.javaClass.simpleName
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private val viewmodel : FeedViewModel by viewModels()
    private var postList : List<PostInfo> = emptyList()
    private var roomId : Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        getPreference()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        initData()
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun getPreference() {
        val spf = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        roomId = spf.getInt("room_id", 0)

    }

    private fun setupObservers() {
        viewmodel.getPostListResponse.observe(viewLifecycleOwner, Observer { response->
            if(response == null) return@Observer
            if(response.isSuccessful){
                val feedResponse = response.body()
                feedResponse?.let {
                    postList = it.result
                    updateContents()
                }
            }else{
                Log.d(TAG,"response 응답 실패")
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rvFeed.visibility = View.GONE
            }
        })

        viewmodel.getFeedInfoResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response == null) return@Observer
            if (response.isSuccessful) {
                val feedResponse = response.body()
                feedResponse?.let {
                    binding.tvFeedName.text = it.result.name
                    binding.tvFeedInfo.text = it.result.diescription
                }
            } else {
                Log.d(TAG, "response 응답 실패")
            }
        })

    }

    private fun initData() {
        if (view == null) return
        viewmodel.getPostList(roomId, 0)
        viewmodel.getFeedInfo(roomId)

    }


    private fun updateContents() {
        binding.rvFeed.apply {
            adapter = FeedRVAdapter(postList)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


}