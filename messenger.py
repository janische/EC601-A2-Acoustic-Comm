#Example code taken from github 
#user joextodd https://github.com/chirp/chirp-python-examples

import argparse
import time

from chirpsdk import ChirpSDK, CallbackSet

#PASTE API KEYS HERE

class Callbacks(CallbackSet):

    def on_receiving(self, channel):
        print('Receiving message...')

    def on_received(self, payload, channel):
        if payload is None:
            print('Failed to decode message!')
        else:
            print('Received: {data}'.format(
                data=payload.decode('utf-8')))


def main(args):
    # ------------------------------------------------------------------------
    # Initialise the Connect SDK.
    # ------------------------------------------------------------------------
    sdk = ChirpSDK(app_key,app_secret,app_config)
    print(sdk.audio.query_devices())
    print(str(sdk))
    sdk.audio.output_device = args.o
    if args.network_config:
        sdk.set_config_from_network()

    if sdk.protocol_name != '16khz-mono':
        raise RuntimeError('Must use the 16khz-mono protocol ' +
                           'to be compatible with other Chirp Messenger apps.')

    # ------------------------------------------------------------------------
    # Parse unicode and send as a chirp payload
    # ------------------------------------------------------------------------
    message = args.message.encode('utf-8')
    payload = sdk.new_payload(message)

    sdk.volume = args.volume
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
    # ------------------------------------------------------------------------
    # Parse command-line arguments.
    # ------------------------------------------------------------------------
    parser = argparse.ArgumentParser(
        description='Chirp Messenger',
        epilog="Send a message to other Chirp Messengers"
    )
    parser.add_argument('message', help='Text or emoji message to send')
    parser.add_argument('-v', '--volume', help='Volume', default=1.0)
    parser.add_argument('-o', type=int, default=None, help='Output device index (optional)')
    parser.add_argument('--network-config', action='store_true', help='Optionally download a config from the network')
    args = parser.parse_args()

    main(args)
