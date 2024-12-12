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


    @GetMapping(path = "/person", params = "version=1")
    public PersonV1 getThirdVersionOfPesonRequestParameter() {
        return new PersonV1("Andrew Waibi");
    }

    @GetMapping(path = "/person", params = "version=2")
    public PersonV2 getSecondVersionOfPesonRequestParameter() {
        return new PersonV2("Andrew", "Waibi");
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
    public PersonV1 getFirstVersionOfPesonRequestHeader() {
        return new PersonV1("Andrew Waibi");
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
    public PersonV2 getSecondVersionOfPesonRequestHeader() {
        return new PersonV2("Andrew",  "Waibi");

    }
}
