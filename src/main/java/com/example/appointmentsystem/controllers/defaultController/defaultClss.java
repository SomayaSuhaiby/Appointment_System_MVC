package com.example.appointmentsystem.controllers.defaultController;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class defaultClss {

    @GetMapping("/default")
   public String defaultAfterLogin(Authentication authentication) {
    for (GrantedAuthority authority : authentication.getAuthorities()) {
        String role = authority.getAuthority();
        if (role.equals("ROLE_admin")) {
            return "admin";
        } else if (role.equals("ROLE_user")) {
            return "user";
        } else if (role.equals("ROLE_service_provider")) {
            return "service_provider";
        }
    }
    return "login";
}

}

      
      /*
        String role=authentication.getAuthorities().iterator().next().getAuthority();
      switch (role) {
         case "ROLE_user":
            return "redirect:/api/user/appointment";
         case "ROLE_admin":
          return "redirect:api/services";
      case "ROLE_service_provider":
          return "redirect:api/services";
         default:
            return "redirect:/";
      }
       */
     
     

