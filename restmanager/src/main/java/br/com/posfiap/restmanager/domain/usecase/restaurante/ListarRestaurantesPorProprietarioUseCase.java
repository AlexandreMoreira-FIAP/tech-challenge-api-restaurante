package br.com.posfiap.restmanager.domain.usecase.restaurante;

import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.model.Restaurante;
import br.com.posfiap.restmanager.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class ListarRestaurantesPorProprietarioUseCase {

    private static final String NENHUM_RESTAURANTE_ENCONTRADO = "Nenhum restaurante encontrado para o proprietário com ID {0}.";

    private final RestauranteRepository restauranteRepository;

    public List<Restaurante> executar(Long idProprietario) {
        var lista = restauranteRepository.listarPorProprietario(idProprietario);

        if (lista == null || lista.isEmpty()) {
            throw new NotFoundException(format(NENHUM_RESTAURANTE_ENCONTRADO, idProprietario));
        }

        return lista;
    }
}
