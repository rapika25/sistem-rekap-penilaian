package com.be.app.service;

import com.be.app.dto.request.MataKuliahRequest;
import com.be.app.dto.response.BaseResponse;

public interface MataKuliahService {

    BaseResponse saveMataKuliah(MataKuliahRequest request);

    BaseResponse updateMataKuliahByUUID(String uuid, MataKuliahRequest request);

    BaseResponse deleteMataKuliahByUUID(String uuid);

    BaseResponse getMataKuliah(int page, int limit, String search);

    BaseResponse getMataKuliahByUUID(String uuid);

}
