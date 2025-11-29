Pass as many optional parameters as you want
If you want to pass on extra optional parameters to the messages->sendMessage(...) method from the Twilio SDK, you can do so by adding to the message method. All arguments are passed on, and the from field is prepended from configuration.

$twilio->message($to, $message, $mediaUrls, $params);
// passes all these params on.
The same is true for the call method.

$twilio->call($to, $message, $params);
// passes all these params on.
Dummy class
There is a dummy implementation of the TwilioInterface available: Aloha\Twilio\Dummy. This class allows you to inject this instead of a working implementation in case you need to run quick integration tests.

Logging decorator
There is one more class available for you: the Aloha\Twilio\LoggingDecorator. This class wraps any TwilioInterface object and logs whatever Twilio will do for you. It also takes a Psr\Log\LoggerInterface object (like Monolog) for logging, you know.

By default the service providers don't wrap objects with the LoggingDecorator, but it is at your disposal in case you want it. A possible use case is to construct a TwilioInterface object that logs what will happen, but doesn't actually call Twilio (using the Dummy class):

if (getenv('APP_ENV') === 'production') {
    $twilio = $container->make(\Aloha\Twilio\Manager::class);
} else {
    $psrLogger = $container->make(\Psr\Log\LoggerInterface::class);
    $twilio = new LoggingDecorator($psrLogger, new \Aloha\Twilio\Dummy());
}

// Inject it wherever you want.
$notifier = new Notifier($twilio);
