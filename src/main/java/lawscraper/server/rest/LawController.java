package lawscraper.server.rest;

import lawscraper.server.entities.law.Law;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/18/12
 * Time: 5:50 PM
 */
@EnableWebMvc
@Controller
public class LawController {

    @RequestMapping(value = "foo",method = RequestMethod.GET )
    @ResponseBody
    public final List<Law> getAll(){
        return new ArrayList<Law>();
    }

}
