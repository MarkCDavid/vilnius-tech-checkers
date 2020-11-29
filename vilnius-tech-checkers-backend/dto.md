# DTO


## DTO

### Coordinate
* x: Integer
* y: Integer

### Side
* value: Integer

### Piece
* side: Side
* promotionLevel: Integer

### Move
* from: Coordinate
* to: Coordinate

### Player
* side: Side
* name: String

### PlayerToken
* value: String

### GameToken
* value: String

### GameLaunchData
* game: GameToken
* players: Dictionary<Side, PlayerToken>

### Ruleset
* id: String
* name: String
* playerCount: int

## Services

### Ruleset Service

> ### Reason
> Provides the player with the ability to fetch available
> rulesets the backend provides

> ### Interface
> getRulesets() : List<Ruleset>
>


### Game Launch Service

> ### Reason
> Provides the player with the ability to start a new game
> locally or remotely, or join an already existing game.

> ### Interface
> launchLocal(Ruleset ruleset, List<String> names) : GameLaunchData
>
> launchRemote(Ruleset ruleset, String name) : GameLaunchData
>
> joinGame(GameToken game, String name) : GameLaunchData
>
> leaveGame(GameToken game, PlayerToken player) : void
>

### Game Data Service

> ### Reason
> Provides the player with the ability to query the backend
> for data of a specific game.

> ### Interface
> getPlayers(GameToken game) : List<Player>
>
> getCells(GameToken game) : List<Cell>
>
> getCurrentSide(GameToken game): Side
>
> getMoveHistory(GameToken game): List<Move>
>


### Game Interaction Service

> ### Reason
> Provides the player with the ability to interact with the
> game, given they have their player token.

> ### Interface
> getInteractable(GameToken game, PlayerToken player) : List<Coordinate>
>
> getMoves(GameToken game, PlayerToken player, Coordinate from) : List<Move>
>
> applyMove(GameToken game, PlayerToken player, Move move) : boolean
>
