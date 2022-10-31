package com.stussy.stussy.clone20220929ng.service.auth;


import com.stussy.stussy.clone20220929ng.domain.User;
import com.stussy.stussy.clone20220929ng.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2Service extends DefaultOAuth2UserService {

    private final AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("user request >>>>> {}", userRequest);
        log.info("getClientRegistration >>>>> {}", userRequest.getClientRegistration());
        log.info("getAttributes >>>>> {}", oAuth2User.getAttributes());


        String provider = userRequest.getClientRegistration().getClientName();

        User user = getOauth2User(provider, oAuth2User.getAttributes());

        return new PrincipalDetails(user, oAuth2User.getAttributes()); // 업캐스팅 되어 로그인 됨.
    }


    private User getOauth2User(String provider, Map<String, Object> attributes)  {
        String oauth2_id = null;
        String id = null;
        String email = null;

        User user = null;


        Map<String, Object> response = null;

        //  네이버 구글 . . . 에서 주는 로그인 정보가 다 달라서 조건을 주어야 함 . . .
        if(provider.equalsIgnoreCase("google")) {
            response = attributes;
            id = (String) response.get("sub"); // google 에서 준 개인 식별 ID
        } else if (provider.equalsIgnoreCase("naver")) {
            response = (Map<String, Object>) attributes.get("response");
            id = (String) response.get("id");
        }

        oauth2_id = provider + "_" + id;
        email =(String) response.get("email");

        user = accountRepository.findUserByEmail(email);
        if(user == null) {
            user = User.builder()
                    .username(email)
                    .oauth_username(oauth2_id)
                    .password(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString().replaceAll("-", "")))
                    .name((String) response.get("name"))
                    .email(email)
                    .role_id(1)
                    .provider(provider)


                    .build();

            accountRepository.save(user);

            user = accountRepository.findUserByEmail(email);
        }



        return user;
    }

}
