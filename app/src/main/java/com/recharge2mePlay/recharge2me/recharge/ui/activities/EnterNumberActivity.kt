package com.recharge2mePlay.recharge2me.recharge.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.recharge2mePlay.recharge2me.R
import com.recharge2mePlay.recharge2me.base.ui.AppBaseActivity
import com.recharge2mePlay.recharge2me.constants.AppConstants
import com.recharge2mePlay.recharge2me.databinding.ActivityEnterNumberBinding

class EnterNumberActivity : AppBaseActivity() {

    private lateinit var mBinding: ActivityEnterNumberBinding
    private val TAG = "EnterNumberActivityTAG"
    private var rechargeType: Int = AppConstants.RECHARGE_TYPE_PREPAID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityEnterNumberBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setData();
        setEventListeners();
        setObservers();

    }

    private fun setData() {
        intent.let {
            rechargeType = it.getIntExtra(AppConstants.RECHARGE_TYPE, AppConstants.RECHARGE_TYPE_PREPAID)
        }

        val leftDrawable = ContextCompat.getDrawable(this, R.drawable.phone_24);
        leftDrawable?.setBounds(0, 0, leftDrawable.intrinsicWidth, leftDrawable.intrinsicHeight)

        val endDrawable = ContextCompat.getDrawable(this, R.drawable.arrow_forward_24);
        endDrawable?.setBounds(0, 0, endDrawable.intrinsicWidth, endDrawable.intrinsicHeight)

        mBinding.etNumber.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, endDrawable, null)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setEventListeners() {
        val drawable = mBinding.etNumber.compoundDrawables[2]

        mBinding.etNumber.setOnTouchListener { _, event ->
            showSimpleLog(TAG, "In TouchListener Action: ${event.action}, drawable: $drawable")
            if (
                event.action == MotionEvent.ACTION_UP &&
                drawable != null &&
                event.x >= ((mBinding.etNumber.right - drawable.bounds.width()) - 50)
            ) {
                validateAndProceedToRechargeActivity()
                true
            } else {
                false
            }
        }
    }

    private fun setObservers() {

    }

    private fun validateAndProceedToRechargeActivity() {
        val number = mBinding.etNumber.text.toString().trim()

        if(number.length != 10) {
            showErrorSnackBar("Please enter valid number", mBinding.root)
            return
        }

        val intent = Intent(this, RechargePlanActivity::class.java)
        intent.putExtra(AppConstants.RECHARGE_TYPE, rechargeType);
        intent.putExtra(AppConstants.NUMBER, number)
        startActivity(intent)
    }
}