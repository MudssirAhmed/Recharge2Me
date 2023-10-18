package com.recharge2mePlay.recharge2me.recharge.models

data class MobileRechargePlansResponse(
    val status: Int,
    val message: String,
    val data: MobileRechargePlansData
)

data class MobileRechargePlansData(
        val entity: String,
        val operator: String,
        val circle: String,
        val categories: ArrayList<MobileRechargePlanCategory>
)

data class MobileRechargePlanCategory (
    val entity: String,
    val name: String,
    val count: Int,
    val items: ArrayList<MobileRechargePlan>
)

data class MobileRechargePlan(
        val entity: String,
        val plan_id: Int,
        val amount: Int,
        val validity: String,
        val talktime: String,
        val benefit: String,
        val calls: String,
        val sms: String,
        val data: String,
        val remark: String,
        val subscriptions: ArrayList<MobileRechargeSubscription>
)

data class MobileRechargeSubscription(
        val entity: String,
        val name: String,
        val description: String,
        val logo: String
)
