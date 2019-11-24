#Example code taken from github 
#user joextodd https://github.com/chirp/chirp-python-examples

import argparse
import time
from chirpc_GM import app_key, app_secret, app_config
from chirpsdk import ChirpSDK, CallbackSet

'''
#chirp = ChirpSDK()
#chirp.start(send=True, receive=True)
contacts = {
    "Amanda": '4',
    "Glenn": '3',
    "Jonathan": '2',
    "JP": '1'
    }

'''
# create a Message class w/ props: my_id, recipient_id, payload and func that creates the message
# Message should also contain the dict storing the codes for the 4 messages
# farm a lot of main's processes to functions: use main for user input 

class Contacts(dict):
    def __init__(self, contact_list):
        print("Constructor called")
        self.my_id = 0               # set owner's id (always 0)
        contact_list.insert(0, "me")
        id_list = range(len(contact_list))
        self.dict = dict(zip(id_list, contact_list))
        #print(type(self.dict[0]))

    def __getitem__(self, key):
        #print("getitem fxn")
        return self.dict.get(key)

    def __setitem__(self, key, name):
        # key = "1, 2, 3, etc"
        # print("setitem fxn")
        self.dict[key] = item


class Callbacks(CallbackSet):

    def on_receiving(self, channel):
        print('Receiving message...')

    def on_received(self, payload, channel):
        if payload is None:
            print('Failed to decode message!')
        else:
            message = payload.decode('utf-8') 
            if message[1] == my_id:
                # Implement de-coding of message!! 
                print('User-specific Message Received from ' + my_contacts[message[0]] + 
                        ":" + message)
            else:
                print("No messages for you.")
                #return
            
def main():
    # ------------------------------------------------------------------------
    # Initialise the Connect SDK.
    # ------------------------------------------------------------------------
    #sdk = ChirpSDK(app_key,app_secret,app_config)
    sdk = ChirpSDK()
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
    to_send = input("""Would you like to send a message now? Y/N: """)

    if to_send == 'Y' or to_send == 'y':

        print("To whom would you like to send your message?")
        recipient = input("""Enter the first name of the person to whom 
                            you'd like to send a message: """)


        # farm this out to a function owned by Contacts
        for id, name in my_contacts.dict.items():
            if name == recipient:
                #print(id)
                recipient_id = id
        
        choice = input("""Please enter 1, 2, 3, 4 to indicate your message: 
                        1. Help! \n
                        2. I'm going to the boat. \n
                        3. Look at this neat fish! \n 
                        4. My oxygen is low. \n""")

        # farm this out to a function owned by Message
        if choice == '1':
            user_payload = 1
        elif choice == '2':
            user_payload = 2
        elif choice == '3':
            user_payload = 3
        elif choice == '4':
            user_payload = 4
        else:
            print('Invalid user input.')

        #print(payload)

        encoded_transmission = str(my_contacts.my_id) + str(recipient_id) + str(user_payload)
        encoded_transmission = encoded_transmission.encode('utf-8')   # is this step still necessary now that str is used?
        payload = sdk.new_payload(encoded_transmission)

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