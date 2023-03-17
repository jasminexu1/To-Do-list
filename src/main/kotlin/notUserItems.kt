import com.google.gson.annotations.SerializedName

data class notUserItems(

    @SerializedName("Items"        ) var Items: ArrayList<Tisk> = arrayListOf(),
    @SerializedName("Count"        ) var Count: Int?             = null,
    @SerializedName("ScannedCount" ) var ScannedCount: Int?             = null

)