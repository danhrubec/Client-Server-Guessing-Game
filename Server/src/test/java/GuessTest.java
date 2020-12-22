import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.net.Socket;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GuessTest {

	@Test
	void testPickingWord(){
		try{
			Socket socket = new Socket("127.0.0.1",5555);
		}catch(Exception e){
			System.out.println("Didnt start socket");
		}
	}

}
