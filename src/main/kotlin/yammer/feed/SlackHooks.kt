package yammer.feed

import org.jsoup.Jsoup


fun postYammerMessageToSlack(slackWebHook: String, message: Message, user: UserReference?, group: GroupReference?) {
    Jsoup.connect(slackWebHook)
            .data("payload",
                    "{\"text\": \"New post by *${user!!.fullName}* in the group *${group!!.fullName}*\","
                            + "\"attachments\": ["
                            + "{\"title\": \"Click here to see thread\","
                            + "\"title_link\": \"${message.webUrl}\","
                            + "\"thumb_url\": \"${user.mugshotUrl}\","
                            + "\"text\": \"${message.contentExcerpt}\"}"
                            + "],"
                            + "\"username\": \"Yammer Feed\","
                            + "\"icon_emoji\": \":newspaper:\"}")
            .post()
}

fun postStatusToSlack(slackWebHook: String, status: String) {
    Jsoup.connect(slackWebHook)
            .data("payload",
                    "{\"text\": \"$status\", \"icon_emoji\": \":warning:\", \"username\": \"Yammer Feed\"}")
            .post()
}