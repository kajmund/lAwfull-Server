package lawscraper.server.service;

import lawscraper.server.entities.user.User;
import lawscraper.shared.UserStatus;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 4/16/12
 * Time: 8:34 PM
 */

public interface UserService {
    User find(Long id);

    Long addUser(User user);

    void updateUser(User user);

    UserStatus login(String userName, String password);

    List<User> findAll();

    User getCurrentUser();
}
