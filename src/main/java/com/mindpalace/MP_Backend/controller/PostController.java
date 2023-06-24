package com.mindpalace.MP_Backend.controller;

import com.mindpalace.MP_Backend.dto.PostDTO;
import com.mindpalace.MP_Backend.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    //게시글 등록페이지 호출
    @GetMapping("/save")
    public String saveForm(){
        return "post/save";
    }

    //게시글 등록
    @PostMapping("/save")
    public String save(@ModelAttribute PostDTO postDTO){
        System.out.println("postDTO = " + postDTO);
        postService.save(postDTO);
        return "index";
    }

    //게시글 목록조회
    @GetMapping("/list")
    public String findAll(Model model) {
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<PostDTO> boardDTOList = postService.findAll();
        model.addAttribute("postList", boardDTOList);
        return "post/list";
    }

    //게시글 상세조회
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){//경로상 있는 값 가져올 땐 pathVariable
        PostDTO postDTO = postService.findById(id);
        model.addAttribute("post", postDTO);
        return "post/detail";
    }

    //게시글 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        postService.delete(id);
        return "redirect:/post/list";
    }
}
