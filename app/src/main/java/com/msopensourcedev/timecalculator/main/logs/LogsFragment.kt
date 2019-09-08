package com.msopensourcedev.timecalculator.main.logs

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.snackbar.Snackbar
import com.msopensourcedev.timecalculator.R
import kotlinx.android.synthetic.main.fragment_logs.*

class LogsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sort_type_spinner.setItems("Daily", "Weekly", "Monthly")
        sort_type_spinner.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(
                view,
                "Clicked $item",
                Snackbar.LENGTH_LONG
            ).show()
        }

        pie_chart.setUsePercentValues(true)
        pie_chart.description.isEnabled = false
        //menja poziciju grafika unutar elementa
        pie_chart.setExtraOffsets(5F, 5F, 5F, 5F)

        //menja kako se krug vrti, sto veci koeficijent, to manja frikcija
        pie_chart.dragDecelerationFrictionCoef = 0.5F

        //dozvoljava crtanje kruga, bez njega izgleda kao obican piechart
        pie_chart.isDrawHoleEnabled = true

        //odredjuje boju sredisnjeg kruga
        pie_chart.setHoleColor(context!!.resources.getColor(R.color.color_white)
        )
        //menja debljinu belog kruga
        pie_chart.transparentCircleRadius = 55F
        pie_chart.setTransparentCircleAlpha(110)

        // enable rotation of the pie_chart by touch
        pie_chart.rotationAngle = 0f
        pie_chart.setTouchEnabled(false)

        pie_chart.setDrawCenterText(true)

        val yValues = ArrayList<PieEntry>()

        yValues.add(PieEntry(7F, "Efficient"))
        yValues.add(PieEntry(4F, "Non efficient"))
        yValues.add(PieEntry(4F, "Gray era"))
        yValues.add(PieEntry(9F, "Sleep"))

        //podaci i pripadajuce boje svakom redu podataka
        val dataSet = PieDataSet(yValues, "")
        dataSet.sliceSpace = 2F
        dataSet.selectionShift = 5F
        val green = context!!.resources.getColor(R.color.color_green)
        val orange = context!!.resources.getColor(R.color.color_orange)
        val gray = context!!.resources.getColor(R.color.color_medium_gray)
        val blue = context!!.resources.getColor(R.color.color_background_dark)
        dataSet.setColors(green, orange, gray, blue)

        val data = PieData(dataSet)
        data.setValueTextSize(10F)
        data.setValueTextColor(R.color.color_yellow)

        //animira grafik kada fragment dobije fokus
        pie_chart.animateY(1400, Easing.EaseInOutQuad)

        // setuje font, boju i velicinu
        pie_chart.setEntryLabelColor(Color.WHITE)
        pie_chart.setEntryLabelTypeface(
            Typeface.createFromAsset(
                context!!.assets,
                "OpenSans-Regular.ttf"
            )
        )
        pie_chart.setEntryLabelTextSize(12f)

        val l = pie_chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 10f
        l.yOffset = 4f

        pie_chart.data = data
    }
}
