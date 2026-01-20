package com.jusoft.bookingengine.config;

import com.jusoft.bookingengine.holder.DataHolder;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataTableTypeConverters {

    @DataTableType
    public List<Long> userIds(DataTable dataTable) {
        return dataTable.asList().stream().map(Long::parseLong).collect(Collectors.toList());
    }

    @DataTableType
    public DataHolder.ReservedSlotsOfDayHolder reservedSlotsOfDay(Map<String, String> entry) {
        return new DataHolder.ReservedSlotsOfDayHolder(
                DayOfWeek.valueOf(entry.get("dayOfWeek").toUpperCase()),
                entry.get("slotsStartTime"),
                entry.get("zoneId"));
    }

    @ParameterType("MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY|SUNDAY")
    public DayOfWeek dayOfWeek(String day) {
        return DayOfWeek.valueOf(day);
    }

    @DataTableType
    public List<DayOfWeek> dayOfWeek(DataTable dataTable) {
        return dataTable.asList().stream()
                .map(dayOfWeek -> DayOfWeek.valueOf(dayOfWeek.toUpperCase()))
                .collect(Collectors.toList());
    }
}
