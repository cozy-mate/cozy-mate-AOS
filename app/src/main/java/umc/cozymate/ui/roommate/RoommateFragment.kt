package umc.cozymate.ui.roommate

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import umc.cozymate.R
import umc.cozymate.data.model.RoomInfoRequest
import umc.cozymate.databinding.FragmentRoommateBinding
import umc.cozymate.ui.MainActivity

@AndroidEntryPoint
class RoommateFragment : Fragment() {
    private var _binding: FragmentRoommateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RoomViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoommateBinding.inflate(inflater, container, false)

        binding.btnTest.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_container, RoommateOnboardingFragment()).commitAllowingStateLoss()
        }
        binding.btnDetail.setOnClickListener {
            val intent = Intent(activity, RoommateDetailActivity::class.java)
            startActivity(intent)
        }
        binding.btnCrew.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_container, RoommateMakeCrewableFragment()).commitAllowingStateLoss()
        }


        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }
    private fun initObserver(){
        binding.viewModel = viewModel

        viewModel.sendRoomInfo(
            RoomInfoRequest(
                24,
                3,
                "빵파소",
                4
            )
        )
    }

}