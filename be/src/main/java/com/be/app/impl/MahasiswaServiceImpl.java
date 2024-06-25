package com.be.app.impl;

import com.be.app.dto.request.MahasiswaInsertRequest;
import com.be.app.dto.request.MahasiswaUpdateRequest;
import com.be.app.dto.response.BaseResponse;
import com.be.app.entity.MahasiswaEntity;
import com.be.app.repository.MahasiswaRepository;
import com.be.app.service.MahasiswaService;
import com.be.constanta.ResponseMessagesConst;
import com.be.handler.InternalServerErrorHandler;
import com.be.helper.DateHelper;
import com.be.helper.NullEmptyChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class MahasiswaServiceImpl implements MahasiswaService {

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Override
    public BaseResponse saveMahasiswa(MahasiswaInsertRequest mahasiswaRequest) {
        try {

            MahasiswaEntity newMahasiswa = new MahasiswaEntity();
            newMahasiswa.setUuid(UUID.randomUUID().toString());
            newMahasiswa.setNim(mahasiswaRequest.getNim());
            newMahasiswa.setNamaMahasiswa(mahasiswaRequest.getNamaMahasiswa());

            Timestamp dateNow = DateHelper.getTimestampNow();

            newMahasiswa.setCreatedAt(dateNow);
            newMahasiswa.setModifiedAt(dateNow);

            MahasiswaEntity listNewMahasiswa = mahasiswaRepository.save(newMahasiswa);

            return new BaseResponse(true, ResponseMessagesConst.INSERT_SUCCESS.toString(), listNewMahasiswa);
        } catch (Exception e) {
            return InternalServerErrorHandler.InternalServerError(e);
        }
    }

    @Override
    public BaseResponse updateMahasiswaByUUID(String uuid, MahasiswaUpdateRequest mahasiswaRequest) {
        try {
            MahasiswaEntity oldMahasiswa = mahasiswaRepository.findByUUID(uuid);
            if (NullEmptyChecker.isNullOrEmpty(oldMahasiswa)) {
                return new BaseResponse(false, ResponseMessagesConst.DATA_NOT_FOUND.toString(), null);
            }

            MahasiswaEntity updateMahasiswa = mahasiswaRepository.findByUUID(uuid);
            updateMahasiswa.setNim(mahasiswaRequest.getNim());
            updateMahasiswa.setNamaMahasiswa(mahasiswaRequest.getNamaMahasiswa());


            Timestamp dateNow = DateHelper.getTimestampNow();

            updateMahasiswa.setModifiedAt(dateNow);

            MahasiswaEntity mahasiswa = mahasiswaRepository.save(updateMahasiswa);

            return new BaseResponse(true, ResponseMessagesConst.UPDATE_SUCCESS.toString(), mahasiswa);
        } catch (Exception e) {
            return InternalServerErrorHandler.InternalServerError(e);
        }
    }

    @Override
    public BaseResponse deleteMahasiswaByUUID(String uuid) {
        try {
            MahasiswaEntity oldMahasiswa = mahasiswaRepository.findByUUID(uuid);
            if (NullEmptyChecker.isNullOrEmpty(oldMahasiswa)) {
                return new BaseResponse(false, ResponseMessagesConst.DATA_NOT_FOUND.toString(), null);
            }

            mahasiswaRepository.delete(oldMahasiswa);

            return new BaseResponse(true, ResponseMessagesConst.UPDATE_SUCCESS.toString(), null);
        } catch (Exception e) {
            return InternalServerErrorHandler.InternalServerError(e);
        }
    }

    @Override
    public BaseResponse getMahasiswa(int page, int limit, String search) {
        try {
            List<MahasiswaEntity> listMahasiswa;
            HashMap<String, Object> addEntity = new HashMap<>();
            if (page < 0 || NullEmptyChecker.isNullOrEmpty(limit)) {
                listMahasiswa = mahasiswaRepository.findAll();
            } else if (NullEmptyChecker.isNullOrEmpty(search)) {
                Pageable pageable = PageRequest.of(page, limit);
                Page<MahasiswaEntity> pageMahasiswa = mahasiswaRepository.findAll(pageable);
                listMahasiswa = pageMahasiswa.toList();

                addEntity.put("totalPage", pageMahasiswa.getTotalPages());
                addEntity.put("totalData", pageMahasiswa.getTotalElements());
                addEntity.put("numberOfData", pageMahasiswa.getNumberOfElements());
                addEntity.put("number", pageMahasiswa.getNumber());
            } else {
                listMahasiswa = mahasiswaRepository.findByNimOrName(search);
            }

            if (NullEmptyChecker.isNotNullOrEmpty(listMahasiswa)) {
                return new BaseResponse(true, ResponseMessagesConst.DATA_FOUND.toString(), listMahasiswa, addEntity);
            }

            return new BaseResponse(false, ResponseMessagesConst.DATA_NOT_FOUND.toString());
        } catch (Exception e) {
            return InternalServerErrorHandler.InternalServerError(e);
        }
    }

    @Override
    public BaseResponse getMahasiswaByUUID(String uuid) {
        try {
            MahasiswaEntity listMahasiswa = mahasiswaRepository.findByUUID(uuid);

            if (NullEmptyChecker.isNotNullOrEmpty(listMahasiswa)) {
                return new BaseResponse(true, ResponseMessagesConst.DATA_FOUND.toString(), listMahasiswa);
            }

            return new BaseResponse(false, ResponseMessagesConst.DATA_NOT_FOUND.toString());
        } catch (Exception e) {
            return InternalServerErrorHandler.InternalServerError(e);
        }
    }
}
