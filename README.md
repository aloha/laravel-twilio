laravel-twilio
===============
Laravel Twilio API Integration

[![License](https://img.shields.io/github/license/aloha/laravel-twilio?style=flat-square)](#license)

## Installation

```bash
composer require rksugarfree/twilio
```

#### Facade

The default TwilioManager facade is available to you:

```php
use TwilioManager;
```

### Interfaces
You can register the interfaces provided and, using Laravel's Service Container, bind them to an implementation in your app. One they are registered, all you'll
have to do is type hint an interface, and the chosen implementation will be injected. [Service Container Docs](https://laravel.com/docs/8.x/container)

#### Twilio Implementation
```php
$this->app->singleton(Interfaces\CommunicationsClient::class, function (Application $app) {
    return $app->make('twilio')->defaultConnection();
});

$this->app->singleton(Interfaces\ClientManager::class, function (Application $app) {
    return $app->make('twilio');
});
```

#### Custom Implementation
Create you own implementations by implementing CommunicationsClient and ClientManager in your own custom classes. Just create the classes and customize the published configuration to
suit your needs.
```php
$this->app->singleton(Interfaces\CommunicationsClient::class, function (Application $app) {
    return new YourClassThatImplementsCommunicationsClient($app['config']->get("clients.{$defaultConnectionKey}"));
});

$this->app->singleton(Interfaces\ClientManager::class, function (Application $app) {
    return new YourClassThatImplementsClientManager($app['config']->get('clients'));
});
```

You can publish the default config file to `config/clients.php` with the artisan command. You don't need to follow the conventions of the 'twilio'
key, you can add whatever sub keys you need.

```shell
php artisan vendor:publish --tag=config --provider=Rksugarfree\Twilio\Support\Laravel\ServiceProvider
```

```php
return [
    'your-connection-key' => [
        //whatever you want
    ]
];
```

### Usage of the default TwilioManager

To access the default Twilio implementation call the TwilioManager facade using the default connection. Since it's a facade
you should access the methods statically:

```php
$twilio = TwilioManager::defaultConnection();
```

If you want to access a different connection from the Manager you can do this:

```php
$twilio = TwilioManager::from("{$keyInClientsConfig}");
```

Sending a text message:

```php
$twilio->message('+18085551212', 'Pink Elephants and Happy Rainbows');
```

Creating a call:

```php
$twilio->call('+18085551212', 'http://foo.com/call.xml');
```

Generating a call and building the message in one go:

```php
$twilio->call('+18085551212', function (\Twilio\TwiML\VoiceResponse $message) {
    $message->say('Hello');
    $message->play('https://api.twilio.com/cowbell.mp3', ['loop' => 5]);
});
```

or to make a call with _any_ Twiml description you can pass along any Twiml object:

```php
$message = new \Twilio\TwiML\VoiceResponse();
$message->say('Hello');
$message->play('https://api.twilio.com/cowbell.mp3', ['loop' => 5]);

$twilio->call('+18085551212', $message);
```

Access the configured `Twilio\Rest\Client` object:

```php
$sdk = $twilio->getClient();
```

##### Pass as many optional parameters as you want

[Twilio Message Docs](https://www.twilio.com/docs/api/messaging/send-messages)

You can customize tons of things about the call/message, including here it comes from: 

```php
$params['from'] = '222-222-2222';

$twilio->message($to, $message, $mediaUrls, $params);
```

The same is true for the [call method](https://www.twilio.com/docs/api/voice/call#post-parameters).

```php
$params['from'] = '222-222-2222';

$twilio->call($to, $message, $params);
```

#### Dummy class

There is a dummy implementation of the `CommunicationsClient` available: `Rksugarfree\Twilio\TwilioFake`. This class
allows you to inject this instead of a working implementation in case you need to run quick integration tests.

## Credits

- Original Repo [Aloha/Twilio](https://github.com/aloha/laravel-twilio)
- Robert Kerr [@rksugarfree](https://twitter.com/rksugarfree)

### License

laravel-twilio is open-sourced software licensed under the [MIT license](http://opensource.org/licenses/MIT)
