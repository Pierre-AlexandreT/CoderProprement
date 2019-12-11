package fr.appsolute.tp.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "episode")
data class Episode @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode") val episode: String,
    @Ignore
    @SerializedName("characters") val characters: List<String> = emptyList(),
    @SerializedName("url") val url: String,
    @SerializedName("created") val created: String
)