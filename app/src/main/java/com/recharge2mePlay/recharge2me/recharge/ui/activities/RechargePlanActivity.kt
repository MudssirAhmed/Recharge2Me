package com.recharge2mePlay.recharge2me.recharge.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.recharge2mePlay.recharge2me.R
import com.recharge2mePlay.recharge2me.constants.AppConstants
import com.recharge2mePlay.recharge2me.databinding.ActivityRechargePlanBinding

class RechargePlanActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRechargePlanBinding

    private var rechargeType: Int = AppConstants.RECHARGE_TYPE_PREPAID
    private var number: String = ""
    private var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRechargePlanBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setData()
    }

    private fun setData() {
        intent.let {
            rechargeType = it.getIntExtra(AppConstants.RECHARGE_TYPE, AppConstants.RECHARGE_TYPE_PREPAID)
            name = it.getStringExtra(AppConstants.NAME).toString()
            number = it.getStringExtra(AppConstants.NUMBER).toString()
        }

        mBinding.tvName.text = name
        mBinding.tvName.text = number
    }
}