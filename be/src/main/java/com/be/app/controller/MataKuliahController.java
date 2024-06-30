package com.be.app.controller;

import com.be.app.dto.request.MataKuliahRequest;
import com.be.app.dto.response.BaseResponse;
import com.be.app.impl.MataKuliahServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/mata-kuliah")
public class MataKuliahController {

    @Autowired
    private MataKuliahServiceImpl mataKuliahService;

    @PostMapping
    private BaseResponse saveMataKuliah(@RequestBody MataKuliahRequest request) {
        return mataKuliahService.saveMataKuliah(request);
    }

    @PutMapping(value = "/{uuid}")
    private BaseResponse updateMataKuliah(@PathVariable("uuid") String uuid, @RequestBody MataKuliahRequest request) {
        return mataKuliahService.updateMataKuliahByUUID(uuid, request);
    }

    @DeleteMapping(value = "/{uuid}")
    private BaseResponse deleteMataKuliahByUUID(@PathVariable("uuid") String uuid) {
        return mataKuliahService.deleteMataKuliahByUUID(uuid);
    }

    @GetMapping
    private BaseResponse getMataKuliah(@RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "limit", required = false, defaultValue = "0") int limit, @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        return mataKuliahService.getMataKuliah(page, limit, search);
    }

    @GetMapping(value = "/{uuid}")
    private BaseResponse getMataKuliahByUUID(@PathVariable("uuid") String uuid) {
        return mataKuliahService.getMataKuliahByUUID(uuid);
    }

}
