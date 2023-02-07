# Watcher IoT platform
The Watcher is a Java based desktop application where you can make users and see
the attributes of different nodes and sensors. 

## Usage
### User creation
Firstly user needs to be
registered, following this the application will send a confirmation email with a
verification link which needs to be activated before usage. If the email has not
been verified, the user cannot be logged into.

### Logging in
When the user has been registered and verified you are able to log in. The
server responds to a successfull login with a session token, which the client
will use for communicating with the server and gaining authorization to
endpoints.

### Nodes
A admin can create a new node to the database. User can subscribe to a node from
which it wants data. The user table has a array in it which contains the ID's of
the subscribed nodes. The node table has info on the node, but there will be a
subtable which contains nodeID, timestamp and attributes.

## Future implementations
Watcher should have the possibility to add friends to users friends list and
possibly share their data.
