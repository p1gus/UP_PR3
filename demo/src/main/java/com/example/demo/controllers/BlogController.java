package com.example.demo.controllers;

import com.example.demo.models.Chitateli;
import com.example.demo.models.Jornalisti;
import com.example.demo.repo.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repo.PostRepository;
import com.example.demo.repo.JornalRepository;
import com.example.demo.models.Post;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class BlogController  {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private JornalRepository jornalRepository;
    @Autowired
    private ReaderRepository chitRepository;


    @GetMapping("/")
    public String Main(Model model)
    {
        return "blog-main";
    }

    @GetMapping("/stati")
    public String blogMain(Model model)
    {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "stati";
    }

    @GetMapping("/jornalisti")
    public String blogJornalists(Model model)
    {
        Iterable<Jornalisti> jornalisti = jornalRepository.findAll();
        model.addAttribute("jornalisti", jornalisti);
        return "jornalisti";
    }

    @GetMapping("/chitateli")
    public String chitateli(Model model)
    {
        Iterable<Chitateli> chitateliIterable = chitRepository.findAll();
        model.addAttribute("chitateli", chitateliIterable);
        return "chitateli";
    }

   @GetMapping("/blog/add")
    public String blogAdd(Model model)
    {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam(defaultValue = "1999-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd")  Date datavihoda,
                              @RequestParam Double cenastat,
                              @RequestParam Float hours,
                              @RequestParam String full_text, Model model)
    {
        Post post = new Post(title, datavihoda, full_text, cenastat, hours);
        postRepository.save(post);
        return "redirect:/";
    }

    @GetMapping("/blog/filter")
    public String blogFilter(Model model)
    {
        return "blog-filter";
    }

    @PostMapping("/blog/filter/result")
    public String blogResult(@RequestParam String title, Model model)
    {
        List<Post> result = postRepository.findByTitleContains(title);
        model.addAttribute("result", result);
        return "blog-filter";
    }

    @GetMapping("/stati/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        if(!postRepository.existsById(id)){
            return "redirect:/stati";
        }
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable("id") long id, Model model)
    {
        if(!postRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);

        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable("id") long id,
                                 @RequestParam String title,
                                 @RequestParam(defaultValue = "1999-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd")  Date datavihoda,
                                 @RequestParam Double cenastat,
                                 @RequestParam Float hours,
                                 @RequestParam String full_text, Model model)
    {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setCenastat(cenastat);
        post.setHours(hours);
        post.setDatavihoda(datavihoda);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostRemove(@PathVariable("id") long id, Model model)
    {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/";
    }







    @GetMapping("/jornalisti/filter")
    public String jornalFilter(Model model)
    {
        return "jornal-filter";
    }

    @PostMapping("/jornalisti/filter/result")
    public String jornalResult(@RequestParam String fam, Model model)
    {
        List<Jornalisti> result = jornalRepository.findByFam(fam);
        model.addAttribute("result", result);
        return "jornal-filter";
    }
    @PostMapping("/jornalisti/filter/tochresult")
    public String jornalResultToch(@RequestParam String fam, Model model)
    {
        List<Jornalisti> tochresult = jornalRepository.findByFamContains(fam);
        model.addAttribute("tochresult", tochresult);
        return "jornal-filter";
    }
    @GetMapping("/jornalisti/add")
    public String jornalistiAdd(Model model)
    {
        return "jornal-add";
    }

    @PostMapping("/jornalisti/add")
    public Object jornalistiAdd(@RequestParam String name,
                                @RequestParam String fam,
                                @RequestParam Double cena,
                                @RequestParam Integer kolvastat,
                                @RequestParam(defaultValue = "1999-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date denroj, Model model)
    {
        Jornalisti jornalisti = new Jornalisti(name, fam,  kolvastat, denroj, cena);
        jornalRepository.save(jornalisti);
        return "redirect:/";
    }


    @GetMapping("/jornalisti/{id}")
    public String jornalDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Jornalisti> jornalisti = jornalRepository.findById(id);
        ArrayList<Jornalisti> res = new ArrayList<>();
        jornalisti.ifPresent(res::add);
        model.addAttribute("jornalisti", res);
//        if(!postRepository.existsById(id)){
//            return "redirect:/jornalisti";
//        }
        return "jornalisti-details";
    }

    @GetMapping("/jornalisti/{id}/edit")
    public String jornalEdit(@PathVariable("id") long id, Model model)
    {
//        if(!jornalRepository.existsById(id)){
//            return "redirect:/";
//        }
        Optional<Jornalisti> jornalisti = jornalRepository.findById(id);
        ArrayList<Jornalisti> res = new ArrayList<>();
        jornalisti.ifPresent(res::add);
        model.addAttribute("jornalisti", res);

        return "jornalisti-edit";
    }

    @PostMapping("/jornalisti/{id}/edit")
    public String jornalPostUpdate(@PathVariable("id") long id,
                                   @RequestParam String name,
                                   @RequestParam String fam,
                                   @RequestParam Double cena,
                                   @RequestParam Integer kolvastat,
                                   @RequestParam(defaultValue = "2022-02-02") @DateTimeFormat(pattern = "yyyy-MM-dd") Date denroj, Model model)
    {
        Jornalisti jornalisti = jornalRepository.findById(id).orElseThrow();
        jornalisti.setName(name);
        jornalisti.setFam(fam);
        jornalisti.setCena(cena);
        jornalisti.setKolvastat(kolvastat);
        jornalisti.setDenroj(denroj);
        jornalRepository.save(jornalisti);
        return "redirect:/";
    }

    @PostMapping("/jornalisti/{id}/remove")
    public String jornalPostRemove(@PathVariable("id") long id, Model model)
    {
        Jornalisti jornalisti = jornalRepository.findById(id).orElseThrow();
        jornalRepository.delete(jornalisti);
        return "redirect:/";
    }




    @GetMapping("/chitateli/filter")
    public String chitatelFilter(Model model)
    {
        return "chitateli-filter";
    }

    @PostMapping("/chitateli/filter/result")
    public String chitatelResult(@RequestParam String fam, Model model)
    {
        List<Chitateli> result = chitRepository.findByFam(fam);
        model.addAttribute("result", result);
        return "chitateli-filter";
    }
    @GetMapping("/chitateli/add")
    public String chitatelAdd(Model model)
    {
        return "chitateli-add";
    }

    @PostMapping("/chitateli/add")
    public Object chitatelAdd(@RequestParam String name,
                                @RequestParam String fam,
                                @RequestParam Double oklad,
                                @RequestParam Integer kolvostat,
                                @RequestParam(defaultValue = "1999-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date denroj, Model model)
    {
        Chitateli chitateli = new Chitateli(name, fam, kolvostat, denroj, oklad);
        chitRepository.save(chitateli);
        return "redirect:/";
    }




    @GetMapping("/chitateli/{id}")
    public String chitatelDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Chitateli> chitateli = chitRepository.findById(id);
        ArrayList<Chitateli> res = new ArrayList<>();
        chitateli.ifPresent(res::add);
        model.addAttribute("chitateli", res);
        if(!chitRepository.existsById(id)){
            return "redirect:/chitateli";
        }
        return "chitateli-details";
    }

    @GetMapping("/chitateli/{id}/edit")
    public String chitatelEdit(@PathVariable("id") long id, Model model)
    {
        if(!chitRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Chitateli> chitateli = chitRepository.findById(id);
        ArrayList<Chitateli> res = new ArrayList<>();
        chitateli.ifPresent(res::add);
        model.addAttribute("chitateli", res);

        return "chitateli-edit";
    }

    @PostMapping("/chitateli/{id}/edit")
    public String chitatelPostUpdate(@PathVariable("id") long id,
                                     @RequestParam String name,
                                     @RequestParam String fam,
                                     @RequestParam Double oklad,
                                     @RequestParam Integer kolvostat,
                                     @RequestParam(defaultValue = "1999-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date denroj, Model model)
    {
        Chitateli chitateli = chitRepository.findById(id).orElseThrow();
        chitateli.setName(name);
        chitateli.setFam(fam);
        chitateli.setOklad(oklad);
        chitateli.setKolvostat(kolvostat);
        chitateli.setDenroj(denroj);
        chitRepository.save(chitateli);
        return "redirect:/";
    }

    @PostMapping("/chitateli/{id}/remove")
    public String chitatelPostRemove(@PathVariable("id") long id, Model model)
    {
        Chitateli chitateli = chitRepository.findById(id).orElseThrow();
        chitRepository.delete(chitateli);
        return "redirect:/";
    }
}
