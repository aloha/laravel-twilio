Twilio Docs
Search
Programmable SMS










Message Resource
A Message resource represents an inbound or outbound message. Twilio creates a Message when any of the following occur:

You send an SMS, WhatsApp, or Channels message via the REST API
You use the <Message> verb in TwiML
You originate a message from a Programmable Wireless SIM
Someone sends a message to one of your Twilio numbers
With this REST API, you can:

Create new messages
Fetch a specific message
Read a list of messages
Update or redact the content of an existing message
Delete messages from your account
Are you looking for step-by-step instructions for sending your first SMS with Twilio using the helper libraries? Check out one of our SMS quickstarts in your programming language of choice: C#/.NET, Java, Node.js, PHP, Python, or Ruby.

Or, if you're looking to get up and running with the Twilio API for WhatsApp Quickstart: C#/.NET, Java, Node.js, PHP, Python, or Ruby.

Message properties
Resource Properties in PHP format
body
STRING PII MTL: 30 DAYS
The message text. Can be up to 1,600 characters long.

numSegments
STRING NOT PII
The number of segments that make up the complete message. A message body that is too large to be sent in a single SMS message is segmented and charged as multiple messages. Inbound messages over 160 characters are reassembled when the message is received. Note: When using a Messaging Service to send messages, num_segments will always be 0 in Twilio's response to your API request.

direction
ENUM:DIRECTION NOT PII
The direction of the message. Can be: inbound for incoming messages, outbound-api for messages initiated by a REST API, outbound-call for messages initiated during a call, or outbound-reply for messages initiated in response to an incoming message.

from
PHONE_NUMBER PII MTL: 120 DAYS
The phone number (in E.164 format), alphanumeric sender ID, or Wireless SIM that initiated the message. For incoming messages, this will be the number of the sending phone. For outgoing messages, this value will be one of your Twilio phone numbers or the alphanumeric sender ID used.

to
STRING PII MTL: 120 DAYS
The phone number in E.164 format that received the message. For incoming messages, this will be one of your Twilio phone numbers. For outgoing messages, this will be the sending phone.

dateUpdated
DATE_TIME<RFC2822> NOT PII
The date and time in GMT that the resource was last updated specified in RFC 2822 format.

price
STRING NOT PII
The amount billed for the message, in the currency specified by price_unit. Note that your account is charged for each segment we send to the handset. Populated after the message has been sent. May not be immediately available.

errorMessage
STRING NOT PII
The description of the error_code if your message status is failed or undelivered. If the message was successful, this value is null.

uri
STRING NOT PII
The URI of the resource, relative to https://api.twilio.com.

accountSid
SID<AC> NOT PII
The SID of the Account that sent the message that created the resource.

numMedia
STRING NOT PII
The number of media files associated with the message. A message can send up to 10 media files.

status
ENUM:STATUS NOT PII
The status of the message. Can be: accepted, scheduled (Pilot only), canceled (Pilot only), queued, sending, sent, failed, delivered, undelivered, receiving, received, or read (WhatsApp only). For more information, See detailed descriptions.

messagingServiceSid
SID<MG> NOT PII
The SID of the Messaging Service used with the message. The value is null if a Messaging Service was not used.

sid
SID<MM> NOT PII
The unique string that that we created to identify the Message resource.

dateSent
DATE_TIME<RFC2822> NOT PII
The date and time in GMT that the resource was sent specified in RFC 2822 format. For outgoing messages, this is when we sent the message. For incoming messages, this is when we made the HTTP request to your application.

dateCreated
DATE_TIME<RFC2822> NOT PII
The date and time in GMT that the resource was created specified in RFC 2822 format.

errorCode
INTEGER? NOT PII
The error code returned if your message status is failed or undelivered. The error_code provides more information about the failure. If the message was successful, this value is null.

priceUnit
CURRENCY NOT PII
The currency in which price is measured, in ISO 4127 format (e.g. usd, eur, jpy).

