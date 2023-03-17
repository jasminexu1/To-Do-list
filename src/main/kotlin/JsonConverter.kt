import com.google.gson.annotations.SerializedName

data class jsonConverter (

    @SerializedName("Items"        ) var Items        : ArrayList<Items> = arrayListOf(),
    @SerializedName("Count"        ) var Count        : Int?             = null,
    @SerializedName("ScannedCount" ) var ScannedCount : Int?             = null

)