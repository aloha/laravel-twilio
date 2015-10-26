<?php

namespace Aloha\Twilio\Commands;

use Illuminate\Console\Command;
use Symfony\Component\Console\Input\InputOption;
use Symfony\Component\Console\Input\InputArgument;
use Aloha\Twilio;

class TwilioMmsCommand extends Command {

    /**
     * The console command name.
     *
     * @var string
     */
    protected $name = 'twilio:mms';

    /**
     * The console command description.
     *
     * @var string
     */
    protected $description = 'Command to test MMS with Twilios API.';

    /**
     * Execute the console command.
     *
     * @return void
     */
    public function fire()
    {
        $this->line('Sending MMS via Twilio to: '.$this->argument('phone'));

        // Grab the text option if specified
        $text = $this->option('text');

        // If we havent specified a message, setup a default one
        if(is_null($text)) {
            $text = "This is a test message sent from the artisan console";
        }

        $this->line($text);

        // Grab the image option if specified
        $imageUrl = $this->option('image_url');

        // If we havent specified a message, setup a default one
        if(is_null($imageUrl)) {
            $imageUrl = "http://placehold.it/200x200";
        }

        $this->line($imageUrl);

        Twilio::messageWithMedia($this->argument('phone'), $text, array($imageUrl));
    }

    /**
     * Get the console command arguments.
     *
     * @return array
     */
    protected function getArguments()
    {
        return array(
            array('phone', InputArgument::REQUIRED, 'The phone number that will receive a test message.'),
        );
    }

    /**
     * Get the console command options.
     *
     * @return array
     */
    protected function getOptions()
    {
        return array(
            array('text', null, InputOption::VALUE_OPTIONAL, 'Optional message that will be sent.', null),
            array('image_url', null, InputOption::VALUE_REQUIRED, 'Required image url url that will be sent.', null)
        );
    }

}