package com.leggo.cooperativa.domain.model.common;

import lombok.Data;

@Data
public class Year implements Comparable<Year>
{
    private final Integer year;

    public static Year of(Integer year)
    {
        return new Year(year);
    }

    @Override
    public int compareTo(Year o)
    {
        return year.compareTo(o.year);
    }
}
