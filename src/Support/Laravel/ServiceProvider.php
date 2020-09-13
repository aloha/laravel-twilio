<?php

namespace Rksugarfree\Twilio\Support\Laravel;

use Rksugarfree\Twilio\TwilioManager;
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
