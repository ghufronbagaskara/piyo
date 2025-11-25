package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight

data class DonutSegment(val color: Color, val percent: Float, val label: String)

@Composable
fun ProgressDonutChart(
    modifier: Modifier = Modifier,
    segments: List<DonutSegment>,
    sizeDp: Dp = 220.dp,
    strokeWidth: Dp = 36.dp,
    title: String? = null,
    subtitle: String? = null
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        // optional header inside the chart area (useful when the chart is placed inside a Card)
        if (!title.isNullOrEmpty()) {
            Text(
                text = title,
                fontFamily = com.example.piyo.ui.theme.Sora,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }

        if (!subtitle.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = subtitle,
                fontFamily = com.example.piyo.ui.theme.Sora,
                fontSize = 14.sp,
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }

    // ensure donut arcs don't visually overlap the title/subtitle by adding
    // top spacing that's at least the stroke width
    Spacer(modifier = Modifier.height(strokeWidth + 12.dp))

    val strokePx = with(LocalDensity.current) { strokeWidth.toPx() }

        // Center the donut
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.size(sizeDp)) {
                val total = 100f
                val radius = (size.minDimension - strokePx) / 2f
                var startAngle = -90f

                segments.forEach { seg ->
                    val sweep = (seg.percent / total) * 360f
                    drawArc(
                        color = seg.color,
                        startAngle = startAngle,
                        sweepAngle = sweep,
                        useCenter = false,
                        style = Stroke(width = strokePx, cap = StrokeCap.Butt)
                    )
                    startAngle += sweep
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Legend rows below the chart
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)) {
            segments.forEach { seg ->
                LegendItemRow(color = seg.color, label = seg.label, percent = "${seg.percent.toInt()}%")
            }
        }
    }
}
// Note: original API used `sizeDp` as the parameter name. Callers should use that
// name or the positional argument. If you prefer `size = ...` in call sites,
// change them to `sizeDp = ...` or call positionally.

@Composable
private fun LegendItemRow(color: Color, label: String, percent: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(color = color)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = label,
            fontFamily = com.example.piyo.ui.theme.Sora,
            fontSize = 16.sp,
            color = Color(0xFF374151),
            modifier = Modifier.weight(1f)
        )

        Text(
            text = percent,
            fontFamily = com.example.piyo.ui.theme.Sora,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }
}
