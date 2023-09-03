package org.saigon4paws.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    @GetMapping("/layout")
    public String managerPage() {
        return "manager/layout";
    }

    @GetMapping({"", "/"})
    public String managerIndex() {
        return "manager/index";
    }
}
