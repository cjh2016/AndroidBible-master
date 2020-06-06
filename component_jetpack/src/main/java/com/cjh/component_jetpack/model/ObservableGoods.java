package com.cjh.component_jetpack.model;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableFloat;

import io.reactivex.internal.operators.observable.ObservableFilter;

/**
 * @author: caijianhui
 * @date: 2020/5/18 11:17
 * @description:
 */
public class ObservableGoods {

    private ObservableField<String> name;

    private ObservableFloat price;

    private ObservableField<String> details;

    public ObservableGoods() {
    }


    public ObservableGoods(ObservableField<String> name, ObservableFloat price,
                           ObservableField<String> details) {
        this.name = name;
        this.price = price;
        this.details = details;
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(ObservableField<String> name) {
        this.name = name;
    }

    public ObservableFloat getPrice() {
        return price;
    }

    public void setPrice(ObservableFloat price) {
        this.price = price;
    }

    public ObservableField<String> getDetails() {
        return details;
    }

    public void setDetails(ObservableField<String> details) {
        this.details = details;
    }
}
