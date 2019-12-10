package fr.appsolute.tp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Response wrapper for paginated response from the api
 */
data class PaginatedResult<T>(
    @SerializedName("info") val information: Information,
    @SerializedName("results") val results: List<T>
) {
    /**
     * Define information for the pagination
     */
    data class Information(
        // Total number of element
        @SerializedName("count") val count: Int,
        // Total number of page
        @SerializedName("pages") val pages: Int,
        // Url for the next page, empty if there is no page
        @SerializedName("next") val next: String,
        // Url for the previous page, empty if there is no page
        @SerializedName("prev") val prev: String
    )
}