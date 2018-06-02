<?php
namespace Tests;

use Aloha\Twilio\Support\Laravel\Facade as Twilio;
use Orchestra\Testbench\TestCase;

class TwilioFakeTest extends TestCase
{
    public function testItCanFakePhoneCalls()
    {
        Twilio::fake();

        Twilio::from('default')->call('+15551234567', 'phone call fake');

        Twilio::assertCallSent('+15551234567', 'phone call fake');
        Twilio::assertCallSent('+15551234567', 'phone call fake', 'default');
    }

    public function testItCanFakeMessages()
    {
        Twilio::fake();

        Twilio::message('+32474123456', 'message fake');

        Twilio::assertMessageSent('+32474123456', 'message fake');
    }

    public function testItWillThrowAnExceptionWhenPhoneCallWasNotMade()
    {
        $this->expectAssertionFailedException('The expected [phone call not found] call was not sent.');

        Twilio::fake();

        Twilio::call('+15551234567', 'phone call fake');

        Twilio::assertCallSent('+15551234567', 'phone call not found');
    }

    public function testItWillThrowAnExceptionWhenMessageWasNotMadeOnTheSameConnection()
    {
        $this->expectAssertionFailedException('The expected [another connection] message was not sent.');

        Twilio::fake();

        Twilio::from('europe')->message('+32474123456', 'another connection');

        Twilio::assertMessageSent('+32474123456', 'another connection', 'north-america');
    }

    protected function expectAssertionFailedException($message)
    {
        if ($this->isNamespacedPHPUnit()) {
            $this->expectException(\PHPUnit\Framework\AssertionFailedError::class);
            $this->expectExceptionMessage($message);
        } else {
            $this->setExpectedException(\PHPUnit_Framework_AssertionFailedError::class, $message);
        }
    }

    protected function isNamespacedPHPUnit()
    {
        return method_exists($this, 'expectException') && !method_exists($this, 'setExpectedException');
    }
}
