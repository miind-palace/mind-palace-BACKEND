package com.mindpalace.MP_Backend.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindpalace.MP_Backend.LocalDateTimeSerializer;
import com.mindpalace.MP_Backend.dto.PostDTO;
import com.mindpalace.MP_Backend.service.CloudinaryService;
import com.mindpalace.MP_Backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final CloudinaryService cloudinaryService;
    //게시글 등록페이지 호출
    @GetMapping("/save")
    public String saveForm(){
        return "post/save";
    }

    //게시글 등록
    @PostMapping("/save")
    public String save(@ModelAttribute PostDTO postDTO, @RequestParam("file") MultipartFile file){
        System.out.println(postDTO);
        if (!file.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(file);
            postDTO.setBackgroundImage(imageUrl);
        }
        System.out.println("postDTO = " + postDTO);
        postService.save(postDTO);
        return "index";
    }

    //게시글 목록조회
    @GetMapping("/list")
    public String findAll(Model model) {
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<PostDTO> postDTOList = postService.findAll();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        String json = gson.toJson(postDTOList);
        model.addAttribute("memoryList", json);
        return "post/list";
    }

    //게시글 상세조회
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable){//경로상 있는 값 가져올 땐 pathVariable
        PostDTO postDTO = postService.findById(id);
        model.addAttribute("post", postDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "post/detail";
    }

    //게시글 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        postService.delete(id);
        return "redirect:/post/list";
    }

    @GetMapping("/mypage")
    public String findByMemberId(@RequestParam Long memberId, Model model){
        List<PostDTO> postDTOList = postService.findByMemberId(memberId);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        String json = gson.toJson(postDTOList);
        model.addAttribute("memoryList", json);
        return "post/mypage";
    }

    @GetMapping("/paging")
    public String paging(@PageableDefault(page=1) Pageable pageable, Model model){
        //pageable.getPageNumber();
        Page<PostDTO> postList = postService.paging(pageable);
        int blockLimit = 3; // 밑에 보여지는 페이지 개수
        //1, 4, 7, 10 ~~
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        //3, 6, 9, 12, <총 페이지가 8개라면 7, 8로 끝나야 함. 삼항연산자 사용
        int endPage = ((startPage + blockLimit - 1) < postList.getTotalPages()) ? startPage + blockLimit - 1 : postList.getTotalPages();

        model.addAttribute("postList", postList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "post/paging";
    }
}
