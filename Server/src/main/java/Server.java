import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Consumer;

public class  Server{
    int count = 1;
    //categories correlating to word bank
    String[] categories = {"Pokemon", "Food", "Movies"};
    //word bank containing words to choose from
    ArrayList<String[]> wordBanks = new ArrayList<>();
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    private Consumer<Serializable> callback;
    TheServer server;
    int portNum;


    Server(Consumer<Serializable> call, int portNum){
        //initializes the word bank values
        //could make it dynamic
        String[] pokemon = {"metagross", "pikachu", "squirtle"};
        String[] food = {"tacos", "pizza", "pasta"};
        String[] movies = {"jumanji", "aladdin", "bloodshot"};
        wordBanks.add(pokemon);
        wordBanks.add(food);
        wordBanks.add(movies);
        callback = call;
        this.portNum = portNum;
        server = new TheServer();
        server.start();
    }

    public class TheServer extends Thread{
        public void run() {
            try(ServerSocket mysocket = new ServerSocket(portNum);){
                callback.accept("Server is waiting for the client");
                while(true) {
                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    callback.accept("Player " + count + " has joined the server");
                    clients.add(c);
                    c.start();
                    count++;
                }
            }//end of try
            catch(Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
    }

    public class ClientThread extends Thread{
        Player player;
        //Used to check if the users has already guessed that word or attempted it
        //if in hash set then selection is invalid
        HashSet<String> guessedWords;
        //check if letter was guessed already
        HashSet<String> guessedLetters;
        boolean guessingWord;
        String currentWord;
        //used to find positions and send back indexes
        char[] currentWordArray;
        Socket connection;
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;
        ClientThread(Socket s, int count){
            guessingWord = false;
            guessedWords = new HashSet<String>();
            this.connection = s;
            this.count = count;
        }
        public String getCurrentWord(){
            return currentWord;
        }

        private void chooseAWord(int category,HangmanSerializable d) throws Exception{
            //checks if word is still being guesed just in case

                currentWord = findNotGuessedWord(wordBanks.get(category), d);

                if(currentWord != null){
                    currentWordArray = currentWord.toCharArray();
                    guessingWord = true;
                    //System.out.println(1);
                    returnLetterCount(d);
                }else{
                    //none left in that category all guessed or attempted
                    //TODO:should send back to client that the category isn't available

                    /*
                    This is taken care of client side
                     */
                }

                //removing the if else case previously here. I took care
                //of checking it client side.

        }

        private String findNotGuessedWord(String[] category,HangmanSerializable d){
            //loops through all in category and finds first that isnt guessed or attemted and returns it
            //could be more effecient

            for(int i = 0; i < category.length; i++){
                if(!isWordGuessed(category[i],d)){
                    return category[i];
                }
            }
            return null;
        }
        //checks if the word is guessed
        private boolean isWordGuessed(String word, HangmanSerializable d){
            //adds word to guessedwords since it will either be guessed or attempted eventually
            if(guessedWords.contains(word)){
                return true;
            }else{
                guessedWords.add(word);
                return false;
            }
        }
        private void evaluateGuess(String word) throws Exception{
            if(currentWord != null){
                HangmanSerializable message = new HangmanSerializable();
                if(currentWord.equals(word)){
                    player.won();
                    message.setSendingGuessedCorrectly(true);
                    message.setGuessedCorrectly(true);
                }else{
                    message.setSendingGuessedCorrectly(true);
                    message.setGuessedCorrectly(false);
                }
                out.writeObject(message);
            }
        }
        private void resetGame() throws Exception
        {
            guessedWords = new HashSet<String>();
            HangmanSerializable newHM = new HangmanSerializable();
            currentWord = null;
            guessingWord = false;
            //create a new blank Hangman object to send back.
            out.writeObject(newHM);

        }
        //finds letter positions and sends them back
        private void returnLetterPositions(char letter, HangmanSerializable d) throws Exception{
            if(currentWord != null){

                ArrayList<Integer> positions = new ArrayList<Integer>();
                for(int i = 0; i < currentWordArray.length; i++){
                    if(currentWordArray[i] == letter){
                        positions.add(i);
                    }
                }
                //sending back letter positions
                d.setLetterPositions(positions);
                d.setSendLetterPositions(true);
                out.writeObject(d);
            }
        }

        private void returnLetterCount(HangmanSerializable d) throws Exception{
            //System.out.println(currentWord);
            if(currentWord != null){
                //System.out.println(2);

                int letterCount = currentWord.length();
                //System.out.println(3);
                //sending letter count
                d.setLetterCount(letterCount);
                d.setSendingLetterCount(true);
                //System.out.println(4);
                out.writeObject(d);
                //System.out.println(5);
                
            }

        }
        

        public void run(){
            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }
            while(true) {
                try {
                    //communicating using serializable
                    //System.out.println("HERE!");
                    HangmanSerializable data = (HangmanSerializable) in.readObject();


                    try{
                        if(data.getPlayerQuit())
                        {
                            callback.accept("Player 1 lost.\nPlayer 1 has quit.");
                        }
                        else if(data.getPlayAgain())
                        {
                            resetGame();
                            callback.accept("Player 1 wants to play again. Resetting.");
                        }
                        else if(data.getPickingLetter()){
                            //handle picking a letter returns the letter positions
                            returnLetterPositions(data.getLetterPick(), data);
                            callback.accept("Player guessed letter: " + data.getLetterPick());
                        }else if(data.getGuessingWord()){
                            //handle guessing a word
                            evaluateGuess(data.getWordGuess());
    
                        }else if(data.getPickedCategory()){
                            //handle picking a category
                            int category = data.getCategory();
                            //check if sent categories are between 0 and 2

                            if(category >= 0 && category <= 2){
                                //chooses a word

                                chooseAWord(data.getCategory(), data);
                                callback.accept("Word sent to client");
                            }else{
                                callback.accept("Category not between bounds");
                            }
                        }
                        //more cases
                    }catch(Exception e){
                        callback.accept("Error sending data");
                    }
                }
                catch(Exception e) {
                    callback.accept("Something wrong with the socket from client: " + count + "....closing down!");
                    clients.remove(this);
                    break;
                }
            }
        }//end of run

    }
}
