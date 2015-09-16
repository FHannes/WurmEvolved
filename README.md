# WurmEvolved

## About
WurmEvolved is a Java based private server built for the online role playing game WurmOnline. It was written for client version 3.54, as an alternative to the WurmEmu project. It uses the WurmEmu login server to establish a connection between the client and the server. The project is now abandoned as WurmOnline has announced that it will release it's own private server system on Steam in October 2015. As such, the source code to this server is made available as an educational resource for aspiring developers interested in game development or more basic aspects applied in the project.

## License
This project is licensed under a [Creative Commons Attribution-NonCommercial 4.0 International License](http://creativecommons.org/licenses/by-nc/4.0/). You are allowed to resume working on this project, without the need to release your source code, but under no circumstances are you allowed to distribute this project or a derivative work in source code or binary form for monetary gain. You are also not allowed to operate a private game server based on this project for monetary gain.

## FYI
This server is very basic. Currently players can move around in the world and view each other. A local area is implemented in which other players and ground items are visible. This includes a local chat and player list. A server-wide chat and player-list is also available. Each player has a randomized character appearance. Elementary support for inventory items and ground items is also present. The final addition was a prototype for an action system, which might still contain bugs and may require a redesign.

The server stores all data in a HyperSQL database, which runs as stand-alone server, using the database name `wurmevolved`. The database server can be started using the command:
```
java -cp hsqldb.jar org.hsqldb.server.Server --database.0 file:database --dbname.0 wurmevolved
```

This software is provided as-is with no guarantee that it will operate correctly. It is only compatible with the 3.54 version of the game client, which you will not be provided, as it is the property of Code Club AB.

## Acknowledgments
WurmOnline is a trademark of Code Club AB, with whom this project has no affiliation.