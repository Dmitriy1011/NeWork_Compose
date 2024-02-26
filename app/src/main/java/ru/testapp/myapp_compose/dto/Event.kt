package ru.testapp.myapp_compose.dto

data class Event(
    val id: Int,
    val authorId: Int,
    val author: String,
    val authorAvatar: String? = null,
    val authorJob: String? = null,
    val content: String,
    val dateTime: String? = null,
    val published: String,
    val coords: EventCoordinates? = null,
    val type: String,
    val likeOwnerIds: List<Int>? = null,
    val likedByMe: Boolean,
    val speakerIds: List<Int>? = null,
    val participantsIds: List<Int>? = null,
    val participatedByMe: Boolean,
    val attachment: EventAttachment? = null,
    val link: String? = null,
    val ownedByMe: Boolean,
    val users: Map<Int, EventUserPreview>? = null
)

data class EventCoordinates(
    val latitude: String,
    val longitude: String
)

data class EventAttachment(
    val url: String,
    val type: String
)

data class EventUserPreview(
    val name: String,
    val avatar: String? = null
)