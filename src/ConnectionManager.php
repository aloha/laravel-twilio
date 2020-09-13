<?php

namespace Aloha\Twilio;

use Aloha\Twilio\Interfaces\CommunicationsFacilitator;
use InvalidArgumentException;

class ConnectionManager
{
    /* @var string */
    private $default;

    /* @var array */
    private $settings;

    public function __construct(string $default = 'default', array $settings = [])
    {
        $this->default = $default;
        $this->settings = $settings ?? config('twilio');
    }

    public function from(string $connection): CommunicationsFacilitator
    {
        if (empty($this->settings[$connection])) {
            throw new InvalidArgumentException("Connection \"{$connection}\" is not configured.");
        }

        $settings = $this->settings[$connection];

        return new Twilio($settings['sid'], $settings['token'], $settings['from']);
    }

    public function defaultConnection(): CommunicationsFacilitator
    {
        return $this->from($this->default);
    }
}
