package yammer.feed

import org.jsoup.Jsoup


fun postYammerMessageToSlack(slackWebHook: String, message: Message, user: UserReference?, group: GroupReference?) {
    Jsoup.connect(slackWebHook)
            .data("payload",
                    "{\"text\": \"There is a new post in the group [${group!!.fullName}]\","
                            + "\"attachments\": ["
                            + "{\"title\": \"New post by ${user!!.fullName}\","
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
                    "{\"text\": \"$status\", \"icon_emoji\": \":newspaper:\"}")
            .post()
}