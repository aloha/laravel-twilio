laravel5-twilio
===============
Laravel 5 Twillio API Integration


- `twilio:sms`
- `twilio:call`


## Installation
Begin by installing this package through Composer. Edit your project's `composer.json` file to require `olsgreen/twilio` and the repository (only until we publish on packagist).

    "require": {
        ...
        "olsgreen/twilio": "2.0.*",
    },
    "repositories": [
        {
            "type": "vcs",
            "url":  "git@github.com:olsgreen/laravel5-twilio.git"
        }
    ]

Next, update Composer from the Terminal:

    composer update

Once composer is finished, you need to add the service provider. Open `config/app.php`, and add a new item to the providers array.

    'Aloha\Twilio\TwilioServiceProvider',

Then, add a Facade for more convenient usage. In `config/app.php` add the following line to the `aliases` array:

    'Twilio' => 'Aloha\Twilio\Facades\Twilio',

Add the configuration to `config/services.php`

```
'twilio' => [
    'sid' => 'Your Twilio Account SID #',
    'token' => 'Access token that can be found in your Twilio dashboard',
    'from' => 'The Phone number registered with Twilio that your SMS & Calls will come from',
    'ssl_verify' => true, // Development switch to bypass API SSL certificate verfication
]
```

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

laravel5-twilio is open-sourced software licensed under the [MIT license](http://opensource.org/licenses/MIT)

### Credits

Based on the original Laravel 4 package by Travis J Ryan that can be found here:

https://github.com/aloha/laravel4-twilio
