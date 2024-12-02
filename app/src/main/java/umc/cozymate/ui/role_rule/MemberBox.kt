package umc.cozymate.ui.role_rule

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import umc.cozymate.R
import umc.cozymate.data.model.entity.RoleData
import umc.cozymate.data.model.response.room.GetRoomInfoResponse.Result.MateDetail

class MemberBox (
    val info : Mate,
    val box : CheckBox
){
    init { setBox() }
    fun getMateInfo(): RoleData.mateInfo { return RoleData.mateInfo(info.mateId, info.nickname) }
    fun setBox(){
        box.apply {
            val layoutParams  = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ConvertDPtoPX(context,37)) // 여기 wrap으로 줄이기
            layoutParams.setMargins(
                ConvertDPtoPX(context, 0), // left
                ConvertDPtoPX(context, 0), // top
                ConvertDPtoPX(context, 8), // right
                ConvertDPtoPX(context, 8)  // bottom
            )
            text = info.nickname
            setPadding( ConvertDPtoPX(context,20), ConvertDPtoPX(context,10), ConvertDPtoPX(context,20), ConvertDPtoPX(context,10))
            setTextAppearance(context, R.style.TextAppearance_App_12sp_Medium)
            setBackgroundResource(R.drawable.ic_input_role_member)
            setTextColor(ContextCompat.getColor(context, R.color.unuse_font))
            gravity = Gravity.CENTER
            buttonDrawable = null
            this.layoutParams = layoutParams
        }
    }
    private fun ConvertDPtoPX(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }
}
class Mate(m : MateDetail? = null){
    var memberId : Int = 0
    var nickname : String = ""
    var mateId : Int = 0
    init {
        if (m != null) {
            memberId = m.memberId
            nickname = m.nickname
            mateId = m.mateId
        }
    }

}