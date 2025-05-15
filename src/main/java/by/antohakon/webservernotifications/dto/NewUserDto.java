package by.antohakon.webservernotifications.dto;

public record NewUserDto(String firstName,String lastName,String login) {

    @Override
    public String firstName() {
        return firstName;
    }

    @Override
    public String lastName() {
        return lastName;
    }

    @Override
    public String login() {
        return login;
    }
}
