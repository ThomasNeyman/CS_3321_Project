# Changelog
All notable changes to this project will be documented in this file.

Format based on [Keep a Changelog]

## 4.0.1 2021-12-9

### Changed
- Fixed a game breaking bug

## [4.0.0] 2021-12-5
### Added
- Dockerization of server
- UI updated to look better
- Travis CI script for continuous integration
- Finishing touches to ensure game operates properly

### Changed
- Changed how chance crads function
- Changed how buying houses on properties functions
- Server side handles dice roll creation
- Fixed bug where chance was not moving the player to Boardwalk
- Fixed bug where character position was not properly displayed on the board
- Fixed bug where houses would not be built on the properties

### Removed
- Removed trading functionalities for properties

## [3.0.1] 2021-11-14
### Added
- Two separate clients now able to connect to the server
- clients take turns sending requests to the server to play
- microservice structure implemented using http protocol
- jail functionality
- house building functionality
### Changed
- Frontend client no longer handles the Game State
### Removed
- Project no longer uses module files for dependencies

## [2.0.0] 2021-10-31
### Added
- Added cs3321.MoServer, Library, and Client modules
- cs3321.MoServer and Client able to form a connection
- Structure of UI board completed
- Dice roll action is functional
- UML Diagram Structure completed
- Basic Unit Tests added for server


## [1.0.0] 2021-10-17
### Added
- Added Chance, Monoploly, Player, State, Property classes to Library module
- Added gameplay logic to the Monopoly class
- Added Unit Tests for classes
- UI functionality to read game state and update board
- Added functionality to play game from client
- Added Library functional tests
 ### Changed 
- Refactored our Module set up
- changed ChanceCardIndex to be an Array


[1.0.0]: https://github.com/ThomasNeyman/CS_3321_Project/releases/tag/v1.0.0
[2.0.0]: https://github.com/ThomasNeyman/CS_3321_Project/releases/tag/v2.0.0
[3.0.1]: https://github.com/ThomasNeyman/CS_3321_Project/releases/tag/v3.0.1
[4.0.0]: https://github.com/ThomasNeyman/CS_3321_Project/releases/tag/v4.0.0
[Keep a Changelog]: https://keepachangelog.com/en/1.0.0/
