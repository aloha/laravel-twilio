<?php

namespace Rksugarfree\Twilio\Interfaces;

interface ClientManager
{
    public function from(string $connection): CommunicationsClient;

    public function defaultConnection(): CommunicationsClient;
}
