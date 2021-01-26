package com.github.alexanderfefelov.bgbilling.dynservice.demo;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface HelloWorldPolyglot {

    List<String> hello();

}
