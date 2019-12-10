package fr.appsolute.tp.data.model

import com.google.gson.annotations.SerializedName

/**
 * This class define what is the model Character
 */
data class Character(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("origin") val origin: Place,
    @SerializedName("location") val location: Place,
    @SerializedName("image") val image: String,
    @SerializedName("episode") val episode: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val created: String
) {
    /**
     * Define what is a place
     */
    data class Place(
        @SerializedName("name") val name: String,
        @SerializedName("url") val url: String
    )
}