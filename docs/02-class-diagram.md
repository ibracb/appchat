# Class diagram of the model

## System modelling

```mermaid
classDiagram
    class User
    class Contact {
        <<abstract>>
    }
    class IndividualContact
    class Group
    class Message
    class MessageType
    class Discount {
        <<interface>>
    }
    class DiscountNull
    class DiscountByDate
    class DiscountByMessage
    class DiscountFactory

    Contact <|-- IndividualContact
    Contact <|-- Group
    User "1" o-- "*" Contact
    Contact "1" o-- "*" Message
    Message --> MessageType : type
    IndividualContact --> User : references
    Group "1" o-- "*" IndividualContact : members
    User --> Discount
    Discount <|.. DiscountNull
    Discount <|.. DiscountByDate
    Discount <|.. DiscountByMessage
    DiscountFactory ..> Discount : creates
```

## Notes about the modelling

- `Contact` is an abstract class and the common root of `IndividualContact` and `Group`.
- An `IndividualContact` references the real AppChat `User` it represents.
- `Group` keeps its own collection of `IndividualContact` members, independent of the contacts of the user who created the group.
- A `User`'s discount is chosen by `DiscountFactory` (Strategy + Factory pattern), see [design-patterns.md](05-design-patterns.md).