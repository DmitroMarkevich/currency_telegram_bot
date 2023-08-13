package com.example.demo.service;

import com.example.demo.model.Bank;
import com.example.demo.model.Currency;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<Currency> getUserCurrency(Long userId) {
        return userRepository.findById(userId).map(User::getCurrency).orElse(null);
    }

    public Integer getUserDecimalPlaces(Long userId) {
        return userRepository.findById(userId).map(User::getDecimalPlaces).orElse(null);
    }

    public Bank getUserBank(Long userId) {
        return userRepository.findById(userId).map(User::getCurrentBank).orElse(null);
    }

    public Integer getUserNotificationTime(Long userId) {
        return userRepository.findById(userId).map(User::getNotificationTime).orElse(null);
    }

    public List<User> getUsersByNotificationTime(Integer notificationTime) {
        return userRepository.getUsersByNotificationTime(notificationTime);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public boolean userExistsById(Long userId) {
        return userRepository.existsById(userId);
    }

    public void saveUser(User user) {
        Long userId = user.getId();

        if (userRepository.existsById(userId)) {
            log.error("User with id {} already exists", userId);
        } else {
            userRepository.save(user);
            log.info("Saved user {}", user);
        }
    }

    public void updateUserDecimalPlaces(Long userId, Integer decimalPlaces) {
        updateUser(userId, user -> user.setDecimalPlaces(decimalPlaces));
    }

    public void updateUserCurrentBank(Long userId, Bank bank) {
        updateUser(userId, user -> user.setCurrentBank(bank));
    }

    public void updateUserNotificationTime(Long userId, Integer notificationTime) {
        updateUser(userId, user -> user.setNotificationTime(notificationTime));
    }

    @Transactional
    public void updateUserCurrency(Long userId, Currency currency) {
        updateUser(userId, user -> {
            if (!user.getCurrency().contains(currency)) {
                user.getCurrency().add(currency);
            } else if (user.getCurrency().size() > 1) {
                user.getCurrency().remove(currency);
            }
        });
    }

    private void updateUser(Long userId, UserUpdater updater) {
        Optional<User> optionalUser = userRepository.findById(userId);

        optionalUser.ifPresent(user -> {
            updater.update(user);
            userRepository.save(user);
        });

        if (optionalUser.isEmpty()) {
            log.error("User with id {} not found", userId);
        }
    }

    interface UserUpdater {
        void update(User user);
    }
}
