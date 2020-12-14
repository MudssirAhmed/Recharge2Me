package recahrge.myAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.recharge2mePlay.recharge2me.R
import kotlinx.android.synthetic.main.db_number_card.view.*
import local_Databasse.entity_numberDetails
import recahrge.prePaidDirections

class dbNumberDetails_adapter: RecyclerView.Adapter<dbNumberDetails_adapter.myViewHolder>() {

    private var numberList = emptyList<entity_numberDetails>()

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
            "Idea" -> holder.itemView.iv_dbCard_operator.setImageResource(R.drawable.idea)
            "Vodafone" -> holder.itemView.iv_dbCard_operator.setImageResource(R.drawable.idea)
            "Vodafone Idea" -> holder.itemView.iv_dbCard_operator.setImageResource(R.drawable.idea)
            "Reliance Jio" -> holder.itemView.iv_dbCard_operator.setImageResource(R.drawable.jio)
            "Airtel" -> holder.itemView.iv_dbCard_operator.setImageResource(R.drawable.airtel)
            "Bsnl" -> holder.itemView.iv_dbCard_operator.setImageResource(R.drawable.bsnl)
            "BSNL" -> holder.itemView.iv_dbCard_operator.setImageResource(R.drawable.bsnl)
            else -> holder.itemView.iv_dbCard_operator.setImageResource(R.drawable.mtnl)
        }

        holder.itemView.db_card_numberDetails.setOnClickListener {
            val action = prePaidDirections.actionPrePaid3ToMobileDetailsFinder( "null", "null")
            action.setNumberData(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
    return numberList.size
    }

    fun setData(numData: List<entity_numberDetails>){
        this.numberList = numData
        notifyDataSetChanged()
    }
}