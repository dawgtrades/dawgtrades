package edu.uga.dawgtrades.model;

/**
 * This interface represents a RegisteredUser of DawgTrades.  It has a collection of methods to read/write 
 * RegisteredUser's attributes.
 * It also extends the Persistable interface, since objects of this class will be stored in the persistent data store.
 * 
 *  * @author Krys J. Kochut
 *
 */
public interface RegisteredUser
    extends Persistable
{
    /**
     * Return the user name of this RegisteredUser (used as the login name).
     * @return the user's name
     */
    String getName();
    
    /**
     * Set the name of this RegisteredUser (used as the login name).
     * @param name the new user name
     */
    void setName(String name);
    
    /**
     * Return the first name of this RegisteredUser.
     * @return the user's first name
     */
    String getFirstName();
    
    /**
     * Set the first name of this RegisteredUser.
     * @param firstName the new first name
     */
    void setFirstName(String firstName);
    
    /**
     * Return the last name of this RegisteredUser.
     * @return the user's last name
     */
    String getLastName();
    
    /**
     * Set the last name of this RegisteredUser.
     * @param lastName the new last name
     */
    void setLastName(String lastName);
    
    /**
     * Return the password of this RegisteredUser.
     * @return the user's password
     */
    String getPassword();
    
    /**
     * Set the password of this RegisteredUser.
     * @param password the new password
     */
    void setPassword(String password);
    
    /**
     * Return true if this RegisteredUser is an administrator of Dawg Trades.
     * @return true if this user is an administrator
     */
    boolean getIsAdmin();
    
    /**
     * Set the administrator status of this RegisteredUser.
     * @param isAdmin the new status as an administrator
     */
    void setIsAdmin(boolean isAdmin);
    
    /**
     * Return the email address of this RegisteredUser.
     * @return the user's email
     */
    String getEmail();
    
    /**
     * Set the email address of this RegisteredUser.
     * @param email the new email the new email
     */
    void setEmail(String email);
    
    /**
     * Return the phone number of this RegisteredUser.
     * @return the user's phone number
     */
    String getPhone();
    
    /**
     * Set the phone number of this RegisteredUser.
     * @param phone the new phone number 
     */
    void setPhone(String phone);
    
    /**
     * Return true if this RegisteredUser's phone can accept text messages.
     * @return true if this RegisteredUser's phone can accept text
     */
    boolean getCanText();
    
    /**
     * Set the indication if this RegisteredUser's phone can accept text messages.
     * @param canText the new indication of being able to receive texts
     */
    void setCanText(boolean canText);
}
