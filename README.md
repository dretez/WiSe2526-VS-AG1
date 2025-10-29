# WiSe2526-VS-AG1
The aim of the assignment is to implement a remote procedure call (RPC) and compare it with a
local call. The assignment should be completed in teams of two.
For practical groups with an odd number of people, there will be a team of three.
For the comparison, we will use a “Datastore” interface with the following methods:
void write(int index, String data)
String read(int index) throws NoSuchElementException
- The programming language used can be freely chosen. One option would be
  implementation in Java. The method signatures must be adapted to the
  programming language accordingly.
- The design must allow for different parameter types with variable numbers, exceptions, and
  return values. The implementation should be as easily adaptable as possible so that
  methods other than the example mentioned here can also be addressed via RPC.
- Errors during transmission between the client and server must be encapsulated as exceptions (depending on the
programming language).
- The stubs should not be generated automatically, but implemented manually.
  This means that they would have to be adapted each time the interface is changed.
- Communication should take place via the network. It must be possible to run the client and
  server applications on different computers.
- Communication should take place via a standardized human-readable
  message format, such as JSON or XML.
- No external libraries may be used for implementation. In particular,
  no RPC libraries or ready-made protocols are permitted.
  Libraries may only be used for marshalling and unmarshalling.
  1. Create a sequence diagram that shows the flow of the RPC from the client to the server and
  back again, including all intermediate steps.
  2. Define the message format based on the selected standard. For each type
  of message, describe the structure and provide an example.
  3. Implement the RPC in the programming language of your choice.
  4. Measure the duration of a local method call in comparison to your RPC
  implementation. Use the same interface for this.
  5. Describe the extent to which transparency is achieved in distributed execution with your
  implementation and how comprehensive it is.

  A report must be submitted in the form of a text document containing all relevant design
  decisions (e.g., choice of programming language) and the results of the tasks. The
  source code must be explainable at the time of submission. The implementation must be executable on
  the pool computers, via the network if necessary.
  The latest submission date is at the beginning of the second practical session.
