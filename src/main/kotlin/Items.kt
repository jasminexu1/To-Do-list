import com.google.gson.annotations.SerializedName

data class Items (

    @SerializedName("uuid"       ) var uuid       : String?            = null,
    @SerializedName("Archive"    ) var Archive    : ArrayList<Tisk> = arrayListOf(),
    @SerializedName("CustomTags" ) var CustomTags : ArrayList<String>  = arrayListOf(),
    @SerializedName("CustomPriorities" ) var CustomPriorities : ArrayList<String>  = arrayListOf(),
    @SerializedName("Tasks"      ) var Tasks      : ArrayList<Tisk>   = arrayListOf()

)