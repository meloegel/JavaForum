package com.javaforum.JavaForum.services;

import com.javaforum.JavaForum.exceptions.ResourceFoundException;
import com.javaforum.JavaForum.exceptions.ResourceNotFoundException;
import com.javaforum.JavaForum.models.Role;
import com.javaforum.JavaForum.repository.RoleRepository;
import com.javaforum.JavaForum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserAuditing userAuditing;

    @Override
    public List<Role> findAll() {
        List<Role> list = new ArrayList<>();
        roleRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Role findRoleById(long id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
    }

    @Transactional
    @Override
    public Role save(Role role) {
        if (role.getUsers().size() > 0) {
            throw new ResourceFoundException("User Roles are not updated through Role.");
        }
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        Role rr = roleRepository.findByNameIgnoreCase(name);
        if (rr != null) {
            return rr;
        } else {
            throw new ResourceNotFoundException(name);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteAll() {
        roleRepository.deleteAll();
    }

    @Transactional
    @Override
    public Role update(long id, Role role) {
        if (role.getName() == null) {
            throw new ResourceNotFoundException("No role name found to update!");
        }
        if (role.getUsers().size() > 0) {
            throw new ResourceFoundException("User Roles are not updated through Role. See endpoint POST: users/user/{userid}/role/{roleid}");
        }
        Role newRole = findRoleById(id); // see if id exists
        roleRepository.updateRoleName(userAuditing.getCurrentAuditor().get(), id, role.getName());
        return findRoleById(id);
    }
}
