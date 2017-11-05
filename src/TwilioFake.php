<?php
namespace Aloha\Twilio;

use Psr\Log\LoggerInterface;
use RuntimeException;

class TwilioFake implements TwilioInterface
{
    /**
     * @var LoggingDecorator
     */
    protected $logger;

    /**
     * @var LoggerInterface $logger
     */
    public function __construct(LoggerInterface $logger)
    {
        $this->logger = new LoggingDecorator($logger, new Dummy);
    }

    /**
     * @return TwilioInterface
     */
    public function from()
    {
        return $this;
    }

    /**
     * @return TwilioInterface
     */
    public function getConnection()
    {
        return $this;
    }

    /**
     * @param string $to
     * @param string|callable $message
     *
     * @return void
     */
    public function message($to, $message)
    {
        return call_user_func_array([$this->logger, 'message'], func_get_args());
    }

    /**
     * @param string $to
     * @param string|callable $message
     *
     * @return void
     */
    public function call($to, $message)
    {
        return call_user_func_array([$this->logger, 'call'], func_get_args());
    }

    /**
     * @param string $method
     * @param array $arguments
     *
     * @throws RuntimeException
     *
     * @return void
     */
    public function __call($method, $arguments)
    {
        throw new RuntimeException(
            "Can't call Twilio@{$method}() when faking API requests."
        );
    }
}
