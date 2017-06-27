<?php
namespace Aloha\Twilio\Support\Laravel;

use Illuminate\Support\ServiceProvider as LaravelServiceProvider;

class ServiceProvider extends LaravelServiceProvider
{
    /**
     * @var \Illuminate\Support\ServiceProvider
     */
    protected $provider;

    /**
     * Boot method.
     */
    public function boot()
    {
        return $this->provider()->boot();
    }

    /**
     * Register the service provider.
     */
    public function register()
    {
        return $this->provider()->register();
    }

    /**
     * @return \Illuminate\Support\ServiceProvider
     */
    protected function provider()
    {
        if ($this->provider) {
            return $this->provider;
        }

        if ($this->is_laravel() && version_compare($this->app->version(), '5.0', '<')) {
            $this->provider = new L4ServiceProvider($this->app);
        } else {
            $this->provider = new L5ServiceProvider($this->app);
        }

        return $this->provider;
    }

    private function is_lumen() {
        return class_exists('Laravel\Lumen\Application');
    }

    private function is_laravel() {
        return !$this->is_lumen();
    }
}
