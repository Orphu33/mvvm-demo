package com.serengetitech.databindingbaseapp.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class SpinnerItem extends BaseObservable {

        private int selectedItemPosition;

        @Bindable
        public int getSelectedItemPosition() {
            return selectedItemPosition;
        }

        public void setSelectedItemPosition(int selectedItemPosition) {
            this.selectedItemPosition = selectedItemPosition;
        }

}
