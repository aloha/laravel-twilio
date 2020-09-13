<?php

namespace Aloha\Twilio\Interfaces;

use Twilio\Rest\Api\V2010\Account\CallInstance;
use Twilio\Rest\Api\V2010\Account\MessageInstance;

interface CommunicationsFacilitator
{
    public function call(string $to, $message, array $params): CallInstance;

    public function message(string $to, string $message, array $mediaUrls = [], array $params = []): MessageInstance;
}
