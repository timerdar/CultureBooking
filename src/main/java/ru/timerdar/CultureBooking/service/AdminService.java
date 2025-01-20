package ru.timerdar.CultureBooking.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.timerdar.CultureBooking.dto.AdminCreatingDto;
import ru.timerdar.CultureBooking.dto.ShortAdminDto;
import ru.timerdar.CultureBooking.model.Admin;
import ru.timerdar.CultureBooking.repository.AdminRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService implements UserDetailsService{

    @Value("${ru.timerdar.security.admin.code}")
    private String code;

    @Autowired
    @Lazy
    private PasswordEncoder encoder;

    @Autowired
    private AdminRepository adminRepository;

    public Admin createNewAdmin(AdminCreatingDto adminCreatingDto){
        if(!adminCreatingDto.isValid()){
            throw new IllegalArgumentException("Пароль должен содержать строчные и заглавные буквы, цифры и спецсимволы: @#$%^&+=");
        }
        //TODO сделать обработку эксепшна
        if(!adminCreatingDto.getCreatingCode().equals(code)){
            throw new AccessDeniedException("Доступ к регистрации возможен только при наличии регистрационного кода");
        }
        Admin newAdmin = new Admin(null,
                adminCreatingDto.getUsername(),
                encoder.encode(adminCreatingDto.getPassword()),
                adminCreatingDto.getName(),
                adminCreatingDto.getMobilePhone(),
                "ROLE_ADMIN",
                LocalDateTime.now());

        return adminRepository.save(newAdmin);
    }

    public ShortAdminDto getAdminInfo(Long id){
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isEmpty()){
            throw new EntityNotFoundException("Администратор не найден");
        }
        return admin.get().toShort();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> admin = adminRepository.getByUsername(username);
        if(admin.isEmpty()){
            throw new UsernameNotFoundException("Администратор не найден");
        }
        ArrayList<String> roles = new ArrayList<>();
        roles.add(admin.get().getRole());
        return new User(
                admin.get().getUsername(),
                admin.get().getPasswordHash(),
                mapRolesToAuthorities(roles)
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
