package yammer.feed

import org.json.simple.JSONObject
import org.json.simple.JSONValue
import org.jsoup.Jsoup
import java.io.FileInputStream
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * I will poll supplied Yammer feed and push new posts to Slack.
 */

var didUpdate = HashSet<Long>()
var firstRun = true
var runWithError = false

fun main(args: Array<String>) {
    val yammerFeedConfig = yammerFeedConfig()
    val ses = Executors.newSingleThreadScheduledExecutor()
    ses.scheduleAtFixedRate({ getYammerStatus(yammerFeedConfig) }, 0, 17, TimeUnit.MINUTES)
}

private fun yammerFeedConfig(): YammerFeedConfig {
    val prop = Properties()
    FileInputStream("yamKaboom.properties").use {
        prop.load(it)
    }

    val yammerFeedConfig = YammerFeedConfig(
            prop.getProperty("slack.web.hook"),
            prop.getProperty("yammer.oauth.token"),
            prop.getProperty("yammer.messages.url")
    )
    return yammerFeedConfig
}


fun getYammerStatus(yammerFeedConfig: YammerFeedConfig): Boolean {

    if(runWithError) {
        return false // TODO: Should cancel the execution.
    }

    val soup = Jsoup.connect(yammerFeedConfig.yammerMessageSource)
            .cookie("oauth_token", yammerFeedConfig.yammerOAuthToken)
            .ignoreContentType(true)
            .ignoreHttpErrors(true)
            .execute()

    if (soup.statusCode() != 200) {
        postStatusToSlack(yammerFeedConfig.slackWebHook, "Failed to fetch data from Yammer. Status was ${soup.statusCode()}.")
        runWithError = true
        return false
    }

    val datastruktur = JSONValue.parse(soup.body()) as JSONObject

    val userReferenceMap = getUserReferencesFromJsonObject(datastruktur)
    val groupReferenceMap = getGroupReferencesFromJsonObject(datastruktur)
    val messagesMap = getMessagesFromJsonObject(datastruktur)

    val iterator = messagesMap.iterator()
    while (iterator.hasNext()) {
        val message = iterator.next().value
        if (didUpdate.add(message.threadId)) {
            val user = userReferenceMap[message.senderId]
            val group = groupReferenceMap[message.groupId]
            if (firstRun) {
                println("First run adding threadId: ${message.threadId} posted by ${user!!.fullName} in ${group!!.fullName}")
            } else {
                println("Found new threadId: ${message.threadId} posted by ${user!!.fullName}")
                postYammerMessageToSlack(yammerFeedConfig.slackWebHook, message, user, group)
            }
        }
    }

    if (firstRun) {
        firstRun = false
    }
    return true
}