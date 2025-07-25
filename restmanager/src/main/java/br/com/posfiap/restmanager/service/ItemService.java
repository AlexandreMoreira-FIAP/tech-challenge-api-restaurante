package br.com.posfiap.restmanager.service;

import br.com.posfiap.restmanager.domain.Item;
import br.com.posfiap.restmanager.error.AuthenticationException;
import br.com.posfiap.restmanager.error.BusinessException;
import br.com.posfiap.restmanager.error.NotFoundException;
import br.com.posfiap.restmanager.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.text.MessageFormat.format;
import static java.time.LocalDateTime.now;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
@RequiredArgsConstructor
public class ItemService {

    private static final String Item_NAO_ENCONTRADO = "Usuário não encontrado com ID {0}.";
    private static final String LOGIN_INDISPONIVEL = "Login {0} não está disponível.";
    private static final String SENHA_INCORRETA = "Senha incorreta.";

    private final ItemRepository itemRepository;
    private final PasswordEncoder passwordEncoder;

    public Item incluir(Item item) {

        return itemRepository.salvar(item);
    }

    public Item buscarPorId(Long id) {

        return itemRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException(format(Item_NAO_ENCONTRADO, id)));
    }

    public void excluir(Long id) {

        buscarPorId(id);
        itemRepository.excluir(id);
    }

    public Item atualizar(Long id, Item item) {

        var itemAtual = buscarPorId(id);

        item.setId(itemAtual.getId());

        return itemRepository.salvar(item);
    }

}