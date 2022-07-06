package web.spring.contextAttrPrinter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/spring/beans")
@RequiredArgsConstructor
public class SpringController {
    private transient final ApplicationContext applicationContext;

    @GetMapping
    public Collection<? extends String> getListOfServletContextAttributes() {
        return List.of(applicationContext.getBeanDefinitionNames());
    }

    @GetMapping("/{name}")
    public String getServletContextAttributeByName(@PathVariable("name") String attrName) {
        return applicationContext.getBean(attrName).toString();
    }
}
