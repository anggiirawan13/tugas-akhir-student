package com.be.app.service;

import com.be.app.dto.request.MahasiswaInsertRequest;
import com.be.app.dto.request.MahasiswaUpdateRequest;
import com.be.app.dto.response.BaseResponse;

public interface MahasiswaService {

    BaseResponse saveMahasiswa(MahasiswaInsertRequest request);

    BaseResponse updateMahasiswaByUUID(String uuid, MahasiswaUpdateRequest request);

    BaseResponse deleteMahasiswaByUUID(String uuid);

    BaseResponse getMahasiswa(int page, int limit, String search);

    BaseResponse getMahasiswaByUUID(String uuid);

}
