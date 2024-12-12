package org.lectures.restapi.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

    @GetMapping("/v1/person")
    public PersonV1 getFirstVersionOfPerson() {
        return new PersonV1("Andrew");
    }

    @GetMapping("/v2/person")
    public PersonV2 getSecondVersionOfPeson() {
        return new PersonV2("Andrew", "Waibi");
    }
}
