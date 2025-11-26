package com.example.piyo.presentation.home.beranda

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import com.example.piyo.ui.theme.Sora

data class DonutSegment(val color: Color, val percent: Float, val label: String)

@Composable
fun ProgressDonutChart(
    modifier: Modifier = Modifier,
    segments: List<DonutSegment>,
    sizeDp: Dp = 220.dp,
    strokeWidth: Dp = 36.dp,
    title: String? = null,
    subtitle: String? = null,
    onChartClick: (() -> Unit)? = null
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (!title.isNullOrEmpty()) Text(text = title, fontFamily = Sora, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
        if (!subtitle.isNullOrEmpty()) Text(text = subtitle, fontFamily = Sora, fontSize = 14.sp, color = Color.DarkGray)

        Spacer(modifier = Modifier.height(strokeWidth + 12.dp))

        val strokePx = with(LocalDensity.current) { strokeWidth.toPx() }

        val boxModifier = Modifier
            .size(sizeDp)
            .then(modifier)
            .let { if (onChartClick != null) it.clickable { onChartClick() } else it }

        Box(modifier = boxModifier, contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                var startAngle = -90f
                segments.forEach { seg ->
                    val sweep = seg.percent / 100f * 360f
                    drawArc(color = seg.color, startAngle = startAngle, sweepAngle = sweep, useCenter = false, style = Stroke(strokePx, cap = StrokeCap.Butt))
                    startAngle += sweep
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
            segments.forEach { seg ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(14.dp).background(seg.color, CircleShape))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = seg.label, fontFamily = Sora, fontSize = 16.sp, color = Color(0xFF374151), modifier = Modifier.weight(1f))
                    Text(text = "${seg.percent.toInt()}%", fontFamily = Sora, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                }
            }
        }
    }
}
