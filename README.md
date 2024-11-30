# Chess engine ♟️

This is my initial attempt at implementing an alpha-beta driven computer chess engine.

**What works**
- All pieces can move according to the basic chess rules and make captures.
- Can select a piece and deselect it to make a different move during the thinking phase.
- Playing between two human opponents (not computer) appears to be working OK.

**Limitations**
- Castling rights, en passant, promotion and checkmate have not been implemented. This is to simplify the chess engine, since the main purpose is to implement the alpha-beta algorithm. Instead, the player who captures all of the opponent's pieces emerges victorious. This game is essentially checkers with chess-like movements.

**Known bugs**
- The alpha-beta algorithm is bugged. The pawns are always advancing in order, and pawn movements are being prioritised for a reaosn not yet found.
- Concurrency error occurs when playing against the computer (`computerMove()` function, line 178)
