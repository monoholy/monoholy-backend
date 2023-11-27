package com.monoholy.api.server;

import javax.servlet.http.HttpServletRequest;

import com.monoholy.constant.SessionCookieConstant;
import com.monoholy.entity.SessionCookieToken;
import com.monoholy.repository.UserRepository;
import com.monoholy.service.SessionCookieTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationApi {

  @Autowired private SessionCookieTokenService tokenService;
  @Autowired private UserRepository userRepository;

  /**
   * Allows client to log in to the system and creates a session for the current user.
   *
   * @param request - http request sent by client
   * @return - a string response which includes session token id to use in other api endpoints.
   */
  @GetMapping(value = "/login", produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> login(HttpServletRequest request) {
    String encryptedUsername =
        (String) request.getAttribute(SessionCookieConstant.REQUEST_ATTRIBUTE_USERNAME);
    SessionCookieToken token = new SessionCookieToken();
    token.setUsername(encryptedUsername);
    String tokenId = tokenService.store(request, token);

    return ResponseEntity.ok().body(tokenId);
  }

  /**
   * Allows client to log out from the system and deletes the session and session cookie.
   *
   * @param request - http request sent by client
   * @return - string type of message
   */
  @DeleteMapping(value = "/logout", produces = MediaType.TEXT_PLAIN_VALUE)
  public String logout(HttpServletRequest request) {
    tokenService.delete(request);

    return "Logged out!";
  }
}
