package ru.timerdar.CultureBooking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class AdminCreatingDto {

    private String username;
    private String password;
    private String mobilePhone;
    private String name;
    //Регистрация возможна только при наличии кода
    private String creatingCode;


    @JsonIgnore
    public boolean isValid(){
        return !username.isEmpty() && passwordValidation();
    }

    public boolean passwordValidation(){
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,}$");
    }
}

