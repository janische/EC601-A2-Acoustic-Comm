
class Contacts():
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


class Messages():
    def __init__(self):
        # hard-code the key list and message list, for now
        key_list = [1, 2, 3, 4]
        message_list = ["Help!", 
                        "I'm going to the boat.", 
                        "Look at this neat fish!", 
                        "My oxygen is low."]
        self.dict = dict(zip(key_list, message_list))

class Callbacks():
    def __init__(self):
        print('Callback constructor called')
    '''
    def on_receiving(self, channel):
        print('Receiving message...')
    '''
    def on_received(self, message):
        print('Receiving message: ')
        if message is None:
            print('Failed to decode message!')
        else:
            #print(type(message[1]))
            #message = payload.decode('utf-8') 
            if int(message[1]) == my_contacts.my_id:
                #print("post-int message[1]", type(int(message[1])))
                # Implement de-coding of message
                #print(type(message[0]))
                print('User-specific Message Received from ', my_contacts[int(message[0])])
                #print(type(message[0]))
                if int(message[2]) in message_dict.dict.keys():
                    decoded_message = message_dict.dict[int(message[2])]
                    print("Message: ", decoded_message)
            else:
                print("No messages for you.")
                #return


def main():
    global message_dict 
    message_dict = Messages()

    contact_list = ["one", "two"]
    global my_contacts 
    my_contacts = Contacts(contact_list)
    #print(my_contacts[0])


    recipient_id = 0
    user_payload = 1
    encoded_transmission = str(my_contacts.my_id) + str(recipient_id) + str(user_payload)
    new_callback = Callbacks()
    new_callback.on_received(encoded_transmission)



if __name__ == '__main__':
    main()
