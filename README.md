# lineup

A program for generating a player lineup for a game, based on a set of
rules.

The real purpose is to teach me logic programming using Clojure's
core.logic library.

Using wishful thinking, this is what I want the program to end up
doing.

## Scenario
I am coaching a kid's soccer team (U-6).

There are 10 kids on the roster.

```clj
(def roster [1 2 3 4 5 6 7 8 9 10])
```

The game is structured as 4 quarters with 5 players on the
field at one time (4 field players, 1 goalie.)

```clj
([[1 2 3 4 5][1 2 3 4 5][1 2 3 4 5][1 2 3 4 5]])
```

The league mandates 50% playing time for everyone on the team.

```clj
([[1 2 3 4 5][1 2 3 4 5][6 7 8 9 10][6 7 8 9 10]])
```

I don't want anyone to have to play goalie more than once. Goalie
position indicated as last slot in each vector.

```clj
([[1 2 3 4 5][2 3 4 5 1][6 7 8 9 10][7 8 9 10 6]])
```

**All of the rules below are preferred but not required and should not
block a lineup that meets the above criteria from being created.**

The order of players on the roster has no special meaning except that
player #1 is our star field player. When he is on the field no one
else gets the ball on our team. Since this is for fun and we want
everyone to have a turn, no one should have to play with player #1
more than once.

```clj
([[1 2 3 4 5][2 3 4 5 6][7 8 9 10 1][6 7 8 9 10]])
```

Alternatively, it's ok to play with player #1 more than once if you
were the goalie one of those times.

But we still like scoring so, player #1 does not play goalie.

```clj
([[1 2 3 4 5][2 3 4 5 6][7 8 10 1 9][6 7 8 9 10]])
```

Little kids get tired so players should not play in adjacent quarters.
Does not apply to halftime.

```clj
([[1 2 3 4 5][6 7 8 9 10][2 3 4 5 6][7 8 10 1 9]])
```


### Additional capabilities
* Support preferred and un-preferred goalies list
* Support preferred and un-preferred pairings of players
* Not all ten players show up for every game
* Support input of previous games' lineups to inform goalie selection
  and who has to play with player #1
    * Should the input/output be the lineups for all games in the
      season? It would start with all blanks and then get filled in as
      the season goes along.
