package web.spring.contextAttrPrinter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/servlet/attributes")
@RequiredArgsConstructor
public class ServletController {

    private transient final ServletContext servletContext;

    @GetMapping
    public Collection<? extends String> getListOfServletContextAttributes() {
        var iterator = servletContext.getAttributeNames().asIterator();
        List<String> attributeList = new ArrayList<>();
        iterator.forEachRemaining(attributeList::add);
        return attributeList;
    }

    @GetMapping("/{name}")
    public String getServletContextAttributeByName(@PathVariable("name") String attrName) {
        return servletContext.getAttribute(attrName).toString();
    }
}
