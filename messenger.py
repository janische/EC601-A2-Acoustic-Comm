import argparse
import time

from chirpsdk import ChirpSDK, CallbackSet

app_key = "6efd304EaC763fBD0Ad090cEA"
app_secret = "78cdbdDCF0bA36A1ac4d41EAC1B2BABEF614890AeD9Ec64eFd"
app_config = "Nqppl9n7EM8KBXVrDFiCrCe6gkt2mMJAolzpeqnwX6TqgeoDA4uyk7Zel/qOxzt9jLacDXG8zOycr3I2v66LY37qF/8JVavWnIrpM7iUgjlQ1XbJ8n3xEbMdqEIscaQODKp7TCv7LVt+q6TvhutqjYzI5TCq4TPISWdvb51SVP57NW1w0xbqPfwuRsbaL2mbMLQv1Uw4/0vMdP3Qaa0gLeZRg4R5hmvoUGrlGbrbHLbEQ4mfTlRCf1n4NU077ClI6gMVMx2KIN6zN8MvsgwXDkfnAdYtztD+8x6s3xFbheanX0dwuvsXucMys9zoG7A+isMAp2C/Ac1S5A1TrLPaUP/ZT2AN7cbdzY0wahWZTjKJRvAeS0rGdxzAgUJCibH1/kwurYRNkO+sYoCeYic5dhF2XJ5OyLjj5Z3ijYM3rirPz2E2FsI6/dyJBH7h9MBvX8s1Cwt7274tH3hZLHxPQlTHpOUqX0P8Q+1r+6g9bjKeff3IgbpgDmMulYLMS1KQYFfMq6a8BOXIuqWzq0lWIY7guQka3Ik4xp/0iEFpz5yDoIrAmtpso4t6veH7u3NkpwVPCTGGndHasVB49t5jrXQAihagh92hkd0E7sjJKlJ1dPrwtAwILyuYvyK71FDb9KIUMhO87aeY7UpRIqOS8ZGoikltUjKbES1cCVcWsFTM3ubHH7u+q96Jfm1kpzQ4/wpLlZ6a6q3BWd9s+ZdLo4lx0Bts7TDYuaCl0NqYPhlnbZ3VzEJR7+7E5x8zk+71YSxoyK5buH2aTRvwPJ89Tf2rUg+SIduAabjlczndC4oUwsbidHkInDRSlfE+mnt2M3NiOA1LVhLGRBcumKLHUZiwyJau0nMfgRbPk470PYs="

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
