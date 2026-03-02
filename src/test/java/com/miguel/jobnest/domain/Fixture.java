package com.miguel.jobnest.domain;

import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.*;

import java.time.LocalDateTime;

public class Fixture {

    private Fixture() {
    }

    /* ========================= USER ========================= */

    public static class UserFixture {
        private UserFixture() {
        }

        public static User newUser(AuthorizationRole authorizationRole) {
            return User.newUser(
                    "john",
                    "johndoe1947@gmail.com",
                    "12345@JH",
                    authorizationRole,
                    "Louisville",
                    "Kentucky",
                    "United States"
            );
        }

        public static User withUserStatus(User user, UserStatus userStatus) {
            return User.with(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getDescription(),
                    user.getPassword(),
                    userStatus,
                    user.getAuthorizationRole(),
                    user.getCity(),
                    user.getState(),
                    user.getCountry(),
                    user.getCreatedAt()
            );
        }
    }

    /* ========================= JOB VACANCY ========================= */

    public static class JobVacancyFixture {
        private JobVacancyFixture() {
        }

        public static JobVacancy newJobVacancy(String userId) {
            return JobVacancy.newJobVacancy(
                    userId,
                    "Java Developer",
                    "This is the job vacancy description",
                    SeniorityLevel.JUNIOR,
                    Modality.REMOTE,
                    "Company Name"
            );
        }
    }

    /* ========================= SUBSCRIPTION ========================= */

    public static class SubscriptionFixture {
        private SubscriptionFixture() {
        }

        public static Subscription newSubscription(String userId, String jobVacancyId) {
            return Subscription.newSubscription(
                    userId,
                    jobVacancyId,
                    "resume-url"
            );
        }
    }

    /* ========================= USER CODE ========================= */

    public static class UserCodeFixture {

        private UserCodeFixture() {
        }

        public static UserCode newUserCode(String userId, UserCodeType userCodeType) {
            return UserCode.newUserCode(
                    userId,
                    "1AB23CT2",
                    userCodeType
            );
        }

        public static UserCode withExpiresIn(UserCode userCode, LocalDateTime expiresIn) {
            return UserCode.with(
                    userCode.getId(),
                    userCode.getUserId(),
                    userCode.getCode(),
                    userCode.getUserCodeType(),
                    expiresIn,
                    userCode.getCreatedAt()
            );
        }
    }
}