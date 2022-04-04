package com.epam.tax.services;

import com.epam.tax.entities.User;

import java.util.List;

/**
 * Interface, that contains methods for
 * multiply operations related to entity user.
 */
public interface UserService {

    /**
     * Method for inserting user in database.
     *
     * @param user user, that must be saved.
     * @return true if user was saved to database.
     */
    boolean insert(User user);

    /**
     * Method for updating user in database.
     *
     * @param user user, that must be updated.
     * @return true if user was updated to database.
     */
    boolean update(User user);

    /**
     * Method for deleting user from database.
     *
     * @param user user, that must be deleted.
     * @return true if user was deleted from database.
     */
    boolean delete(User user);

    /**
     * Method for getting user from database by id.
     *
     * @param id is an id of the user which should be returned.
     * @return entity user.
     */
    User getById(Long id);

    /**
     * Method for getting user from database by login.
     *
     * @param login is a login of the user which should be returned.
     * @return entity user.
     */
    User getByLogin(String login);

    /**
     * Method for finding all users.
     *
     * @return list of users.
     */
    List<User> findAll();

    /**
     * Method to check if user is inspector.
     *
     * @param user is user to be checked.
     * @return true if user is inspector.
     */
    boolean isInspector(User user);

    /**
     * Method to check if user is client.
     *
     * @param user is user to be checked.
     * @return true if user is client.
     */
    boolean isClient(User user);

    /**
     * Method to check if user is registered and input is correct.
     *
     * @param user     is user to be checked.
     * @param login    input parameter for login
     * @param password input parameter for password
     * @return true if user is registered and input is correct.
     */
    boolean isValid(String login, String password, User user);

    /**
     * Method to check valid input when registering.
     *
     * @param name     input parameter for name
     * @param surname  input parameter for surname
     * @param login    input parameter for login
     * @param password input parameter for password
     * @return true if all input is correct.
     */
    boolean checkValidUserInputRegister(String name, String surname, String login, String password);

    /**
     * Method to check if input is not null and not empty.
     *
     * @param input string to be checked
     * @return true if input is not null and not empty.
     */
    boolean checkValidInput(String input);
}
