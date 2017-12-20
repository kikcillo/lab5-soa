package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class SearchController {

    @Autowired
    private ProducerTemplate producerTemplate;

    @RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
        Map<String,Object> headers = new HashMap<>();

        if (q.matches(".+ max:[0-9]+")) {
            headers.put("CamelTwitterKeywords", q.substring(0, q.lastIndexOf("max:")));
            headers.put("CamelTwitterCount",q.substring(q.lastIndexOf("max:")+4));
        } else {
            headers.put("CamelTwitterKeywords",q);
        }
        return producerTemplate.requestBodyAndHeaders("direct:search","",headers);
    }

}