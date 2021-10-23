package ar.com.alkemy.blog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.alkemy.blog.entities.Usuario;
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
		Usuario usuario = new Usuario();

		usuario.setUsername("Diana@gmail.com");
		usuario.setPassword("qp5TPhgUtIf7RDylefkIbw==");

		assertFalse(!usuario.getPassword().equals(Crypto.encrypt("AbcdE23", usuario.getUsername().toLowerCase())));

	}

}
