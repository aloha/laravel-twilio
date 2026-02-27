laravel-twilio
Laravel Twilio API Integration

Build Status Total Downloads Latest Stable Version License

Installation
Begin by installing this package through Composer. Run this command from the Terminal:

composer require aloha/twilio
This will register two new artisan commands for you:

twilio:sms
twilio:call
And make these objects resolvable from the IoC container:

Aloha\Twilio\Manager (aliased as twilio)
Aloha\Twilio\TwilioInterface (resolves a Twilio object, the default connection object created by the Manager).
There's a Facade class available for you, if you like. In your app.php config file add the following line to the aliases array if you want to use a short class name:

'Twilio' => 'Aloha\Twilio\Support\Laravel\Facade',
You can publish the default config file to config/twilio.php with the terminal command

php -r "copy('vendor/aloha/twilio/src/config/config.php', 'config/twilio.php');"
Facade
The facade has the exact same methods as the Aloha\Twilio\TwilioInterface. First, include the Facade class at the top of your file:

use Twilio;
To send a message using the default entry from your twilio config file:

Twilio::message($user->phone, $message);
One extra feature is that you can define which settings (and which sender phone number) to use:

Twilio::from('call_center')->message($user->phone, $message);
Twilio::from('board_room')->message($boss->phone, 'Hi there boss!');
Define multiple entries in your twilio config file to make use of this feature.

Usage
Creating a Twilio object. This object implements the Aloha\Twilio\TwilioInterface.

$twilio = new Aloha\Twilio\Twilio($accountId, $token, $fromNumber);
Sending a text message:

$twilio->message('+18085551212', 'Pink Elephants and Happy Rainbows');
Creating a call:

$twilio->call('+18085551212', 'http://foo.com/call.xml');
Generating a call and building the message in one go:

$twilio->call('+18085551212', function (\Twilio\TwiML\VoiceResponse $message) {
    $message->say('Hello');
    $message->play('https://api.twilio.com/cowbell.mp3', ['loop' => 5]);
});
or to make a call with any Twiml description you can pass along any Twiml object:

$message = new \Twilio\TwiML\VoiceResponse();
$message->say('Hello');
$message->play('https://api.twilio.com/cowbell.mp3', ['loop' => 5]);

$twilio->call('+18085551212', $message);
Access the configured Twilio\Rest\Client object:

$sdk = $twilio->getTwilio();
You can also access this via the Facade as well:

$sdk = Twilio::getTwilio();
Pass as many optional parameters as you want
If you want to pass on extra optional parameters to the messages->sendMessage(...) method from the Twilio SDK, you can do so by adding to the message method. All arguments are passed on, and the from field is prepended from configuration.

$twilio->message($to, $message, $mediaUrls, $params);
// passes all these params on.
The same is true for the call method.

$twilio->call($to, $message, $params);
// passes all these params on.
