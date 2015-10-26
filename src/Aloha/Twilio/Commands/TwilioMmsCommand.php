<?php

namespace Aloha\Twilio\Commands;

use Illuminate\Console\Command;
use Symfony\Component\Console\Input\InputOption;
use Symfony\Component\Console\Input\InputArgument;

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
    protected $description = 'Send an MMS with Twilio.';

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

        // If we haven't specified a message, setup a default one
        if (! is_null($text)) {
            $this->line($text);
        }

        // Grab the image URLs passed as options
        $imageUrls = $this->option('image_url');

        foreach ($imageUrls as $imageUrl) {
            $this->line($imageUrl);
        }

        \Twilio::messageWithMedia($this->argument('phone'), $text, $imageUrls);
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
        $description = 'URLs (zero or more) for images to add to the MMS.';

        return array(
            array('text', null, InputOption::VALUE_OPTIONAL, 'Text message to add.', null),
            array('image_url', null, InputOption::VALUE_OPTIONAL|InputOption::VALUE_IS_ARRAY, $description, array()),
        );
    }

}