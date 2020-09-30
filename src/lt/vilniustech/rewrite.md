# System Rewrite

## Abstract

#### Input Interface

The system simply accepts a move to perform.

#### Input Processing

The system checks if the given move is a legitimate one 
and applies it to the board.

#### Possible Move Generation

The system iterates through all pieces of the currently
playing side and generate all possible moves. The system
is able to discard invalid moves early.

#### Winning Condition Check

The system checks the winning conditions based on the 
executing ruleset and possible moves. If a winner has 
been determined the system emits an event with the winner 
information and halts execution.

#### Passing possible and executed moves to currently executing state

The system passes previously generated moves, and the move
that was pushed for processing to the currently executing
state. The executing state determines which moves are 
valid based on its internal state.

#### Providing the moves

Once the moves have been properly filtered and mutated they
can be presented to the output interface.
