package com.vokasi.canvasviewapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.colorpicker.ColorPickerDialog;
import com.github.dhaval2404.colorpicker.listener.ColorListener;
import com.github.dhaval2404.colorpicker.model.ColorShape;
import com.github.stephenvinouze.materialnumberpickercore.MaterialNumberPicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog numberPickerDialog;
    private FloatingActionButton fabPathColor, fabPathStrokeWidth;
    private MaterialNumberPicker numberPicker;
    private MyCanvasView myCanvasView;

    private int colorSelected = R.color.black;
    private int strokeWidthSelected = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        MyCanvasView myCanvasView;
//        myCanvasView=new MyCanvasView(this);
//        myCanvasView.setSystemUiVisibility(SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        myCanvasView = findViewById(R.id.my_canvas_view);

        fabPathColor = findViewById(R.id.fab_path_color);
        fabPathStrokeWidth = findViewById(R.id.fab_path_stroke_width);
        fabPathColor.setOnClickListener(this);
        fabPathStrokeWidth.setOnClickListener(this);

        numberPicker = new MaterialNumberPicker(this);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);

        AlertDialog.Builder numberPickerDialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Pick Path Stroke Width")
                .setView(numberPicker)
                .setNegativeButton(getString(android.R.string.cancel), null)
                .setPositiveButton(getString(android.R.string.ok), (dialogInterface, i) -> {
                    strokeWidthSelected = numberPicker.getValue();
                    myCanvasView.setPathStrokeWidth(numberPicker.getValue());
                    numberPicker.removeAllViews();
                });
        numberPickerDialog = numberPickerDialogBuilder.create();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.fab_path_color){
            showColorPickerDialog();
        } else if (id == R.id.fab_path_stroke_width) {
            showNumberPickerDialog();
        }
    }

    private void showColorPickerDialog() {
        new ColorPickerDialog.Builder(this)
                .setTitle("Pick Path Color")
                .setColorShape(ColorShape.SQAURE)
                .setDefaultColor(colorSelected)
                .setColorListener((ColorListener) (color, colorHex) -> {
                    colorSelected = color;
                    myCanvasView.setPathColor(color);
                    fabPathColor.setBackgroundColor(color);
                    fabPathStrokeWidth.setBackgroundColor(color);
                }).show();
    }

    private void showNumberPickerDialog() {
        numberPicker.setValue(strokeWidthSelected);
        numberPickerDialog.show();
    }
}