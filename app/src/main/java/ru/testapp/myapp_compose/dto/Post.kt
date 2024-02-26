package ru.testapp.myapp_compose.dto

data class Post(
    val id: Long,
    val authorId: Int,
    val author: String,
    val authorAvatar: String? = null,
    val authorJob: String? = null,
    val content: String,
    val published: String,
    val coords: Coordinates? = null,
    val link: String? = null,
    val likeOwnerIds: List<Int>? = null,
    val mentionIds: List<Int>? = null,
    val mentionedMe: Boolean,
    val likedByMe: Boolean,
    val attachment: Attachment? = null,
    val ownedByMe: Boolean,
    val users: Map<Long, UsersPreview>? = null
)

data class Coordinates(
    val latitude: String,
    val longitude: String
)

data class Attachment(
    val url: String,
    val type: String
)

data class UsersPreview(
    val name: String,
    val avatar: String
)


