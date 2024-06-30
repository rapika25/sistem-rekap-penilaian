package com.be.app.service;

import com.be.app.dto.request.MahasiswaRequest;
import com.be.app.dto.response.BaseResponse;

public interface MahasiswaService {

    BaseResponse saveMahasiswa(MahasiswaRequest request);

    BaseResponse updateMahasiswaByUUID(String uuid, MahasiswaRequest request);

    BaseResponse deleteMahasiswaByUUID(String uuid);

    BaseResponse getMahasiswa(int page, int limit, String search);

    BaseResponse getMahasiswaByUUID(String uuid);

}
