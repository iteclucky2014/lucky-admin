package cn.sambo.difference.platform.gentools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "gen")
public class GenToolsController {

    @Autowired
    DomainGenerator domainGenerator;

    @Autowired
    RepoGenerator repoGeregenerator;

    @Autowired
    CtrlGenerator ctrlGenerator;

    @GetMapping("/all")
    public String all() throws Exception {
        domainGenerator.execute();
        repoGeregenerator.execute();
        ctrlGenerator.execute();
        return "OK";
    }

    @GetMapping("/domain")
    public String domain() throws Exception {
        domainGenerator.execute();
        return "OK";
    }

    @GetMapping("/repo")
    public String repo() throws Exception {
        repoGeregenerator.execute();
        return "OK";
    }

    @GetMapping("/ctrl")
    public String ctrl() throws Exception {
        ctrlGenerator.execute();
        return "OK";
    }
}
