package lawscraper.server.service;

import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.user.User;
import lawscraper.server.repositories.RepositoryBase;
import lawscraper.shared.UserRole;
import lawscraper.shared.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RolesAllowed;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 4/16/12
 * Time: 8:33 PM
 */

@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService, UserService {

    private RepositoryBase<User>userRepository = null;
    private RepositoryBase<LawDocumentPart> lawPartRepository = null;
    private PasswordEncoder passwordEncoder = null;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, RepositoryBase<User> userRepository,
                           @Qualifier("repositoryBaseImpl") RepositoryBase<LawDocumentPart> lawPartRepository) {
        this.userRepository = userRepository;
        this.lawPartRepository = lawPartRepository;
        this.passwordEncoder = passwordEncoder;

        this.userRepository.setEntityClass(User.class);
        this.lawPartRepository.setEntityClass(LawDocumentPart.class);
    }

    @Override
    public User find(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    @RolesAllowed({"ROLE_USER", "ROLE_ADMINISTRATOR"})
    public List<User> findAll() throws AccessDeniedException {
        List<User> allUsersList = new ArrayList<User>();
        Iterable<User> allUsers = userRepository.findAll();
        while (allUsers.iterator().hasNext()) {
            User user = allUsers.iterator().next();
            allUsersList.add(user);
        }

        return allUsersList;
    }

    @Override
    public UserStatus login(String userName, String password) {
        UserStatus userStatus = new UserStatus();
        //User user =
        //userStatus.setUserId(user.getId());
        loadUserByUsername(userName);
        return userStatus;
    }

    @Override
    @Transactional(readOnly = false)
    @RolesAllowed({"ROLE_ADMINISTRATOR"})
    public Long addUser(User user) {
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
        user.setUserRole(UserRole.Administrator);
        user = userRepository.save(user);
        return user.getId();
    }

    @Override
    @Transactional(readOnly = false)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user;
            if (username.equals("root")) {
                user = new User("root", passwordEncoder.encodePassword("root", null), UserRole.Administrator);
            } else {
                user = getUserByUserName(username);
            }
            SecurityUser securityUser = new SecurityUser(user);

            return securityUser;
        } catch (Exception e) {
            throw new UsernameNotFoundException("User " + username + "not found.");
        }
    }

    private User getUserByUserName(String username) throws NoResultException {
        return userRepository.findByPropertyValue("userName", username).iterator().next();
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = null;
        try {
            if (principal instanceof UserDetails) {
                user = getUserByUserName(((UserDetails) principal).getUsername());
            } else {
                userRepository.findByPropertyValue("userName", principal.toString());
            }
        } catch (NoResultException e) {
            System.out.println("User unknown");
        }

        if (principal instanceof UserDetails && ((UserDetails) principal).getUsername().equals("root")) {
            return new User("root", passwordEncoder.encodePassword("root", null), UserRole.Administrator);
        }

        if (user == null) {
            return User.AnonymousUser();
        }

        return user;
    }

}
