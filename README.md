# yamKaboom
Program that polls new posts from Yammer and re-posts them to Slack. 

The program uses a user's Yammer oauth token to poll posts from Yammer. 
Its recommended to use the Yammer API for accessing data as the oauth token expires.
However you might not have that privilege, thus why this program was created.

## Properties
### Specifying the Yammer source.
There are two options of specifying Yammer posts. You can fetch all posts 
from all groups from messages.json ``https://www.yammer.com/api/v1/messages.json`` 
or you can specify url to only look for posts in a specific group like this
``https://www.yammer.com/api/v1/messages/in_group/<group id>.json``. 

### Fetching the Yammer OAuth
After logging in to the Yammer domain, access one of the Yammer source's you want
to pull posts from. From the browser press F12 and look at the Network tab.
Refresh the page and and inspect the request header. Look for the cookie data and 
the keyword oauth_token. 

### Creating a Slack Hook
You should find all you need here (https://api.slack.com/incoming-webhooks) to create
a incomming webhook for your desired channel. Formatting is handled by the program
and the properties only requires an endpoint.

### Example file
The properties file should be named ``yamKaboom.properties`` and contain the 
following properties.
```
# Example properties
slack.web.hook=https://hooks.slack.com/services/X/Y
yammer.oauth.token=xxx-yyyzzz
yammer.messages.url=https://www.yammer.com/api/v1/messages/in_group/xxxx.json
```