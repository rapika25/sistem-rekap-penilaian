package com.be.app.impl;

import com.be.app.dto.request.MahasiswaRequest;
import com.be.app.dto.request.MataKuliahNilaiRequest;
import com.be.app.dto.response.BaseResponse;
import com.be.app.entity.MahasiswaEntity;
import com.be.app.entity.MataKuliahNilaiEntity;
import com.be.app.repository.MahasiswaRepository;
import com.be.app.repository.MataKuliahNilaiRepository;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class MahasiswaServiceImpl implements MahasiswaService {

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private MataKuliahNilaiRepository mataKuliahNilaiRepository;

    @Override
    public BaseResponse saveMahasiswa(MahasiswaRequest mahasiswaRequest) {
        try {

            MahasiswaEntity newMahasiswa = new MahasiswaEntity();
            newMahasiswa.setUuid(UUID.randomUUID().toString());
            newMahasiswa.setNim(mahasiswaRequest.getNim());
            newMahasiswa.setNamaMahasiswa(mahasiswaRequest.getNamaMahasiswa());

            Timestamp dateNow = DateHelper.getTimestampNow();

            newMahasiswa.setCreatedAt(dateNow);
            newMahasiswa.setModifiedAt(dateNow);

            MahasiswaEntity listNewMahasiswa = mahasiswaRepository.save(newMahasiswa);

            for (MataKuliahNilaiRequest item : mahasiswaRequest.getListNilai()) {
                MataKuliahNilaiEntity nilai = new MataKuliahNilaiEntity();
                nilai.setUuid(UUID.randomUUID().toString());
                nilai.setMahasiswaID(listNewMahasiswa.getId());
                nilai.setMataKuliahID(item.getMataKuliahID());
                nilai.setNilai(item.getNilai());
                nilai.setCreatedAt(dateNow);
                nilai.setModifiedAt(dateNow);

                mataKuliahNilaiRepository.save(nilai);
            }

            return new BaseResponse(true, ResponseMessagesConst.INSERT_SUCCESS.toString(), listNewMahasiswa);
        } catch (Exception e) {
            return InternalServerErrorHandler.InternalServerError(e);
        }
    }

    @Override
    public BaseResponse updateMahasiswaByUUID(String id, MahasiswaRequest mahasiswaRequest) {
        try {
            MahasiswaEntity existingMahasiswa = mahasiswaRepository.findByUUID(id);
            if (existingMahasiswa == null) {
                return new BaseResponse(false, "Mahasiswa not found", null);
            }

            existingMahasiswa.setNim(mahasiswaRequest.getNim());
            existingMahasiswa.setNamaMahasiswa(mahasiswaRequest.getNamaMahasiswa());
            existingMahasiswa.setModifiedAt(DateHelper.getTimestampNow());

            MahasiswaEntity updatedMahasiswa = mahasiswaRepository.save(existingMahasiswa);

            mataKuliahNilaiRepository.deleteByMahasiswaID(updatedMahasiswa.getId());

            for (MataKuliahNilaiRequest item : mahasiswaRequest.getListNilai()) {
                MataKuliahNilaiEntity nilai = new MataKuliahNilaiEntity();
                nilai.setUuid(UUID.randomUUID().toString());
                nilai.setMahasiswaID(updatedMahasiswa.getId());
                nilai.setMataKuliahID(item.getMataKuliahID());
                nilai.setNilai(item.getNilai());
                nilai.setCreatedAt(DateHelper.getTimestampNow());
                nilai.setModifiedAt(DateHelper.getTimestampNow());

                mataKuliahNilaiRepository.save(nilai);
            }

            return new BaseResponse(true, ResponseMessagesConst.UPDATE_SUCCESS.toString(), updatedMahasiswa);
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

            mataKuliahNilaiRepository.deleteByMahasiswaID(oldMahasiswa.getId());

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
