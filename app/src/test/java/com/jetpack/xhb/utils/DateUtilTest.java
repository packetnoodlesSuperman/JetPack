package com.jetpack.xhb.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class DateUtilTest {

    private String date;

    public DateUtilTest(String date) {
        this.date = date;
    }

    @Parameterized.Parameters
    public static Collection putParameters(){
        return Arrays.asList("2019/2/27 15:19:53", "2019/2/27 15:19:53", "2019/2/27 15:19:53");
    }

    @Test
    public void dateToStamps() throws ParseException {
        assertEquals(1551251993000L, DateUtil.dateToStamp(date));
    }


    @Test
    public void dateToStamp() throws ParseException {

        assertEquals(1551251993000L, DateUtil.dateToStamp("2019/2/27 15:19:53"));
    }
}