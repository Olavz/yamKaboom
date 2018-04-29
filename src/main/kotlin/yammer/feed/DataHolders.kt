package yammer.feed

data class YammerFeedConfig(
        val slackWebHook: String,
        val yammerOAuthToken: String,
        val yammerMessageSource: String
)

data class Message(
        val id: Long,
        val senderId: Long,
//       val repliedToId: Long,
        val createdAt: String,
        val networkId: Long,
        val messageType: String,
        val senderType: String,
        val url: String,
        val webUrl: String,
        val groupId: Long,
//        val body: Body,
        val threadId: Long,
        val clientType: String,
        val clientUrl: String,
        val systemMessage: Boolean,
        val directMessage: Boolean,
        // val chat_client_sequence ??
        val language: String,
        // val notified_user_ids,
        val privacy: String,
//        val attachments ?
//        val likedBy: LikedBy,
        val contentExcerpt: String
        //val groupCreatedId: Long
)

data class UserReference(
        val id: Long,
        val type: String,
        val name: String?,
        val state: String,
        val fullName: String,
        val jobTitle: String,
        val networkId: Long,
        val mugshotUrl: String,
        val mugshotUrlTemplate: String,
        val url: String,
        val webUrl: String,
        val activatedAt: String,
        val autoActivated: Boolean,
//        val stats: Stats,
        val email: String
)

data class GroupReference(
        val id: Long,
        val type: String,
        val fullName: String,
        val description: String
)

data class SlackUser(
        val id: String,
        val teamId: String,
        val name: String,
        val deleted: Boolean,
        val color: String,
        val realName: String,
        val tz: String,
        val tzLabel: String,
        val tzOffset: Int,
        val profile: SlackUserProfile,
        val isAdmin: Boolean,
        val isOwner: Boolean,
        val isPrimaryOwner: Boolean,
        val isRestricted: Boolean,
        val isUltraRestricted: Boolean,
        val isBot: Boolean,
        val updated: Long,
        val isAppUser: Boolean
)

data class SlackUserProfile(
        val title: String,
        val phone: String,
        val skype: String,
        val realName: String,
        val realNameNormalized: String,
        val displayName: String,
        val displayNameNormalized: String,
        val statusText: String,
        val statusEmoji: String,
        val statusExpiration: Long,
        val avatarHash: String,
        val email: String,
        val firstName: String,
        val lastName: String,
        val image24: String,
        val image32: String,
        val image48: String,
        val image72: String,
        val image192: String,
        val image512: String,
        val image1024: String,
        val team: String
)