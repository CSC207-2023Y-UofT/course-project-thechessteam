![Front page](https://i.imgur.com/XUXVs9u.png)
# The Chess Team

The Chess Team is a two player bitboard based chess game engine.

## Table of contents
- [About the project](#about-the-project)
- [Build Details](#build-details)
- [Features](#features)
  - [Move piece](#move-a-piece)
  - [Capture](#capture-pieces)
  - [Castling](#castling)
  - [Check](#check)
  - [Draw](#draw)
  - [Forfeit](#forfeit)
  - [Leaderboard](#leaderboard)

![Game interface](https://i.imgur.com/4Jx8TaG.png)
## About The Project

The Chess Team is a two player chess game engine that focuses on interactive gameplay.
It features all common chess mechanisms, including move generation, move validation, castling, pawn promotion, draw and forfeit options. Players can also enter themselves into the leaderboard after a win, which accumulates and retains all player wins. Java Chess is written in Java, and uses Java swing for GUI. The board is represented using bitboards, making the program lightweight and fast.

## Build Details

- Language: Java
- GUI: Java swing
- Architecture Pattern: Model-View-controller (MVC)
- Board Representation: Bitboard

This project uses MVC pattern to deploy clean architecture. While some interdependency is unavoidable due to the nature of chess as a game, all such dependencies are contained within each layer and adhere to SOLID principles.
![MVC](https://i.imgur.com/fJ5qtHP.png)

## Features

### Selecting a piece and show valid moves
<!--- game start, valid move --->
Clicking on a piece while the player's turn is active shows the places that piece can move to.
<p float="left">
    <img src="https://i.imgur.com/4Jx8TaG.png" width="49%" />
    <img src="https://i.imgur.com/n7BA4TE.png" width="49%" />
</p>

### Move a piece
<!--- valid move, completed move, explain what happens when invalid parts are selected, use pawn --->
The piece can be moved to a valid position. Clicking on an invalid spot returns the player to piece selection.
<p float="left">
    <img src="https://i.imgur.com/n7BA4TE.png" width="49%" />
    <img src="https://i.imgur.com/6eKmWg7.png" width="49%" />
</p>

### Capture pieces
<!--- About to capture, captured, pawn capture --->
Pieces can be captures in valid capture positions(For example, pawn can only move forward and capture diagonally).
<p float="left">
    <img src="https://i.imgur.com/7WClSAD.png" width="49%" />
    <img src="https://i.imgur.com/NxuuMkv.png" width="49%" />
</p>

### Castling
<!--- before and after castling, write about the conditions --->
If neither the king nor the rook has moved, and the king is not in check, clicking on the king shows the castling spot, two squares left or right of the king. Clicking on the square afterwards activates castling.
<p float="left">
    <img src="https://i.imgur.com/2rXFcHp.png" width="49%" />
    <img src="https://i.imgur.com/4tVivzL.png" width="49%" />
</p>

[//]: # (### Pawn promotion)

[//]: # (<!--- before and after pawn promotion --->)

[//]: # (When a pawn reaches the end of the board, it can be promoted.)

[//]: # (![Pawn promotion]&#40;https://i.imgur.com/pKTplXd.png&#41;)

### Check
<!--- explain checkmate, one pic --->
When the king is in check, only moves to get out of the check are allowed.
<p float="left">
    <img src="https://i.imgur.com/9mP2Xcu.png" width="49%" />
    <img src="https://i.imgur.com/MqN837b.png" width="49%" />
</p>


### Draw
<!--- Draw screen --->
![Leaderboard](https://i.imgur.com/zCnG3fr.png)

### Forfeit
<!--- forfeit screen --->
![Leaderboard](https://i.imgur.com/C91Gmxn.png)


### Leaderboard
<!--- Leaderboard image --->
The Chess Team also features a leaderboard to record winner names and win counts.
![Leaderboard](https://i.imgur.com/OnYeVW4.png)