apiVersion
STRING NOT PII
The API version used to process the message.

subresourceUris
URI_MAP NOT PII
A list of related resources identified by their URIs relative to https://api.twilio.com

Create a Message resource
POST https://api.twilio.com/2010-04-01/Accounts/{AccountSid}/Messages.json
To send a new outgoing message, make an HTTP POST to this Messages list resource URI.

If you want to send messages while in trial mode, you must first verify your 'To' phone number with Twilio. You can verify your phone number by adding it to your Verified Caller IDs in the console.

When creating a new message via the API, you must include the To parameter. This value should be either a destination phone number or a Channel address. You also need to pass a Body or MediaUrl containing the message's content.

You must also include either the From parameter or MessagingServiceSid parameter. Use MessagingServiceSid if sending your message with a messaging service.

There is a slight difference in how Twilio's API responds based on the parameter you include:

From: Twilio will validate the phone numbers or Channel addresses synchronously. The API returns either a queued status or an error.
MessagingServiceSid: the API will first return a status of accepted. Twilio then determines the optimal From phone number. Any delivery errors will be sent asynchronously to your StatusCallback URL.
If the body of your message is more than 160 GSM-7 characters (or 70 UCS-2 characters), Twilio will send the message as a segmented SMS and charge your account accordingly.

Parameters
Parameters in PHP format
accountSid
Path
POST SID<AC> NOT PII
The SID of the Account that will create the resource.

to
Required
POST PHONE_NUMBER PII MTL: 120 DAYS
The destination phone number in E.164 format for SMS/MMS or Channel user address for other 3rd-party channels.

statusCallback
Optional
POST URL NOT PII
The URL we should call using the status_callback_method to send status information to your application. If specified, we POST these message status changes to the URL: queued, failed, sent, delivered, or undelivered. Twilio will POST its standard request parameters as well as some additional parameters including MessageSid, MessageStatus, and ErrorCode. If you include this parameter with the messaging_service_sid, we use this URL instead of the Status Callback URL of the Messaging Service. URLs must contain a valid hostname and underscores are not allowed.

applicationSid
Optional
POST SID<AP> NOT PII
The SID of the application that should receive message status. We POST a message_sid parameter and a message_status parameter with a value of sent or failed to the application's message_status_callback. If a status_callback parameter is also passed, it will be ignored and the application's message_status_callback parameter will be used.

maxPrice
Optional
POST DECIMAL NOT PII
The maximum total price in US dollars that you will pay for the message to be delivered. Can be a decimal value that has up to 4 decimal places. All messages are queued for delivery and the message cost is checked before the message is sent. If the cost exceeds max_price, the message will fail and a status of Failed is sent to the status callback. If MaxPrice is not set, the message cost is not checked.

provideFeedback
Optional
POST BOOLEAN NOT PII
Whether to confirm delivery of the message. Set this value to true if you are sending messages that have a trackable user action and you intend to confirm delivery of the message using the Message Feedback API. This parameter is false by default.

attempt
Optional
POST INTEGER NOT PII
Total number of attempts made ( including this ) to send out the message regardless of the provider used

validityPeriod
Optional
POST INTEGER NOT PII
How long in seconds the message can remain in our outgoing message queue. After this period elapses, the message fails and we call your status callback. Can be between 1 and the default value of 14,400 seconds. After a message has been accepted by a carrier, however, we cannot guarantee that the message will not be queued after this period. We recommend that this value be at least 5 seconds.

forceDelivery
Optional
POST BOOLEAN NOT PII
Reserved

contentRetention
Optional
POST ENUM:CONTENT_RETENTION NOT PII
Determines if the message content can be stored or redacted based on privacy settings

addressRetention
Optional
POST ENUM:ADDRESS_RETENTION NOT PII
Determines if the address can be stored or obfuscated based on privacy settings

smartEncoded
Optional
POST BOOLEAN NOT PII
Whether to detect Unicode characters that have a similar GSM-7 character and replace them. Can be: true or false.

