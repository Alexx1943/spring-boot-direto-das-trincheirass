package academy.devdojo.Repository;


import academy.devdojo.Domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class UserData {

    private final List<User> users = new ArrayList<>();
    private final List<User> user = new ArrayList<>();

    {
        User test1 = User.builder().id(1L).firstName("FirstName1").lastName("lastname").email("emailTes").build();
        User test2 = User.builder().id(2L).firstName("FirstName2").lastName("lastname").email("emailTes").build();
        User test3 = User.builder().id(3L).firstName("FirstName3").lastName("lastname").email("emailTes").build();
        User test4 = User.builder().id(4L).firstName("FirstName4").lastName("lastname").email("emailTes").build();
        User test5 = User.builder().id(5L).firstName("FirstName5").lastName("lastname").email("emailTes").build();

        users.addAll(List.of(test1, test2, test3, test4, test5));
    }

    public User getUser(){

        var getReturn = User.builder().id(ThreadLocalRandom.current().nextLong()).firstName("testeFirstName").lastName("testLastName").email("emailTest").build();

        return getReturn;

    }

    public List<User> getUsers() {
        return users;
    }




}
