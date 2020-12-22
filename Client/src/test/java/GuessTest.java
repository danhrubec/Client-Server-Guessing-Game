import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GuessTest {

	Client clientConnection;

	@BeforeEach
	void init(){
		clientConnection = new Client(
				data -> {}, 5555, "127.0.0.1");
	}

	@Test
	void testInClassClient() {
		assertEquals("Client", clientConnection.getClass().getName(), "did not initialize proper Client object");
	}

	@Test
	void testClientPort(){
		assertEquals(clientConnection.port, 5555, "Did not set proper port");
	}


	@Test
	void testClientIP(){
		assertEquals(clientConnection.ip,"127.0.0.1", "IP was not set properly");
	}

	@Test
	void testClientHangman(){
		assertNotNull(clientConnection.hm, "Hangman not being instantiated");
	}

	@Test
	void testClientHangmanBooleans(){
		assertFalse(clientConnection.hm.getSendLetterPositions(),"Not set to false when instantiated");
		assertFalse(clientConnection.hm.getGuessingWord(), "Not set to false when instantiated");
	}


}
