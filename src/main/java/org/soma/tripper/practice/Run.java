package org.soma.tripper.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/practice")
public class Run {
    @Autowired
    private MemberRepository mr;
    @Autowired
    private MemberRepository pr;

    @GetMapping("/getAll")
    public List<Member> getmember() {
        List<Member> list = mr.findAll();
       return list;
    }
}
