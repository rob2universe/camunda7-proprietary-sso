package org.camunda.example.sso;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.security.auth.AuthenticationProvider;
import org.camunda.bpm.engine.rest.security.auth.AuthenticationResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Slf4j
public class MyAuthProvider implements AuthenticationProvider {

  public AuthenticationResult extractAuthenticatedUser(HttpServletRequest request, ProcessEngine engine) {

    Principal principal = request.getUserPrincipal();
// expecting request parameters, e.g. http://localhost:8080/camunda/app/welcome/default/?sso-user=demo&sso-token=demo123
    String token = request.getParameter("sso-token");
    String userId = request.getParameter("sso-user");

    log.info("Staring custom authentication for principal {}, userId {}, token {}", principal, userId, token);

/*
    if a proper container-based authentication already occurred, then the principal can be used.
    if (principal == null) {
      return AuthenticationResult.unsuccessful();
    }
    String name = principal.getName();
    if (name == null || name.isEmpty()) {
      return AuthenticationResult.unsuccessful();
    }
*/
    if (userId != null && token != null && isValidToken(userId, token)) {
      log.info("{} successfully authenticated.", userId);
      return AuthenticationResult.successful(userId);
    } else {
      log.info("Authenticating {} with token {} failed.", userId, token);
      return AuthenticationResult.unsuccessful();
    }
  }

  private boolean isValidToken(String userId, String token) {
    //TODO: add to call external service to validate

    // just an example check
    boolean valid = token.startsWith(userId);
    log.info("Valid token: {}", valid);
    return valid;
  }

  public void augmentResponseByAuthenticationChallenge(HttpServletResponse response, ProcessEngine engine) {
    // noop
  }
}
