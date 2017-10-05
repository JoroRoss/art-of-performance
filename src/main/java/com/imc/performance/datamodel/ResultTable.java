package com.imc.performance.datamodel;

import com.google.common.collect.ForwardingTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Tables.newCustomTable;

public class ResultTable extends ForwardingTable<Option, Result, Double> {

    private Table<Option, Result, Double> delegate =
            newCustomTable(newHashMap(), Maps::newHashMap);

    @Override
    protected Table<Option, Result, Double> delegate() {
        return delegate;
    }
}
