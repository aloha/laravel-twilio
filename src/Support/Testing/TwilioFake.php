<?php
namespace Aloha\Twilio\Support\Testing;

use Aloha\Twilio\TwilioInterface;
use PHPUnit\Framework\Assert as PHPUnit;
use Psr\Log\LoggerInterface;

class TwilioFake implements TwilioInterface
{
    protected $calls = [];
    protected $connection;
    protected $logger;
    protected $messages = [];

    public function __construct(LoggerInterface $logger)
    {
        $this->logger = new LogDriver($logger);
    }

    /**
     * @param string $connection
     *
     * @return $this
     */
    public function from($connection)
    {
        $this->connection = $connection;

        return $this;
    }

    /**
     * @param string $to
     * @param string $message
     */
    public function call($to, $message)
    {
        $this->calls[] = (object) [
            'to' => $to,
            'message' => $message,
            'connection' => $this->connection,
        ];

        call_user_func_array([$this->logger, 'call'], func_get_args());
    }

    /**
     * @param string $to
     * @param string $message
     */
    public function message($to, $message)
    {
        $this->messages[] = (object) [
            'to' => $to,
            'message' => $message,
            'connection' => $this->connection,
        ];

        call_user_func_array([$this->logger, 'message'], func_get_args());
    }

    /**
     * @param string $to
     * @param string $message
     * @param string|null $connection
     *
     * @return $this
     */
    public function assertCallSent($to, $message, $connection = null)
    {
        PHPUnit::assertTrue(
            $this->sent($this->calls, $to, $message, $connection)->count() > 0,
            "The expected [{$message}] call was not sent."
        );

        return $this;
    }

    /**
     * @param string $to
     * @param string $message
     * @param string|null $connection
     *
     * @return $this
     */
    public function assertMessageSent($to, $message, $connection = null)
    {
        PHPUnit::assertTrue(
            $this->sent($this->messages, $to, $message, $connection)->count() > 0,
            "The expected [{$message}] message was not sent."
        );

        return $this;
    }

    /**
     * @param array $twilioRequests
     * @param string $to
     * @param string $message
     * @param string|null $connection
     *
     * @return \Illuminate\Support\Collection
     */
    protected function sent($twilioRequests, $to, $message, $connection)
    {
        return collect($twilioRequests)->filter(function ($twilioRequest) use ($to, $message, $connection) {
            return $twilioRequest->to === $to && $twilioRequest->message === $message && (
                is_null($connection) || $twilioRequest->connection === $connection
            );
        });
    }
}
