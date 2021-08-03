use Twilio;
To send a message using the default entry from your twilio config file:

Twilio::message($user->phone, $message);
One extra feature is that you can define which settings (and which sender phone number) to use:

Twilio::from('call_center')->message($user->phone, $message);
Twilio::from('board_room')->message($boss->phone, 'Hi there boss!');
