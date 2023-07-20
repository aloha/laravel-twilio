<?php

namespace Aloha\Twilio\Support;

use Twilio\Exceptions\ConfigurationException;
use Twilio\Exceptions\TwilioException;
use Twilio\Rest\Api;
use Twilio\Rest\Api\V2010;
use Twilio\Rest\Api\V2010\Account\CallInstance;
use Twilio\Rest\Api\V2010\Account\MessageInstance;
use Twilio\Rest\Client;
use Twilio\TwiML\TwiML;
use Twilio\TwiML\VoiceResponse;
use Aloha\Twilio\TwilioInterface;
use Aloha\Twilio\Manager as Twilio;
use Illuminate\Support\Arr;
use PHPUnit\Framework\Assert;

class FakeFacade implements TwilioInterface
{
	/**
	 * @var string
	 */
	protected $sid;

	/**
	 * @var string
	 */
	protected $token;

	/**
	 * @var string
	 */
	protected $from;

	/**
	 * @var bool
	 */
	protected $sslVerify;

	/**
	 * @var Client
	 */
	protected $twilio;
	
	protected $history;
	
	protected $settings = [
		'default' => [
			'sid' => 'sid',
			'token' => 'token',
			'from' => '+18005551234',
		]
	];

	/**
	 * @param string $token
	 * @param string $from
	 * @param string $sid
	 * @param bool $sslVerify
	 */
	 
	public function __construct(Twilio $instance)
	{
		$this->instance = $instance;
		$this->history = collect([]);
	}
	
	public function instance(string $sid, string $token, string $from, bool $sslVerify = true)
	{
		$this->sid = $sid;
		$this->token = $token;
		$this->from = $from;
		$this->sslVerify = $sslVerify;
		
		return $this;
	}

	/**
	 * @param string $to
	 * @param string $message
	 * @param array $mediaUrls
	 * @param array $params
	 *
	 * @see https://www.twilio.com/docs/api/messaging/send-messages Documentation
	 *
	 * @throws ConfigurationException
	 * @throws TwilioException
	 *
	 * @return MessageInstance
	 */
	public function message(string $to, string $message, array $mediaUrls = [], array $params = []): MessageInstance
	{
		$params['body'] = $message;

		if (!isset($params['from'])) {
			$params['from'] = $this->from;
		}

		if (!empty($mediaUrls)) {
			$params['mediaUrl'] = $mediaUrls;
		}
		
		$this->history["sms_$to"] = new MessageInstance(new V2010(new Api(new Client('nonsense', 'nonsense'))), [], '');
		
		return $this->history["sms_$to"];
	}

	/**
	 * @param string $to
	 * @param callable|string|TwiML $message
	 * @param array $params
	 *
	 * @throws TwilioException
	 *
	 * @see https://www.twilio.com/docs/api/voice/making-calls Documentation
	 *
	 * @return CallInstance
	 */
	public function call(string $to, $message, array $params = []): CallInstance
	{
		if (is_callable($message)) {
			$message = $this->twiml($message);
		}

		if ($message instanceof TwiML) {
			$params['twiml'] = (string) $message;
		} else {
			$params['url'] = $message;
		}

		$this->history["call_$to"] = new CallInstance(new V2010(new Api(new Client('nonsense', 'nonsense'))), [], '');
		
		return $this->history["call_$to"];
	}

	/**
	 * @throws ConfigurationException
	 *
	 * @return Client
	 */
	public function getTwilio(): Client
	{
		if ($this->twilio) {
			return $this->twilio;
		}

		return $this->twilio = new Client($this->sid, $this->token);
	}

	/**
	 * @param callable $callback
	 *
	 * @return TwiML
	 */
	private function twiml(callable $callback): TwiML
	{
		$message = new VoiceResponse();

		call_user_func($callback, $message);

		return $message;
	}
	
	/**
	 * @return TwilioInterface
	 */
	public function defaultConnection(): TwilioInterface
	{
		return $this->from('default');
	}
	
	/**
	 * @param string $connection
	 *
	 * @return TwilioInterface
	 */
	public function from(string $connection): TwilioInterface
	{
		if (!isset($this->settings[$connection])) {
			throw new InvalidArgumentException("Connection \"{$connection}\" is not configured.");
		}
	
		$settings = $this->settings[$connection];
	
		return $this->instance($settings['sid'], $settings['token'], $settings['from']);
	}
	
	public function assertMessageSent(string $to)
	{
		Assert::assertInstanceOf(MessageInstance::class, $this->history["sms_$to"] ?? null);
	}
	
	public function assertCallSent(string $to)
	{
		Assert::assertInstanceOf(CallInstance::class, $this->history["call_$to"] ?? null);
	}
}
