#Example code taken from github 
#user joextodd https://github.com/chirp/chirp-python-examples

import argparse
import time
from chirpc_GM import app_key, app_secret, app_config
from chirpsdk import ChirpSDK, CallbackSet

'''
contacts = {
    "Amanda": '4',
    "Glenn": '3',
    "Jonathan": '2',
    "JP": '1'
    }
'''
# Read in "my_id" from dict
# Hard-code self's user id?
# Create and ID class to store contact dict and have self id set (getter/setter for that?)

class Contacts(dict):
    def __init__(self, contact_list):
        print("Constructor called")
        my_id = 0               # set owner's id (always 0)
        contact_list.insert(0, "me")
        id_list = range(len(contact_list))
        self.dict = dict(zip(id_list, contact_list))
        #print(type(self.dict[0]))

    def __getitem__(self, key):
        #print("getitem fxn")
        return self.dict.get(key)


class Callbacks(CallbackSet):

    def on_receiving(self, channel):
        print('Receiving message...')

    def on_received(self, payload, channel):
        if payload is None:
            print('Failed to decode message!')
        else:
            message = payload.decode('utf-8') 
            if message[1] == my_id:
                print('User-specific Message Received from ' + my_contacts[message[0]] + 
                        ":" + message)
            else:
                print("No messages for you.")
                #return
            
def main():
    # ------------------------------------------------------------------------
    # Initialise the Connect SDK.
    # ------------------------------------------------------------------------
    sdk = ChirpSDK(app_key,app_secret,app_config)
    # ------------------------------------------------------------------------
    # Parse unicode and send as a chirp payload
    # ------------------------------------------------------------------------

    num_contacts = int(input("How many contacts would you like to add?: "))
    contact_list = []
    for i in range(num_contacts):
        contact_name = input("Enter a name to add: ")
        contact_list.append(contact_name)
        #print(contact_list)
    my_contacts = Contacts(contact_list)
    #print(my_contacts[0])
    #print(my_contacts[1])

    # begin chirp stff
    choice = input("""Please enter 1, 2, 3, 4 to indicate your message: 
                    1. Help! \n
                    2. I'm going to the boat. \n
                    3. Look at this neat fish! \n 
                    4. My oxygen is low. \n""")

    if choice == '1':
        message = 1
    elif choice == '2':
        message = 2
    elif choice == '3':
        message = 3
    elif choice == '4':
        message = 4
    else:
        print('Invalid user input.')

    print(message)

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