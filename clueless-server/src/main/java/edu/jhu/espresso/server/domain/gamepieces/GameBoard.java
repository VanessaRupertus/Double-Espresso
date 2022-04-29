package edu.jhu.espresso.server.domain.gamepieces;

import java.util.*;

public class GameBoard
{

    private ArrayList<Location> locationList;
    private ArrayList<Room> roomList;
    private EnumMap<CharacterNames, Location> characterLocationMap;
    private HashMap<String, Location> stringLocationHashMap;
    private ArrayList<Hallway> hallwayList;
    private ArrayList<HomeSquare> homeSquareList;

    //private ArrayList<Location> legalMovesList;

    public GameBoard()
    {

        //Create hashmap to translate String names to Location Objects
        this.stringLocationHashMap = new HashMap<String, Location>();

        // Loop over RoomName values and create rooms.
        this.roomList = new ArrayList<Room>();
        for (RoomNames rooms : RoomNames.values())
        {
            Room thisRoom = new Room(rooms);
            roomList.add(thisRoom);
            this.stringLocationHashMap.put(rooms.name(), thisRoom);
        }

        this.hallwayList = new ArrayList<Hallway>();
        for (int i = 1; i < 13; i++)
        {
            String newName = "H" + String.valueOf(i);
            Hallway thisHallway = new Hallway("H" + String.valueOf(i));
            this.hallwayList.add(thisHallway);
            this.stringLocationHashMap.put(newName, thisHallway);
        }

        //Create HomeSquare Objects and enumMap
        this.characterLocationMap = new EnumMap<>(CharacterNames.class);
        this.homeSquareList = new ArrayList<HomeSquare>();

        //Loop over character names, create new homesquare for each, and add to homesquare list and map.
        for (CharacterNames cn : CharacterNames.values())
        {
            HomeSquare thisHomeSquare = new HomeSquare(cn);
            this.homeSquareList.add(thisHomeSquare);
            this.characterLocationMap.put(cn, (Location) thisHomeSquare);
        }

        this.moveCharacter(CharacterNames.COLONEL_MUSTARD, roomList.get(0));
        this.moveCharacter(CharacterNames.MISS_SCARLET , roomList.get(1));
        this.moveCharacter(CharacterNames.MR_GREEN , roomList.get(2));
        this.moveCharacter(CharacterNames.MRS_PEACOCK, roomList.get(3));
        this.moveCharacter(CharacterNames.MRS_WHITE , roomList.get(4));
        this.moveCharacter(CharacterNames.PROFESSOR_PLUM , roomList.get(5));

    }

    public void moveCharacter(CharacterNames ch, Location newLoc)
    {
        this.characterLocationMap.get(ch).setFull(false);
        this.characterLocationMap.remove(ch);
        this.characterLocationMap.put(ch, newLoc);
        //The setFull method for Room doesn't do anything, but for Hallway it makes it full.
        newLoc.setFull(true);
    }

    public Location getCharacterLocation(CharacterNames ch)
    {
        return this.characterLocationMap.get(ch);
    }

    public ArrayList<Location> getLegalMoves(CharacterNames ch)
    {
        Location startingLoc = getCharacterLocation(ch);
        ArrayList<String> possibleMoves = startingLoc.getPossibleDestinations();
        ArrayList<Location> locationList = new ArrayList<>();

        //Change from a string list to a location list.
        for (String loc : possibleMoves)
        {
            if (!stringLocationHashMap.get(loc).isFull())
            {
                locationList.add(stringLocationHashMap.get(loc));
            }
        }
        return locationList;
    }

    public Map<CharacterNames, LocationNames> getLocationMappingNames()
    {
        Map<CharacterNames, LocationNames> namesEnumMap = new EnumMap<>(CharacterNames.class);
        characterLocationMap.keySet()
                .forEach(characterNames -> namesEnumMap.put(
                        characterNames,
                        LocationNames.fromStringName(characterLocationMap.get(characterNames).getLocationName()))
                );

        return namesEnumMap;
    }
}


