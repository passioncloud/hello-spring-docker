package net.passioncloud.hello.controller;


import net.passioncloud.hello.model.Member;
import net.passioncloud.hello.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/hello")
public class MainController {
    @Autowired // means to get the bean automatically
    private MemberRepository memberRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewMember(@RequestParam String firstName,
                                             @RequestParam String lastName,
                                             @RequestParam String email) {
        Member m = new Member();
        m.setEmail(email);
        m.setFirstName(firstName);
        m.setLastName(lastName);
        memberRepository.save(m);
        return "Saved successfully";
    }

    @GetMapping(path="/all")
    @ResponseBody public Iterable<Member> getAllMembers() {
        return memberRepository.findAll();
    }

}
