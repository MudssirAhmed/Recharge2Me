package com.recharge2mePlay.recharge2me.constants

object AppConstants {

    // BaseUrl
    const val BASE_URL = "https://recharge2me-06b916c0a9b9.herokuapp.com/"

    // Navigation
    const val SELECTED_CIRCLE = "SELECTED_CIRCLE"
    const val SELECTED_OPERATOR = "SELECTED_OPERATOR"

    // Api
    const val GET_MOBILE_RECHARGE_PLANS = "api/app/recharge/get-recharge-plans"
    const val GET_OPERATOR_LOOKUP = "api/app/recharge/get-operator-lookup"

    // Messages
    const val SOMETHING_WENT_WRONG = "Something went wrong please try again";

    // Values
    const val RECHARGE_TYPE_PREPAID = 1
    const val RECHARGE_TYPE_POSTPAID = 2

    // Fields
    const val NAME = "NAME"
    const val NUMBER = "NUMBER"
    const val RECHARGE_TYPE = "NUMBER"
}