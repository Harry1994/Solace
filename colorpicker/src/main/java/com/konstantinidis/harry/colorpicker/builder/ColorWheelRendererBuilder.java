package com.konstantinidis.harry.colorpicker.builder;


import com.konstantinidis.harry.colorpicker.ColorPickerView;
import com.konstantinidis.harry.colorpicker.renderer.ColorWheelRenderer;
import com.konstantinidis.harry.colorpicker.renderer.FlowerColorWheelRenderer;
import com.konstantinidis.harry.colorpicker.renderer.SimpleColorWheelRenderer;

public class ColorWheelRendererBuilder {
	public static ColorWheelRenderer getRenderer(ColorPickerView.WHEEL_TYPE wheelType) {
		switch (wheelType) {
			case CIRCLE:
				return new SimpleColorWheelRenderer();
			case FLOWER:
				return new FlowerColorWheelRenderer();
		}
		throw new IllegalArgumentException("wrong WHEEL_TYPE");
	}
}