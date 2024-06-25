package com.be.app.service;

import com.be.app.dto.request.MataKuliahInsertRequest;
import com.be.app.dto.request.MataKuliahUpdateRequest;
import com.be.app.dto.response.BaseResponse;

public interface MataKuliahService {

    BaseResponse saveMataKuliah(MataKuliahInsertRequest request);

    BaseResponse updateMataKuliahByUUID(String uuid, MataKuliahUpdateRequest request);

    BaseResponse deleteMataKuliahByUUID(String uuid);

    BaseResponse getMataKuliah(int page, int limit);

    BaseResponse getMataKuliahByUUID(String uuid);

}
