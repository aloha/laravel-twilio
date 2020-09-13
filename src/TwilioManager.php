<?php

namespace Rksugarfree\Twilio;

use Rksugarfree\Twilio\Interfaces\CommunicationsClient;
use InvalidArgumentException;

class TwilioManager
{
    /* @var string */
    private $default;

    /* @var array */
    private $settings;

    public function __construct(string $default = 'twilio', array $settings = [])
    {
        $this->default = $default;
        $this->settings = $settings ?? config('clients');
    }

    public function from(string $connection): CommunicationsClient
    {
        if (empty($this->settings[$connection])) {
            throw new InvalidArgumentException("Connection \"{$connection}\" is not configured.");
        }

        $settings = $this->settings[$connection];

        return new Twilio($settings['sid'], $settings['token'], $settings['from']);
    }

    public function defaultConnection(): CommunicationsClient
    {
        return $this->from($this->default);
    }
}
