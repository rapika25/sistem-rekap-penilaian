package com.be.app.controller;

import com.be.app.dto.request.MahasiswaRequest;
import com.be.app.dto.response.BaseResponse;
import com.be.app.impl.MahasiswaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/mahasiswa")
public class MahasiswaController {

    @Autowired
    private MahasiswaServiceImpl mahasiswaService;

    @PostMapping
    private BaseResponse saveMahasiswa(@RequestBody MahasiswaRequest request) {
        return mahasiswaService.saveMahasiswa(request);
    }

    @PutMapping("/{uuid}")
    private BaseResponse updateMahasiswa(@PathVariable("uuid") String uuid, @RequestBody MahasiswaRequest request) {
        return mahasiswaService.updateMahasiswaByUUID(uuid, request);
    }

    @DeleteMapping(value = "/{uuid}")
    private BaseResponse deleteMahasiswaByUUID(@PathVariable("uuid") String uuid) {
        return mahasiswaService.deleteMahasiswaByUUID(uuid);
    }

    @GetMapping
    private BaseResponse getMahasiswa(@RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "limit", required = false, defaultValue = "0") int limit, @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        return mahasiswaService.getMahasiswa(page, limit, search);
    }

    @GetMapping(value = "/{uuid}")
    private BaseResponse getMahasiswaByUUID(@PathVariable("uuid") String uuid) {
        return mahasiswaService.getMahasiswaByUUID(uuid);
    }

}
