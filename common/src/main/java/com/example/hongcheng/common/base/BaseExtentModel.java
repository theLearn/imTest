package com.example.hongcheng.common.base;

/**
 * Created by hongcheng on 17/5/1.
 */

public class BaseExtentModel<T>
{
	private Boolean isChecked;
	private T model;

	public BaseExtentModel(Boolean isChecked, T model) {
		this.isChecked = isChecked;
		this.model = model;
	}

	public Boolean getChecked() {
		return isChecked;
	}

	public void setChecked(Boolean checked) {
		isChecked = checked;
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}
}
