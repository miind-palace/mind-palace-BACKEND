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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
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
        if (!file.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(file);
            postDTO.setBackgroundImage(imageUrl);
        }
        System.out.println("postDTO = " + postDTO);
        postService.save(postDTO);
        return "게시글 업로드 성공!";
    }

    //게시글 목록조회
    @GetMapping("/list")
    public List<PostDTO> findAll(Model model) {
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<PostDTO> postDTOList = postService.findAll();
        // model.addAttribute("memoryList", json);
        return postDTOList;
    }

    //게시글 상세조회
    @GetMapping("/postDetail")
    public String findById(@RequestParam Long postId
                          ){//경로상 있는 값 가져올 땐 pathVariable
        PostDTO postDTO = postService.findById(postId);
        if (postDTO == null) {
            return "해당 게시물은 없습니다!";
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        String json = gson.toJson(postDTO);

        return json;
    }

    @GetMapping("/findByMemberId")
    public List<PostDTO> findByMemberId(@RequestParam Long memberId, Model model){
        List<PostDTO> postDTOList = postService.findByMemberId(memberId);
        return postDTOList;
    }

    //게시글 삭제
    @GetMapping("/delete")
    public String delete(@RequestParam Long postId){
        postService.delete(postId);
        return "삭제 성공!";
    }

    //@GetMapping("/paging")
    public String paging(@PageableDefault(page=1) Pageable pageable, Model model){
        //pageable.getPageNumber();
        Page<PostDTO> postList = postService.paging(pageable);
        int blockLimit = 3; // 밑에 보여지는 페이지 개수
        //1, 4, 7, 10 ~~
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        //3, 6, 9, 12, <총 페이지가 8개라면 7, 8로 끝나야 함. 삼항연산자 사용
        int endPage = ((startPage + blockLimit - 1) < postList.getTotalPages()) ? startPage + blockLimit - 1 : postList.getTotalPages();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();


        model.addAttribute("postList", postList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        String json = gson.toJson(postList);
        return json;
    }

    @GetMapping("/page")
    public Page<PostDTO> findPageByMemberId(@PageableDefault(page=1) Pageable pageable, Model model, @RequestParam("memberId") Long memberId){
        Page<PostDTO> postList = postService.findPageByMemberId(pageable, memberId);
        return postList;
    }

    @GetMapping("/page/rand")
    public Page<PostDTO> randomizePosts(@PageableDefault(size=5) Pageable pageable, @RequestParam("memberId") Long memberId){
        Page<PostDTO> postList = postService.randomizePosts(pageable, memberId);
        return postList;
    }
}
