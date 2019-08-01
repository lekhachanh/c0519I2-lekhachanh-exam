package com.codegym.repository;

import com.codegym.model.Receptionist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceptionistRepositoryImpl implements ReceptionistRepository<Receptionist> {
    private static Map<Integer, Receptionist> receptionistList;

     static {
        receptionistList = new HashMap<>();
        receptionistList.put(1, new Receptionist(1, "Phan Văn Đức", 21, "Yên Thành, Nghệ An", "play football"));
        receptionistList.put(2, new Receptionist(2, "Phan Đình Tùng", 31, "Q3, Tp. Hồ Chí Minh", "sing"));
        receptionistList.put(3, new Receptionist(3, "Hồ Quang Hiếu", 30, "Quảng Nam", "sing and dance"));
        receptionistList.put(4, new Receptionist(4, "Mai Phương Thúy", 32, "Cầu Giấy, Hà Nội", "Play volleyball"));
        receptionistList.put(5, new Receptionist(5, "Lại Văn Sâm", 45, "Khâm Thiên, Hà Nội", "Read book"));
        receptionistList.put(6, new Receptionist(6, "Leona messi", 36, "Barcelona, spain", "Play volleyball"));
    }

    @Override
    public List<Receptionist> findAll() {
        return new ArrayList<>(receptionistList.values());
    }

    @Override
    public void save(Receptionist receptionist) {
        receptionistList.put(receptionistList.size()+1, receptionist);
    }

    @Override
    public Receptionist findById(int id) {
        return receptionistList.get(id);
    }

    @Override
    public void update(int id, Receptionist receptionist) {
        receptionistList.put(id, receptionist);
    }

    @Override
    public void remove(int id) {
        receptionistList.remove(id);
    }
}
