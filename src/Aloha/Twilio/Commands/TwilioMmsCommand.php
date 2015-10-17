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
    protected $description = 'Twilio command to test Twilio API Integration.';

    /**
     * Create a new command instance.
     *
     * @return void
     */
    public function __construct()
    {
        parent::__construct();
    }

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
        $image_url = $this->option('image_url');

        // If we havent specified a message, setup a default one
        if(is_null($image_url)) {
            $image_url = "http://placehold.it/200x200";
        }

        $this->line($image_url);

        Twilio::messageWithMedia($this->argument('phone'), $text, array($image_url));
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
            array('image_url', null, InputOption::VALUE_OPTIONAL, 'Optional image url url that will be sent.', null)
        );
    }

}