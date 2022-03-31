# The SCP Database

#### SCP stands for Secure, Contain, Protect. 
#### It is a community-driven database of fictional entities which possess anomalous properties.
#### These entities are referred to as "SCPs".

[Click here to see the original website.](https://scp-wiki.wikidot.com/)

This application will:
- Be a catalogue of various different "SCPs", each being designated a unique number.
- Allow the user to add descriptions, containment procedures, danger levels, and extra information on every SCP.
- Display images for SCPs when available.
- Allow the user to edit or delete any SCP entry.

*The SCP foundation is one of the largest community projects on the internet. Many people work together to create
an extremely entertaining world of horror. This project is a way to improve my programming skills while learning more
about this project.*

### User Stories - Stage 1:
- As a user, I would like to add an SCP with a number, name, classification, containment status, and any additional notes.
- I would like to be able to edit the information above for any existing SCP except for its number.
- I would like to be able to delete an SCP from the catalogue.
- I should be able to view all SCPs as a legible list.
### User Stories - Stage 2:
- I want the option to save the database manually.
- I want the option to load the database from the save file.

### Phase 4: Task 2
The following is a log produced after running the program, adding two
SCPs in slots 001 and 002 respectively, and then deleting the entry 
for 001. SCP-001 was edited immediately after its creation, but this 
type of action does not get logged.

```
Wed Mar 30 10:17:06 PDT 2022
Entry for SCP-001 was added to the database.


Wed Mar 30 10:17:33 PDT 2022
Entry for SCP-002 was added to the database.


Wed Mar 30 10:17:37 PDT 2022
Entry for SCP-001 was deleted from the database.



Process finished with exit code 0
```

### Phase 4: Task 3
Some thoughts on possible refactoring.

Implementing proper bidirectional relationships between Database 
and Entity, as well as Entity and TextBlock, would have allowed multiple databases to be maintained at 
once, rather than having a primary one, and there would be capability to have a text block that's visible 
under every entry (which could be used for a site footer, for example). Not to mention that 
improving these relationships and their stability would improve the overall robustness of the program 
as a whole as well.

I would have localized Classification to an enum within  Entity, and created getters/setters for the 
possible types of classifications an instance may have. I would have also contained all the formatting 
constants (such as ENTRIES_PER_SERIES or MIN_DIGITS) to either Database or Entity, and created the 
appropriate getter and setter methods. This would avoid the messiness of public constants.

Lastly, I would have cleaned up Entity's purpose more. It currently has much functionality for generating 
strings of related information, which became less useful once UI was implemented. It should not have 
dealt with those tasks in the first place, because putting them in the UI class instead would have 
saved me a lot of time testing them. Reflecting on this more, I would do the same for all classes, 
many of which I have no doubt are performing tasks they do not need to be doing.