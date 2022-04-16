package edu.jhu.espresso.client.domain;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    Menu mainMenu = new Menu();
    ArrayList<String> legalMoves = new ArrayList<>();

    MoveOptions moveOptions = new MoveOptions();
    Suggestion suggestion = new Suggestion();
    Accusation accusation = new Accusation();

    CardDeck deck = new CardDeck();
    Notebook notebook = new Notebook(deck);

    public void makeSampleNotebook () {

        this.notebook.makeHandCard(deck.getCard(deck.getCardsList(),"PROFESSOR_PLUM" ));
            //    new CharacterCard("PROFESSOR_PLUM"));
        this.notebook.makeHandCard(deck.getCard(deck.getCardsList(),"STUDY" ));
        this.notebook.makeHandCard(deck.getCard(deck.getCardsList(),"DAGGER" ));
       // this.notebook.makeHandCard(new RoomCard("STUDY"));
       // this.notebook.makeHandCard(new WeaponCard("DAGGER"));

        this.notebook.makeKnownCard(deck.getCard(deck.getCardsList(),"MISS_SCARLET" ));
        this.notebook.makeKnownCard(deck.getCard(deck.getCardsList(),"BALLROOM" ));
        this.notebook.makeKnownCard(deck.getCard(deck.getCardsList(),"LEAD_PIPE" ));
       // this.notebook.makeKnownCard(new CharacterCard("MISS_SCARLET"));
        //this.notebook.makeKnownCard(new RoomCard("BALLROOM"));
       // this.notebook.makeKnownCard(new WeaponCard("LEAD_PIPE"));
    }

    public List<String> getLegalMoves()
    {
        return legalMoves;
    }

    public void setLegalMoves(ArrayList<String> legalMoves)
    {
        this.legalMoves = legalMoves;
    }

    public void mainMenu ()
    {
        mainMenu.setTitle("*** Possible Actions ***");
        mainMenu.addItem(new MenuItem("Move Character", this, "moveCharacter"));
        mainMenu.addItem(new MenuItem("Make Suggestion", this, "makeSuggestion"));
        mainMenu.addItem(new MenuItem("Make Accusation", this, "makeAccusation"));
        mainMenu.addItem(new MenuItem("View Notebook", this, "openNotebook"));
        mainMenu.execute();
    }

    public void moveCharacter()
    {
        ArrayList<LocationNames> ValidMoves = new ArrayList<>();
        ValidMoves.add(LocationNames.HALLWAY5);
        ValidMoves.add(LocationNames.HALLWAY6);
        ValidMoves.add(LocationNames.HALLWAY11);
        moveOptions.setValidMoves(ValidMoves);
        moveOptions.mainMoveMenu();
    }

    public void makeSuggestion()
    {
        ArrayList<String> validChars = new ArrayList<>();
        validChars.add("MISS_SCARLET");
        validChars.add("PROFESSOR_PLUM");

        ArrayList<String> validWeapons = new ArrayList<>();
        validWeapons.add("REVOLVER");
        validWeapons.add("WRENCH");

        suggestion.setValidCharacters(validChars);
        suggestion.setValidWeapons(validWeapons);
        suggestion.mainSugMenu();
    }

    public void makeAccusation()
    {
        ArrayList<String> validChars = new ArrayList<>();
        validChars.add("MISS_SCARLET");
        validChars.add("PROFESSOR_PLUM");

        ArrayList<String> validRooms = new ArrayList<>();
        validRooms.add("KITCHEN");
        validRooms.add("CELLAR");

        ArrayList<String> validWeapons = new ArrayList<>();
        validWeapons.add("REVOLVER");
        validWeapons.add("WRENCH");

        accusation.setValidCharacters(validChars);
        accusation.setValidRooms(validRooms);
        accusation.setValidWeapons(validWeapons);
        accusation.mainAccMenu();
    }

    public void openNotebook()
    {
        /* System.out.println("\n*** Notebook ***\n|Characters|");
        // output character cards
        System.out.println("\n|Weapons|");
        // output weapon cards
        System.out.println("\n|Rooms|");
        // output room cards

         */

        makeSampleNotebook();
        System.out.println(notebook.toString());
    }
}
