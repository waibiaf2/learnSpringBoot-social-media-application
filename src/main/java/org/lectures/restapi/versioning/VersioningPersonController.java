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
    public PersonV1 getThirdVersionOfPersonRequestParameter() {
        return new PersonV1("Andrew Waibi");
    }

    @GetMapping(path = "/person", params = "version=2")
    public PersonV2 getSecondVersionOfPersonRequestParameter() {
        return new PersonV2("Andrew", "Waibi");
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
    public PersonV1 getFirstVersionOfPersonRequestHeader() {
        return new PersonV1("Andrew Waibi");
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
    public PersonV2 getSecondVersionOfPersonRequestHeader() {
        return new PersonV2("Andrew",  "Waibi");
    }

    @GetMapping(path = "/person/header", produces = "application/vnd.lectures.v1+json")
    public PersonV1 getFirstVersionOfAcceptHeader() {
        return new PersonV1("Andrew Waibi");
    }

    @GetMapping(path = "/person/header", produces = "application/vnd.lectures.v2+json")
    public PersonV2 getSecondVersionOfPesonAcceptHeader() {
        return new PersonV2("Andrew",  "Waibi");
    }
}
