package com.github.GaskaPiotr.spring_boot_boilerplate;

import com.github.GaskaPiotr.spring_boot_boilerplate.dto.UserResponse;
import com.github.GaskaPiotr.spring_boot_boilerplate.entity.Role;
import com.github.GaskaPiotr.spring_boot_boilerplate.entity.User;
import com.github.GaskaPiotr.spring_boot_boilerplate.exception.RoleNotFoundException;
import com.github.GaskaPiotr.spring_boot_boilerplate.repository.RoleRepository;
import com.github.GaskaPiotr.spring_boot_boilerplate.repository.UserRepository;
import com.github.GaskaPiotr.spring_boot_boilerplate.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class SpringBoilerplateApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private JwtService jwtService;


	@BeforeEach
	void setUp() {
		userRepository.deleteAllInBatch();
	}

	@Test
	void shouldGetAllUsers() {
		Role adminRole = roleRepository.findByName("ADMIN")
				.orElseThrow(() -> new RoleNotFoundException("Admin role not found"));

		User adminUser = new User("admin@example.com", "password123");
		adminUser.setRole(adminRole);
		userRepository.save(adminUser);

		String jwtToken = jwtService.generateToken(adminUser);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.COOKIE, "jwt-token=" + jwtToken);
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		Role role = roleRepository.findByName("USER")
				.orElseThrow(() -> new RoleNotFoundException("User role not found"));
		User user1 = new User("email@example.com", "passwordExample123");
		User user2 = new User("example@email.com", "passwordExample321");
		user1.setRole(role);
		user2.setRole(role);
		userRepository.save(user1);
		userRepository.save(user2);

		ResponseEntity<UserResponse[]> response = testRestTemplate.exchange(
				"/api/v1/users",
				HttpMethod.GET,
				requestEntity,
				UserResponse[].class
		);
		System.out.println("HTTP STATUS: " + response.getStatusCode());

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

		UserResponse[] users = response.getBody();

		assertThat(users)
				.extracting(UserResponse::email) // Adjust to match your exact DTO method/field
				.contains(
						"admin@example.com",
						"email@example.com",
						"example@email.com"
				);
	}

}
