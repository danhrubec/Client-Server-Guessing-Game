public class Player{
    private int wins;
    private int amountOfGuesses;

    Player()
    {
        wins = 0;
        amountOfGuesses = 0;
    }

    public void resetPlayer(){
        wins = 0;
        amountOfGuesses = 0;
    }
    public void resetGuesses(){
        amountOfGuesses = 0;
    }

    /**
     * @return the amountOfGuesses
     */
    public int getAmountOfGuesses() {
        return amountOfGuesses;
    }
    /**
     * @return the wins
     */
    public int getWins() {
        return wins;
    }
    public void guessed() {
        amountOfGuesses++;
    }
    public void won() {
        wins++;
    }
}