package com.app.bn.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val species: String,
    val type: String,
    val image: String,
    val url: String,
) : Parcelable

data class PagedResponse<T>(
    @SerializedName("info") val pageInfo: PageInfo,
    val results: List<T> = listOf()
)

data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class BnResponse(
    var cards: List<Card>?
)

data class Card(
    var `data`: Data?
)

data class Data(
    var horizontal_cards: List<HorizontalCard>?,
    var main_post: MainPost?,
    var total_matches_count: Int?
)

data class HorizontalCard(
    var assigned_to: AssignedTo?,
    var info: String?,
    var post_uuid: String?,
    var price: Int?,
    var rent_expected: Int?,
    var sub_info: List<SubInfo?>?,
    var title: String?,
    var type: Type?,
    var updation_time: Int?,
    var uuid: String?
)

data class MainPost(
    var assigned_to: AssignedTo?,
    var info: String?,
    var match_count: Int?,
    var max_budget: Int?,
    var max_rent: Int?,
    var post_uuid: String?,
    var sub_info: List<SubInfoX>?,
    var title: String?,
    var type: Type?,
    var uuid: String?
)

data class AssignedTo(
    var name: String?,
    var org_name: String?,
    var phone_number: String?,
    var profile_pic_url: String?,
    var uuid: String?
)

data class SubInfo(
    var perfect_match: Boolean?,
    var text: String?
)

data class Type(
    var id: String?,
    var name: String?
)

data class SubInfoX(
    var text: String?
)