# User stories

## 2. Register
As a user, I want to register so that I can send and/or receive messages.

- When "register" is pressed on the login window, the system shows the fields required to complete the registration.
- If required fields are missing (all except greeting and image), the system shows an error and allows another attempt.
- If the passwords entered do not match, the system shows an error and allows another attempt.

## 1. Login
As a user, I want to log in so that I can send and receive messages.

- If the user is registered and enters their credentials correctly, the system shows the main window.
- If the user is not registered, the system returns to the login window showing the error "User not registered".
- If the password is incorrect, the system returns to the login window showing the error "Incorrect password".

## 3. Add a contact
As a user, I want to add a phone number to my contacts so that I can add it to the contact list and send messages to it and create groups with it.

- When the "+" button is pressed next to a message from a number that has not been added, the add-contact window opens with the phone number pre-filled.
- When the name and phone number of an existing AppChat user are completed and "Accept" is pressed, the contact is added to the list.
- If the phone number entered does not correspond to a user registered in AppChat, the system shows the error "The contact could not be added".

## 4. Create a group
As a user, I want to create a group so that I can send messages to a set of contacts.

- When "add group" is pressed on the contacts window, the system shows the fields required to complete the creation (name is mandatory, image is optional, members).
- If the name is not completed, the system shows an error indicating the missing field.

## 5. Apply filters
As a user, I want to apply filters so that I can search for messages.

- When one or more filter fields are completed and "Search" is pressed, the system shows the matching messages by sender and/or recipient.
- If "Search" is pressed without completing any filter, the system shows the notice "No data to search. Please complete at least one of the fields.".