package ar.com.alkemy.blog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.alkemy.blog.entities.User;
import ar.com.alkemy.blog.security.Crypto;

@SpringBootTest
class BlogApplicationTests {

	@Test
	void ctestearEncriptacion() {

		String contraseñaImaginaria = "pitufosasesinos";
		String contraseñaImaginariaEncriptada = Crypto.encrypt(contraseñaImaginaria, "palabra");

		String contraseñaImaginariaEncriptadaDesencriptada = Crypto.decrypt(contraseñaImaginariaEncriptada, "palabra");

		assertEquals(contraseñaImaginariaEncriptadaDesencriptada, contraseñaImaginaria);
	}

	@Test
	void testearContraseña() {
		User user = new User();

		user.setEmail("Diana@gmail.com");
		user.setPassword("qp5TPhgUtIf7RDylefkIbw==");

		assertFalse(!user.getPassword().equals(Crypto.encrypt("AbcdE23", user.getEmail().toLowerCase())));

	}

}
