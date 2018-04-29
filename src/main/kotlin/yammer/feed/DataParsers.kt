package yammer.feed

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.util.HashMap

fun getMessagesFromJsonObject(datastruktur: JSONObject): HashMap<Long, Message> {
    val messages = datastruktur["messages"] as JSONArray
    val messageMap = HashMap<Long, Message>()
    for (i in 0 until (messages.size)) {
        val jsonObject = messages[i] as JSONObject
        val id = jsonObject["id"].toString().toLong()
        val senderId = jsonObject["sender_id"].toString().toLong()
        // al repliedToId: Long
        val createdAt = jsonObject["created_at"].toString()
        val networkId = jsonObject["network_id"].toString().toLong()
        val messageType = jsonObject["message_type"].toString()
        val senderType = jsonObject["sender_type"].toString()
        val url = jsonObject["url"].toString()
        val webUrl = jsonObject["web_url"].toString()
        val groupId = if(jsonObject["group_id"] != null) jsonObject["group_id"].toString().toLong() else 0
        // val body: Body
        val threadId = jsonObject["thread_id"].toString().toLong()
        val clientType = jsonObject["client_type"].toString()
        val clientUrl = jsonObject["client_url"].toString()
        val systemMessage = jsonObject["system_message"].toString().toBoolean()
        val directMessage = jsonObject["direct_message"].toString().toBoolean()
        // val chat_client_sequence ?
        val language = jsonObject["language"].toString()
        // val notified_user_ids
        val privacy = jsonObject["privacy"].toString()
        // val attachments
        // val likedBy: LikedBy
        val contentExcerpt = jsonObject["content_excerpt"].toString()
        // val groupCreatedId = jsonObject["group_created_id"].toString().toLong()

        val message = Message(
                id,
                senderId,
                createdAt,
                networkId,
                messageType,
                senderType,
                url,
                webUrl,
                groupId,
                threadId,
                clientType,
                clientUrl,
                systemMessage,
                directMessage,
                language,
                privacy,
                contentExcerpt
        )
        if (messageMap.put(id, message) != null) {
            println("Found id: $id to be duplicate when adding to messageMap.")
        }

    }

    return messageMap
}

fun getUserReferencesFromJsonObject(datastruktur: JSONObject): HashMap<Long, UserReference> {
    val references = datastruktur["references"] as JSONArray
    val referencesMap = HashMap<Long, UserReference>()
    for (i in 0 until (references.size)) {
        val jsonObject = references[i] as JSONObject
        if (jsonObject["type"] == "user") {
            val id = jsonObject["id"].toString().toLong()
            val type = jsonObject["type"].toString()
            val name = jsonObject["name"]?.toString()
            val state = jsonObject["state"].toString()
            val fullName = jsonObject["full_name"].toString()
            val jobTitle = jsonObject["job_title"].toString()
            val networkId = jsonObject["network_id"].toString().toLong()
            val mugshotUrl = jsonObject["mugshot_url"].toString()
            val mugshotUrlTemplate = jsonObject["mugshot_url_template"].toString()
            val url = jsonObject["url"].toString()
            val webUrl = jsonObject["web_url"].toString()
            val activatedAt = jsonObject["activated_at"].toString()
            val autoActivated = jsonObject["auto_activated"].toString().toBoolean()
            // val stats: Stats
            val email = jsonObject["email"].toString()

            val userReference = UserReference(
                    id,
                    type,
                    name,
                    state,
                    fullName,
                    jobTitle,
                    networkId,
                    mugshotUrl,
                    mugshotUrlTemplate,
                    url,
                    webUrl,
                    activatedAt,
                    autoActivated,
                    email
            )
            if (referencesMap.put(id, userReference) != null) {
                println("Found id: $id to be duplicate when adding to map.")
            }
        }
    }

    return referencesMap
}

fun getGroupReferencesFromJsonObject(datastruktur: JSONObject): HashMap<Long, GroupReference> {
    val references = datastruktur["references"] as JSONArray
    val referencesMap = HashMap<Long, GroupReference>()
    for (i in 0 until (references.size)) {
        val jsonObject = references[i] as JSONObject
        if (jsonObject["type"] == "group") {
            val id = jsonObject["id"].toString().toLong()
            val type = jsonObject["type"].toString()
            val fullName = jsonObject["full_name"].toString()
            val description = jsonObject["description"].toString()

            val groupReference = GroupReference(
                    id,
                    type,
                    fullName,
                    description
            )
            if (referencesMap.put(groupReference.id, groupReference) != null) {
                println("Found id: ${groupReference.id} to be duplicate when adding to map.")
            }
        }
    }

    // Fake entry to cover All Company "group"
    val groupReference = GroupReference(
            0,
            "group",
            "All Company",
            ""
    )
    if (referencesMap.put(groupReference.id, groupReference) != null) {
        println("Found id: ${groupReference.id} to be duplicate when adding to map.")
    }

    return referencesMap
}