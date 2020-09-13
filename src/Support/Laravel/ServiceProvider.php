<?php

namespace Aloha\Twilio\Support\Laravel;

use Aloha\Twilio\Interfaces\ClientManager;
use Aloha\Twilio\Interfaces\CommunicationsClient;
use Aloha\Twilio\TwilioManager;
use Illuminate\Foundation\Application;
use Illuminate\Support\ServiceProvider as BaseServiceProvider;

class ServiceProvider extends BaseServiceProvider
{
    public function register()
    {
        $this->app->singleton('twilio', function(Application $app) {
            $config = $app['config']->get('clients');
            return new TwilioManager($config);
        });
    }

    public function boot()
    {
        $this->publishes([
            __DIR__.'/../../config/clients.php' => config_path('clients.php'),
        ], 'config');

        $this->mergeConfigFrom(__DIR__.'/../../config/clients.php', 'clients');
    }
}
