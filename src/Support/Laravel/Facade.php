<?php
namespace Aloha\Twilio\Support\Laravel;

use Aloha\Twilio\Support\Testing\TwilioFake;
use Illuminate\Support\Facades\Facade as BaseFacade;

class Facade extends BaseFacade
{
    /**
     * Get the registered name of the component.
     *
     * @return string
     */
    protected static function getFacadeAccessor()
    {
        return 'twilio';
    }

    /**
     * Replace the bound instance with a fake.
     */
    public static function fake()
    {
        static::swap(static::$app->make(TwilioFake::class));
    }
}
