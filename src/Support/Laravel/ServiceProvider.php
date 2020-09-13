<?php

namespace Aloha\Twilio\Support\Laravel;

use Aloha\Twilio\Interfaces\CommunicationsFacilitator;
use Aloha\Twilio\ConnectionManager;
use Illuminate\Foundation\Application;
use Illuminate\Support\ServiceProvider as BaseServiceProvider;

class ServiceProvider extends BaseServiceProvider
{
    public function register()
    {
        $this->app->singleton('twilio-manager', function(Application $app) {
            $config = $app['config']->get('twilio');
            return new ConnectionManager($config);
        });

        $this->app->singleton(CommunicationsFacilitator::class, function(Application $app) {
            return $app->make('twilio-manager')->defaultConnection();
        });
    }

    public function boot()
    {
        $this->publishes([
            __DIR__.'/../../config/twilio.php' => config_path('twilio.php'),
        ], 'config');

        $this->mergeConfigFrom(__DIR__.'/../../config/twilio.php', 'twilio');
    }
}
