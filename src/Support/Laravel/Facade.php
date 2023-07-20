<?php

namespace Aloha\Twilio\Support\Laravel;

use Illuminate\Support\Facades\Facade as BaseFacade;
use Aloha\Twilio\Support\FakeFacade;
use Aloha\Twilio\Dummy;

class Facade extends BaseFacade
{
    /**
     * Get the registered name of the component.
     *
     * @return string
     */
    protected static function getFacadeAccessor(): string
    {
        return 'twilio';
    }
    
    public static function fake($jobsToFake = [])
    {
        static::swap($fake = new FakeFacade(static::getFacadeRoot()));
        return $fake;
    }
}
