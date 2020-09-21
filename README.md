# Checkers

## "Software Design" course work - Vilnius Tech

A game of checkers for two players.


## Checkers Version

Given the [available versions of the game](https://en.wikipedia.org/wiki/Draughts), one of the versions has to be chosen for implementation. 

The implementation should support a relatively simple swap of the rulesets, allowing to easily implement other versions of the game without rewriting most of the underlying code.

The chosen implementation is [English draughts](https://en.wikipedia.org/wiki/English_draughts).

## Implementation

Given the [rules](RULES.md) and the [sample](SAMPLE.md) the surface look at the implementation of the engine is as follows:

* User interaction with UI generates input as standard notation.
* The engine handles the input and updates the state internally
* UI consumes that data and presents this state to the user

Due to this, UI can be separated from the given ruleset.