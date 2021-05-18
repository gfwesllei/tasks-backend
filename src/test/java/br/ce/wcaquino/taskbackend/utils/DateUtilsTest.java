package br.ce.wcaquino.taskbackend.utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class DateUtilsTest {

    @Test
    public void shouldReturnTrueOnFutureDate() {
        LocalDate dateFuture = LocalDate.now().plusMonths(1);
        Assert.assertTrue(DateUtils.isEqualOrFutureDate(dateFuture));
    }

    @Test
    public void shouldReturnFalseOnPasteDate() {
        LocalDate dateFuture = LocalDate.now().plusMonths(-1);
        Assert.assertFalse(DateUtils.isEqualOrFutureDate(dateFuture));
    }

    @Test
    public void shouldReturnTrueOnPresentDate() {
        LocalDate dateFuture = LocalDate.now();
        Assert.assertTrue(DateUtils.isEqualOrFutureDate(dateFuture));
    }
}