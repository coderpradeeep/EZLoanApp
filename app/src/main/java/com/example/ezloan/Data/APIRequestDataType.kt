package com.example.ezloan.Data

import com.google.gson.annotations.SerializedName

data class APIRequestDataType(

    @SerializedName("no_of_dependents")
    var no_of_dependents : Int,

    @SerializedName("education")
    var education : Int,

    @SerializedName("self_employed")
    var self_employed : Int,

    @SerializedName("income_annum")
    var income_annum : Int,

    @SerializedName("loan_amount")
    var loan_amount : Int,

    @SerializedName("loan_term")
    var loan_term : Int,

    @SerializedName("cibil_score")
    var cibil_score : Int,

    @SerializedName("residential_assets_value")
    var residential_assets_value : Int,

    @SerializedName("commercial_assets_value")
    var commercial_assets_value : Int,

    @SerializedName("luxury_assets_value")
    var luxury_assets_value : Int,

    @SerializedName("bank_asset_value")
    var bank_asset_value : Int
)
