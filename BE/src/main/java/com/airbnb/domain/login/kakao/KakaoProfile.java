package com.airbnb.domain.login.kakao;

public class KakaoProfile {

    public Long id;
    public String connectedAt;
    public Properties properties;
    public KakaoAccount kakaoAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConnectedAt() {
        return connectedAt;
    }

    public void setConnectedAt(String connectedAt) {
        this.connectedAt = connectedAt;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public KakaoAccount getKakaoAccount() {
        return kakaoAccount;
    }

    public void setKakaoAccount(KakaoAccount kakaoAccount) {
        this.kakaoAccount = kakaoAccount;
    }

    public class Properties {

        public String nickname;
        public String profileImage;
        public String thumbnailImage;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getThumbnailImage() {
            return thumbnailImage;
        }

        public void setThumbnailImage(String thumbnailImage) {
            this.thumbnailImage = thumbnailImage;
        }
    }

    public class KakaoAccount {

        public Boolean profileNicknameNeedsAgreement;
        public Boolean profileImageNeedsAgreement;
        public Profile profile;
        public Boolean hasEmail;
        public Boolean emailNeedsAgreement;
        public Boolean isEmailValid;
        public Boolean isEmailVerified;
        public String email;

        public Boolean getProfileNicknameNeedsAgreement() {
            return profileNicknameNeedsAgreement;
        }

        public void setProfileNicknameNeedsAgreement(Boolean profileNicknameNeedsAgreement) {
            this.profileNicknameNeedsAgreement = profileNicknameNeedsAgreement;
        }

        public Boolean getProfileImageNeedsAgreement() {
            return profileImageNeedsAgreement;
        }

        public void setProfileImageNeedsAgreement(Boolean profileImageNeedsAgreement) {
            this.profileImageNeedsAgreement = profileImageNeedsAgreement;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

        public Boolean getHasEmail() {
            return hasEmail;
        }

        public void setHasEmail(Boolean hasEmail) {
            this.hasEmail = hasEmail;
        }

        public Boolean getEmailNeedsAgreement() {
            return emailNeedsAgreement;
        }

        public void setEmailNeedsAgreement(Boolean emailNeedsAgreement) {
            this.emailNeedsAgreement = emailNeedsAgreement;
        }

        public Boolean getEmailValid() {
            return isEmailValid;
        }

        public void setEmailValid(Boolean emailValid) {
            isEmailValid = emailValid;
        }

        public Boolean getEmailVerified() {
            return isEmailVerified;
        }

        public void setEmailVerified(Boolean emailVerified) {
            isEmailVerified = emailVerified;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public class Profile {

            public String nickname;
            public String thumbnailImageUrl;
            public String profileImageUrl;
            public Boolean isDefaultImage;

            public String getNickname() {
                return nickname;
            }

            public String getThumbnailImageUrl() {
                return thumbnailImageUrl;
            }

            public String getProfileImageUrl() {
                return profileImageUrl;
            }

            public Boolean getDefaultImage() {
                return isDefaultImage;
            }
        }
    }
}

