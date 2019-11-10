#Example code taken from github 
#user joextodd https://github.com/chirp/chirp-python-examples

import argparse
import time
from chirpc_GM import app_key, app_secret, app_config
from chirpsdk import ChirpSDK, CallbackSet

class Callbacks(CallbackSet):

    def on_receiving(self, channel):
        print('Receiving message...')

    def on_received(self, payload, channel):
        if payload is None:
            print('Failed to decode message!')
        else:
        	message = payload.decode('utf-8')
        	if message[:4] == '1234':
        		print('Secure Message Received: ' +  message)
        	else:
        		print("Invalid Message")
        	
def main():
    # ------------------------------------------------------------------------
    # Initialise the Connect SDK.
    # ------------------------------------------------------------------------
    sdk = ChirpSDK(app_key,app_secret,app_config)
    # ------------------------------------------------------------------------
    # Parse unicode and send as a chirp payload
    # ------------------------------------------------------------------------

    choice = input("Please Type a message to deliver:")

    if choice == '1':
        message = "Help"
    elif choice == '2':
        message = 'Surface'
    elif choice == '3':
        message = 'SHARK!'
    else:
        message = "Out of Oxygen"

    message = message.encode('utf-8')
    payload = sdk.new_payload(message)

    sdk.set_callbacks(Callbacks())
    sdk.start()
    sdk.send(payload)

    try:
        # Process audio streams
        while True:
            time.sleep(0.1)
    except KeyboardInterrupt:
        print('Exiting')

    sdk.stop()


if __name__ == '__main__':
    main()
