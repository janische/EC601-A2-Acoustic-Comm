import argparse
import time
from math import floor
from chirpsdk import ChirpSDK, CallbackSet


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
    sdk = ChirpSDK()
    print(sdk.audio.query_devices())
    print(str(sdk))
    sdk.audio.output_device = args.o
    if args.network_config:
        sdk.set_config_from_network()

    if sdk.protocol_name != '16khz-mono':
        print("The protocol name is: " + sdk.protocol_name)
        raise RuntimeError('Must use the 16khz-mono protocol ' +
                           'to be compatible with other Chirp Messenger apps.')
    else: 
        print(sdk.protocol_name)
    # ------------------------------------------------------------------------
    # Parse unicode and send as a chirp payload
    # ------------------------------------------------------------------------
    message   = args.message.encode('utf-8')
    waitTime  = args.wait
    payload   = sdk.new_payload(message)

    sdk.volume = args.volume
    sdk.set_callbacks(Callbacks())
    sdk.start()
    sdk.send(payload)

    try:
        # Process audio streams
        tic =  time.time()
        elapsedFirst = 0; 
        elapsed      = 0;
        while elapsed < waitTime:
                time.sleep(0.1)
                elapsed = time.time() - tic
                elapsedLast = floor(elapsed)
                if elapsedLast > elapsedFirst:
                    elapsedFirst = elapsedLast
                    print(f"Waiting for messages ({elapsedLast} s of {waitTime} s)")
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
    parser.add_argument('--wait',  help='Time to wait for a Rx message (seconds)', default = 5.0, type = float)
    parser.add_argument('-m', '--message', help='Text or emoji message to send', type = str)
    parser.add_argument('-v', '--volume', help='Volume', default=1.0)
    parser.add_argument('-o', type=int, default=None, help='Output device index (optional)')
    parser.add_argument('--network-config', action='store_true', help='Optionally download a config from the network')
    args = parser.parse_args()

    main(args)