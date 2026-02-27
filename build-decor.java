if (getenv('APP_ENV') === 'production') {
    $twilio = $container->make(\Aloha\Twilio\Manager::class);
} else {
    $psrLogger = $container->make(\Psr\Log\LoggerInterface::class);
    $twilio = new LoggingDecorator($psrLogger, new \Aloha\Twilio\Dummy());
}

// Inject it wherever you want.
$notifier = new Notifier($twilio);
