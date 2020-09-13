<?php

return [
    /*
    |-------------------------------------------------------------------------------
    | Connections
    |-------------------------------------------------------------------------------
    |
    | The default connection should be enough for most apps. If you need additional
    | connections you can add them to the configuration here.
    |
    */
    'default' => [
        /*
        |--------------------------------------------------------------------------
        | SID
        |--------------------------------------------------------------------------
        |
        | Your Twilio Account SID #
        |
        */
        'sid' => env('TWILIO_SID', ''),

        /*
        |--------------------------------------------------------------------------
        | Access Token
        |--------------------------------------------------------------------------
        |
        | Access token that can be found in your Twilio dashboard
        |
        */
        'token' => env('TWILIO_TOKEN', ''),

        /*
        |--------------------------------------------------------------------------
        | From Number
        |--------------------------------------------------------------------------
        |
        | The Phone number registered with Twilio that your SMS & Calls will come from,
        | You can override this when making calls and sms messages by setting the new number
        | in the $params
        |
        */
        'from' => env('TWILIO_FROM', ''),
    ]
];
