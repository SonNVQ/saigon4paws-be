package org.saigon4paws.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class GuestController {
    @RequestMapping({"", "/"})
    public String guestIndex() {
        return "guest/layout";
    }
}
