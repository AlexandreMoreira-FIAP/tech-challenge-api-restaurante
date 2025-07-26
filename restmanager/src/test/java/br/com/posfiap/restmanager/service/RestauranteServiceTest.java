package br.com.posfiap.restmanager.service;

import br.com.posfiap.restmanager.domain.entities.Endereco;
import br.com.posfiap.restmanager.domain.entities.Restaurante;
import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.error.AuthenticationException;
import br.com.posfiap.restmanager.error.BusinessException;
import br.com.posfiap.restmanager.error.NotFoundException;
import br.com.posfiap.restmanager.infrastructure.adapters.persistence.RestauranteRepository;
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
class RestauranteServiceTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RestauranteService restauranteService;

    private Restaurante restaurante;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        endereco = Endereco.builder()
                .id(1L)
                .rua("Rua Teste")
                .numero("123")
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
                .login("restaurante@test.com")
                .senha("senha123")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .endereco(endereco)
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();
    }

    @Test
    void deveIncluirRestauranteComSucesso() {
        when(restauranteRepository.buscarPorLogin(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("senha123")).thenReturn("senhaEncriptada");
        when(restauranteRepository.salvar(any(Restaurante.class))).thenReturn(restaurante);

        Restaurante resultado = restauranteService.incluir(restaurante);

        assertNotNull(resultado);
        verify(restauranteRepository).buscarPorLogin("restaurante@test.com");
        verify(passwordEncoder).encode("senha123");
        verify(restauranteRepository).salvar(restaurante);
    }

    @Test
    void deveLancarExcecaoQuandoLoginJaExisteAoIncluir() {
        when(restauranteRepository.buscarPorLogin(anyString())).thenReturn(Optional.of(restaurante));

        assertThrows(BusinessException.class, 
            () -> restauranteService.incluir(restaurante));

        verify(restauranteRepository).buscarPorLogin("restaurante@test.com");
        verify(restauranteRepository, never()).salvar(any());
    }

    @Test
    void deveBuscarRestaurantePorIdComSucesso() {
        when(restauranteRepository.buscarPorId(1L)).thenReturn(Optional.of(restaurante));

        Restaurante resultado = restauranteService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(restaurante, resultado);
        verify(restauranteRepository).buscarPorId(1L);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        when(restauranteRepository.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, 
            () -> restauranteService.buscarPorId(1L));

        verify(restauranteRepository).buscarPorId(1L);
    }

    @Test
    void deveExcluirRestauranteComSucesso() {
        when(restauranteRepository.buscarPorId(1L)).thenReturn(Optional.of(restaurante));

        restauranteService.excluir(1L);

        verify(restauranteRepository).buscarPorId(1L);
        verify(restauranteRepository).excluir(1L);
    }

    @Test
    void deveBuscarRestaurantePorLogin() {
        when(restauranteRepository.buscarPorLogin("restaurante@test.com")).thenReturn(Optional.of(restaurante));

        Optional<Restaurante> resultado = restauranteService.buscarPorLogin("restaurante@test.com");

        assertTrue(resultado.isPresent());
        assertEquals(restaurante, resultado.get());
        verify(restauranteRepository).buscarPorLogin("restaurante@test.com");
    }

    @Test
    void deveAlterarSenhaComSucesso() {
        when(restauranteRepository.buscarPorId(1L)).thenReturn(Optional.of(restaurante));
        when(passwordEncoder.matches("senhaAtual", "senha123")).thenReturn(true);
        when(passwordEncoder.encode("novaSenha")).thenReturn("novaSenhaEncriptada");
        when(restauranteRepository.salvar(any(Restaurante.class))).thenReturn(restaurante);

        restauranteService.alterarSenha(1L, "senhaAtual", "novaSenha");

        verify(restauranteRepository).buscarPorId(1L);
        verify(passwordEncoder).matches("senhaAtual", "senha123");
        verify(passwordEncoder).encode("novaSenha");
        verify(restauranteRepository).salvar(restaurante);
    }

    @Test
    void deveLancarExcecaoQuandoSenhaAtualIncorreta() {
        when(restauranteRepository.buscarPorId(1L)).thenReturn(Optional.of(restaurante));
        when(passwordEncoder.matches("senhaErrada", "senha123")).thenReturn(false);

        assertThrows(AuthenticationException.class, 
            () -> restauranteService.alterarSenha(1L, "senhaErrada", "novaSenha"));

        verify(restauranteRepository).buscarPorId(1L);
        verify(passwordEncoder).matches("senhaErrada", "senha123");
        verify(restauranteRepository, never()).salvar(any());
    }

    @Test
    void deveAtualizarRestauranteComLoginDiferente() {
        Restaurante restauranteAtualizado = Restaurante.builder()
                .nome("Nome Atualizado")
                .login("novo@email.com")
                .endereco(endereco)
                .build();

        when(restauranteRepository.buscarPorId(1L)).thenReturn(Optional.of(restaurante));
        when(restauranteRepository.buscarPorLogin("novo@email.com")).thenReturn(Optional.empty());
        when(restauranteRepository.salvar(any(Restaurante.class))).thenReturn(restauranteAtualizado);

        Restaurante resultado = restauranteService.atualizar(1L, restauranteAtualizado);

        assertNotNull(resultado);
        verify(restauranteRepository).buscarPorId(1L);
        verify(restauranteRepository).buscarPorLogin("novo@email.com");
        verify(restauranteRepository).salvar(restauranteAtualizado);
    }

    @Test
    void deveAtualizarRestauranteSemMudarLogin() {
        Restaurante restauranteAtualizado = Restaurante.builder()
                .nome("Nome Atualizado")
                .login("restaurante@test.com")
                .endereco(endereco)
                .build();

        when(restauranteRepository.buscarPorId(1L)).thenReturn(Optional.of(restaurante));
        when(restauranteRepository.salvar(any(Restaurante.class))).thenReturn(restauranteAtualizado);

        Restaurante resultado = restauranteService.atualizar(1L, restauranteAtualizado);

        assertNotNull(resultado);
        verify(restauranteRepository).buscarPorId(1L);
        verify(restauranteRepository, never()).buscarPorLogin(anyString());
        verify(restauranteRepository).salvar(restauranteAtualizado);
    }

    @Test
    void deveAtualizarRestauranteComEnderecoNulo() {
        Restaurante restauranteAtualSemEndereco = Restaurante.builder()
                .id(1L)
                .nome("Restaurante Sem Endereco")
                .login("restaurante@test.com")
                .endereco(null)
                .build();

        Restaurante restauranteAtualizado = Restaurante.builder()
                .nome("Nome Atualizado")
                .login("novo@email.com")
                .endereco(endereco)
                .build();

        when(restauranteRepository.buscarPorId(1L)).thenReturn(Optional.of(restauranteAtualSemEndereco));
        when(restauranteRepository.buscarPorLogin("novo@email.com")).thenReturn(Optional.empty());
        when(restauranteRepository.salvar(any(Restaurante.class))).thenReturn(restauranteAtualizado);

        Restaurante resultado = restauranteService.atualizar(1L, restauranteAtualizado);

        assertNotNull(resultado);
        verify(restauranteRepository).salvar(restauranteAtualizado);
    }

    @Test
    void deveLancarExcecaoQuandoLoginJaExisteAoAtualizar() {
        Restaurante outroRestaurante = Restaurante.builder().id(2L).login("novo@email.com").build();
        Restaurante restauranteAtualizado = Restaurante.builder()
                .nome("Nome Atualizado")
                .login("novo@email.com")
                .build();

        when(restauranteRepository.buscarPorId(1L)).thenReturn(Optional.of(restaurante));
        when(restauranteRepository.buscarPorLogin("novo@email.com")).thenReturn(Optional.of(outroRestaurante));

        assertThrows(BusinessException.class, 
            () -> restauranteService.atualizar(1L, restauranteAtualizado));

        verify(restauranteRepository, never()).salvar(any());
    }
}