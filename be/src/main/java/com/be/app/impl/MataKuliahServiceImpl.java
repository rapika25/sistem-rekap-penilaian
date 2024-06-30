package com.be.app.impl;

import com.be.app.dto.request.MataKuliahRequest;
import com.be.app.dto.response.BaseResponse;
import com.be.app.entity.MataKuliahEntity;
import com.be.app.repository.MataKuliahRepository;
import com.be.app.service.MataKuliahService;
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
public class MataKuliahServiceImpl implements MataKuliahService {

    @Autowired
    private MataKuliahRepository mataKuliahRepository;

    @Override
    public BaseResponse saveMataKuliah(MataKuliahRequest request) {
        try {
            MataKuliahEntity newMataKuliah = new MataKuliahEntity();
            newMataKuliah.setUuid(UUID.randomUUID().toString());
            newMataKuliah.setKodeMataKuliah(request.getKodeMataKuliah());
            newMataKuliah.setNamaMataKuliah(request.getNamaMataKuliah());

            Timestamp dateNow = DateHelper.getTimestampNow();

            newMataKuliah.setCreatedAt(dateNow);
            newMataKuliah.setModifiedAt(dateNow);

            MataKuliahEntity mataKuliah = mataKuliahRepository.save(newMataKuliah);

            return new BaseResponse(true, ResponseMessagesConst.INSERT_SUCCESS.toString(), mataKuliah);
        } catch (Exception e) {
            return InternalServerErrorHandler.InternalServerError(e);
        }
    }

    @Override
    public BaseResponse updateMataKuliahByUUID(String uuid, MataKuliahRequest request) {
        try {
            MataKuliahEntity oldMataKuliah = mataKuliahRepository.findByUUID(uuid);
            if (NullEmptyChecker.isNullOrEmpty(oldMataKuliah)) {
                return new BaseResponse(false, ResponseMessagesConst.DATA_NOT_FOUND.toString(), null);
            }

            MataKuliahEntity updateMataKuliah = mataKuliahRepository.findByUUID(uuid);
            updateMataKuliah.setKodeMataKuliah(request.getKodeMataKuliah());
            updateMataKuliah.setNamaMataKuliah(request.getNamaMataKuliah());

            Timestamp dateNow = DateHelper.getTimestampNow();

            updateMataKuliah.setModifiedAt(dateNow);

            MataKuliahEntity mataKuliah = mataKuliahRepository.save(updateMataKuliah);

            return new BaseResponse(true, ResponseMessagesConst.UPDATE_SUCCESS.toString(), mataKuliah);
        } catch (Exception e) {
            return InternalServerErrorHandler.InternalServerError(e);
        }
    }

    @Override
    public BaseResponse deleteMataKuliahByUUID(String uuid) {
        try {
            MataKuliahEntity oldMataKuliah = mataKuliahRepository.findByUUID(uuid);
            if (NullEmptyChecker.isNullOrEmpty(oldMataKuliah)) {
                return new BaseResponse(false, ResponseMessagesConst.DATA_NOT_FOUND.toString(), null);
            }

            mataKuliahRepository.delete(oldMataKuliah);

            return new BaseResponse(true, ResponseMessagesConst.UPDATE_SUCCESS.toString(), null);
        } catch (Exception e) {
            return InternalServerErrorHandler.InternalServerError(e);
        }
    }

    @Override
    public BaseResponse getMataKuliah(int page, int limit, String search) {
        try {
            List<MataKuliahEntity> listMataKuliah;
            HashMap<String, Object> addEntity = new HashMap<>();
            if (page < 0 || NullEmptyChecker.isNullOrEmpty(limit)) {
                listMataKuliah = mataKuliahRepository.findAll();
            } else if (NullEmptyChecker.isNullOrEmpty(search)) {
                Pageable pageable = PageRequest.of(page, limit);
                Page<MataKuliahEntity> pageMataKuliah = mataKuliahRepository.findAll(pageable);
                listMataKuliah = pageMataKuliah.toList();

                addEntity.put("totalPage", pageMataKuliah.getTotalPages());
                addEntity.put("totalData", pageMataKuliah.getTotalElements());
                addEntity.put("numberOfData", pageMataKuliah.getNumberOfElements());
                addEntity.put("number", pageMataKuliah.getNumber());
            } else {
                listMataKuliah = mataKuliahRepository.findByKodeOrNamaMataKuliah(search);
            }

            if (NullEmptyChecker.isNotNullOrEmpty(listMataKuliah)) {
                return new BaseResponse(true, ResponseMessagesConst.DATA_FOUND.toString(), listMataKuliah, addEntity);
            }

            return new BaseResponse(false, ResponseMessagesConst.DATA_NOT_FOUND.toString());
        } catch (Exception e) {
            return InternalServerErrorHandler.InternalServerError(e);
        }
    }

    @Override
    public BaseResponse getMataKuliahByUUID(String uuid) {
        try {
            MataKuliahEntity listMataKuliah = mataKuliahRepository.findByUUID(uuid);

            if (NullEmptyChecker.isNotNullOrEmpty(listMataKuliah)) {
                return new BaseResponse(true, ResponseMessagesConst.DATA_FOUND.toString(), listMataKuliah);
            }

            return new BaseResponse(false, ResponseMessagesConst.DATA_NOT_FOUND.toString());
        } catch (Exception e) {
            return InternalServerErrorHandler.InternalServerError(e);
        }
    }
}
