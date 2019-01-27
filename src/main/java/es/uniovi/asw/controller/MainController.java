package es.uniovi.asw.controller;

import es.uniovi.asw.utils.LongProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.Random;

@Controller
public class MainController {

  private Random random = new Random();
  private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

  @RequestMapping("/")
  public ModelAndView landing(Model model) {
    return new ModelAndView("landing");
  }

  @RequestMapping("/sort")
  public ModelAndView sort() {
    int times = 500 + random.nextInt(500);
    int size = 1000 + random.nextInt(10000);
    long elapsed = LongProcess.sortStream(times,size,random);
    ModelAndView model = new ModelAndView("sort");
    model.addObject("size", size);
    model.addObject("times", times);
    model.addObject("elapsed", elapsed);
    return model;
  }

  @RequestMapping(path ="/search", method = RequestMethod.GET)
  public ModelAndView search(@RequestParam("name") String name) {
    LOG.info("Searching..." + name);
    long elapsed = 0;
    switch (name) {
        case "long": elapsed = LongProcess.sortStream(10000,10000, random);
        break;
        case "error":
            throw new RuntimeException("Name 'error' not found");
        default:
            elapsed = LongProcess.sortStream(random.nextInt(500),random.nextInt(10000), random);
    }
    ModelAndView model = new ModelAndView("search");
    model.addObject("name",name);
    model.addObject("elapsed",elapsed);
    return model;
  }
}