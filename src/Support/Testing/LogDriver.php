<?php
namespace Aloha\Twilio\Support\Testing;

use Aloha\Twilio\TwilioInterface;
use Psr\Log\LoggerInterface;

class LogDriver implements TwilioInterface
{
    /**
     * @var LoggerInterface
     */
    private $logger;

    /**
     * @param LoggerInterface $logger
     */
    public function __construct(LoggerInterface $logger)
    {
        $this->logger = $logger;
    }

    /**
     * @param string $to
     * @param string $message
     */
    public function message($to, $message)
    {
        $this->logger->info("Sending a message [\"{$message}\"] to {$to}");
    }

    /**
     * @param string $to
     * @param string|callable $message
     */
    public function call($to, $message)
    {
        $this->logger->info("Calling {$to}");
    }
}
