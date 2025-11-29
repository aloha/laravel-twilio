from flask import Flask
from twilio.twiml.voice_response import VoiceResponse
app = Flask(__name__)

@app.route("/answer", methods=['GET', 'POST'])
def answer_call():
    resp = VoiceResponse()
    resp.say("Twilio's always there when you call!")
    return str(resp)

if __name__ == "__main__":
    app.run()
github: hannesvdvreken
