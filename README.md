# Alex Wu's Personal Project

## NBA Fantasy Draft Simulator 

**Program Features:**
- Allows you to simulate a draft based on different draft pick positions 
- Provide overall statistical analysis of your team in regard to NBA stats 
- (Possibly) Implement a features to recommend players based on what stats your 
team is missing 

The program will allow you to do a mock draft for your NBA fantasy league. You will be able to preview your teams current stats aswell as apply
filters to whoevers available to draft. Anyone who is interested in planning for their upcoming NBA fantasy draft or wants to use this simulator 
during the actual draft to help decide on their next pick. I really enjoy playing NBA fantasy with my friends and I hope to
use this software to gain an advantage over them. 

**User Stories:**
- As a user, I want to create a draft pool and list all the available players
- As a user, I want to be able to create a new team and draft players to it
- As a user, I want to be able to select a team and view the players on that team
- As a user, I want to be able to filter out the draft pool based on a specific category 
- As a user, if I need to quit, I want to have the option to save my current draft to file 
- As a user, when I start the application, I want to be given the option to load my previous draft from file.

# Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by pressing create team which adds a Team (X) 
to a League (Y) or Draft a Player which adds Player (X) to a Team (Y)
- You can generate the second required action related to adding Xs to a Y by after starting the draft you can press filter
then Points which filters players based on their points.
- You can locate my visual component by after starting the draft double-clicking on a player name to draft them 
and seeing a picture of the respective player
- You can save the state of my application by after starting the draft pressing file then Save
- You can reload the state of my application by in the starting menu press file then load 


# Phase 4: Task 2 

- Sat Apr 08 22:39:43 PDT 2023
League created
- Sat Apr 08 22:39:57 PDT 2023
Team added
- Sat Apr 08 22:40:02 PDT 2023
Team added
- Sat Apr 08 22:40:06 PDT 2023
Player drafted
- Sat Apr 08 22:40:08 PDT 2023
Player drafted
- Sat Apr 08 22:40:10 PDT 2023
League saved
- Sat Apr 08 22:40:12 PDT 2023
League loaded

# Phase 4: Task 3
I think my design was pretty abstract for the most of it, I could have definitely refactored how my DraftApp and Team
extends the draftpool.  The only reason I extended it was so it was easier for me to reference the fields from Draftpool
when making calls from Team. I could have just made it so any method that requires a field from DraftPool, I would have 
it take a DraftPool as an input then call a getter to get that field. In terms of the DraftApp I needed to extend it so 
I could just make calls directly to the DraftPool field. Instead I could have just instantiated a DraftPool in my 
DraftApp class and then made calls from there. This same concept goes with how my DraftApp has a field of type League. 
I could have refactored it so that DraftApp class instantiates a new league which wouldn't require me to have the field
anymore. This logic could also apply with DraftGUI having a field of DraftPool