package edu.jhu.espresso.server.protocol;

import edu.jhu.espresso.server.domain.*;
import edu.jhu.espresso.server.domain.builder.GameStartBuilder;
import edu.jhu.espresso.server.domain.gameEvents.GameStart;
import edu.jhu.espresso.server.domain.gamepieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ClueLessServerGameProtocol
{
    private final List<Player> players;
    private final Game game;

    private int activePlayerIndex = 0;

    public ClueLessServerGameProtocol(List<Player> players, Game game)
    {
        this.players = players;
        this.game = game;
    }

    public void playGame()
    {
        notifyPlayersOfCharacters();

        boolean playing = true;
        while(playing)
        {
            Player activePlayer = players.get(activePlayerIndex);
            List<Player> waitingPlayers = players.stream()
                    .filter(handler -> !handler.equals(activePlayer))
                    .collect(Collectors.toList());

            playing = new ClueLessTurnProtocol(waitingPlayers, activePlayer, game).executeTurn();
            activePlayerIndex = (activePlayerIndex + 1) % players.size();
        }
    }

    private void notifyPlayersOfCharacters()
    {
        List<CompletableFuture<GameStart>> responses = new ArrayList<>();
        CardPlayer cardPlayer = game.getCardPlayer();
        cardPlayer.dealCards(players);

        for(Player player : players)
        {
            GameStart gameStart = gameStartForPlayer(player);
            responses.add(
                    player.asyncWriteInstanceAndExpectType(
                            gameStart,
                            GameStart.class
                    )
            );
        }

        responses.forEach(CompletableFuture::join);
    }

    public static void broadcast(Game game, String announcement, List<Player> players)
    {
        TurnStart turnStart = new TurnStart(ClueLessProtocolType.INFORMATIONAL, game.getLocations(), announcement);
        players.stream().map(player -> player.asyncWriteInstanceAndExpectType(
                                turnStart,
                        TurnStart.class)
                )
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public GameStart gameStartForPlayer(Player player)
    {
        Notebook notebook = player.getNotebook();
        List<CharacterNames> characterNamesList = new ArrayList<>();
        List<RoomNames> roomNamesList = new ArrayList<>();
        List<Weapon> weapons = new ArrayList<>();

        for(Card card : notebook.getHandCards())
        {
            card.getRoomName().ifPresent(roomNamesList::add);
            card.getWeapon().ifPresent(weapons::add);
            card.getCharacterName().ifPresent(characterNamesList::add);
        }

        return GameStartBuilder.aGameStart()
                .withCharacterNamesList(characterNamesList)
                .withRoomNamesList(roomNamesList)
                .withWeapons(weapons)
                .withCharacterNames(player.getCharacter().getName())
                .build();
    }
}
