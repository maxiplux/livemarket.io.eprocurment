package io.eprocurment.b2b2021.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class AuthExtraTools {

    public static Optional<Map<String, String>> getClaimsFromPrincipal(Principal principal) {
        OAuth2Authentication oauth = (OAuth2Authentication) principal;
        OAuth2AuthenticationDetails auth2AuthenticationDetails = (OAuth2AuthenticationDetails) oauth.getDetails();
        return getAllClaimsFromToken(auth2AuthenticationDetails.getTokenValue());

    }

    private static Optional<Map<String, String>> getAllClaimsFromToken(String token) {
        Map<String, String> fullClaims = new HashMap<>();
        try {
            DecodedJWT jwt = JWT.decode(token);
            Map<String, Claim> claims = jwt.getClaims();
            claims.forEach((key, claim) -> {


                fullClaims.put(key, claim.asString());
            });
            return Optional.of(fullClaims);
        } catch (Exception e) {
            log.error("Could not get all claims Token from passed token" + e.getMessage());

        }
        return Optional.empty();
    }
}
