package org.acme.service;

import org.acme.model.Perfil;

public interface JwtService {

    String generateJwt(String usuario, Perfil perfil);

}
