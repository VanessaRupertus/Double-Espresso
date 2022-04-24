package edu.jhu.espresso.server.domain.builder;

import edu.jhu.espresso.server.domain.gamepieces.CharacterNames;
import edu.jhu.espresso.server.domain.gameEvents.GameStart;
import edu.jhu.espresso.server.domain.gamepieces.RoomNames;
import edu.jhu.espresso.server.domain.gamepieces.Weapon;

import java.util.List;

public final class GameStartBuilder
{
    private List<RoomNames> roomNamesList;
    private List<Weapon> weapons;
    private List<CharacterNames> characterNamesList;
    private CharacterNames characterNames;

    private GameStartBuilder()
    {
    }

    public static GameStartBuilder aGameStart()
    {
        return new GameStartBuilder();
    }

    public GameStartBuilder withRoomNamesList(List<RoomNames> roomNamesList)
    {
        this.roomNamesList = roomNamesList;
        return this;
    }

    public GameStartBuilder withWeapons(List<Weapon> weapons)
    {
        this.weapons = weapons;
        return this;
    }

    public GameStartBuilder withCharacterNamesList(List<CharacterNames> characterNamesList)
    {
        this.characterNamesList = characterNamesList;
        return this;
    }

    public GameStartBuilder withCharacterNames(CharacterNames characterNames)
    {
        this.characterNames = characterNames;
        return this;
    }

    public GameStart build()
    {
        return new GameStart(roomNamesList, weapons, characterNamesList, characterNames);
    }
}
