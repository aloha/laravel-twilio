<?php

namespace Aloha\Twilio\Support\Laravel;

use Aloha\Twilio\Interfaces\CommunicationsFacilitator;
use Aloha\Twilio\Manager;
use Illuminate\Support\ServiceProvider as BaseServiceProvider;

class ServiceProvider extends BaseServiceProvider
{
    public function register()
    {
        // Register manager for usage with the Facade.
        $this->app->singleton('twilio', function() {
            $config = $this->app['config']->get('twilio.twilio');

            return new Manager($config['default'], $config['connections']);
        });

        // Define an alias.
        $this->app->alias('twilio', Manager::class);

        // Register TwilioInterface concretion.
        $this->app->singleton(CommunicationsFacilitator::class, function() {
            return $this->app->make('twilio')->defaultConnection();
        });
    }

    public function boot()
    {
        $this->publishes([
            __DIR__.'/../../config/config.php' => config_path('twilio.php'),
        ], 'config');

        $this->mergeConfigFrom(__DIR__.'/../../config/config.php', 'twilio');
    }
}
