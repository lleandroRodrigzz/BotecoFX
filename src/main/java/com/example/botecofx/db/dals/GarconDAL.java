package com.example.botecofx.db.dals;

import com.example.botecofx.db.entidades.Garcon;
import com.example.botecofx.db.util.IDAL;

import java.util.List;

public class GarconDAL implements IDAL<Garcon> {
    @Override
    public boolean gravar(Garcon entidade) {
        return false;
    }

    @Override
    public boolean alterar(Garcon entidade) {
        return false;
    }

    @Override
    public boolean apagar(Garcon entidade) {
        return false;
    }

    public Garcon get(int garId) {
        return new Garcon();
    }

    @Override
    public List<Garcon> get(String filtro) {
        return null;
    }
}
