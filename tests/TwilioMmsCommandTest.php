<?php
namespace Aloha\Twilio\Tests;

use Aloha\Twilio\Commands\TwilioMmsCommand;
use PHPUnit_Framework_TestCase;

class TwilioMmsCommandTest extends PHPUnit_Framework_TestCase
{
    /**
     * Test the name of the command
     */
    public function testName()
    {
        // Arrange
        $stub = $this->getMock('Aloha\Twilio\TwilioInterface');
        $command = new TwilioMmsCommand($stub);

        // Act
        $name = $command->getName();

        // Assert
        $this->assertEquals('twilio:mms', $name);
    }
}
