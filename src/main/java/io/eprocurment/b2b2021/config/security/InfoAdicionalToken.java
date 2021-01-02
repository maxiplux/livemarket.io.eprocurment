package io.eprocurment.b2b2021.config.security;


import io.eprocurment.b2b2021.models.corporate.Company;
import io.eprocurment.b2b2021.models.users.User;
import io.eprocurment.b2b2021.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;


@Component
@Log4j2
public class InfoAdicionalToken implements TokenEnhancer {


    @Autowired
    private UserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {


        User user = userService.findByUsername(authentication.getName());
        HashMap<String, Object> info = new HashMap<String, Object>();


        Optional<Company> optionalCompany = this.userService.findCompanyByUserId(user.getId());


        info.put("companyId", "null");
        info.put("mainOfficeId", "null");
        info.put("isCompanyManager", String.valueOf(user.isCompanyManager() ));

        info.put("userId",String.valueOf( user.getId()));

        optionalCompany.ifPresent(company ->
                {
                    info.put("companyId", String.valueOf( company.getId()));
                    info.put("mainOfficeId", String.valueOf( company.getMainOffice()) );


                }
        );


        info.put("nombre", user.getFirtsName());
        info.put("apellido", user.getLastName());
        info.put("email", user.getEmail());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

        return accessToken;
    }

}
