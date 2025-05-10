package br.com.posfiap.restmanager.dtos;

import java.util.UUID;

import br.com.posfiap.restmanager.entities.enumeration.UserTypes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private UUID userUuid;

    private UserTypes userType;
    private String userName;
    private String email;
    private String password;


}
