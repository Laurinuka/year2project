package com.project.xlore;

import com.project.xlore.data.User;

public class UserSelf
{
    public static User user;

    public static User getUser()
    {
        return user;
    }

    public static void setUser(User setUser)
    {
        user = setUser;
    }
}
