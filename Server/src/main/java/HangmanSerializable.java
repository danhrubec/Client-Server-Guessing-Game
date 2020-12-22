import java.io.Serializable;
import java.util.ArrayList;

class HangmanSerializable implements Serializable {
    // kinda has like a flag situation so if you want to guess a letter than set
    // guessingWord bool to true. same for the others
    //Values
    // letter that was picked to be revealed


    private char letterPick;
    // actual string that was guessed
    private String wordGuess;
    // list of categories to choose from in order to display
    private String[] categories;
    // count of letters
    private int letterCount;
    //category itself being sent
    private int category;
    // letter positions that were guessed
    private ArrayList<Integer> letterPositions;
    //setting wether user guessed correctly
    private boolean guessedCorrectly;


    //Flags
    //if the player wants to play again
    private boolean playAgain;
    //for the server to recognize if the player lost.
    private boolean playerLost;
    //checks if the player chose to quit instead of playing again.
    private boolean playerQuit;
    //if true then retrieve guessedCorrectly
    private boolean sendingGuessedCorrectly;
    //if true then sendingLetterPositions
    private boolean sendLetterPositions;
    //true if picking a letter to reveal
    private boolean pickingLetter;
    //true if the user is guessing the entire word
    private boolean guessingWord;
    //true if picking a category
    private boolean pickedCategory;
    //get letter count if true;
    private boolean sendingLetterCount;
    //retrieve category of true;
    private boolean sendingCategories;

    HangmanSerializable(){
        //setting all boolean flags to false;
        sendLetterPositions = false;
        pickingLetter = false;
        guessingWord = false;
        pickedCategory = false;
        sendingLetterCount = false;
        playAgain = false;
        playerLost = false;
        playerQuit = false;

    }

    public boolean getPlayerQuit()
    {
        return playerQuit;
    }
    public void setPlayerQuit(boolean b)
    {
        playerQuit = b;
    }
    public boolean getPlayerLost()
    {
        return playerLost;

    }
    public void setPlayerLost(boolean b)
    {
        playerLost = b;
    }
    public boolean getPlayAgain() {
        return playAgain;
    }
    public void setPlayAgain(boolean b)
    {
        playAgain = b;
    }
    public boolean getGuessedCorrectly(){
        return guessedCorrectly;
    }
    public boolean getSendingGuessedCorrectly(){
        return sendingGuessedCorrectly;
    }
    public ArrayList<Integer> getLetterPositions() {
        return letterPositions;
    }
    public boolean getSendLetterPositions() {
        return sendLetterPositions;
    }

    public boolean getPickingLetter(){
        return pickingLetter;
    }
    public boolean getGuessingWord(){
        return guessingWord;
    }
    public boolean getSendingCategories(){
        return sendingCategories;
    }
    public int getLetterCount() {
        return letterCount;
    }
    public boolean getSendingLetterCount(){
        return sendingLetterCount;
    }
    public String[] getCategories() {
        return categories;
    }

    public int getCategory() {
        return category;
    }

    public boolean getPickedCategory(){
        return pickedCategory;
    }

    public char getLetterPick() {
        return letterPick;
    }

    public String getWordGuess() {
        return wordGuess;
    }


    public void setCategories(String[] categories) {
        this.categories = categories;
    }
    public void setLetterPick(char letterPick) {
        this.letterPick = letterPick;
    }
    public void setWordGuess(String wordGuess) {
        this.wordGuess = wordGuess;
    }

    public void setPickingLetter(boolean pickingLetter){
        this.pickingLetter = pickingLetter;
    }

    public void setGuessingWord(boolean guessingWord){
        this.guessingWord = guessingWord;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setPickedCategory(boolean pickedCategory) {
        this.pickedCategory = pickedCategory;
    }

    public void setLetterCount(int letterCount) {
        this.letterCount = letterCount;
    }

    public void setSendingLetterCount(boolean sendingLetterCount) {
        this.sendingLetterCount = sendingLetterCount;
    }

    public void setLetterPositions(ArrayList<Integer> letterPositions) {
        this.letterPositions = letterPositions;
    }

    public void setSendLetterPositions(boolean sendLetterPositions) {
        this.sendLetterPositions = sendLetterPositions;
    }

    public void setSendingCategories(boolean sendingCategories) {
        this.sendingCategories = sendingCategories;
    }


    public void setGuessedCorrectly(boolean guessedCorrectly) {
        this.guessedCorrectly = guessedCorrectly;
    }

    public void setSendingGuessedCorrectly(boolean sendingGuessedCorrectly) {
        this.sendingGuessedCorrectly = sendingGuessedCorrectly;
    }



}