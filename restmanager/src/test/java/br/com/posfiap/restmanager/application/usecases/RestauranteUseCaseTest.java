package br.com.posfiap.restmanager.application.usecases;

import br.com.posfiap.restmanager.domain.entities.Endereco;
import br.com.posfiap.restmanager.domain.entities.Restaurante;
import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.ports.RestauranteRepositoryPort;
import br.com.posfiap.restmanager.error.AuthenticationException;
import br.com.posfiap.restmanager.error.BusinessException;
import br.com.posfiap.restmanager.error.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteUseCaseTest {

    @Mock
    private RestauranteRepositoryPort restauranteRepositoryPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RestauranteUseCase restauranteUseCase;

    private Restaurante restaurante;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        endereco = Endereco.builder()
                .id(1L)
                .rua("Rua do Restaurante")
                .numero("456")
                .bairro("Centro")
                .cidade("São Paulo")
                .cep("01234-567")
                .estado("SP")
                .pais("Brasil")
                .build();

        restaurante = Restaurante.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .tipoDeCozinha("Italiana")
                .login("restaurante@email.com")
                .senha("senha123")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .endereco(endereco)
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();
    }

    @Test
    void deveIncluirRestauranteComSucesso() {
        when(restauranteRepositoryPort.buscarPorLogin(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("senhaEncriptada");
        when(restauranteRepositoryPort.salvar(any(Restaurante.class))).thenReturn(restaurante);

        Restaurante resultado = restauranteUseCase.incluir(restaurante);

        assertNotNull(resultado);
        verify(restauranteRepositoryPort).buscarPorLogin(restaurante.getLogin());
        verify(passwordEncoder).encode("senha123");
        verify(restauranteRepositoryPort).salvar(restaurante);
    }

    @Test
    void deveLancarExcecaoQuandoLoginJaExisteAoIncluir() {
        when(restauranteRepositoryPort.buscarPorLogin(anyString())).thenReturn(Optional.of(restaurante));

        assertThrows(BusinessException.class, () -> restauranteUseCase.incluir(restaurante));
        
        verify(restauranteRepositoryPort).buscarPorLogin(restaurante.getLogin());
        verify(restauranteRepositoryPort, never()).salvar(any());
    }

    @Test
    void deveBuscarRestaurantePorIdComSucesso() {
        when(restauranteRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(restaurante));

        Restaurante resultado = restauranteUseCase.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(restaurante.getId(), resultado.getId());
        verify(restauranteRepositoryPort).buscarPorId(1L);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        when(restauranteRepositoryPort.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> restauranteUseCase.buscarPorId(1L));
        
        verify(restauranteRepositoryPort).buscarPorId(1L);
    }

    @Test
    void deveExcluirRestauranteComSucesso() {
        when(restauranteRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(restaurante));

        restauranteUseCase.excluir(1L);

        verify(restauranteRepositoryPort).buscarPorId(1L);
        verify(restauranteRepositoryPort).excluir(1L);
    }

    @Test
    void deveBuscarRestaurantePorLogin() {
        when(restauranteRepositoryPort.buscarPorLogin("restaurante@email.com")).thenReturn(Optional.of(restaurante));

        Optional<Restaurante> resultado = restauranteUseCase.buscarPorLogin("restaurante@email.com");

        assertTrue(resultado.isPresent());
        assertEquals(restaurante, resultado.get());
        verify(restauranteRepositoryPort).buscarPorLogin("restaurante@email.com");
    }

    @Test
    void deveAlterarSenhaComSucesso() {
        String senhaAtual = "senhaAtual";
        String novaSenha = "novaSenha"; 
        String novaSenhaEncriptada = "novaSenhaEncriptada";
        
        when(restauranteRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(restaurante));
        when(passwordEncoder.matches(senhaAtual, "senha123")).thenReturn(true);
        when(passwordEncoder.encode(novaSenha)).thenReturn(novaSenhaEncriptada);
        when(restauranteRepositoryPort.salvar(any(Restaurante.class))).thenReturn(restaurante);

        restauranteUseCase.alterarSenha(1L, senhaAtual, novaSenha);

        verify(restauranteRepositoryPort).buscarPorId(1L);
        verify(passwordEncoder).matches(senhaAtual, "senha123");
        verify(passwordEncoder).encode(novaSenha);
        verify(restauranteRepositoryPort).salvar(restaurante);
    }

    @Test
    void deveLancarExcecaoQuandoSenhaAtualErrada() {
        String senhaAtual = "senhaErrada";
        String novaSenha = "novaSenha";
        
        when(restauranteRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(restaurante));
        when(passwordEncoder.matches(senhaAtual, "senha123")).thenReturn(false);

        assertThrows(AuthenticationException.class, 
            () -> restauranteUseCase.alterarSenha(1L, senhaAtual, novaSenha));

        verify(restauranteRepositoryPort).buscarPorId(1L);
        verify(passwordEncoder).matches(senhaAtual, "senha123");
        verify(restauranteRepositoryPort, never()).salvar(any());
    }

    @Test
    void deveAtualizarRestauranteComSucesso() {
        Restaurante restauranteAtualizado = Restaurante.builder()
                .nome("Nome Atualizado")
                .login("novo@email.com")
                .endereco(endereco)
                .build();
        
        when(restauranteRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(restaurante));
        when(restauranteRepositoryPort.buscarPorLogin("novo@email.com")).thenReturn(Optional.empty());
        when(restauranteRepositoryPort.salvar(any(Restaurante.class))).thenReturn(restauranteAtualizado);

        Restaurante resultado = restauranteUseCase.atualizar(1L, restauranteAtualizado);

        assertNotNull(resultado);
        verify(restauranteRepositoryPort).buscarPorId(1L);
        verify(restauranteRepositoryPort).buscarPorLogin("novo@email.com");
        verify(restauranteRepositoryPort).salvar(restauranteAtualizado);
    }

    @Test
    void deveAtualizarRestauranteSemMudarLogin() {
        Restaurante restauranteAtualizado = Restaurante.builder()
                .nome("Nome Atualizado")
                .login("restaurante@email.com") // mesmo login
                .endereco(endereco)
                .build();
        
        when(restauranteRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(restaurante));
        when(restauranteRepositoryPort.salvar(any(Restaurante.class))).thenReturn(restauranteAtualizado);

        Restaurante resultado = restauranteUseCase.atualizar(1L, restauranteAtualizado);

        assertNotNull(resultado);
        verify(restauranteRepositoryPort).buscarPorId(1L);
        verify(restauranteRepositoryPort, never()).buscarPorLogin(anyString());
        verify(restauranteRepositoryPort).salvar(restauranteAtualizado);
    }

    @Test
    void deveAtualizarRestauranteComEnderecoNulo() {
        Restaurante restauranteSemEndereco = Restaurante.builder()
                .nome("Nome Atualizado")
                .login("novo@email.com")
                .endereco(null)
                .build();
        
        when(restauranteRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(restaurante));
        when(restauranteRepositoryPort.buscarPorLogin("novo@email.com")).thenReturn(Optional.empty());
        when(restauranteRepositoryPort.salvar(any(Restaurante.class))).thenReturn(restauranteSemEndereco);

        Restaurante resultado = restauranteUseCase.atualizar(1L, restauranteSemEndereco);

        assertNotNull(resultado);
        verify(restauranteRepositoryPort).salvar(restauranteSemEndereco);
    }

    @Test
    void deveLancarExcecaoQuandoLoginJaExisteAoAtualizar() {
        Restaurante outroRestaurante = Restaurante.builder().id(2L).login("novo@email.com").build();
        Restaurante restauranteAtualizado = Restaurante.builder()
                .nome("Nome Atualizado")
                .login("novo@email.com")
                .build();
        
        when(restauranteRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(restaurante));
        when(restauranteRepositoryPort.buscarPorLogin("novo@email.com")).thenReturn(Optional.of(outroRestaurante));

        assertThrows(BusinessException.class, () -> restauranteUseCase.atualizar(1L, restauranteAtualizado));
        verify(restauranteRepositoryPort, never()).salvar(any());
    }
}