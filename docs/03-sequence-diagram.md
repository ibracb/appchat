# Sequence diagram: adding a contact to a group

This diagram exemplifies the MVC pattern followed by AppChat: the View never accesses the Model directly; all requests pass through the Controller (Facade), which coordinates the Model and the Persistence.

```mermaid
sequenceDiagram
    actor User
    participant View
    participant Controller
    participant Model as Group
    participant Persistence as GroupDAO

    User ->> View: selects group, presses "Add members"
    View ->> Controller: request the group's non-members
    Controller -->> View: list of contacts
    View -->> User: shows available contacts
    User ->> View: selects a contact and confirms
    View ->> Controller: add contact to the group
    Controller ->> Model: add member
    Model -->> Controller: ok
    View ->> Controller: save the group
    Controller ->> Persistence: update group
    Persistence -->> Controller: ok
    Controller -->> View: ok
    View -->> User: contact added to the group
    Note over View,Controller: This is repeated for each selected contact
```