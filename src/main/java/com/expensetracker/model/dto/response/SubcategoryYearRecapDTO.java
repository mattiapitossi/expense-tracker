package com.expensetracker.model.dto.response;

import com.expensetracker.annotations.MonthMapping;
import com.expensetracker.model.Subcategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;

@NoArgsConstructor
@Getter
public class SubcategoryYearRecapDTO {

    private Subcategory subcategory;
    private BigDecimal January;
    private BigDecimal February;
    private BigDecimal March;
    private BigDecimal April;
    private BigDecimal May;
    private BigDecimal June;
    private BigDecimal July;
    private BigDecimal August;
    private BigDecimal September;
    private BigDecimal October;
    private BigDecimal November;
    private BigDecimal December;

    public SubcategoryYearRecapDTO(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    @MonthMapping(key = 1)
    private void setJanuary(BigDecimal january) {
        January = january;
    }


    @MonthMapping(key = 2)
    private void setFebruary(BigDecimal february) {
        February = february;
    }


    @MonthMapping(key = 3)
    private void setMarch(BigDecimal march) {
        March = march;
    }


    @MonthMapping(key = 4)
    private void setApril(BigDecimal april) {
        April = april;
    }


    @MonthMapping(key = 5)
    private void setMay(BigDecimal may) {
        May = may;
    }


    @MonthMapping(key = 6)
    private void setJune(BigDecimal june) {
        June = june;
    }


    @MonthMapping(key = 7)
    private void setJuly(BigDecimal july) {
        July = july;
    }


    @MonthMapping(key = 8)
    private void setAugust(BigDecimal august) {
        August = august;
    }


    @MonthMapping(key = 9)
    private void setSeptember(BigDecimal september) {
        September = september;
    }


    @MonthMapping(key = 10)
    private void setOctober(BigDecimal october) {
        October = october;
    }


    @MonthMapping(key = 11)
    private void setNovember(BigDecimal november) {
        November = november;
    }


    @MonthMapping(key = 12)
    private void setDecember(BigDecimal december) {
        December = december;
    }

    public void setMonth(int monthKey, BigDecimal monthValue) {
        Method setMethod = Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(MonthMapping.class) && monthKey == method.getAnnotation(MonthMapping.class).key())
                .findFirst()
                .get();

        try {
            setMethod.invoke(this, monthValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}