persistentAction
Optional
POST STRING[] NOT PII
Rich actions for Channels Messages.

from
Required if messagingServiceSid is not passed
POST PHONE_NUMBER PII MTL: 120 DAYS
A Twilio phone number in E.164 format, an alphanumeric sender ID, or a Channel Endpoint address that is enabled for the type of message you want to send. Phone numbers or short codes purchased from Twilio also work here. You cannot, for example, spoof messages from a private cell phone number. If you are using messaging_service_sid, this parameter must be empty.

messagingServiceSid
Required if from is not passed
POST SID<MG> NOT PII
The SID of the Messaging Service you want to associate with the Message. Set this parameter to use the Messaging Service Settings and Copilot Features you have configured and leave the from parameter empty. When only this parameter is set, Twilio will use your enabled Copilot Features to select the from phone number for delivery.

body
Required if mediaUrl is not passed
POST STRING PII MTL: 30 DAYS
The text of the message you want to send. Can be up to 1,600 characters in length.

mediaUrl
Required if body is not passed
POST URL[] NOT PII
The URL of the media to send with the message. The media can be of type gif, png, and jpeg and will be formatted correctly on the recipient's device. The media size limit is 5MB for supported file types (JPEG, PNG, GIF) and 500KB for other types of accepted media. To send more than one image in the message body, provide multiple media_url parameters in the POST request. You can include up to 10 media_url parameters per message. You can send images in an SMS message in only the US and Canada.

Example 1
Create a message
<?php

// Update the path below to your autoload.php,
// see https://getcomposer.org/doc/01-basic-usage.md
require_once '/path/to/vendor/autoload.php';

use Twilio\Rest\Client;

// Find your Account SID and Auth Token at twilio.com/console
// and set the environment variables. See http://twil.io/secure
$sid = getenv("TWILIO_ACCOUNT_SID");
$token = getenv("TWILIO_AUTH_TOKEN");
$twilio = new Client($sid, $token);

$message = $twilio->messages
                  ->create("+15558675310", // to
                           ["from" => "+15017122661", "body" => "Hi there"]
                  );

print($message->sid);
Example 2
Create a WhatsApp Message
<?php

// Update the path below to your autoload.php,
// see https://getcomposer.org/doc/01-basic-usage.md
require_once '/path/to/vendor/autoload.php';

use Twilio\Rest\Client;

// Find your Account SID and Auth Token at twilio.com/console
// and set the environment variables. See http://twil.io/secure
$sid = getenv("TWILIO_ACCOUNT_SID");
$token = getenv("TWILIO_AUTH_TOKEN");
$twilio = new Client($sid, $token);

$message = $twilio->messages
                  ->create("whatsapp:+15005550006", // to
                           [
                               "body" => "This is a message that I want to send over WhatsApp with Twilio!",
                               "from" => "whatsapp:+14155238886"
                           ]
                  );

print($message->sid);
Use the whatsapp: prefix to send a WhatsApp message
Example 3
Create a message and specify a StatusCallback URL
<?php

// Update the path below to your autoload.php,
// see https://getcomposer.org/doc/01-basic-usage.md
require_once '/path/to/vendor/autoload.php';

use Twilio\Rest\Client;

// Find your Account SID and Auth Token at twilio.com/console
// and set the environment variables. See http://twil.io/secure
$sid = getenv("TWILIO_ACCOUNT_SID");
$token = getenv("TWILIO_AUTH_TOKEN");
$twilio = new Client($sid, $token);

$message = $twilio->messages
                  ->create("+15558675310", // to
                           [
                               "body" => "McAvoy or Stewart? These timelines can get so confusing.",
                               "from" => "+15017122661",
                               "statusCallback" => "https://abc1234.free.beeceptor.com"
                           ]
                  );

print($message->sid);
A note on message rate limiting
As you send more messages via the API, Twilio will queue them up for delivery at your prescribed rate limit. API requests for messages that exceed the specified rates will be queued and executed as capacity is available.

