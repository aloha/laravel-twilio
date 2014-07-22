laravel4-twilio
===============
Laravel 4 Twillio API Integration


- `twilio:sms`
- `twilio:call`


## Installation
Begin by installing this package through Composer. Edit your project's `composer.json` file to require `aloha/twilio`.

    "require": {
		"laravel/framework": "4.*",
		"aloha/twilio": "dev-master"
	},
	"minimum-stability" : "dev"


Next, update Composer from the Terminal:

    composer update

Once composer is finished, you need to add the service provider. Open `app/config/app.php`, and add a new item to the providers array.

    'Travisjryan\Twilio\TwilioServiceProvider',

Then, add a Facade for more convenient usage. In `app/config/app.php` add the following line to the `aliases` array:

        'Twilio' => 'Travisjryan\Twilio\Facades\Twilio',

Publish config files from the Terminal

        php artisan config:publish travisjryan/twilio
        
Edit `config/packages/travisjryan/twilio` with your appropriate Twilio settings        


## Usage

Sending a SMS Message

```php
<?php

Twilio::message('+18085551212', 'Pink Elephants and Happy Rainbows');

?>
```

Creating a Call

```php
<?php

Twilio::call('+18085551212', 'http://foo.com/call.xml');

?>
```

Generating TwiML

```php
<?php

$twiml = Twilio::twiml(function($message) {
    $message->say('Hello');
    $message->play('https://api.twilio.com/cowbell.mp3', array('loop' => 5));
});

print $twiml;

?>
```

### License

laravel4-twilio is open-sourced software licensed under the [MIT license](http://opensource.org/licenses/MIT)
