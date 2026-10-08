package sk.upjs.paz;

import java.util.List;

public class UserService {
    List<User> users;

    public UserService(List<User> users){
        this.users = users;
    }

    GenderRatio calculateGenderRAtio(){
        if(users == null || users.isEmpty()){
            return  new GenderRatio(0.0,0.0,0.0,0.0);

        }
        int noOfUsers = users.size();
        int boys = 0, girls = 0, other = 0, unknown =0;

        for (User u: users){
            switch (u.gender()){
                case MALE -> boys++;
                case FEMALE -> girls++;
                case OTHER -> other++;
                case UNKNOWN -> unknown++;
                default -> unknown++;
            }
        }

        return  new GenderRatio((double) boys/noOfUsers,
                (double) girls/noOfUsers,
                (double) unknown/noOfUsers,
                (double) other/noOfUsers);
    }
}