If your application tries to enqueue more than 4 hours worth of outbound traffic (e.g., enqueuing more than 14,400 messages to Canada over one long code phone number), the API will start returning 429 errors.

If you need to enqueue a large volume of messages, you may find that it's helpful to leverage Twilio's Messaging Services. See our guide on how to set up and send messages from a messaging service in your language of choice for more tips.

Fetch a Message resource
GET https://api.twilio.com/2010-04-01/Accounts/{AccountSid}/Messages/{Sid}.json
Returns a single message specified by the provided Message {SID}.

Parameters
Parameters in PHP format
accountSid
Path
GET SID<AC> NOT PII
The SID of the Account that created the Message resource to fetch.

sid
Path
GET SID<MM> NOT PII
The Twilio-provided string that uniquely identifies the Message resource to fetch.

Example 1
Fetch Message
<?php

// Update the path below to your autoload.php,
// see https://getcomposer.org/doc/01-basic-usage.md
require_once '/path/to/vendor/autoload.php';

use Twilio\Rest\Client;

// Find your Account SID and Auth Token at twilio.com/console
// and set the environment variables. See http://twil.io/secure
$sid = getenv("TWILIO_ACCOUNT_SID");
$token = getenv("TWILIO_AUTH_TOKEN");
$twilio = new Client($sid, $token);

