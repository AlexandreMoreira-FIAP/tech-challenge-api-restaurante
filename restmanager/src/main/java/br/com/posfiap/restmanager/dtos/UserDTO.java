package br.com.posfiap.restmanager.dtos;

import br.com.posfiap.restmanager.entities.enumeration.UserTypes;
import com.fasterxml.jackson.annotation.JsonAnyGetter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

@Getter
@Setter
public class UserDTO {

    private UUID userUuid;

    private UserTypes userType;
    private String userName;
    private String email;
    private String password;


}
