package com.epam.tax.dao;

import com.epam.tax.entities.User;

import java.util.List;

/**
 * Interface, that contains methods for
 * creating, updating, deleting, finding entity user from database.
 */
public interface UserDao {

    /**
     * Method for inserting user in database.
     *
     * @param user user, that must be saved.
     * @return true if user was saved to database.
     */
    boolean insertUser(User user);

    /**
     * Method for finding all users.
     *
     * @return list of users.
     */
    List<User> findAllUsers();

    /**
     * Method for getting user from database by id.
     *
     * @param id is an id of the user which must be returned.
     * @return entity user.
     */
    User getUserById(Long id);

    /**
     * Method for getting user from database by login.
     *
     * @param login is a login of the user which must be returned.
     * @return entity user.
     */
    User getUserByLogin(String login);

    /**
     * Method for updating user in database.
     *
     * @param user user, that must be updated.
     * @return true if user was updated to database.
     */
    boolean updateUser(User user);

    /**
     * Method for deleting user from database.
     *
     * @param user user, that must be deleted.
     * @return true if user was deleted from database.
     */
    boolean deleteUser(User user);
}
