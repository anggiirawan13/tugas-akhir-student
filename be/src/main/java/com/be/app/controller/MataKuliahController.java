package com.be.app.controller;

import com.be.app.dto.request.MataKuliahInsertRequest;
import com.be.app.dto.request.MataKuliahUpdateRequest;
import com.be.app.dto.response.BaseResponse;
import com.be.app.impl.MataKuliahServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/matakul")
public class MataKuliahController {

    @Autowired
    private MataKuliahServiceImpl categoryService;

    @PostMapping
    private BaseResponse saveMataKuliah(@RequestBody MataKuliahInsertRequest request) {
        return categoryService.saveMataKuliah(request);
    }

    @PutMapping(value = "/{uuid}")
    private BaseResponse updateMataKuliah(@PathVariable("uuid") String uuid, @RequestBody MataKuliahUpdateRequest request) {
        return categoryService.updateMataKuliahByUUID(uuid, request);
    }

    @DeleteMapping(value = "/{uuid}")
    private BaseResponse deleteMataKuliahByUUID(@PathVariable("uuid") String uuid) {
        return categoryService.deleteMataKuliahByUUID(uuid);
    }

    @GetMapping
    private BaseResponse getMataKuliah(@RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "limit", required = false, defaultValue = "0") int limit) {
        return categoryService.getMataKuliah(page, limit);
    }

    @GetMapping(value = "/{uuid}")
    private BaseResponse getMataKuliahByUUID(@PathVariable("uuid") String uuid) {
        return categoryService.getMataKuliahByUUID(uuid);
    }

}
