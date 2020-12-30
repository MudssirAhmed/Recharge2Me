package recahrge.myAdapters

import android.app.Activity
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.recharge2mePlay.recharge2me.R
import kotlinx.android.synthetic.main.db_number_card.view.*
import local_Databasse.entity_numberDetails
import recahrge.prePaidDirections

class dbNumberDetails_adapter(activity: FragmentActivity) : RecyclerView.Adapter<dbNumberDetails_adapter.myViewHolder>() {

    private var numberList = emptyList<entity_numberDetails>()
    private var activity: Activity = activity



    class myViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.db_number_card, parent, false))
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        val currentItem = numberList[position]

        holder.itemView.tv_dbCard_circle.text = currentItem.circle
        holder.itemView.tv_dbCard_number.text = currentItem.number

        when (currentItem.operator) {
            "IDEA" -> holder.itemView.iv_dbCard_operator.setImageResource(R.drawable.idea)
            "VODAFONE" -> holder.itemView.iv_dbCard_operator.setImageResource(R.drawable.idea)
            "Vodafone Idea" -> holder.itemView.iv_dbCard_operator.setImageResource(R.drawable.idea)
            "Jio" -> holder.itemView.iv_dbCard_operator.setImageResource(R.drawable.jio)
            "AIRTEL" -> holder.itemView.iv_dbCard_operator.setImageResource(R.drawable.airtel)
            "BSNL" -> holder.itemView.iv_dbCard_operator.setImageResource(R.drawable.bsnl)
        }

        holder.itemView.db_card_numberDetails.setOnClickListener {
            val action = prePaidDirections.actionPrePaid3ToMobileDetailsFinder("null", "null")
            action.setNumberData(currentItem)
            hideBackButton();
            holder.itemView.findNavController().navigate(action)
        }

    }

    fun hideBackButton(){
        val iv_rechargeUi_back: ImageView = activity.findViewById<ImageView>(R.id.iv_rechargeUi_back)
        val handler = Handler()
        handler.postDelayed({ iv_rechargeUi_back.visibility = View.GONE }, 100)
    }

    override fun getItemCount(): Int {
    return numberList.size
    }

    fun setData(numData: List<entity_numberDetails>){
        this.numberList = numData
        notifyDataSetChanged()
    }
}