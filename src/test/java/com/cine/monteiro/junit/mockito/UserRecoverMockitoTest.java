package com.cine.monteiro.junit.mockito;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.repository.UserRepository;
import com.cine.monteiro.domain.services.UserService;
import com.cine.monteiro.exception.UserException;
import com.cine.monteiro.mail.EmailConfig;
import com.cine.monteiro.utils.UserUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRecoverMockitoTest {
	
	@Mock private UserRepository userRepositoryMock;
	@Spy private UserUtils userUtilsSpy;
	@Mock private EmailConfig emailConfigMock;
	
	@InjectMocks
	private UserService userService;
	
	@Test
	public void t1_recuperacaoContaSucess() throws Exception {
			
		User userMock = mock(User.class);
		
		when(userMock.getId()).thenReturn(1L);
		when(userMock.getEmail()).thenReturn("user@cinemonteiro.email.com");
		when(userRepositoryMock.findByEmail(anyString())).thenReturn(userMock);
		when(emailConfigMock.enviarEmail(anyString(), anyString(), anyString())).thenReturn(true);
		
		assertDoesNotThrow(() -> userService.recuperarPassword("user@cinemonteiro.email.com"));
		
		verify(userUtilsSpy, times(1)).gerarNovaSenha();
		verify(userMock, times(1)).setPassword(anyString());
		verify(userRepositoryMock, times(1)).save(any(User.class));
	}
	
	@Test
	public void t2_recuperacaoContaEmailNaoCadastradoError() throws Exception {
			
		User userMock = mock(User.class);
		
		when(userMock.getId()).thenReturn(1L);
		when(userMock.getEmail()).thenReturn("user@cinemonteiro.email.com");
		when(userRepositoryMock.findByEmail(anyString())).thenReturn(null);
		when(emailConfigMock.enviarEmail(anyString(), anyString(), anyString())).thenReturn(true);
		
		UserException userException = assertThrows(UserException.class, () -> userService.recuperarPassword("user@cinemonteiro.emailnull.com"));
		
		assertNotNull(userException);
		
		verify(userUtilsSpy, never()).gerarNovaSenha();
		verify(userMock, never()).setPassword(anyString());
		verify(userRepositoryMock, never()).save(any(User.class));
	}
	
	@Test
	public void t3_recuperacaoContaEmailNaoEnviadoError() throws Exception {
			
		User userMock = mock(User.class);
		
		when(userMock.getId()).thenReturn(1L);
		when(userMock.getEmail()).thenReturn("user@cinemonteiro.email.com");
		when(userRepositoryMock.findByEmail(anyString())).thenReturn(userMock);
		when(emailConfigMock.enviarEmail(anyString(), anyString(), anyString())).thenReturn(false);
		
		UserException userException = assertThrows(UserException.class, () -> userService.recuperarPassword("user@cinemonteiro.email.com"));
		
		assertNotNull(userException);
		
		verify(userUtilsSpy, times(1)).gerarNovaSenha();
		verify(userMock, never()).setPassword(anyString());
		verify(userRepositoryMock, never()).save(any(User.class));
	}

}
