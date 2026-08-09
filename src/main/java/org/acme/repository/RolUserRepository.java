package org.acme.repository;

import java.util.List;

import org.acme.models.RolsUser;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RolUserRepository implements PanacheRepositoryBase<RolsUser, Long> {

    public RolsUser findByRolIdAndUserId(Long rolId, Long userId) {
        return find("from RolsUser ru where ru.rol.id = ?1 and ru.user.id = ?2", rolId, userId).firstResult();
    }

    public List<RolsUser> findByUserId(Long userId) {
        return list("from RolsUser ru where ru.user.id = ?1", userId);
    }

}
