<?php

namespace Aloha\Twilio;

use Aloha\Twilio\Interfaces\CommunicationsFacilitator;
use InvalidArgumentException;
use Twilio\Rest\Api\V2010\Account\CallInstance;
use Twilio\Rest\Api\V2010\Account\MessageInstance;
use Twilio\TwiML\TwiML;

class Manager
{
    /* @var string */
    protected $default;

    /* @var array */
    protected $settings;

    public function __construct(string $default, array $settings)
    {
        $this->default = $default;
        $this->settings = $settings;
    }

    /* @return mixed */
    public function __call(string $method, array $arguments)
    {
        return call_user_func_array([$this->defaultConnection(), $method], $arguments);
    }

    public function from(string $connection): CommunicationsFacilitator
    {
        if (!isset($this->settings[$connection])) {
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