$message = $twilio->messages("MMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                  ->fetch();

print($message->to);
Read multiple Message resources
GET https://api.twilio.com/2010-04-01/Accounts/{AccountSid}/Messages.json
Returns a list of messages associated with your account. If you are using the Twilio REST API or the Twilio-CLI, the list includes paging information. If you use one of Twilio's Server-Side Helper Libraries, paging happens under the hood.

When getting the list of all messages, results will be sorted on the DateSent field with the most recent messages appearing first.

If you are using the Twilio REST API and plan on requesting more records than will fit on a single page, you may want to use the provided nextpageuri. This method ensures that your next request picks up where it left off and can prevent you from retrieving duplicate data if you are actively sending or receiving messages.

This is not necessary if you are using a Twilio Helper Library, which will automatically handle paging. Take a look at the Helper Library documentation for more information.

You may also filter the list by providing the following query string parameters to the listing resource:

Parameters
Parameters in PHP format
accountSid
Path
GET SID<AC> NOT PII
The SID of the Account that created the Message resources to read.

to
Optional
GET PHONE_NUMBER PII MTL: 120 DAYS
Read messages sent to only this phone number.

from
Optional
GET PHONE_NUMBER PII MTL: 120 DAYS
Read messages sent from only this phone number or alphanumeric sender ID.

dateSent
Optional
GET DATE_TIME_INEQUALITY<ISO8601> NOT PII
The date of the messages to show. Specify a date as YYYY-MM-DD in GMT to read only messages sent on this date. For example: 2009-07-06. You can also specify an inequality, such as DateSent<=YYYY-MM-DD, to read messages sent on or before midnight on a date, and DateSent>=YYYY-MM-DD to read messages sent on or after midnight on a date.

Example 1
Read: List all messages
<?php

// Update the path below to your autoload.php,
// see https://getcomposer.org/doc/01-basic-usage.md
require_once '/path/to/vendor/autoload.php';

use Twilio\Rest\Client;

// Find your Account SID and Auth Token at twilio.com/console
// and set the environment variables. See http://twil.io/secure
$sid = getenv("TWILIO_ACCOUNT_SID");
$token = getenv("TWILIO_AUTH_TOKEN");
$twilio = new Client($sid, $token);

$messages = $twilio->messages
                   ->read([], 20);

foreach ($messages as $record) {
    print($record->sid);
}
Get all messages associated with your account
Example 2
Read: List messages matching filter criteria
<?php

// Update the path below to your autoload.php,
// see https://getcomposer.org/doc/01-basic-usage.md
require_once '/path/to/vendor/autoload.php';

use Twilio\Rest\Client;

// Find your Account SID and Auth Token at twilio.com/console
// and set the environment variables. See http://twil.io/secure
$sid = getenv("TWILIO_ACCOUNT_SID");
$token = getenv("TWILIO_AUTH_TOKEN");
$twilio = new Client($sid, $token);

$messages = $twilio->messages
                   ->read([
                              "dateSent" => new \DateTime('2016-08-31T00:00:00Z'),
                              "from" => "+15017122661",
                              "to" => "+15558675310"
                          ],
                          20
                   );

foreach ($messages as $record) {
    print($record->sid);
}
List all messages sent on August 31, 2016 from +15017122661 and to +15558675310
Example 3
Read: List messages (filter by 'before sent' date)
<?php

// Update the path below to your autoload.php,
// see https://getcomposer.org/doc/01-basic-usage.md
require_once '/path/to/vendor/autoload.php';

use Twilio\Rest\Client;

// Find your Account SID and Auth Token at twilio.com/console
// and set the environment variables. See http://twil.io/secure
$sid = getenv("TWILIO_ACCOUNT_SID");
$token = getenv("TWILIO_AUTH_TOKEN");
$twilio = new Client($sid, $token);

$messages = $twilio->messages
                   ->read(["dateSentBefore" => new \DateTime('2019-03-01')],
                          20
                   );

foreach ($messages as $record) {
    print($record->sid);
}
List all messages sent before March 1st, 2019
Example 4
Read: List messages (filter by a period of time)
<?php

// Update the path below to your autoload.php,
// see https://getcomposer.org/doc/01-basic-usage.md
require_once '/path/to/vendor/autoload.php';

use Twilio\Rest\Client;

// Find your Account SID and Auth Token at twilio.com/console
// and set the environment variables. See http://twil.io/secure
$sid = getenv("TWILIO_ACCOUNT_SID");
$token = getenv("TWILIO_AUTH_TOKEN");
$twilio = new Client($sid, $token);

$messages = $twilio->messages
                   ->read([
                              "dateSentAfter" => new \DateTime('2021-01-15T01:23:45Z'),
                              "dateSentBefore" => new \DateTime('2021-01-17T01:23:45Z')
                          ],
                          20
                   );

foreach ($messages as $record) {
    print($record->sid);
}
List all messages sent between January 15th, 2021, 01:23:45 UTC and January 17th, 2021, 01:23:45 UTC
Update a Message resource
POST https://api.twilio.com/2010-04-01/Accounts/{AccountSid}/Messages/{Sid}.json
Updates the body of a Message resource.

This action is primarily used to redact messages: to do so, POST to the above URI and set the Body parameter as an empty string: "". This will allow you to effectively redact the text of a message while keeping the other message resource properties intact.

Parameters
Parameters in PHP format
accountSid
Path
POST SID<AC> NOT PII
The SID of the Account that created the Message resources to update.

sid
Path
POST SID<MM> NOT PII
The Twilio-provided string that uniquely identifies the Message resource to update.

body
Optional
POST STRING PII MTL: 30 DAYS
The text of the message you want to send. Can be up to 1,600 characters long.

Example 1
Update: redact a message
You are viewing an outdated version of this SDK.
<?php
// Get the PHP helper library from https://twilio.com/docs/libraries/php
require_once '/path/to/vendor/autoload.php'; // Loads the library
use Twilio\Rest\Client;

// Your Account Sid and Auth Token from twilio.com/user/account
$sid = "ACe2cd954d7c5aa8ee517d9c7b04c0bff5";
$token = "your_auth_token";
$client = new Client($sid, $token);

$message = $client
    ->messages("MM800f449d0399ed014aae2bcc0cc2f2ec")
    ->update(
        array("body" => "")
    );
To redact the message-body from a message record, POST to the instance with an empty Body
Delete a Message resource
DELETE https://api.twilio.com/2010-04-01/Accounts/{AccountSid}/Messages/{Sid}.json
Deletes a message record from your account. Once the record is deleted, it will no longer appear in the API and Account Portal logs.

If successful, returns HTTP 204 (No Content) with no body.

Attempting to delete an in-progress message record will result in an error.

Parameters
Parameters in PHP format
accountSid
Path
DELETE SID<AC> NOT PII
The SID of the Account that created the Message resources to delete.

sid
Path
DELETE SID<MM> NOT PII
The Twilio-provided string that uniquely identifies the Message resource to delete.

Note: Deleting a message will also delete any media associated with the message, unless the same media object is associated with another message on your account that has not been deleted. For example, if you sent 1000 media messages with the same media attachment, that media object would remain accessible until the 1000 message records associated with it were deleted.

Example 1
Delete Message
<?php

// Update the path below to your autoload.php,
// see https://getcomposer.org/doc/01-basic-usage.md
require_once '/path/to/vendor/autoload.php';

use Twilio\Rest\Client;

// Find your Account SID and Auth Token at twilio.com/console
// and set the environment variables. See http://twil.io/secure
$sid = getenv("TWILIO_ACCOUNT_SID");
$token = getenv("TWILIO_AUTH_TOKEN");
$twilio = new Client($sid, $token);

$twilio->messages("MMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
       ->delete();
Message Media Subresources
Media List Subresource
Message instance resources have a Media list resource for the set of media elements included with a given Message:

/2010-04-01/Accounts/{AccountSid}/Messages/{MessageSid}/Media
Media Instance Subresource
Message instance resources have Media instance subresources. If media exists on a given message, you can retrieve information about images and other media.

/2010-04-01/Accounts/{AccountSid}/Messages/{MessageSid}/Media/{MediaSid}
Appendix
Message Status Values
The following are the possible values for the 'Status' parameter:

Status	Description
accepted	Twilio has received your API request to send a message with a Messaging Service and a From number is being dynamically selected. This will be the initial status when sending with a Messaging Service and the From parameter.
scheduled	(Pilot opt-in) The message is scheduled to be sent. This will be the initial status when scheduling a message with a Messaging Service
queued	The API request to send a message was successful and the message is queued to be sent out. This will be the initial status when you are not using a Messaging Service
sending	Twilio is in the process of dispatching your message to the nearest upstream carrier in the network.
sent	The nearest upstream carrier accepted the message.
receiving	The inbound message has been received by Twilio and is currently being processed.
received	On inbound messages only. The inbound message was received by one of your Twilio numbers.
delivered	Twilio has received confirmation of message delivery from the upstream carrier, and, where available, the destination handset.
undelivered	Twilio has received a delivery receipt indicating that the message was not delivered. This can happen for many reasons including carrier content filtering and the availability of the destination handset.
failed	The message could not be sent. This can happen for various reasons including queue overflows, account suspensions and media errors (in the case of MMS). Twilio does not charge you for failed messages.
read	On WhatsApp messages only. The message has been delivered and opened by the recipient in the conversation. The recipient must have enabled read receipts.
canceled	(Pilot opt-in) The message has been canceled. This status is only accessible when using a Messaging Service
Minimum library values that support "READ" receipts for WhatsApp messages

C#: 5.30.0
Java: 7.39.0
Node: 3.32.0
PHP: 5.33.0
Python: 6.28.0
Ruby: 5.24.0
Back to the Message properties list.

Delivery Related Errors
When a message's status is 'failed' or 'undelivered', the ErrorCode and ErrorMessage properties will contain one of the following.

Note that the ErrorMessage is meant to be a human-readable description – the values returned are subject to change in the future. A full list of Twilio Error Codes and troubleshooting tips can help you troubleshoot delivery issues.

Error Code	Error Message	Description
30001	Queue overflow	You tried to send too many messages too quickly, and your message queue overflowed. Try sending your message again after waiting for some time.
30002	Account suspended	Your account was suspended between the time of message send and delivery. Please contact Twilio.
30003	Unreachable destination handset	The destination handset you are trying to reach is switched off or otherwise unavailable.
30004	Message blocked	The destination number you are trying to reach is blocked from receiving this message (e.g., due to blacklisting).
30005	Unknown destination handset	The destination number you are trying to reach is unknown and may no longer exist.
30006	Landline or unreachable carrier	The destination number is unable to receive this message. Potential reasons could include trying to reach a landline or, in the case of short codes, an unreachable carrier.
30007	Carrier violation	Your message was flagged as objectionable by the carrier. To protect their subscribers, many carriers have implemented content or spam filtering. Learn more about carrier filtering
30008	Unknown error	The error does not fit into any of the above categories.
30009	Missing segment	One or more segments associated with your multi-part inbound message was not received.
30010	Message price exceeds max price.	The price of your message exceeds the max price parameter.
The following ErrorCodes apply only when you are sending a message via WhatsApp or a Channel.

ErrorCode	ErrorMessage	Description
63001	Channel could not authenticate the request	Channel authentication credentials are incorrect. Please check the credentials in Channel page in Console or re-authenticate with the Channel.
63002	Channel could not find From address	The From address does not map to any configured Channels. Check that you are using the correct Channel endpoint address from the Channel page.
63003	Channel could not find To address	The To address is incorrect.
63005	Channel did not accept given content	
63006	Could not format given content for the channel	
63007	Twilio could not find a Channel with the specified From address	The From address does not map to any configured Channels. Check that you are using the correct Channel endpoint address from the Channel page.
63008	Could not execute the request because the channel module is misconfigured	Please check the Channel configuration in Twilio.
63009	Channel returned an error when executing the request	Please see Channel specific error message for more information.
63010	Channels - Twilio Internal error	
63012	Channel returned an internal error that prevented it from completing the request	
63013	This message send failed because it violates Channel provider's policy.	Please see Channel specific error message for more information.
63014	This message failed to be delivered to the user because it was blocked by a user action.	Please see Channel specific error message for more information.
Back to the Message properties list.

NumSegments Property
For outbound messages, this property indicates the number of SMS messages it took to deliver the body of the message.

If the body of a message is more than 160 GSM-7 characters (or 70 UCS-2 characters), Twilio will segment and annotate your messages to attempt proper reassembly on the recipient's handset (not supported by all carriers and handsets). This ensures your body text transmits with the highest fidelity.

On inbound messages, this property indicates the number of SMS messages that make up the message received.

If the body of a message is more than 160 GSM-7 characters (or 70 UCS-2 characters), Twilio will attempt to reassemble the message received by your Twilio phone number. All carriers and handsets do not necessarily support this.

Your account will be charged for each segment sent or received.

Back to the Message properties list.

StatusCallback Request Parameters
The parameters Twilio passes to your application in its request to the StatusCallback URL include a subset of the standard request parameters and these additional parameters:

Parameter	Description
MessageStatus	The status of the message. Message delivery information is reflected in message status. The possible values are listed in the Message resource.
ErrorCode	The error code (if any) associated with your message. If your message status is failed or undelivered, the ErrorCode can give you more information about the failure. If the message was delivered successfully, no ErrorCode will be present. Find the possible values here.
ChannelInstallSid	The Installed Channel SID (found on the Channel detail page) that was used to send this message. Only present if the message was sent using a Channel.
ChannelStatusMessage	The Error message returned by the underlying Channel if Message delivery failed. Only present if the message was sent using a Channel and message delivery failed.
ChannelPrefix	Channel specific prefix that allows you to identify which channel this message was sent over.
EventType	Contains post-delivery events. If the Channel supports Read receipts, this parameter will be included with a value of READ after the user has read the message. Currently supported only for WhatsApp.
Back to Create a Message resource

Need some help?
We all do sometimes; code is hard. Get help now from our support team, or lean on the wisdom of the crowd by visiting Twilio's Community Forums or browsing the Twilio tag on Stack Overflow.

Terms of Service
Privacy Policy
Copyright © 2021 Twilio Inc.
