Quiz Server & Client
===================
The Quiz Server and Client is a persistent quiz Server which can be accessed via Java RMI. 

Packages
-------------

Network
-------
Contains all of the networking components, including the UserInterface

 1. QuizServer - Persistent server that holds the data
 2. QuizServerLauncher - Launches the QuizServer
 3. PlayerInterface - QuizServer implements to allow PlayerClient access
 4. PlayerClient - Handles setup of client and communicates with server
 5. PlayerClientUI - Handles UI for the client only
 6. SetupInterface -  QuizServer implements to allow SetupClient access
 7.  SetupClient - Handles setup of client and communicates with server
 8. SetupClientUI - Handles UI for the client only
 9. QuizServerTestScript - Test script that simulates events int he clients and tests outcomes are valid
 10. QuizServerSetupTest - Initial tests for RMI setup

Components
-------------
xImpl indicates implementation of RMI interface

 1. Player - Player in the system, either for setup or player client
 2. Quiz - A quiz set up by SetupClient
 3. Question - component question with answers for Quiz
 4. Game - a Player/Quiz combination for a game in progress

Scratchpad is solely for trying some code snippets.

Tests
-------------
Largely deprecated. These unit tests tested the components but were found to be insufficient for Java RMI tests.  QuizServerTestScript  supercedes these. Some are now failing but have been left for posterity. 

Running
-------------
To run, from the bin folder use the following:

java -Djava.security.policy=../server.policy network.QuizServerLauncher 
java -Djava.security.policy=../server.policy network.PlayerClientUI
java -Djava.security.policy=../server.policy network.SetupClientUI
